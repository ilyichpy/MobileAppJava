package com.example.cattleapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cattleapp.R;

import com.example.cattleapp.models.Farmer;
import com.example.cattleapp.network.ApiClient;
import com.example.cattleapp.network.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends AppCompatActivity {

    private TextView textViewInstructions;
    private EditText editTextCode;
    private Button buttonVerify;

    private ApiService apiService;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        textViewInstructions = findViewById(R.id.textViewInstructions);
        editTextCode = findViewById(R.id.editTextCode);
        buttonVerify = findViewById(R.id.buttonVerify);

        email = getIntent().getStringExtra("email");

        apiService = ApiClient.getClient().create(ApiService.class);

        buttonVerify.setOnClickListener(v -> {
            String code = editTextCode.getText().toString().trim();
            if (code.isEmpty()) {
                Toast.makeText(VerificationActivity.this, "Введите код", Toast.LENGTH_SHORT).show();
            } else {
                verifyCode(code);
            }
        });
    }

    private void verifyCode(String code) {
        Farmer farmer = new Farmer();
        farmer.setEmail(email);
        farmer.setCode(code);

        Call<ResponseBody> call = apiService.verifyCode(farmer);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(VerificationActivity.this, "Успешно подтверждено", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(VerificationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(VerificationActivity.this, "Неверный код", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(VerificationActivity.this, "Ошибка сети: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
