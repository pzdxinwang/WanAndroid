package com.example.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.activity.ArticleDetailActivity;
import com.example.wanandroid.model.Project;
import com.example.wanandroid.utils.HttpUtil;
import com.example.wanandroid.utils.SharePreferencesUtil;
import com.example.wanandroid.utils.ToastUtil;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.InnerHolder> {
    private Context mContext;
    private List<Project> projectList;

    private ImageView pic;

    public ProjectAdapter(Context mContext, List<Project> projectList) {
        this.mContext = mContext;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_project, parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        Project project = projectList.get(position);
        holder.author.setText(project.getAuthor());
        holder.classification.setText(project.getSuperChapterName());
        holder.decs.setText(project.getDesc());
        holder.data.setText(project.getNiceShareDate());

        //使用glide来加载图片
        Glide.with(mContext).load(project.getEnvelopePic())
                .thumbnail(0.1f)
                .into(pic);

        //项目的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ArticleDetailActivity.class);
                intent.putExtra("title", project.getTitle());
                intent.putExtra("link", project.getProjectLink());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (projectList.size() != 0) {
            return projectList.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private TextView author, data, decs, classification;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.tv_project_author2);
            data = itemView.findViewById(R.id.tv_project_data);
            pic = itemView.findViewById(R.id.iv_project_pic);
            decs = itemView.findViewById(R.id.tv_project_description);
            classification = itemView.findViewById(R.id.tv_project_classification2);
        }
    }
}
