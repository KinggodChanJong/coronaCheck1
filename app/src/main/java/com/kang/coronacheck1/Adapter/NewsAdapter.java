package com.kang.coronacheck1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kang.coronacheck1.NewsItem;
import com.kang.coronacheck1.NewsWebViewActivity;
import com.kang.coronacheck1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String TAG = "로그";

    private ArrayList<NewsItem> listData = new ArrayList<>();

    Context context ;

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, viewGroup, false);
        context = view.getContext();
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
        private String mUrl="";
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_news_item_title);
            mContents = itemView.findViewById(R.id.tv_news_item_contents);
            mImage  = itemView.findViewById(R.id.iv_news_item_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos !=RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, NewsWebViewActivity.class);

                        intent.putExtra("url",mUrl); /*송신*/
                        context.startActivity(intent);

                        Toast.makeText(context, mTitle.getText(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        void onBind(NewsItem data){
            mTitle.setText(data.getTitle());
            mContents.setText(data.getContents());
            if(data.getImage().isEmpty()){
                Picasso.get().load(R.drawable.ic_home_black_24dp).into(mImage);
            }
            else{
                Picasso.get().load(data.getImage()).centerCrop().resize(0,75).into(mImage);
            }
            mUrl=data.getUrl();
        }
    }
}
