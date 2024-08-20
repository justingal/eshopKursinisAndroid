package com.example.stalozaidimai.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stalozaidimai.R;
import com.example.stalozaidimai.helpers.Constants;
import com.example.stalozaidimai.helpers.Rest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WarehousesActivity extends AppCompatActivity {

    private EditText editWarehouseId;
    private EditText editWarehouseTitle;
    private EditText editWarehouseAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouses);

        editWarehouseId = findViewById(R.id.editWarehouseID);
        editWarehouseTitle = findViewById(R.id.editWarehouseTitle);
        editWarehouseAddress = findViewById(R.id.editWarehouseAddress);

        loadWarehouses();
    }

    private void loadWarehouses() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String response = Rest.sendGet(Constants.GET_WAREHOUSES);
                handler.post(() -> {
                    try {
                        if (!response.equals("Error") && !response.equals("")) {

                            JSONArray jsonArray = new JSONArray(response);
                            List<String> warehouseDisplayList = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                int id = jsonObject.getInt("id");
                                String title = jsonObject.getString("title");
                                String address = jsonObject.getString("address");

                                // Sukurkite rodinį eilutėje, kuri apjungia ID, pavadinimą ir adresą
                                String displayText = "ID:" + id + "  " + title + " " + address;

                                warehouseDisplayList.add(displayText);
                            }

                            ListView warehousesListView = findViewById(R.id.warehousesListView);

                            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, warehouseDisplayList);
                            warehousesListView.setAdapter(adapter);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateWarehouse(View view) {
        String idText = editWarehouseId.getText().toString();
        if (idText.isEmpty()) {
            Toast.makeText(this, "Please enter a warehouse ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedWarehouseId = Integer.parseInt(idText);

        String newTitle = editWarehouseTitle.getText().toString();
        String newAddress = editWarehouseAddress.getText().toString();

        JSONObject warehouseJson = new JSONObject();
        try {
            warehouseJson.put("title", newTitle);
            warehouseJson.put("address", newAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Constants.BASE_URL + "/updateWarehouse/" + selectedWarehouseId;
                String response = Rest.sendPut(url, warehouseJson.toString());

                handler.post(() -> {
                    if (response != null && !response.equals("Error")) {
                        Toast.makeText(WarehousesActivity.this, "Warehouse updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(WarehousesActivity.this, "Error updating warehouse", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                handler.post(() -> Toast.makeText(WarehousesActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                e.printStackTrace();
            }
        });
    }

    public void deleteWarehouse(View view) {
        String idText = editWarehouseId.getText().toString();
        if (idText.isEmpty()) {
            Toast.makeText(this, "Please enter a warehouse ID.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedWarehouseId = Integer.parseInt(idText);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String url = Constants.BASE_URL + "/deleteWarehouse/" + selectedWarehouseId;
                String response = Rest.sendDelete(url);

                handler.post(() -> {
                    if (response != null && !response.equals("Error")) {
                        Toast.makeText(WarehousesActivity.this, "Warehouse deleted successfully", Toast.LENGTH_SHORT).show();
                        loadWarehouses();  // Reload the list after deletion
                    } else {
                        Toast.makeText(WarehousesActivity.this, "Error deleting warehouse", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                handler.post(() -> Toast.makeText(WarehousesActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                e.printStackTrace();
            }
        });
    }
}
