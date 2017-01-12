package com.example.alfredoluco.recipepuppy;

import android.util.Log;

import okhttp3.*;
import org.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by alfredoluco on 12-01-17.
 */

public class API {

    private String BaseURL;
    private OkHttpClient client;

    public API(String url){
        this.BaseURL = url;
        this.client = new OkHttpClient();
    }

    //methods

    public String catURL() throws IOException{

        Request req = new Request.Builder().url(this.BaseURL).build();

        Response response = client.newCall(req).execute();

        return response.body().string();
    }

    //TODO: hacer un protocolo que haga que retorne las recetas

    public ArrayList<Recipe> getRecipes(){

        final ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        Thread thread = new Thread(new Runnable() {


            //Code Here

            @Override
            public void run(){

                try{

                    String rawJSON = catURL();
                    JSONObject json = new JSONObject(rawJSON);
                    JSONArray arr = json.getJSONArray("results");
                    for(int i = 0; i < arr.length() ; i++){

                        //get the data

                        String recipeName = arr.getJSONObject(i).getString("title");
                        String ingredients = arr.getJSONObject(i).getString("ingredients");
                        String photo = arr.getJSONObject(i).getString("thumbnail");

                        //create new recipe

                        Recipe recipe_aux = new Recipe(recipeName,ingredients,photo);
                        recipes.add(recipe_aux);
                    }


                }catch (IOException e){

                    Log.d("error", e.toString());
                    throw new RuntimeException(e);

                }catch (JSONException e){

                    Log.d("error",e.toString());
                    throw new RuntimeException(e);

                }


            }

            Callable<ArrayList<Recipe>> callback = new Callable<ArrayList<Recipe>>() {
                @Override
                public ArrayList<Recipe> call() throws Exception {
                    return recipes;
                }
            };

        });

        thread.start();

        return null;
    }

}
