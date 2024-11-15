package ru.zuev.mobilefront.registration;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import ru.zuev.mobilefront.MainActivity;
import ru.zuev.mobilefront.R;

public class RegistrationFragment extends Fragment {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView loginLink;

    public String postUrl= "http://10.0.2.2:8081/v1/api/email/send_code";
    public String postBody="{\n\"userEmail\":\"ilyaszuev25@gmail.com\"\n}"; // захардкожено потом можно будет сделать чтобы на любой емейл
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public RegistrationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        // Инициализация полей ввода и кнопки
        emailEditText = view.findViewById(R.id.editTextEmail);
        passwordEditText = view.findViewById(R.id.editTextPassword);
        confirmPasswordEditText = view.findViewById(R.id.editTextPasswordAgain);
        registerButton = view.findViewById(R.id.registrationButton);

        // Устанавливаем слушатель для кнопки регистрации
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRegistration();
            }
        });

        return view;
    }

    private void handleRegistration() {
        // Получаем введенные данные
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String secondPassword = confirmPasswordEditText.getText().toString();

        Future<Integer> futureStatusCode = executorService.submit(() -> {
            return getEmailCode();  // Синхронный запрос
        });

        // Обработка результата в основном потоке
        new Thread(() -> {
            try {
                int statusCode = futureStatusCode.get();  // Получаем статус-код из запроса
                getActivity().runOnUiThread(() -> {
                    if (statusCode == 200) {
                        Toast.makeText(getActivity(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Ошибка регистрации: " + statusCode, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    private boolean registerUserInDatabase(String email, String password) {
        // Реализуем сохранение данных в базе данных
        // Для примера, всегда возвращаем true
        return true;
    }

    private void showLoginScreen() {
        // Переход к экрану входа
        ((MainActivity) getActivity()).showLoginFragment();
    }

    private int getEmailCode() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        RequestBody body = RequestBody.create(JSON, postBody);

        Request request = new Request.Builder()
                .url(postUrl)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            // Выполняем запрос синхронно с помощью execute()
            Response response = client.newCall(request).execute();
            int statusCode = response.code();

            // Закрываем тело ответа, чтобы освободить ресурсы
            if (response.body() != null) {
                response.body().close();
            }

            // Возвращаем статус-код
            return statusCode;
        } catch (IOException e) {
            Log.e("Request Failure", "Failed to send request: " + e.getMessage(), e);
            return 501;  // Код для ошибки соединения
        }
    }
}