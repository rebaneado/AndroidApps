package com.example.assessment2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.assessment2.databinding.ActivityMainBinding;
import com.example.assessment2.databinding.ActivityProfileBinding;

public class MainActivity extends AppCompatActivity {
    Double weight = null;
    String gender;

    private ActivityResultLauncher<Intent> startSetGenderForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        gender = data.getStringExtra(SetGenderActivity.GENDER_KEY);
                        binding.textViewGender.setText(String.valueOf(gender));

                    } else {
                        // Handle the error
                    }
                }
            });

    private ActivityResultLauncher<Intent> startSetWeightForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        weight = data.getDoubleExtra(SetWeightActivity.WEIGHT_KEY, 0);
                        binding.textViewWeight.setText(String.valueOf(weight) + " Lbs");

                    } else {
                        // Handle the error
                    }
                }
            });

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("main");


        binding.buttonSetGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetGenderActivity.class);
                startSetGenderForResult.launch(intent);

            }
        });


        binding.buttonSetWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetWeightActivity.class);
                startSetWeightForResult.launch(intent);

            }
        });

        binding.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = null;
                weight = null;
                binding.textViewGender.setText("");
                binding.textViewWeight.setText("");

            }
        });
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weight == null) {
                    Toast.makeText(MainActivity.this, "Please enter correct weight", Toast.LENGTH_SHORT).show();

                } else if (gender == null) {
                    Toast.makeText(MainActivity.this, "Please enter correct ge nder", Toast.LENGTH_SHORT).show();

                } else {
                    Profile profile = new Profile(gender, weight);
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra(ProfileActivity.PROFILE_KEY, profile);
                    startActivity(intent);

                }
            }
        });

    }
}