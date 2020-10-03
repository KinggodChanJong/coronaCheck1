package com.kang.coronacheck1;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Menu1Fragment extends Fragment {

    private static final String TAG = "로그";

    ViewGroup viewGroup;
    MainActivity activity;
    ArrayList<String> listDecNum = new ArrayList<>();
    ArrayList<String> listYesterdayNum= new ArrayList<>();
    ArrayList<String> listPatientNum = new ArrayList<>();

    BarChart chart;
    String[] days;

    private TextView tvPatient,tvPatientNum,tvPatientYesterday, tvInspection, tvInspectionNum, tvInspectionYesterday,
            tvSafe, tvSafeNum, tvSafeYesterday, tvDie, tvDieNum, tvDieYesterday;

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

        Log.d(TAG, "Menu1Fragment - onCreateView() called");

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu1,container,false);

        init();
        getData();

        return viewGroup;
    }

    private void init() {
        Log.d(TAG, "Menu1Fragment - init() called");

        chart = viewGroup.findViewById(R.id.barchart);
        ///////확진자 현황 텍스트뷰
        tvPatient = viewGroup.findViewById(R.id. tv_home_frag_patient);
        tvInspection = viewGroup.findViewById(R.id.tv_home_frag_inspection);
        tvSafe = viewGroup.findViewById(R.id.tv_home_frag_safe);
        tvDie = viewGroup.findViewById(R.id.tv_home_frag_die);
        //누적 합계
        tvPatientNum = viewGroup.findViewById(R.id. tv_home_frag_patient_num);
        tvInspectionNum = viewGroup.findViewById(R.id.tv_home_frag_inspection_num);
        tvSafeNum = viewGroup.findViewById(R.id.tv_home_frag_safe_num);
        tvDieNum = viewGroup.findViewById(R.id.tv_home_frag_die_num);
        //오늘 증가량
        tvPatientYesterday = viewGroup.findViewById(R.id. tv_home_frag_patient_yesterday);
        tvInspectionYesterday = viewGroup.findViewById(R.id.tv_home_frag_inspection_yesterday);
        tvSafeYesterday = viewGroup.findViewById(R.id.tv_home_frag_safe_yesterday);
        tvDieYesterday = viewGroup.findViewById(R.id.tv_home_frag_die_yesterday);
        // 날짜 배열 만들기
        days = new String[7];
        for(int i=6; i>=0;i--){
            Date dDate = new Date();
            dDate = new Date(dDate.getTime()+(1000*60*60*24*-1)*(6-i));
            SimpleDateFormat dSdf = new SimpleDateFormat("MM/dd", Locale.KOREA);
            days[i] = dSdf.format(dDate);
        }
    }

    private void getData(){
        Log.d(TAG, "Menu1Fragment - getData() called");
        Menu1Fragment.HomeJsoup jsoupAsyncTask = new Menu1Fragment.HomeJsoup();
        jsoupAsyncTask.execute();
    }

    private class HomeJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/country/south-korea/").get();
                Document doc1 = Jsoup.connect("https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%9819").get();

                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 확진자 수 크롤링
                        Elements report_today_patient = doc1.select("div.status_info").select("ul li p");
                        Elements report_yesterday_patient = doc1.select("div.status_info ul em");

                        for(int i =0;i<4;i++){
                            listPatientNum.add(report_today_patient.get(i).text());
                            listYesterdayNum.add(report_yesterday_patient.get(i).text());
                        }

                        tvPatientNum.setText(listPatientNum.get(0));
                        tvInspectionNum.setText(listPatientNum.get(1));
                        tvSafeNum.setText(listPatientNum.get(2));
                        tvDieNum.setText(listPatientNum.get(3));

                        tvPatientYesterday.setText(listYesterdayNum.get(0));
                        tvInspectionYesterday.setText(listYesterdayNum.get(1));
                        tvSafeYesterday.setText(listYesterdayNum.get(2));
                        tvDieYesterday.setText(listYesterdayNum.get(3));
                        // 도표에 넣을 환자 수 크롤링
                        Elements report_patient = doc.select("div .col-md-12").select("div .newsdate_div").select("div div ul li");
                        for(int i=0;i<6;i++){
                            Log.d(TAG, report_patient.get(i).text());
                            String []decNum =report_patient.get(i).text().split(" ");
                            listDecNum.add(decNum[0]);
                        }

                        ArrayList NoOfEmp = new ArrayList();
                        NoOfEmp.add(new BarEntry(Float.parseFloat(listDecNum.get(5)), 0));
                        NoOfEmp.add(new BarEntry(Float.parseFloat(listDecNum.get(4)), 1));
                        NoOfEmp.add(new BarEntry(Float.parseFloat(listDecNum.get(3)), 2));
                        NoOfEmp.add(new BarEntry(Float.parseFloat(listDecNum.get(2)), 3));
                        NoOfEmp.add(new BarEntry(Float.parseFloat(listDecNum.get(1)), 4));
                        NoOfEmp.add(new BarEntry(Float.parseFloat(listDecNum.get(0)), 5));
                        NoOfEmp.add(new BarEntry(Float.parseFloat(listYesterdayNum.get(0)), 6));

                        BarDataSet bardataset = new BarDataSet(NoOfEmp,"요일별");
                        chart.animateY(3000);
                        XAxis xAxis = chart.getXAxis();
                        YAxis yRAxis = chart.getAxisRight();

                        chart.setCameraDistance(20);

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

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}