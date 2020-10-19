package com.kang.coronacheck1.MenuFragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.coronacheck1.Adapter.NewsAdapter;
import com.kang.coronacheck1.Item.NewsItem;
import com.kang.coronacheck1.MainActivity;
import com.kang.coronacheck1.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Menu4Fragment extends Fragment {

    private static final String TAG = "로그";

    ViewGroup viewGroup;
    MainActivity activity;

    // 리사이클러뷰 위한 설정
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    NewsAdapter adapter;

    ArrayList<String> listTitle = new ArrayList<>();
    ArrayList<String> listName = new ArrayList<>();
    ArrayList<String> listImageUrl = new ArrayList<>();
    ArrayList<String> listUrl = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "Menu4Fragment - onAttach() called");
        //이 메소드가 호출될떄는 프래그먼트가 엑티비티위에 올라와있는거니깐 getActivity메소드로 엑티비티참조가능
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "Menu4Fragment - onDetach() called");
        //이제 더이상 엑티비티 참조가 안됨
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d(TAG, "Menu4Fragment - onCreateView() called");

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_menu4,container,false);
/*


        // 리사이클러뷰 아이템
        recyclerView = viewGroup.findViewById(R.id.recycler_view_news);
        // recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter();
        recyclerView.setAdapter(adapter);
        getData();
*/
        itemView();
        getData();
        return viewGroup;
    }

    private void itemView() {
        // 리사이클러뷰 아이템
        recyclerView = viewGroup.findViewById(R.id.recycler_view_news);
        // recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewsAdapter();


    }
    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
    ;
    // 뉴스 받아오기 위한 핸들러

    private void getData(){
        Log.d(TAG, "Menu4Fragment - getData() called");
        NewsJsoup jsoupAsyncTask = new NewsJsoup();
        jsoupAsyncTask.execute();
    }

    private class NewsJsoup extends AsyncTask<Void, Void, Void> {

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
                            Elements news_title = doc.select("#sp_nws"+i).select("dt [title]");
                            if(news_title.isEmpty()){
                                continue;
                            }
                            count++;
                            Elements news_contents = doc.select("#sp_nws"+i).select("dd").not(".txt_inline");
                            Elements news_image = doc.select("#sp_nws"+i).select("img");
                            Elements news_url =doc.select("#sp_nws"+i).select("a");

                            Log.d(TAG, news_url.attr("href"));
                            listTitle.add(news_title.text());
                            listName.add(news_contents.text());
                            listImageUrl.add(news_image.attr("src"));
                            listUrl.add(news_url.attr("href"));

                            if(count ==10){
                                break;
                            }
                        }
                        for (int i = 0; i < 10 ; i++) {
                            NewsItem data = new NewsItem();
                            data.setTitle(listTitle.get(i));
                            data.setImage(listImageUrl.get(i));
                            data.setContents(listName.get(i));
                            data.setUrl(listUrl.get(i));
                            adapter.addItem(data);
                        }
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public void refresh(){
        adapter.notifyDataSetChanged();
    }


}