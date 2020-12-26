package com.example.rentacarapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainPageRecyclerAdapter extends RecyclerView.Adapter<MainPageRecyclerAdapter.CarHolder> {

    private ArrayList<String> imageList;
    private ArrayList<String> markaList;
    private ArrayList<String> modelList;
    private ArrayList<String> vitesList;
    private ArrayList<String> yilList;
    private ArrayList<String> ucretList;

    public MainPageRecyclerAdapter(ArrayList<String> imageList, ArrayList<String> markaList, ArrayList<String> modelList, ArrayList<String> vitesList, ArrayList<String> yilList, ArrayList<String> ucretList) {
        this.imageList = imageList;
        this.markaList = markaList;
        this.modelList = modelList;
        this.vitesList = vitesList;
        this.yilList = yilList;
        this.ucretList = ucretList;

    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new CarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {

        holder.markaTextView.setText(markaList.get(position) + " " + modelList.get(position));
        holder.vitesTextView.setText(vitesList.get(position));
        holder.yilTextView.setText(yilList.get(position));
        holder.ucretTextView.setText(ucretList.get(position) + "₺/Günlük");
        Picasso.get().load(imageList.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return markaList.size();
    }


    class CarHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView markaTextView;
        TextView vitesTextView;
        TextView yilTextView;
        TextView ucretTextView;

        public CarHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.recyclerview_row_imageview);
            markaTextView = itemView.findViewById(R.id.recyclerview_row_marka);
            vitesTextView = itemView.findViewById(R.id.recyclerview_row_vites);
            yilTextView = itemView.findViewById(R.id.recyclerview_row_yil);
            ucretTextView = itemView.findViewById(R.id.recyclerview_row_ucret);
        }
    }



}
