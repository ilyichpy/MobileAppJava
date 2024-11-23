package com.example.cattleapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.cattleapp.R;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cattleapp.activities.AnimalDetailActivity;
import com.example.cattleapp.models.Animal;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalViewHolder> {

    private List<Animal> animalList;
    private Context context;

    public AnimalAdapter(List<Animal> animalList, Context context) {
        this.animalList = animalList;
        this.context = context;
    }

    @Override
    public AnimalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);

        holder.textViewAnimalName.setText(animal.getName());
        holder.textViewAnimalDetails.setText("Тип: " + animal.getType() + ", Возраст: " + animal.getAge() + " лет");

        // Обработка нажатия на элемент списка
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, AnimalDetailActivity.class);
            intent.putExtra("animalId", animal.getId());
            intent.putExtra("userId", animal.getOwnerId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }
}
