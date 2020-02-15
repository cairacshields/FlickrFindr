package com.example.flickrfindr.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flickrfindr.R;
import com.example.flickrfindr.service.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class FlickrItemAdapter extends RecyclerView.Adapter<FlickrItemAdapter.ViewHolder> {

    private List<Photo> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public FlickrItemAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<Photo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.flickr_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo item = mData.get(position);
        holder.textView.setText(item.getTitle());
        Glide.with(holder.imageView.getContext())
                .load("https://farm"+item.getFarm()+".staticflickr.com/"+item.getServer()+"/"+item.getId()+"_"+item.getSecret()+".jpg")
                .centerCrop()
                .override(1200, 800)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.flickr_item_text);
            imageView = itemView.findViewById(R.id.flick_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Photo getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}