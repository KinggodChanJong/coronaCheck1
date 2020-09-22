package com.kang.coronacheck1.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.coronacheck1.NewsItem;
import com.kang.coronacheck1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String TAG = "로그";

    private ArrayList<NewsItem> listData = new ArrayList<>();

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.onBind(listData.get(i));
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public void addItem(NewsItem data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTitle, mContents;
        private ImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_news_item_title);
            mContents = itemView.findViewById(R.id.tv_news_item_contents);
            mImage  = itemView.findViewById(R.id.iv_news_item_image);

        }

        void onBind(NewsItem data){
            mTitle.setText(data.getTitle());
            mContents.setText(data.getContents());
            if(data.getImage().isEmpty()){
                Picasso.get().load(R.drawable.ic_home_black_24dp).into(mImage);
            }
            else{
                Picasso.get().load(data.getImage()).into(mImage);
            }

        }
    }
}
/**
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle, mContents;
        ImageView mImage;

        MyViewHolder(View view) {
            super(view);
            mImage = view.findViewById(R.id.iv_news_item_image);
            mTitle = view.findViewById(R.id.tv_news_item_title);
            mContents = view.findViewById(R.id.tv_news_item_contents);
        }
    }
    private ArrayList<NewsItem> newsArrayList;
    public NewsAdapter(ArrayList<NewsItem> newsArrayList){
        this.newsArrayList = newsArrayList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"NewsAdapter - onCreateViewHolder() called");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG,"NewsAdapter - onBindViewHolder() called");
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.mImage.setImageResource(newsArrayList.get(position).getImage());
        myViewHolder.mTitle.setText(newsArrayList.get(position).getTitle());
        myViewHolder.mContents.setText(newsArrayList.get(position).getContents());
    }
    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }


}
 **/