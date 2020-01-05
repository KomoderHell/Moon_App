package com.company.moon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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

    private ArrayList<String> companies;
    private ArrayList<String> products;

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

        // Spinner Adapters
        companies = new ArrayList<>();
        companies.add("Companies");
        companyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, companies);
        companiesSpinner.setAdapter(companyAdapter);

        products = new ArrayList<>();
        products.add("Products");
        productAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, products);
        productsSpinner.setAdapter(productAdapter);

        // Load names from API
        getProductAndCompanyNames();

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

        return view;
    }

    private void getProductAndCompanyNames() {
        apiRequest.getProductCompanyNames(new Callback<List<ModelNames>>() {
            @Override
            public void onResponse(Call<List<ModelNames>> call, Response<List<ModelNames>> response) {
                if (!response.isSuccessful()) {
                    Log.d("retro:", "onResponse: response failed" + response.code());
                }
                //Log.d("retro:", "onResponse: OK");
                List<ModelNames> names = response.body();
                for (ModelNames name : names) {
                    String productName = name.getProduct_name();
                    products.add(productName);
                    productAdapter.notifyDataSetChanged();

                    String companyName = name.getName();
                    companies.add(companyName);
                    companyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ModelNames>> call, Throwable t) {
                Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
