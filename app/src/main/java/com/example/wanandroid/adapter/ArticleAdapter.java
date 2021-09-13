package com.example.wanandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.activity.ArticleDetailActivity;
import com.example.wanandroid.cogfig.Constant;
import com.example.wanandroid.model.Article;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.SharePreferencesUtil;
import com.example.wanandroid.utils.ToastUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.InnerHolder>
        implements HttpUtil.HttpCallbackListener {
    private List<Article> articleList;
    private Context mContext;

    /**
     * 构造方法
     *
     * @param articleList
     */
    public ArticleAdapter(Context context,List<Article> articleList) {
        this.articleList = articleList;
        this.mContext = context;
    }


    /**
     * 传出去的view实际上就是条目的界面，此时需要创建一个item布局（即我们前面创建的子布局）
     * 两个步骤：一个是拿到view，一个是创建一个InnerHolder并把view传进去
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_article, parent, false);
        return new InnerHolder(view);
    }

    /**
     * 这个方法用于绑定holder，并设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        Resources resources = mContext.getResources();
        Drawable ibtnCollectioned = resources.getDrawable(R.drawable.ic_star_select);
        Article article = articleList.get(position);
        holder.tvTitle.setText(article.getTitle());
        holder.tvAuthor.setText(article.getAuthor());
        holder.tvData.setText(article.getDate());
        if (article.getTags().size() > 0 && article.getTags().get(0).getName() != null) {
            holder.tvTag.setText(article.getTags().get(0).getName());
            holder.tvTag.setVisibility(View.VISIBLE);
        } else {
            holder.tvTag.setVisibility(View.GONE);
        }
        //文章窗口被点击时
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ArticleDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("link", article.getLink());
            mContext.startActivity(intent);
        });
        //收藏
        holder.ibtnCollection.setOnClickListener(view -> {
            if (SharePreferencesUtil.getBoolean(mContext, "isLogin", false)) {
                holder.ibtnCollection.setBackground(ibtnCollectioned);
                String path = Constant.COLLECT + article.getId() + Constant.MYJSON;
                String localCookie = SharePreferencesUtil.getString(mContext, "Cookie", null);

                HttpUtil.post(path, 0, null, localCookie, false, ArticleAdapter.this);
            } else {
                ToastUtil.showShortToast(mContext, "请先登录");
            }
        });
    }

    /**
     * 这个方法用于返回条目的数目
     * @return
     */
    @Override
    public int getItemCount() {
        if (articleList != null) {
            return articleList.size();
        }
        return 0;
    }


    /**
     * 找到所有的控件
     */
    public class InnerHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvAuthor, tvTag, tvData;
        private ImageButton ibtnCollection;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTag = itemView.findViewById(R.id.tv_tag);
            tvData = itemView.findViewById(R.id.tv_data);
            ibtnCollection = itemView.findViewById(R.id.ibtn_collection);
        }
    }

    @Override
    public void onFinish(int requestId, String response, String cookie) {
        if (requestId == 0) {
            JsonObject jsonObj = new JsonParser().parse(response).getAsJsonObject();
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (jsonObj.get("errorCode").getAsInt() == 0) {
                        ToastUtil.showShortToast(mContext, "收藏成功");
                    } else {
                        ToastUtil.showShortToast(mContext, jsonObj.get("errorMsg").getAsString());
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(Exception e) {
        e.printStackTrace();
    }
}
