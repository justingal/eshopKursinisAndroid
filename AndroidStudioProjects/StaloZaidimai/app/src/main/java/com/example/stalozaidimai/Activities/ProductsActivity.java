package com.example.stalozaidimai.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
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
import com.example.stalozaidimai.model.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                String response = Rest.sendGet(Constants.GET_PRODUCTS);
                handler.post(() -> {
                    try {
                        if (!response.equals("Error") && !response.equals("")) {

                            // Rankinis JSON apdorojimas
                            JSONArray jsonArray = new JSONArray(response);
                            List<Product> listOfProducts = new ArrayList<>();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Product product = new Product();
                                product.setTitle(jsonObject.getString("title"));
                                product.setPrice(jsonObject.getDouble("price"));

                                listOfProducts.add(product);
                            }

                            // Rasti ListView pagal jo ID
                            ListView productListElement = findViewById(R.id.productsListView);

                            ArrayAdapter<Product> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfProducts);
                            productListElement.setAdapter(adapter);
                            productListElement.setOnItemClickListener((parent, view, position, id) -> {
                                // Apdoroti elementų paspaudimus
                                Product selectedProduct = listOfProducts.get(position);
                                // Veiksmai su pasirinktu produktu
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

    public void openUserWindow(View view) {
        // Create an Intent to start the UserActivity
        Intent intent = new Intent(ProductsActivity.this, UsersActivity.class);
        // Start the new activity
        startActivity(intent);
    }

    public void openWarehouseTab(View view) {
        // Create an Intent to start the UserActivity
        Intent intent = new Intent(ProductsActivity.this, WarehousesActivity.class);
        // Start the new activity
        startActivity(intent);
    }
}
