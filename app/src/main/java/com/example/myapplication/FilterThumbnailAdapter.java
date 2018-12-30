package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FilterThumbnailAdapter extends RecyclerView.Adapter<FilterThumbnailAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvfilterName;
        public ImageView ivfilterImg;

        public ViewHolder(View itemView){
            super(itemView);

            tvfilterName = itemView.findViewById(R.id.filterName);
            ivfilterImg = itemView.findViewById(R.id.filterThumbnail);
        }
    }

    private ArrayList<FilteredThumbnail> thumbnails;

    public FilterThumbnailAdapter(ArrayList<FilteredThumbnail> newThumbnails){
        this.thumbnails = newThumbnails;
    }

    @Override
    public FilterThumbnailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.filter_thumbnail, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(FilterThumbnailAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        FilteredThumbnail thumbnail = thumbnails.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.tvfilterName;
        textView.setText(thumbnail.getFilterType());
        ImageView imageView = viewHolder.ivfilterImg;
        imageView.setImageBitmap(thumbnail.getImgBP());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return thumbnails.size();
    }
}