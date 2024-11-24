package com.example.cattleapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cattleapp.R;


import com.example.cattleapp.adapters.AnimalAdapter;
import com.example.cattleapp.models.Animal;
import com.example.cattleapp.network.ApiClient;
import com.example.cattleapp.network.ApiService;
import com.example.cattleapp.utils.FeedingAlarmReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalDetailActivity extends AppCompatActivity {

    private EditText editTextName, editTextType, editTextAge, editTextFeedingTime;
    private Button buttonSave;

    private ApiService apiService;
    private int animalId;
    private Animal animal;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        editTextName = findViewById(R.id.editTextName);
        editTextType = findViewById(R.id.editTextType);
        editTextAge = findViewById(R.id.editTextAge);
        editTextFeedingTime = findViewById(R.id.editTextFeedingTime);
        buttonSave = findViewById(R.id.buttonSave);

        animalId = getIntent().getIntExtra("animalId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        apiService = ApiClient.getClient().create(ApiService.class);

        if (animalId != -1) {
            loadAnimalDetails();
        }

        buttonSave.setOnClickListener(v -> {
            saveAnimalDetails();
        });
    }

    private void loadAnimalDetails() {
        Animal animal = new Animal();
        animal.setId(animalId);
        Call<Animal> call = apiService.getAnimalById(animal);
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if(response.isSuccessful()) {
                    Animal animalList = response.body();

                    if (editTextName.getText().toString().trim().isEmpty()) {
                        editTextName.setText(animalList.getName());
                    }
                    if (editTextType.getText().toString().trim().isEmpty()) {
                        editTextType.setText(animalList.getType());
                    }
                    if (editTextAge.getText().toString().trim().isEmpty()) {
                        editTextAge.setText(String.valueOf(animalList.getAge()));
                    }
                    if(editTextFeedingTime.getText().toString().trim().isEmpty()) {
                        editTextFeedingTime.setText(String.valueOf(animalList.getFeedingTime()));
                    }
                    buttonSave.setOnClickListener(v -> {
                        updateAnimalDetails(animalList, editTextName.getText().toString(), editTextType.getText().toString(), Integer.parseInt(editTextAge.getText().toString()), editTextFeedingTime.getText().toString());
                    });

                    Toast.makeText(AnimalDetailActivity.this, "Данные успешно загружены", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AnimalDetailActivity.this, "Ошибка загрузки животных", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(AnimalDetailActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAnimalDetails() {
        String name = editTextName.getText().toString().trim();
        String type = editTextType.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String feedingTime = editTextFeedingTime.getText().toString().trim();

        if (name.isEmpty() || type.isEmpty() || ageStr.isEmpty() || feedingTime.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);


        // Добавление нового животного
        animal = new Animal(name, age, type, feedingTime, userId);

        Call<Animal> call = apiService.addAnimal(animal);
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AnimalDetailActivity.this, "Животное добавлено", Toast.LENGTH_SHORT).show();
                    setFeedingAlarm(feedingTime, name);
                    Intent intent = new Intent(AnimalDetailActivity.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } else {
                    Toast.makeText(AnimalDetailActivity.this, "Ошибка при добавлении", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(AnimalDetailActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAnimalDetails(Animal animal, String name, String type, int age, String time) {
        animal.setId(animal.getId());
        animal.setName(name);
        animal.setType(type);
        animal.setAge(age);
        animal.setFeedingTime(time);

        Call<Animal> call = apiService.updateAnimal(animal);
        call.enqueue(new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AnimalDetailActivity.this, "Информация обновлена", Toast.LENGTH_SHORT).show();
                    setFeedingAlarm(animal.getFeedingTime(), animal.getName());
                    Intent intent = new Intent(AnimalDetailActivity.this, MainActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                } else {
                    Toast.makeText(AnimalDetailActivity.this, "Ошибка при обновлении", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(AnimalDetailActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setFeedingAlarm(String feedingTime, String animalName) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Calendar feedingCalendar = Calendar.getInstance();
            Calendar currentCalendar = Calendar.getInstance();
            feedingCalendar.setTime(sdf.parse(feedingTime));

            feedingCalendar.set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR));
            feedingCalendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));
            feedingCalendar.set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH));

            if (feedingCalendar.before(Calendar.getInstance())) {
                feedingCalendar.add(Calendar.DAY_OF_MONTH, 1);
            }

            Intent intent = new Intent(this, FeedingAlarmReceiver.class);
            intent.putExtra("animalName", animalName);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        feedingCalendar.getTimeInMillis(),
                        pendingIntent
                );
            }

            Toast.makeText(this, "Будильник на кормление установлен", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Неверный формат времени", Toast.LENGTH_SHORT).show();
        }
    }
}
