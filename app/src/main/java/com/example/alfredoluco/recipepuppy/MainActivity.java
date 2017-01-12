package com.example.alfredoluco.recipepuppy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import okhttp3.*;
import org.json.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API api = new API("http://www.recipepuppy.com/api/");
        ArrayList<Recipe> recipies = api.getRecipes();


    }
}
