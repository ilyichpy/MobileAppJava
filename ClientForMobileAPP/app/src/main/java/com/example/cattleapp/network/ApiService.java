package com.example.cattleapp.network;

import com.example.cattleapp.models.Animal;
import com.example.cattleapp.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/v1/send_code")
    Call<ResponseBody> register(@Body User user);

    @POST("api/v1/login")
    Call<User> login(@Body User user);

    @POST("api/v1/verify")
    Call<ResponseBody> verifyCode(@Body User user);

    @POST("api/v1/animals")
    Call<List<Animal>> getAnimals(@Body int ownerId);

    @POST("api/v1/add_animal")
    Call<Animal> addAnimal(@Body Animal animal);

    @POST("api/v1/update_animal")
    Call<Animal> updateAnimal(@Body Animal animal);

    @POST("api/v1/get_animal_by_id")
    Call<Animal> getAnimalById(@Body Animal animal);
}
