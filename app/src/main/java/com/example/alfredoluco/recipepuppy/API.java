package com.example.alfredoluco.recipepuppy;

import android.os.AsyncTask;
import android.util.Log;

import okhttp3.*;
import org.json.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by alfredoluco on 12-01-17.
 */

public class API extends AsyncTask<ArrayList<Recipe>,ArrayList<Recipe>,ArrayList<Recipe>>{


    //protocols

    public interface APIDelegate{
        void onGetRecipies(ArrayList<Recipe> currentRecipies);
    }

    //properties

    private String BaseURL;
    private ArrayList<Recipe> RecipeData;
    private OkHttpClient client;
    public APIDelegate apiDelegate;


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

    public void getRecipes(){

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
                        Log.d("Recipe added:",recipe_aux.getName());
                    }

                    Log.d("Recipes:",recipes.toString());

                    RecipeData = recipes;

                }catch (IOException e){

                    Log.d("error", e.toString());
                    throw new RuntimeException(e);

                }catch (JSONException e){

                    Log.d("error",e.toString());
                    throw new RuntimeException(e);

                }


            }


        });

        thread.start();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        apiDelegate.onGetRecipies(RecipeData);
    }

    @Override
    protected ArrayList<Recipe> doInBackground(ArrayList<Recipe>... params) {

        final ArrayList<Recipe> recipes = new ArrayList<Recipe>();

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
                Log.d("Recipe added:",recipe_aux.getName());
            }

            Log.d("Recipes:",recipes.toString());

            RecipeData = recipes;
            return RecipeData;

        }catch (IOException e){

            Log.d("error", e.toString());
            throw new RuntimeException(e);

        }catch (JSONException e){

            Log.d("error",e.toString());
            throw new RuntimeException(e);

        }


    }
}

