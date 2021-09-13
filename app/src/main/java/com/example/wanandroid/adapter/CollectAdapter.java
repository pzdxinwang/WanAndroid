package com.example.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.activity.ArticleDetailActivity;
import com.example.wanandroid.model.Article;

import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {
    private List<Article> articleList;
    private Context mContext;

    public CollectAdapter(List<Article> articleList, Context mContext) {
        this.articleList = articleList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_collect,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = articleList.get(position);

        holder.tvDate.setText(article.getDate());
        holder.tvTitle.setText(article.getTitle());
        holder.tvAuthor.setText(article.getAuthor());
        holder.tvTag.setText(article.getChapterName());

        //文章窗口被点击时
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ArticleDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("link", article.getLink());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (articleList != null) {
            return articleList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvTag;
        private ImageButton ibtCancle;

        ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date_collect);
            tvTitle = itemView.findViewById(R.id.tv_title_collect);
            tvAuthor = itemView.findViewById(R.id.tv_author_collect);
            tvTag = itemView.findViewById(R.id.tv_tag_collect);
            ibtCancle = itemView.findViewById(R.id.ibt_cancle_collect);
        }
    }
}
