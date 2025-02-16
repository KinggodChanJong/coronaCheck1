package com.kang.coronacheck1.MenuFragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.coronacheck1.Adapter.CityAdapter;
import com.kang.coronacheck1.FlagVar;
import com.kang.coronacheck1.Item.CityItem;
import com.kang.coronacheck1.MainActivity;
import com.kang.coronacheck1.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class Menu2Fragment extends Fragment{

    private static final String TAG = "로그";
    ViewGroup viewGroup;
    MainActivity activity;
    TextView mTitle,mPatient,mDaily,mDeath;

    // 리사이클러뷰 위한 설정
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    CityAdapter adapter;

    ArrayList<String> listTitle = new ArrayList<>();
    ArrayList<String> listPatient = new ArrayList<>();
    ArrayList<String> listDaily = new ArrayList<>();
    ArrayList<String> listDeath = new ArrayList<>();
    ArrayList<Integer> listImage = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "Menu2Fragment - onAttach() called");
        //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Menu2Fragment - onDetach() called");
        //이제 더이상 엑티비티 참조가 안됨
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Menu2Fragment - onCreateView() called");

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu2,container,false);
        //리사이클러뷰 아이템
        init();
        getData();
        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "Menu2Fragment - onResume() called");
        init();
        adapter.notifyDataSetChanged();
    }

    private void init() {
        Log.d(TAG, "Menu2Fragment - init() called");
        //텍스트 크기 지정
        mTitle = viewGroup.findViewById(R.id.tv_report_frag_title);
        mPatient = viewGroup.findViewById(R.id.tv_report_frag_patient);
        mDaily  = viewGroup.findViewById(R.id.tv_report_frag_daily);
        mDeath = viewGroup.findViewById(R.id.tv_report_frag_death);

        fontInit();
    }
    private void fontInit(){
        Log.d(TAG, "Menu2Fragment - fontInit() called");
        int flagVar = FlagVar.getState();
        if(flagVar == 1) {
            ///////확진자 현황 텍스트뷰
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            mPatient.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            mDaily.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            mDeath.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        }else if(flagVar == 2){
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
            mPatient.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
            mDaily.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
            mDeath.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 19);
        }
    }

    private void getData(){
        Log.d(TAG, "Menu2Fragment - getData() called");
        // 리사이클러뷰 아이템
        recyclerView = viewGroup.findViewById(R.id.recycler_view_report);
        // recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CityAdapter();
        recyclerView.setAdapter(adapter);

        Menu2Fragment.NewsJsoup jsoupAsyncTask = new Menu2Fragment.NewsJsoup();
        jsoupAsyncTask.execute();
    }

    private class NewsJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Menu1Fragment - NewsJsoup called");
            try {
                Document doc = Jsoup.connect("http://ncov.mohw.go.kr").get();
                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Elements report_patient = doc.select("div").select("#main_maplayout").select(".num");
                        Elements report_title = doc.select("div").select("#main_maplayout").select(".name");
                        Elements report_daily = doc.select("div").select("#main_maplayout").select(".before");

                        // Log.d(TAG, report_title.text());
                        // Log.d(TAG, report_patient.text());
                        // Log.d(TAG, report_daily.text());

                        for(int i =0 ; i<18;i++){
                            String temp = "";
                            Elements report_death = doc.select("div").select("#map_city"+(i+1)).select(".mapview").select(".num");
                            // Log.d(TAG, report_death.text());
                            CityItem data = new CityItem();
                            listPatient.add(report_patient.get(i).text());
                            listTitle.add(report_title.get(i).text());
                            String deleteString = report_daily.get(i).text().replace("(","");
                            deleteString = deleteString.replace(")", "");
                            listDaily.add(deleteString);
                            listDeath.add(report_death.get(3).text());

                            // Log.d(TAG, String.valueOf(i));
                            listImage.add(i);

                            data.setTitle(listTitle.get(i));
                            data.setPatient(listPatient.get(i));
                            data.setDaily(listDaily.get(i));
                            data.setDeath(listDeath.get(i));
                            data.setImage(listImage.get(i));
                            adapter.addItem(data);
                        }
                        // Log.d(TAG, listPatient.toString());
                        adapter.notifyDataSetChanged();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}