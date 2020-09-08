package com.wherego.driver.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.wherego.driver.R;


public class PrivacyPolicyActivity extends AppCompatActivity {

    private ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        backArrow = findViewById(R.id.backArrow);

        backArrow.setOnClickListener(view -> onBackPressed());

    }
}
