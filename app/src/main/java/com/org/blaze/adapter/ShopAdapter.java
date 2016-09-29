package com.org.blaze.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.org.blaze.R;

/**
 * Created by Big_Scal on 9/28/2016.
 */
public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private Context context;
    private String[] place_name;
    private TypedArray imageId;
    private String selected;

    public ShopAdapter(Context context, String[] place_name, TypedArray imageId) {
        this.context = context;
        this.place_name = place_name;
        this.imageId = imageId;

    }

    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopAdapter.ViewHolder holder, int position) {
        holder.place_img.setImageDrawable(imageId.getDrawable(position));
        holder.place_name.setText(place_name[position]);
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {

                } else {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return place_name.length;
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView place_img;
        TextView place_name;
        LinearLayout item_context_menu, linear_list;
        private ItemClickListener clickListener;

        public ViewHolder(View view) {
            super(view);
            place_img = (ImageView) view.findViewById(R.id.place_img);
            place_name = (TextView) view.findViewById(R.id.place_name);
            view.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
    }

}
