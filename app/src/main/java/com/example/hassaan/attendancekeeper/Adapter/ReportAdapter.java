package com.example.hassaan.attendancekeeper.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hassaan.attendancekeeper.Model.TblAttendance;
import com.example.hassaan.attendancekeeper.Model.TblEmployee;
import com.example.hassaan.attendancekeeper.R;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private Context context;
    private List<TblAttendance> AttendanceList = new ArrayList<>();


    public ReportAdapter(Context applicationContext, List<TblAttendance> AttendanceList) {
        this.context = applicationContext;
        this.AttendanceList = AttendanceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setList(List<TblAttendance> attendanceList) {
        this.AttendanceList = attendanceList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_dblist, tv_checkInTime_dblist, tv_duration_dblist, tv_checkOutTime_dblist;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name_dblist = itemView.findViewById(R.id.tv_Name_dblist);
            tv_checkInTime_dblist = itemView.findViewById(R.id.tv_checkInTime_dblist);
            tv_duration_dblist = itemView.findViewById(R.id.tv_duration_dblist);
//            tv_checkOutTime_dblist = itemView.findViewById(R.id.tv_checkOutTime_dblist);
        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        context=holder.tv_name_dblist.getContext();
        Log.i("TAG", "onBindViewHolder: was here ");

        holder.tv_checkInTime_dblist.setText(AttendanceList.get(position).getCheckIn());
        holder.tv_name_dblist.setText(AttendanceList.get(position).getEmployeeId().toString()
        );


    }

    @Override
    public int getItemCount() {
        return AttendanceList.size();
    }


}
