package com.company.moon;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    private Spinner companiesSpinner;
    private Spinner productsSpinner;
    private ImageView filter;
    private ImageView sort;
    private RecyclerView recyclerView;

    private ArrayAdapter<String> companyAdapter;
    private ArrayAdapter<String> productAdapter;
    private AdapterProductsHomeFragment adapterProductsHomeFragment;

    private ArrayList<String> companies;
    private ArrayList<String> products;
    private ArrayList<ModelProductDetail> displayProducts;

    // API parameters
    private String productType, companiesType;

    private SQLiteDatabase productCompanyDatabase;

    private ApiRequest apiRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        // Finding the views
        companiesSpinner = view.findViewById(R.id.companies_spinner_home_fragment);
        productsSpinner = view.findViewById(R.id.products_spinner_home_fragment);
        filter = view.findViewById(R.id.filter_home_fragment);
        sort = view.findViewById(R.id.sort_home_fragment);
        recyclerView = view.findViewById(R.id.recycler_view_home_fragment);

        apiRequest = ApiRequest.getInstance();

        productCompanyDatabase = getActivity().openOrCreateDatabase("ProductCompanyDatabase", Context.MODE_PRIVATE, null);
        productCompanyDatabase.execSQL("DELETE FROM Companies");
        productCompanyDatabase.execSQL("DELETE FROM Products");

        displayProducts = new ArrayList<>();

        // Spinner Adapters
        companies = new ArrayList<>();
        companies.add("Companies");
        companyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, companies);
        companiesSpinner.setAdapter(companyAdapter);
        productCompanyDatabase.execSQL("INSERT INTO Companies(Name, ID) VALUES('Companies', '0')");

        products = new ArrayList<>();
        products.add("Products");
        productAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, products);
        productsSpinner.setAdapter(productAdapter);
        productCompanyDatabase.execSQL("INSERT INTO Products(Name, ID) VALUES('Products', '0')");

        // Recycler View Adapter
        adapterProductsHomeFragment = new AdapterProductsHomeFragment(getActivity(), displayProducts, getActivity().getSupportFragmentManager());
        recyclerView.setAdapter(adapterProductsHomeFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Default values of variables
        productType = "0";
        companiesType = "0";

        // Load company names and product names through APIs
        loadCompanyNames();
        loadProductNames();

        // Clicks
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        productsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = productCompanyDatabase.rawQuery("SELECT * FROM Products WHERE Name=?",
                        new String[] {products.get(position)});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    productType = cursor.getString(cursor.getColumnIndex("ID"));
                    Log.d("sqlite:", "onItemSelected: " + productType);
                }
                displayProducts.clear();
                loadProductDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        companiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = productCompanyDatabase.rawQuery("SELECT * FROM Companies WHERE Name=?",
                        new String[] {companies.get(position)});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    companiesType = cursor.getString(cursor.getColumnIndex("ID"));
                    Log.d("sqlite:", "onItemSelected: " + companiesType);
                }
                displayProducts.clear();
                loadProductDetails();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void loadCompanyNames() {
        apiRequest.getCompanies(new Callback<List<ModelCompany>>() {
            @Override
            public void onResponse(Call<List<ModelCompany>> call, Response<List<ModelCompany>> response) {
                if (!response.isSuccessful()) {
                    Log.d("retro:", "onResponse: response failed" + response.code());
                }
                List<ModelCompany> names = response.body();
                for (ModelCompany companyName : names) {
                    String nameStr = companyName.getName();
                    String usIDStr = companyName.getUs_id();
                    companies.add(nameStr);
                    companyAdapter.notifyDataSetChanged();

                    // Add this data to SQLite Database
                    String sql = "INSERT INTO Companies(Name, ID) VALUES(?, ?)";
                    SQLiteStatement statement = productCompanyDatabase.compileStatement(sql);
                    statement.bindString(1, nameStr);
                    statement.bindString(2, usIDStr);
                    statement.execute();
                }
                Collections.sort(companies.subList(1, companies.size()));
                companyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelCompany>> call, Throwable t) {
                Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void loadProductNames() {
        apiRequest.getProducts(new Callback<List<ModelProduct>>() {
            @Override
            public void onResponse(Call<List<ModelProduct>> call, Response<List<ModelProduct>> response) {
                if (!response.isSuccessful()) {
                    Log.d("retro:", "onResponse: response failed" + response.code());
                }
                List<ModelProduct> models = response.body();
                for (ModelProduct product : models) {
                    String type = product.getProduct_type();
                    String typeIDStr = product.getType_id();
                    products.add(type);
                    productAdapter.notifyDataSetChanged();

                    // Add this data to SQLite Database
                    String sql = "INSERT INTO Products(Name, ID) VALUES(?, ?)";
                    SQLiteStatement statement = productCompanyDatabase.compileStatement(sql);
                    statement.bindString(1, type);
                    statement.bindString(2, typeIDStr);
                    statement.execute();
                }
                Collections.sort(products.subList(1, products.size()));
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelProduct>> call, Throwable t) {
                Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void loadProductDetails() {
        // This data is shown on the recycler view
        apiRequest.getProductDetails(productType, companiesType, new Callback<List<ModelProductDetail>>() {
            @Override
            public void onResponse(Call<List<ModelProductDetail>> call, Response<List<ModelProductDetail>> response) {
                if (!response.isSuccessful()) {
                    Log.d("retro:", "onResponse: response failed" + response.code());
                }
                List<ModelProductDetail> things = response.body();
                for (ModelProductDetail thing : things) {
                    displayProducts.add(thing);
                    adapterProductsHomeFragment.notifyDataSetChanged();
                }
                //Log.d("retro:", "onResponse: " + things.get(0).getProduct_name());
            }

            @Override
            public void onFailure(Call<List<ModelProductDetail>> call, Throwable t) {
                Log.d("retro: ", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
