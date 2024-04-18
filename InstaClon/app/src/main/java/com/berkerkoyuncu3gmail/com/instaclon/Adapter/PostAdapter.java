package com.berkerkoyuncu3gmail.com.instaclon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berkerkoyuncu3gmail.com.instaclon.Model.Posts;
import com.berkerkoyuncu3gmail.com.instaclon.databinding.RecyclerBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    ArrayList<Posts> postsArrayList;

    public PostAdapter(ArrayList<Posts> postsArrayList) {
        this.postsArrayList = postsArrayList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerBinding recyclerBinding = RecyclerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
            holder.recyclerBinding.recyclerViewUserEmail.setText(postsArrayList.get(position).userEmail);
            holder.recyclerBinding.recyclerViewComment.setText(postsArrayList.get(position).comment);
            Picasso.get().load(postsArrayList.get(position).downloadUrl).into(holder.recyclerBinding.recyclerViewUserImage);
    }

    @Override
    public int getItemCount() {
        return postsArrayList.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder{

        RecyclerBinding recyclerBinding;

        public PostHolder(RecyclerBinding recyclerBinding) {
            super(recyclerBinding.getRoot());
            this.recyclerBinding = recyclerBinding;
        }
    }

}
