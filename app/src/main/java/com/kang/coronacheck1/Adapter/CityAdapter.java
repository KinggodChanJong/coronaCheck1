package com.kang.coronacheck1.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.coronacheck1.FlagVar;
import com.kang.coronacheck1.NewsWebViewActivity;
import com.kang.coronacheck1.R;
import com.kang.coronacheck1.Item.CityItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private static final String TAG = "로그";

    private ArrayList<CityItem> listData = new ArrayList<>();
    String listAddress[]={"https://www.seoul.go.kr/coronaV/coronaStatus.do","http://www.busan.go.kr/covid19/Corona19.do","http://www.daegu.go.kr/intro.jsp",
            "https://www.incheon.go.kr/index", "https://www.gwangju.go.kr/", "https://www.daejeon.go.kr/corona19/index.do", "http://www.ulsan.go.kr/corona.jsp",
            "https://www.sejong.go.kr/bbs/R3273/list.do;jsessionid=sdaREVbYSWWm1zTYow5mjNDkx5Cj4Bgor4iu1dX1X91jhXKTV7gCaoBl9HgQNEv3.Portal_WAS2_servlet_engine5?cmsNoStr=17465",
            "https://www.gg.go.kr/contents/contents.do?ciIdx=1150&menuId=2909","http://www.provin.gangwon.kr/covid-19.html","http://www1.chungbuk.go.kr/intro/index.html",
            "http://www.chungnam.go.kr/coronaStatus.do","http://www.jeonbuk.go.kr/board/list.jeonbuk?boardId=BBS_0000105&menuCd=DOM_000000110001000000&contentsSid=1219&cpath=",
            "https://www.jeonnam.go.kr/coronaMainPage.do", "http://gb.go.kr/corona_main.htm","http://xn--19-q81ii1knc140d892b.kr/main/main.do","https://www.jeju.go.kr/corona19.jsp",
            "http://ncov.mohw.go.kr/"};

    int listImage[] = {R.drawable.city_1,R.drawable.city_2,R.drawable.city_3,R.drawable.city_4,R.drawable.city_5, R.drawable.city_6,
            R.drawable.city_7,R.drawable.city_8,R.drawable.city_9,R.drawable.city_10,R.drawable.city_11,R.drawable.city_12,R.drawable.city_13,
            R.drawable.city_14,R.drawable.city_15,R.drawable.city_16,R.drawable.city_17,R.drawable.city_18};

    Context context ;

    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_city, viewGroup, false);
        // Log.d(TAG, "CityAdapter - onCreateViewHolder() called");
        context = view.getContext();
        return new CityAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(listData.get(i));

        /////텍스트 크기
        int flagVar = FlagVar.getState();
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
        private ImageView mImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_city_item_title);
            mPatient = itemView.findViewById(R.id.tv_city_item_patient);
            mDaily  = itemView.findViewById(R.id.tv_city_item_daily);
            mDeath = itemView.findViewById(R.id.tv_city_item_death);
            mImage = itemView.findViewById(R.id.iv_city_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos !=RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, NewsWebViewActivity.class);
                        intent.putExtra("url",listAddress[pos]);
                        context.startActivity(intent);
                        // Toast.makeText(context, mTitle.getText(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @SuppressLint("ResourceType")
        void onBind(CityItem data){
            mTitle.setText(data.getTitle());
            mPatient.setText(data.getPatient());
            mDaily.setText(data.getDaily());
            mDeath.setText(data.getDeath());

            Picasso.get().load(listImage[data.getImage()]).centerCrop().resize(0,50)
                    .error(R.drawable.city_1)
                    .into(mImage);
        }
    }
}
