package com.vbazh.weatherapp_mvp.screens.citylist.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vbazh.weatherapp_mvp.R;
import com.vbazh.weatherapp_mvp.data.entities.City;
import com.vbazh.weatherapp_mvp.utils.BaseUtils;

import java.util.List;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> mCities;
    private Context mContext;
    private OnItemClickListener mListener;

    public CityAdapter(Context mContext, List<City> mCities, OnItemClickListener listener) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.mListener = listener;
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {

        TextView textName;

        public CityViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_city_name);
        }
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);

        return new CityAdapter.CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CityViewHolder holder, int position) {

        holder.textName.setText(mCities.get(holder.getAdapterPosition()).getCityName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(mCities.get(holder.getAdapterPosition()).getCityName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    public void replaceData(List<City> cities) {
        mCities = BaseUtils.checkNotNull(cities);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {

        mCities.remove(position);
        notifyItemRemoved(position);

    }

    public interface OnItemClickListener {
        void onItemClick(String cityName);
    }

}
