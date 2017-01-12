package com.example.alfredoluco.recipepuppy;

/**
 * Created by alfredoluco on 12-01-17.
 */

public class Recipe {

    private String Name;
    private String Ingredients;
    private String Thumbnail;

    public Recipe(String name, String ingredients, String photo){
        this.Name = name;
        this.Ingredients = ingredients;
        this.Thumbnail = photo;
    }

    //getter

    public String getName(){
        return this.Name;
    }

    public String getIngredients(){
        return this.Ingredients;
    }

    public String getThumbnail(){
        return this.Thumbnail;
    }

}
