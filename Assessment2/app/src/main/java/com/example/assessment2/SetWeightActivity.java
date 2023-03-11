package com.example.assessment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.assessment2.databinding.ActivitySetGenderBinding;
import com.example.assessment2.databinding.ActivitySetWeightBinding;

public class SetWeightActivity extends AppCompatActivity {

    ActivitySetWeightBinding binding;
    public static final String WEIGHT_KEY = "weight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetWeightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("Set weight");


        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    double weight = Double.parseDouble(binding.editTextNumberDecimal.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra(WEIGHT_KEY, weight);

                    setResult(RESULT_OK, intent);
                    finish();
                } catch (NumberFormatException ex) {
                    Toast.makeText(SetWeightActivity.this, "Enter correct weight", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


}