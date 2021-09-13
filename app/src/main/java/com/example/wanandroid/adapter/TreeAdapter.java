package com.example.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.activity.KnowledgeActivity;
import com.example.wanandroid.model.Tree;

import java.util.ArrayList;
import java.util.List;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.InnerHolder> {
    private Context mContext;
    private List<Tree> treeList;

    public TreeAdapter(Context mContext, List<Tree> treeList) {
        this.mContext = mContext;
        this.treeList = treeList;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_tree, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        Tree tree = treeList.get(position);
        holder.tvTitle.setText(tree.getName());
        //体系文章点击事件
        holder.itemView.setOnClickListener(view -> {
            //跳转到相应的子类知识点
            Intent intent = new Intent(mContext, KnowledgeActivity.class);
            intent.putExtra("items",new ArrayList<>(tree.getChildren()));
            intent.putExtra("title", tree.getName());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return treeList.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public InnerHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv2_title_project);
        }
    }
}
