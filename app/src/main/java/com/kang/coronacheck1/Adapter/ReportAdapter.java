package com.kang.coronacheck1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.coronacheck1.NewsWebViewActivity;
import com.kang.coronacheck1.R;
import com.kang.coronacheck1.ReportItem;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private static final String TAG = "로그";

    private ArrayList<ReportItem> listData = new ArrayList<>();

    Context context ;

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_report, viewGroup, false);
        context = view.getContext();
        return new ReportAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder viewHolder, int i) {
        viewHolder.onBind(listData.get(i));
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public void addItem(ReportItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitle, mPatient, mDaily, mDeath;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_report_item_title);
            mPatient = itemView.findViewById(R.id.tv_report_item_patient);
            mDaily  = itemView.findViewById(R.id.tv_report_item_daily);
            mDeath = itemView.findViewById(R.id.tv_report_item_death);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos !=RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, NewsWebViewActivity.class);

                        context.startActivity(intent);

                        Toast.makeText(context, mTitle.getText(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        void onBind(ReportItem data){
            mTitle.setText(data.getTitle());
            mPatient.setText(data.getPatient());
            mDaily.setText(data.getDaily());
            mDeath.setText(data.getDeath());
        }
    }
}
