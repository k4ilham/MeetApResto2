package com.hamaar.meetapresto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hamaar.meetapresto.Activity.DetailMenuActivity;
import com.hamaar.meetapresto.Model.Menu;
import com.hamaar.meetapresto.R;

import java.util.ArrayList;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<Menu> list;

    public MenuAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_resto, parent, false);
        final MenuAdapter.ViewHolder holder = new MenuAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Menu menu = list.get(holder.getAdapterPosition());

        holder.tvName.setText(menu.getName());
        holder.tvPrice.setText(menu.getCost());

        Glide.with(context)
                .load(menu.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(holder.ivMenu);

        // itemview jika di klik
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_tampil = new Intent(context, DetailMenuActivity.class);
                i_tampil.putExtra("key_menu", menu);
                context.startActivity(i_tampil);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(Menu r) {
        list.add(r);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<Menu> moveResults) {
        for (Menu result : moveResults) {
            add(result);
        }
    }

    private void remove(Menu r) {
        int position = list.indexOf(r);
        if (position > -1) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void clearAll() {
        if (!list.isEmpty()) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    private Menu getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivMenu;
        public TextView tvName;
        public TextView tvPrice;

        private ViewHolder(View itemView) {
            super(itemView);

            ivMenu = itemView.findViewById(R.id.ivMenu);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
