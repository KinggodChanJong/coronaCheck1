package com.kang.coronacheck1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kang.coronacheck1.Adapter.NewsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class Menu3Fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "로그";

    ViewGroup viewGroup;
    private FloatingActionButton fabQrcode;
    MainActivity activity;

    // 리사이클러뷰 위한 설정
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    NewsAdapter adapter;
    ArrayList<String> listTitle = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
    ArrayList<String> listUrl = new ArrayList<>();

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

        fabQrcode = (FloatingActionButton)viewGroup.findViewById(R.id.fab);
        fabQrcode.setOnClickListener(this);

        // 리사이클러뷰 아이템
        recyclerView = viewGroup.findViewById(R.id.recycler_view_news);
        // recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
        getData();

        return viewGroup;
    };
    // 뉴스 받아오기 위한 핸들러

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        Log.d(TAG, "Menu3Fragment - onClick() called");
        Intent intent = new Intent(getActivity(), QrcodeActivity.class);
        startActivity(intent);
        getActivity().isDestroyed();
    }
    private void getData(){
        Log.d(TAG, "Menu3Fragment - getData() called");
        NewsJsoup jsoupAsyncTask = new NewsJsoup();
        jsoupAsyncTask.execute();
    }

    private class NewsJsoup extends AsyncTask<Void, Void, Void> {
        ArrayList<String> listTitle = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();
        ArrayList<String> listUrl = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://search.naver.com/search.naver?where=news&query=%EC%BD%94%EB%A1%9C%EB%82%98%20%EB%89%B4%EC%8A%A4&sm=tab_srt&sort=0&photo=0&field=0&reporter_article=&pd=0&ds=&de=&docid=&nso=so%3Ar%2Cp%3Aall%2Ca%3Aall&mynews=0&refresh_start=0&related=0").get();
                Handler handler = new Handler(Looper.getMainLooper()); // 객체생성
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int count=0;
                        for(int i =1;i<50;i++){
                            Elements rank_list1 = doc.select("#sp_nws"+i).select("dt [title]");
                            if(rank_list1.isEmpty()){
                                continue;
                            }
                            count++;
                            Elements rank_list_name = doc.select("#sp_nws"+i).select("dd").not(".txt_inline");
                            Elements image_list1 = doc.select("#sp_nws"+i).select("img");

                            listTitle.add(rank_list1.text());
                            listName.add(rank_list_name.text());
                            listUrl.add(image_list1.attr("src"));

                            if(count ==10){
                                break;
                            }
                        }
                        for (int i = 0; i < 10 ; i++) {
                            NewsItem data = new NewsItem();
                            data.setTitle(listTitle.get(i));
                            data.setImage(listUrl.get(i));
                            data.setContents(listName.get(i));
                            adapter.addItem(data);
                        }
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