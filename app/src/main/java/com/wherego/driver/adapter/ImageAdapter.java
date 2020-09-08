package com.wherego.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.wherego.driver.R;
import com.wherego.driver.model.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context context;
    private List<Image> images;
    private LayoutInflater inflater;

    public ImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        images = new ArrayList<>();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(inflater.inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final Image image = images.get(position);
        Picasso.get()
                .load(image.getPath())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setData(List<Image> images) {
        this.images.clear();
        if (images != null) {
            this.images.addAll(images);
        }
        notifyDataSetChanged();
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_thumbnail);
        }
    }


}
