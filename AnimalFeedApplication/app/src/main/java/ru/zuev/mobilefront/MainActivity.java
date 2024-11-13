package ru.zuev.mobilefront;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ru.zuev.mobilefront.registration.LoginFragment;
import ru.zuev.mobilefront.registration.RegistrationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Если состояние отсутствует (т.е. первый запуск), показываем экран входа
        if (savedInstanceState == null) {
            showLoginFragment();
        }
    }

    // Показать экран входа
    public void showLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, loginFragment);
        transaction.commit();
    }

    // Показать экран регистрации
    public void showRegistrationFragment() {
        RegistrationFragment registrationFragment = new RegistrationFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, registrationFragment);
        transaction.commit();
    }
}