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

public class LoginFragment extends Fragment {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerLink;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Инициализация элементов UI
        usernameEditText = rootView.findViewById(R.id.editTextUsername);
        passwordEditText = rootView.findViewById(R.id.editTextPassword);
        loginButton = rootView.findViewById(R.id.loginButton);
        registerLink = rootView.findViewById(R.id.registerLink);

        // Обработчик кнопки входа
        loginButton.setOnClickListener(v -> handleLogin());

        // Переход к экрану регистрации
        registerLink.setOnClickListener(v -> showRegistrationScreen());

        return rootView;
    }

    private void handleLogin() {
        // Получаем введенные данные
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Валидация данных
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка пользователя через базу данных
        boolean isUserValid = checkUserInDatabase(username, password);

        if (isUserValid) {
            // Переход на главный экран
            // Например, можно начать новую активность
            Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkUserInDatabase(String username, String password) {
        // Здесь нужно будет выполнить запрос к базе данных
        // Для примера, представим, что проверка успешна если имя пользователя == "test" и пароль == "123"
        return "test".equals(username) && "123".equals(password);
    }

    private void showRegistrationScreen() {
        // Переход на экран регистрации
        ((MainActivity) getActivity()).showRegistrationFragment();
    }
}