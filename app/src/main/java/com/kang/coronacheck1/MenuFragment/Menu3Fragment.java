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

import com.kang.coronacheck1.Adapter.WorldAdapter;
import com.kang.coronacheck1.FlagVar;
import com.kang.coronacheck1.Item.WorldItem;
import com.kang.coronacheck1.MainActivity;
import com.kang.coronacheck1.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Menu3Fragment extends Fragment {

    private static final String TAG = "로그";
    ViewGroup viewGroup;
    MainActivity activity;
    TextView mTitle,mPatient,mDaily,mDeath;


    // 리사이클러뷰 위한 설정
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    WorldAdapter adapter;

    ArrayList<String> listNation = new ArrayList<>();
    ArrayList<String> listPatient = new ArrayList<>();
    ArrayList<String> listRank = new ArrayList<>();
    ArrayList<String> listDeath = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "Menu3Fragment - onAttach() called");
        //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Menu3Fragment - onDetach() called");
        //이제 더이상 엑티비티 참조가 안됨
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Menu3Fragment - onCreateView() called");

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu3,container,false);
        //리사이클러뷰 아이템
        init();
        getData();
        return viewGroup;
    }

    private void init() {
        Log.d(TAG, "Menu3Fragment - onCreateView() called");
        //텍스트 크기 지정
        mTitle = viewGroup.findViewById(R.id.tv_world_frag_title);
        mPatient = viewGroup.findViewById(R.id.tv_world_frag_patient);
        mDaily  = viewGroup.findViewById(R.id.tv_world_frag_daily);
        mDeath = viewGroup.findViewById(R.id.tv_world_frag_rank);

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
        // 리사이클러뷰 아이템
        recyclerView = viewGroup.findViewById(R.id.recycler_view_world);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WorldAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void getData(){
        Log.d(TAG, "Menu3Fragment - getData() called");
        Menu3Fragment.WorldJsoup jsoupAsyncTask = new Menu3Fragment.WorldJsoup();
        jsoupAsyncTask.execute();
    }

    private class WorldJsoup extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "Menu3Fragment - WorldJsuop called");
            try {
                Document doc = Jsoup.connect("http://ncov.mohw.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=14&ncvContSeq=&contSeq=&board_id=&gubun=").get();
                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int count=0;
                        Elements world_nation = doc.select("div div.data_table.mgt8").select("table.num").select("tbody tr th");
                        Elements world_patient = doc.select("div div.data_table.mgt8").select("table.num").select("tbody tr td");

                        // Log.d(TAG, "국가"+world_nation.text());
                        // Log.d(TAG, world_patient.text());

                        for(int i=0; i<110;i++){
                            if(i%5==0){
                                listPatient.add(world_patient.get(i).text());
                            }else if(i%5==2){
                                listDeath.add(world_patient.get(i).text());
                            }
                            if(listDeath.size()==20){
                                break;
                            }
                        }
                        for(int i =0 ; i<20;i++) {
                            WorldItem data = new WorldItem();
                            listRank.add(String.valueOf(i + 1));
                            listNation.add(world_nation.get(i).text());

                            data.setRank(listRank.get(i));
                            data.setNation(listNation.get(i));
                            data.setPatient(listPatient.get(i));
                            data.setDeath(listDeath.get(i));
                            adapter.addItem(data);
                        }
                        Log.d(TAG, listPatient.toString());
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