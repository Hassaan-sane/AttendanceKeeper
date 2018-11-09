package com.example.hassaan.attendancekeeper.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hassaan.attendancekeeper.API.ListAPI;
import com.example.hassaan.attendancekeeper.Adapter.ReportAdapter;
import com.example.hassaan.attendancekeeper.Model.TblAttendance;
import com.example.hassaan.attendancekeeper.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ReportFragment extends Fragment {

    private List<TblAttendance> AttendanceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context = getContext();


    private OnFragmentInteractionListener mListener;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        ListAPI service = ListAPI.retrofit.create(ListAPI.class);

        final ReportAdapter reportAdapter = new ReportAdapter(context, AttendanceList);
        recyclerView = view.findViewById(R.id.recyclerView_report);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(reportAdapter);

        Call<List<TblAttendance>> GetAttendance = service.GetAttendance();
        GetAttendance.enqueue(new Callback<List<TblAttendance>>() {
            @Override
            public void onResponse(Call<List<TblAttendance>> call, Response<List<TblAttendance>> response) {
                AttendanceList = response.body();
                Log.i("TAG", "onResponse in Report: " + response);
                reportAdapter.setList(AttendanceList);
            }

            @Override
            public void onFailure(Call<List<TblAttendance>> call, Throwable t) {


            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
