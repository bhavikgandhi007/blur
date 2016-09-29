package com.org.blaze.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.org.blaze.HomeActivity;
import com.org.blaze.PlaceListActivity;
import com.org.blaze.R;

/**
 * Created by Big_Scal on 9/26/2016.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private Context context;
    private String[] place_name;
    private TypedArray imageId, icon_id;
    private String selected;

    public PlaceAdapter(Context context, String[] place_name, TypedArray imageId, TypedArray icon_id) {
        this.context = context;
        this.place_name = place_name;
        this.imageId = imageId;
        this.icon_id = icon_id;

    }

    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlaceAdapter.ViewHolder holder, final int position) {
        holder.img_item.setImageDrawable(imageId.getDrawable(position));
        holder.txt_type.setText(place_name[position]);
        holder.txt_type.setCompoundDrawablesWithIntrinsicBounds(icon_id.getDrawable(position), null, null, null);
        if (selected != null) {
            if (!selected.equals(String.valueOf(position))) {
                holder.item_context_menu.setVisibility(View.GONE);
            }
        } else {
            holder.item_context_menu.setVisibility(View.GONE);
        }
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {

                } else {
                    if (holder.item_context_menu.isShown()) {
                        selected = null;
                        holder.item_context_menu.setVisibility(View.GONE);

                    } else {
                        selected = String.valueOf(position);
                        holder.item_context_menu.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                }
            }
        });
        holder.linear_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlaceListActivity.class);
                intent.putExtra("place_name",place_name[position]);
                context.startActivity(intent);
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
        ImageView img_item;
        TextView txt_type;
        LinearLayout item_context_menu,linear_list;
        private ItemClickListener clickListener;

        public ViewHolder(View view) {
            super(view);
            img_item = (ImageView) view.findViewById(R.id.img_item);
            txt_type = (TextView) view.findViewById(R.id.txt_type);
            item_context_menu = (LinearLayout) view.findViewById(R.id.item_context_menu);
            linear_list= (LinearLayout) view.findViewById(R.id.linear_list);
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
