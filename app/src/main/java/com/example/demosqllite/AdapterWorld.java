package com.example.demosqllite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterWorld extends ArrayAdapter<World> {
    Activity context;
    int resource;
    List<World> object;
    public AdapterWorld(@NonNull Activity context, int resource, @NonNull List<World> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.object = objects;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        view = layoutInflater.inflate(resource, null);

        TextView tvInfor = view.findViewById(R.id.tvInfor);

        World world = object.get(position);

        tvInfor.setText(world.toString());

        return view;
    }
}
