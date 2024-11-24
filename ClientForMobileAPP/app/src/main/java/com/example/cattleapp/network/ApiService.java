package com.example.cattleapp.network;

import com.example.cattleapp.models.Animal;
import com.example.cattleapp.models.Farmer;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/v1/send_code")
    Call<ResponseBody> register(@Body Farmer farmer);

    @POST("api/v1/login")
    Call<Farmer> login(@Body Farmer farmer);

    @POST("api/v1/verify")
    Call<ResponseBody> verifyCode(@Body Farmer farmer);

    @POST("api/v1/animals")
    Call<List<Animal>> getAnimals(@Body int ownerId);

    @POST("api/v1/add_animal")
    Call<Animal> addAnimal(@Body Animal animal);

    @POST("api/v1/update_animal")
    Call<Animal> updateAnimal(@Body Animal animal);

    @POST("api/v1/get_animal_by_id")
    Call<Animal> getAnimalById(@Body Animal animal);
}
