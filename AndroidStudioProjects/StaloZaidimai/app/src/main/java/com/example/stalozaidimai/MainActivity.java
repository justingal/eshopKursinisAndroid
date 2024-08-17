package com.example.stalozaidimai;

import static com.example.stalozaidimai.helpers.Constants.VALIDATE_USER;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.stalozaidimai.helpers.Constants;
import com.example.stalozaidimai.helpers.Rest;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void validateUser(View view) {
        TextView loginField = findViewById(R.id.loginField);
        TextView passwordField = findViewById(R.id.passwordField);

        String username = loginField.getText().toString();
        String password = passwordField.getText().toString();

        // Sukurkite JSON objektą su vartotojo prisijungimo duomenimis

        String jsonData = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        System.out.println(jsonData);
        executor.execute(() -> {
            try {
                String response = Rest.sendPost(Constants.VALIDATE_USER, jsonData);
                handler.post(() -> {
                    if (response != null && !response.equals("Error")) {
                        // Check for specific expected response if necessary, such as a JSON key
                        if (response.contains("Success")) { // Example: Check if response contains specific key or message
                            Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid login credentials.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Error connecting to server. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                handler.post(() -> Toast.makeText(MainActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        });

    }
    /*
    private class UserValidation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String jsonData = params[0];
            try {
                // Išsiųsti POST užklausą
                return Rest.sendPost(Constants.VALIDATE_USER, jsonData);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && !result.equals("Error")) {
                // Jei autentifikacija sėkminga, nukreipkite vartotoją į ProductsActivity
                Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                startActivity(intent);
            } else {
                // Jei autentifikacija nesėkminga, rodyti klaidos pranešimą
                Toast.makeText(MainActivity.this, "Neteisingi prisijungimo duomenys", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
}
