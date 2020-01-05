package com.company.moon;

import android.os.Bundle;
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

public class FragmentHome extends Fragment {

    private Spinner companiesSpinner;
    private Spinner productsSpinner;
    private ImageView filter;
    private ImageView sort;
    private RecyclerView recyclerView;

    private ArrayAdapter<String> companyAdapter;
    private ArrayAdapter<String> productAdapter;

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

        // Spinner Adapters
        ArrayList<String> companies = new ArrayList<>();
        companies.add("Companies");
        companyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, companies);
        companiesSpinner.setAdapter(companyAdapter);

        ArrayList<String> products = new ArrayList<>();
        products.add("Products");
        productAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, products);
        productsSpinner.setAdapter(productAdapter);

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
}
