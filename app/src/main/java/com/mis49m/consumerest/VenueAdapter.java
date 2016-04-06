package com.mis49m.consumerest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class VenueAdapter extends ArrayAdapter<Venue> {
	
	public VenueAdapter(Context context, Venue[] venues) {
		super(context, R.layout.list_item, venues);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Venue venue = getItem(position);
		
		if(convertView==null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
		}
		
		TextView tvLabel = (TextView) convertView.findViewById(R.id.name);
		tvLabel.setText(venue.getName());
		
		return convertView;
	}	
	
}
