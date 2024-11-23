package com.example.cattleapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.cattleapp.R;


public class AnimalViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewAnimalName;
    public TextView textViewAnimalDetails;

    public AnimalViewHolder(View itemView) {
        super(itemView);

        textViewAnimalName = itemView.findViewById(R.id.textViewAnimalName);
        textViewAnimalDetails = itemView.findViewById(R.id.textViewAnimalDetails);
    }
}
