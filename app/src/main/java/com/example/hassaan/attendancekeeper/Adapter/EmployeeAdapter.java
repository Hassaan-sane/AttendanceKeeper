package com.example.hassaan.attendancekeeper.Adapter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassaan.attendancekeeper.API.ListAPI;
import com.example.hassaan.attendancekeeper.Activity.EmployeeActivity;
import com.example.hassaan.attendancekeeper.Activity.EmployeeDetailsActivity;
import com.example.hassaan.attendancekeeper.CSVUtils;
import com.example.hassaan.attendancekeeper.Event.EmployeeDetailEvent;
import com.example.hassaan.attendancekeeper.GMailSender;
import com.example.hassaan.attendancekeeper.Model.TblAdminxEmployeexAttendance;
import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> implements Filterable {
    private Context context;
    public static final String MY_PREFS_NAME = "AdminFile";
    private String emailKey = "email";
    final String email;
    private List<TblEmployee> EmployeeListReport = new ArrayList<>();
    private List<TblEmployee> EmployeeListReportFull = new ArrayList<>();
//    private NotificationManager mNotifyManager;
//    private NotificationCompat.Builder mBuilder;
//    Notification noti;
//    int nid = 1;


    public EmployeeAdapter(Context context, List<TblEmployee> employeeListReport) {
        this.context = context;
        EmployeeListReport = employeeListReport;
        EmployeeListReportFull = new ArrayList<>(EmployeeListReport);
        final SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        email = prefs.getString(emailKey, null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setList(List<TblEmployee> employeeListReport) {
        this.EmployeeListReport = employeeListReport;
        EmployeeListReportFull = new ArrayList<>(EmployeeListReport);
        notifyDataSetChanged();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_elist, tv_dateJoined_elist, tv_duration_elist;
        Button btn_report;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_elist = itemView.findViewById(R.id.tv_Name_elist);
            tv_dateJoined_elist = itemView.findViewById(R.id.tv_dateJoined_elist);
            btn_report = itemView.findViewById(R.id.btn_report);
//            tv_duration_elist = itemView.findViewById(R.id.tv_duration_elist);

        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_name_elist.setText(EmployeeListReport.get(position).getName().toUpperCase());
        holder.tv_dateJoined_elist.setText(EmployeeListReport.get(position).getCreatedAt());
        holder.btn_report.setText("Check\n" + "Report");

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                EventBus.getDefault().postSticky(new EmployeeDetailEvent(EmployeeListReport.get(position), position));
//                Intent intent = new Intent(context, EmployeeDetailsActivity.class);
//                context.startActivity(intent);
//            }
//        });

        holder.btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListAPI service = ListAPI.retrofit.create(ListAPI.class);
                Toast.makeText(context, "Please Wait...", Toast.LENGTH_SHORT).show();
                Call<List<TblAdminxEmployeexAttendance>> getReport = service.GetEmployeeReport(EmployeeListReport.get(position).getEmployeeId());
                getReport.enqueue(new Callback<List<TblAdminxEmployeexAttendance>>() {
                    @Override
                    public void onResponse(Call<List<TblAdminxEmployeexAttendance>> call, Response<List<TblAdminxEmployeexAttendance>> response) {
                        Log.i("TAG", "onResponse: " + response);
                        if (response.isSuccessful() && !response.body().isEmpty()) {

                            List<TblAdminxEmployeexAttendance> Report = response.body();

                            Toast.makeText(context, "Generating Report...", Toast.LENGTH_SHORT).show();

                            final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + EmployeeListReport.get(position).getName() + " Report.csv";
                            Log.i("TAG", "onClick: " + path);
                            try {
                                FileWriter writer = new FileWriter(path);

                                try {
//                                    Log.i("TAG", "onClick: " + Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Report.xlsx");
                                    CSVUtils.writeLine(writer, Arrays.asList("AttendanceId", "EmployeeId\t", "CheckIn\t", "CheckOut\t", "Duration"));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                for (TblAdminxEmployeexAttendance Data : Report) {

                                    List<String> list = new ArrayList<>();
                                    list.add(Data.getAttendanceId().toString());
                                    list.add(Data.getEmployeeId().toString());
                                    list.add(getDate(Data.getCheckIn()));
                                    list.add(getDate(Data.getCheckOut()));
                                    list.add(getDuration(Data.getTotalDuration()));

                                    try {
                                        CSVUtils.writeLine(writer, list);
                                    } catch (IOException e1) {
                                        e1.printStackTrace();
                                    }

                                    //try custom separator and quote.
                                    //CSVUtils.writeLine(writer, list, '|', '\"');
                                }

                                writer.flush();
                                writer.close();
                                new Thread(new Runnable() {

                                    public void run() {

                                        try {
                                            File file = new File(path);
//                                            GMailSender sender = new GMailSender(
//                                                    "hassaanbinsajjad03@gmail.com",
//                                                    "03314995782");
//                                            sender.addAttachment(path);
//
//                                            sender.sendMail("Report of " + EmployeeListReport.get(position).getName(), "This mail has been sent from android app ",
//                                                    "hassaanbinsajjad03@gmail.com",
//                                                    /*email*/ "hassaanbinsajjad@live.com");

                                            GMailSender sender = new GMailSender("hassaanbinsajjad03@gmail.com", "03314995782");
                                            sender.sendMail("Report of " + EmployeeListReport.get(position).getName(),
                                                    "This mail has been sent from android app ",
                                                    "hassaanbinsajjad03@gmail.com",
                                                    email,
                                                    file);
                                        } catch (Exception e) {
                                            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }).start();

//                                mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                                Intent intent = new Intent();
////                                intent.setAction(Intent.ACTION_VIEW);
//                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                File file = new File(path);
//                                Uri uri = Uri.parse(path);
//                                intent.setDataAndType(uri, "text/csv");
//                                PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
//                                mBuilder = new NotificationCompat.Builder(context);
//                                mBuilder.setContentTitle("Report")
//                                        .setContentText(EmployeeListReport.get(position).getName() + " Report.csv")
//                                        .setSmallIcon(R.drawable.notifications_icon)
//                                        .setContentIntent(pIntent);
//                                mNotifyManager.notify(nid, mBuilder.build());
//
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                        } else {
                            Toast.makeText(context, "No Full Record to Show", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TblAdminxEmployeexAttendance>> call, Throwable t) {

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return EmployeeListReport.size();
    }

    public static String getDate(String OldDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formater = new SimpleDateFormat(" HH:mm:ss dd MMM");
        Date dateCheckIn = null;
        String Date = null;
        try {
            dateCheckIn = formatter.parse(OldDate);
            Date = formater.format(dateCheckIn);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Date;

    }

    public static String getDuration(Long d) {

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }


    @Override
    public Filter getFilter() {
        return EmployeeReportFilter;
    }

    private Filter EmployeeReportFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<TblEmployee> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(EmployeeListReportFull);

            } else {
                String filterPattern = charSequence.toString().toLowerCase();
                for (TblEmployee item : EmployeeListReportFull) {
                    if (item.getName().toString().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    } else if (item.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }

                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            EmployeeListReport.clear();
            EmployeeListReport.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

}
