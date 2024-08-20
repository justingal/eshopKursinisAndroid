package com.example.stalozaidimai.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.stalozaidimai.R;
import com.example.stalozaidimai.helpers.Constants;
import com.example.stalozaidimai.helpers.Rest;
import com.example.stalozaidimai.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String response = Rest.sendGet(Constants.GET_USERS); // Assume GET_USERS is the correct endpoint
                handler.post(() -> {
                    try {
                        if (!response.equals("Error") && !response.equals("")) {

                            // Parse JSON response to extract user data
                            JSONArray jsonArray = new JSONArray(response);
                            List<User> listOfUsers = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                User user = new User();

                                user.setName(jsonObject.getString("name"));
                                user.setSurname(jsonObject.getString("surname"));
                                // Add other user fields as needed...

                                listOfUsers.add(user);
                            }

                            // Find ListView by its ID
                            ListView usersListView = findViewById(R.id.usersListView);

                            ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfUsers);
                            usersListView.setAdapter(adapter);
                            usersListView.setOnItemClickListener((parent, view, position, id) -> {
                                // Handle user item clicks
                                User selectedUser = listOfUsers.get(position);
                                // Do something with the selected user
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
