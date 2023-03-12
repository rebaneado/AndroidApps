package com.example.assessment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.assessment3.databinding.FragmentWelcomeBinding;

public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeListener, RegistrationFragment.RegistrationListener, SetGenderFragment.setGenderListener, ProfileFragment.ProfileListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.rootView, new WelcomeFragment()).commit();
    }
    @Override
    public void goToRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegistrationFragment(), "reg-fragment")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void goToSetGenderFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SetGenderFragment() )
                .addToBackStack(null).commit();

    }

    @Override
    public void goToProfile(Profile profile) {
        getSupportFragmentManager().beginTransaction().replace(R.id.rootView, ProfileFragment.newInstance(profile)).addToBackStack(null).commit();
    }

    @Override
    public void sendSelectedGender(String gender) {
        RegistrationFragment fragment = (RegistrationFragment) getSupportFragmentManager().findFragmentByTag("reg-fragment");
        if (fragment != null) {
            fragment.setSelectedGender(gender);
        }
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void cancelSetGender() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void closeProdileFragment() {
        getSupportFragmentManager().popBackStack();

    }
}