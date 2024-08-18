package com.example.stalozaidimai;

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

import com.example.stalozaidimai.helpers.Constants;
import com.example.stalozaidimai.helpers.Rest;
import com.example.stalozaidimai.model.Warehouse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
public class WarehousesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_warehouses);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String response = Rest.sendGet(Constants.GET_WAREHOUSES); // Assume GET_WAREHOUSES is the correct endpoint
                handler.post(() -> {
                    try {
                        if (!response.equals("Error") && !response.equals("")) {

                            // Parse JSON response to extract warehouse data
                            JSONArray jsonArray = new JSONArray(response);
                            List<Warehouse> listOfWarehouses = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Warehouse warehouse = new Warehouse();
                                warehouse.setTitle(jsonObject.getString("title"));
                                warehouse.setAddress(jsonObject.getString("address"));

                                listOfWarehouses.add(warehouse);
                            }

                            ListView warehousesListView = findViewById(R.id.warehousesListView);

                            ArrayAdapter<Warehouse> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfWarehouses);
                            warehousesListView.setAdapter(adapter);
                            warehousesListView.setOnItemClickListener((parent, view, position, id) -> {
                                // Handle warehouse item clicks
                                Warehouse selectedWarehouse = listOfWarehouses.get(position);
                                // Do something with the selected warehouse
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