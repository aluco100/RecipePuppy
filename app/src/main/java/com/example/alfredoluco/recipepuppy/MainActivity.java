package com.example.alfredoluco.recipepuppy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import okhttp3.*;
import org.json.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements API.APIDelegate{

    private RecyclerView rv;
    private LinearLayoutManager layoutManager;
    private RecipePuppyAdapter adapter;
    public ArrayList<Recipe> recipeData = new ArrayList<Recipe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        //Set Recycler View
        rv = (RecyclerView)findViewById(R.id.RecipesRecycleView);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        //Set the Adapter
        adapter = new RecipePuppyAdapter(recipeData);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        //Get API Data
        API api = new API("http://www.recipepuppy.com/api/");
        api.apiDelegate = this;
        //api.getRecipes();
        api.execute();


    }

    @Override
    public void onGetRecipies(final ArrayList<Recipe> currentRecipies) {
        Log.d("Runbefore?","run");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                adapter = new RecipePuppyAdapter(currentRecipies);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("Run?","run");
                adapter.mData.clear();
                adapter.mData.addAll(currentRecipies);
                adapter.notifyDataSetChanged();
            }
        });

        //thread.start();

    }
}
