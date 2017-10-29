package com.granzotto.marcio.loadsmartchallenge.utils.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.granzotto.marcio.loadsmartchallenge.R;
import com.granzotto.marcio.loadsmartchallenge.models.City;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

	private List<City> cities = Collections.emptyList();
	private HashMap<String, Double> temperatures = new HashMap<>();

	@Override
	public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_cell, parent, false);
		return new CityViewHolder(view);
	}

	@Override
	public void onBindViewHolder(CityViewHolder holder, int position) {
		City city = cities.get(position);
		holder.onBind(city, temperatures.get(city.getId()));
	}

	@Override
	public int getItemCount() {
		return cities.size();
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public void setTemperatures(HashMap<String, Double> temperatures) {
		this.temperatures = temperatures;
	}

	//region: ViewHolder

	static class CityViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.imageView)
		ImageView imageView;
		@BindView(R.id.titleTextView)
		TextView titleTextView;
		@BindView(R.id.temperatureTextView)
		TextView tempTextView;

		private Context context;

		CityViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
			context = itemView.getContext();
		}

		void onBind(City city, Double currentTemperature) {
			if (currentTemperature != null) {
				tempTextView.setText(context.getString(R.string.temperature_value, currentTemperature));
			} else {
				tempTextView.setText("");
			}
			String name = city.getName() + ", " + city.getStateName();
			titleTextView.setText(name);
			Glide.with(context)
					.load(city.getImageUrl())
					.centerCrop()
					.placeholder(R.drawable.ic_location_city)
					.crossFade()
					.into(imageView);
		}
	}

	//endregion
}
