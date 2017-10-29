package com.granzotto.marcio.loadsmartchallenge.modules.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.granzotto.marcio.loadsmartchallenge.R;
import com.granzotto.marcio.loadsmartchallenge.utils.adapters.CityAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;

	private CityAdapter adapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		setupRecyclerView();
	}

	private void setupRecyclerView() {
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new CityAdapter();
		recyclerView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@OnClick(R.id.addButton)
	protected void onAddButtonClicked() {
		//TODO
		Log.d("FAB", "The add button was clicked!!");
	}
}