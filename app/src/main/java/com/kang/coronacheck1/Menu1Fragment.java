package com.kang.coronacheck1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Menu1Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "로그";

    ViewGroup viewGroup;
    private FloatingActionButton fabQrcode;
    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "Menu1Fragment - onAttach() called");
        //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Menu1Fragment - onDetach() called");
        //이제 더이상 엑티비티 참조가 안됨
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu1,container,false);

        ////////////// 차트
        BarChart chart = viewGroup.findViewById(R.id.barchart);

        //날짜 배열 선언
        String[] days;
        days = new String[7];

        //날짜 배열에 담기
        int i;
        for(i=0; i<7;i++){
            Date dDate = new Date();
            dDate = new Date(dDate.getTime()+(1000*60*60*24*-1)*i);
            SimpleDateFormat dSdf = new SimpleDateFormat("MM/dd", Locale.KOREA);
            days[i] = dSdf.format(dDate);
        }

        // 날짜 배열 순서 역으로 뒤집기
        for(i=0; i<3;i++){
            String temp;
            temp = days[6-i];
            days[6-i] = days[i];
            days[i] = temp;
        }

        //데이터값 지정,배열 선언
        Float[] val ;
        val = new Float[]{113f,153f,126f,110f,82f,70f,61f};

        //확진자 값 받을 변수
        String vl = "51";
        //큐 역핳
        Float val_temp,  val_temp2 ;
        val_temp = Float.valueOf(vl);
        val_temp2 = 1f;
        for(i=0; i<6;i++){
            val_temp2= val[i];
            val[i] = val[i+1];
        }
        val[6] = val_temp;
        //Log.d("수치값",val[i].toString());

        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new BarEntry(val[0], 0));
        NoOfEmp.add(new BarEntry(val[1], 1));
        NoOfEmp.add(new BarEntry(val[2], 2));
        NoOfEmp.add(new BarEntry(val[3], 3));
        NoOfEmp.add(new BarEntry(val[4], 4));
        NoOfEmp.add(new BarEntry(val[5], 5));
        NoOfEmp.add(new BarEntry(val[6], 6));

        BarDataSet bardataset = new BarDataSet(NoOfEmp,"요일별");
        chart.animateY(5000);
        XAxis xAxis = chart.getXAxis();
        YAxis yRAxis = chart.getAxisRight();
        //가로선 제거
        //chart.getAxisLeft().setDrawGridLines(false);
        //세로선 제거
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getLegend().setEnabled(false);

        //요일 아래 나오게
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //오른쪽 라벨 지우기
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        //데이터 셋
        BarData data = new BarData(days, bardataset);// MPAndroidChart v3.X 오류 발생
        // bar 차트 색 지정
        bardataset.setColor(Color.parseColor("#731D5C"));
        data.setValueTextSize(10f);
        chart.setData(data);

        ///////////////////////////차트 끝




        Log.d(TAG, "Menu1Fragment - onCreateView() called");



        fabQrcode = (FloatingActionButton)viewGroup.findViewById(R.id.fab);
        fabQrcode.setOnClickListener(this);


        return viewGroup;
    };

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        Log.d(TAG, "Menu1Fragment - onClick() called");
        Intent intent = new Intent(getActivity(), QrcodeActivity.class);
        startActivity(intent);
        getActivity().isDestroyed();
    }
}