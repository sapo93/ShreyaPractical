package com.example.shreya.shreyapractical.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.shreya.shreyapractical.R;
import com.example.shreya.shreyapractical.model.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchResultAdapter extends RecyclerView.Adapter {

    private List<Video> dataList=new ArrayList<>();
    private Context context;
    private OnVideoClickListener mListener;

//    public SearchResultAdapter(Context context, List<Video> dataList) {
//        this.dataList = dataList;
//        this.context = context;
//    }
    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Video> videos){
        dataList.clear();
        dataList.addAll(videos);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_video_view, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchResultViewHolder) {
            final SearchResultViewHolder viewHolder = (SearchResultViewHolder) holder;
            Glide.with(context)
                    .asBitmap()
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            viewHolder.ivSearch.setImageBitmap(resource);
                            return false;
                        }
                    })
                    .thumbnail(0.5f)
                    .load(dataList.get(position).getImages().getImagePath().getUrl())
                    .into(viewHolder.ivSearch);

            viewHolder.tvTitle.setText(dataList.get(position).getTitle());
            viewHolder.itemListener = mListener;
            viewHolder.item = dataList.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivSearch)
        ImageView ivSearch;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        private OnVideoClickListener itemListener;
        private Video item;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemListener != null) {
                        itemListener.onVideoSelect(item);
                    }
                }
            });
        }
    }

    public void setOnVideoClickListener(OnVideoClickListener onVideoCLickListener) {
        mListener = onVideoCLickListener;
    }

    public interface OnVideoClickListener {
        void onVideoSelect(Video video);
    }
}