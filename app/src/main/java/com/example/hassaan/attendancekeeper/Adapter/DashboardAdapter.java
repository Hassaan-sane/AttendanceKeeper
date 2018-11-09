package com.example.hassaan.attendancekeeper.Adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.hassaan.attendancekeeper.Model.TblAdminxEmployeexAttendance;
import com.example.hassaan.attendancekeeper.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> implements Filterable {

    private List<TblAdminxEmployeexAttendance> EmployeeList = new ArrayList<>();
    private List<TblAdminxEmployeexAttendance> EmployeeListfull = new ArrayList<>();
    private Context context;

    long startTime, timeInMilliseconds = 0;
    Handler customHandler = new Handler();
    CountDownTimer timer;

    public DashboardAdapter(Context context, List<TblAdminxEmployeexAttendance> dummyList) {
        this.context = context;
        EmployeeList = dummyList;
        EmployeeListfull=  new ArrayList<>(EmployeeList);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setList(List<TblAdminxEmployeexAttendance> employeeList) {
        this.EmployeeList = employeeList;
        EmployeeListfull=  new ArrayList<>(EmployeeList);

        Log.i("TAG", "setList: " + EmployeeList.get(0).getName());
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_dblist, tv_checkInTime_dblist, tv_duration_dblist, tv_checkOutTime_dblist;

        Button btn_checkOut;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_dblist = itemView.findViewById(R.id.tv_Name_dblist);
            tv_checkInTime_dblist = itemView.findViewById(R.id.tv_checkInTime_dblist);
            tv_duration_dblist = itemView.findViewById(R.id.tv_duration_dblist);
//            btn_checkOut = itemView.findViewById(R.id.btn_checkOut);
//            tv_checkOutTime_dblist = itemView.findViewById(R.id.tv_checkOutTime_dblist);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        context = holder.tv_name_dblist.getContext();
        holder.tv_name_dblist.setText(EmployeeList.get(position).getName());
        holder.tv_checkInTime_dblist.setText(EmployeeList.get(position).getCheckIn());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateCheckIn = null;
        try {
            dateCheckIn = formatter.parse(EmployeeList.get(position).getCheckIn().toString());
            startTime = dateCheckIn.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
                long currentTime = Calendar.getInstance().getTimeInMillis();
                timeInMilliseconds = currentTime - startTime;
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                holder.tv_duration_dblist.setText(df.format(timeInMilliseconds));

    }

    @Override
    public int getItemCount() {
        return EmployeeList.size();
    }
    @Override
    public  Filter getFilter() {
        return EmployeeFilter;
    }

    private Filter EmployeeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<TblAdminxEmployeexAttendance> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(EmployeeListfull);

            } else {
                String filterPattern = charSequence.toString().toLowerCase();
                for (TblAdminxEmployeexAttendance item: EmployeeListfull)
                {
                    if(item.getName().toString().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                    else if(item.getUsername().toLowerCase().contains(filterPattern)){
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
            EmployeeList.clear();
            EmployeeList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };


}
