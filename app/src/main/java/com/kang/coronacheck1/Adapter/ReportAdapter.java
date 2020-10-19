package com.kang.coronacheck1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.coronacheck1.FlagVar;
import com.kang.coronacheck1.NewsWebViewActivity;
import com.kang.coronacheck1.R;
import com.kang.coronacheck1.Item.CityItem;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private static final String TAG = "로그";
    private ArrayList<CityItem> listData = new ArrayList<>();

    Context context ;
    private Typeface typeface;

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_city, viewGroup, false);
        context = view.getContext();
        return new ReportAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(listData.get(i));

        /////텍스트 크기
        int flagVar = FlagVar.getState();
        Log.d("로그" , String.valueOf(flagVar));
        if (flagVar == 1){
            TextView title = viewHolder.mTitle;
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            TextView patient = viewHolder.mPatient;
            patient.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            TextView daily = viewHolder.mDaily;
            daily.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            TextView death = viewHolder.mDeath;
            death.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        }else if (flagVar == 2){
            TextView title = viewHolder.mTitle;
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            TextView patient = viewHolder.mPatient;
            patient.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            TextView daily = viewHolder.mDaily;
            daily.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            TextView death = viewHolder.mDeath;
            death.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        }

    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public void addItem(CityItem data) {
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

        void onBind(CityItem data){
            mTitle.setText(data.getTitle());
            mPatient.setText(data.getPatient());
            mDaily.setText(data.getDaily());
            mDeath.setText(data.getDeath());
        }
    }
}
