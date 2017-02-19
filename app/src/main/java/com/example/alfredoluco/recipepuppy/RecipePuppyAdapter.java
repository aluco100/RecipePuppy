package com.example.alfredoluco.recipepuppy;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by alfredoluco on 15-01-17.
 */

public class RecipePuppyAdapter extends RecyclerView.Adapter<RecipePuppyAdapter.ViewHolder>{

    public ArrayList<Recipe> mData;

    //View Holder's custom row
    public class ViewHolder extends RecyclerView.ViewHolder{


        //Properties
        public TextView RecipeTitle;
        public ImageView RecipePhoto;

        //Constructor
        public ViewHolder(View v){
            super(v);
            RecipeTitle = (TextView)v.findViewById(R.id.RecipeTitle);
            RecipePhoto = (ImageView)v.findViewById(R.id.RecipeImage);
        }

    }

    //Adapter's constructor

    public RecipePuppyAdapter(ArrayList<Recipe> recipes){
        mData = recipes;
    }

    //On Create ViewHolder Delegate


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Create View and Layout
        FrameLayout layout = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.framerecipetitle, parent, false);

        View RecipeView = (View)layout.findViewById(R.id.RecipeId);

        layout.removeView(RecipeView);

        //Create View Holder
        ViewHolder vh = new ViewHolder(RecipeView);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Title
        holder.RecipeTitle.setText(mData.get(position).getName());

        //Photo
        try{

            URL url = new URL(mData.get(position).getThumbnail());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.RecipePhoto.setImageBitmap(bmp);

        }catch (MalformedURLException e){

            Log.d("Error",e.toString());

        }catch(IOException e){

            Log.d("Error",e.toString());

        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
