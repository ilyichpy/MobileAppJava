package ru.zuev.mobilefront.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import ru.zuev.mobilefront.MainActivity;
import ru.zuev.mobilefront.R;

public class RegistrationFragment extends Fragment {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView loginLink;

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

        // Валидация данных
        if (password.isEmpty() || email.isEmpty()) {
            Toast.makeText(getActivity(), "Заполните все ячейки", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(secondPassword)) {
            Toast.makeText(getActivity(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка регистрации в базе данных
        boolean isUserRegistered = registerUserInDatabase(email, password);

        if (isUserRegistered) {
            Toast.makeText(getActivity(), "Registration successful", Toast.LENGTH_SHORT).show();
            // После регистрации, можно перейти обратно на экран входа
            ((MainActivity) getActivity()).showLoginFragment();
        } else {
            Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean registerUserInDatabase(String email, String password) {
        // Реализуем сохранение данных в базе данных
        // Для примера, всегда возвращаем true
        return true;
    }

    private void showLoginScreen() {
        // Переход к экрану входа
        ((MainActivity) getActivity()).showLoginFragment();
    }
}