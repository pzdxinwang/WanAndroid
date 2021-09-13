package com.example.wanandroid.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.HolderView> {
    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HolderView extends RecyclerView.ViewHolder {
        public HolderView(@NonNull View itemView) {
            super(itemView);
        }
    }
}
