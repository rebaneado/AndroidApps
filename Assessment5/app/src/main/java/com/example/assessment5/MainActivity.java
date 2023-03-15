package com.example.assessment5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.assessment5.models.Auth;
import com.example.assessment5.models.Forum;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener, RegisterFragment.RegisterListener, CreateForumFragment.CreateForumListener, Serializable, ForumsFragment.ForumsFragmentListener {
    Auth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Check if the user is authenticated or no..
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void authSuccessful() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new ForumsFragment())
                .commit();
    }

    @Override
    public void gotoLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new LoginFragment())
                .commit();
    }

    @Override
    public void authSuccessful(Auth auth) {
        Log.d("demo", "authSuccessful: ");
        this.mAuth = auth;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView,ForumsFragment.newInstance(auth)).commit();

    }

    @Override
    public void gotoRegister() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new RegisterFragment())
                .commit();
    }

    @Override
    public void cancelForumCreate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void completedForumCreate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void logout() {
        //TODO: at 45 mins

    }

    @Override
    public void gotoCreateForum(Auth auth) {
        getSupportFragmentManager().beginTransaction().replace(R.id.rootView, CreateForumFragment.newInstance(mAuth))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void gotoForumMessages(Forum forum) {

    }
}