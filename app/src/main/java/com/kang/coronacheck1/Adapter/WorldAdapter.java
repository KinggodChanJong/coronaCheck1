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
import com.kang.coronacheck1.Item.WorldItem;
import com.kang.coronacheck1.NewsWebViewActivity;
import com.kang.coronacheck1.R;

import java.util.ArrayList;

public class WorldAdapter extends RecyclerView.Adapter<WorldAdapter.ViewHolder> {

    private static final String TAG = "로그";
    private ArrayList<WorldItem> listData = new ArrayList<>();

    Context context ;

    @NonNull
    @Override
    public WorldAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_world, viewGroup, false);
        context = view.getContext();
        return new WorldAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull WorldAdapter.ViewHolder viewHolder, int i) {
        viewHolder.onBind(listData.get(i));

        /////텍스트 크기
        int flagVar = FlagVar.getState();
        Log.d("로그" , String.valueOf(flagVar));

        if (flagVar == 1){
            TextView title = viewHolder.mRank;
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            TextView patient = viewHolder.mNation;
            patient.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            TextView daily = viewHolder.mPatient;
            daily.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            TextView death = viewHolder.mDeath;
            death.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        }else if (flagVar == 2){
            TextView title = viewHolder.mRank;
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            TextView patient = viewHolder.mNation;
            patient.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            TextView daily = viewHolder.mPatient;
            daily.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            TextView death = viewHolder.mDeath;
            death.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        }
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public void addItem(WorldItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mRank, mPatient, mDeath, mNation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRank = itemView.findViewById(R.id.tv_world_item_rank);
            mNation = itemView.findViewById(R.id.tv_world_item_nation);
            mPatient  = itemView.findViewById(R.id.tv_world_item_patient);
            mDeath = itemView.findViewById(R.id.tv_world_item_death);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos !=RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, NewsWebViewActivity.class);

                        context.startActivity(intent);

                        Toast.makeText(context, mNation.getText(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        void onBind(WorldItem data){
            mRank.setText(data.getRank());
            mNation.setText(data.getNation());
            mPatient.setText(data.getPatient());
            mDeath.setText(data.getDeath());
        }
    }
}