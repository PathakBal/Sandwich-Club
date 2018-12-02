package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    static JSONObject jsonObject;
    static JSONArray jsonArrayalsoKnownAs;
    static JSONArray jsoningredients;
    static JSONObject nameObject;

    public static Sandwich parseSandwichJson(String json) {

        String mainName="";
        List<String> alsoKnownAs=new ArrayList<>();
        String placeOfOrigin="";
        String description="";
        String image=null;
        List<String> ingredients=new ArrayList<>();


        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.d("JsonUtil","Parsing error");
            e.printStackTrace();
        }
        try {
            nameObject = jsonObject.getJSONObject("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if(nameObject.has("mainName") && !nameObject.getString("mainName").isEmpty()){

                mainName = nameObject.getString("mainName");
                mainName = mainName.replaceAll("\n"," ");
            }
        } catch (JSONException e) {
            Log.d("JsonUtil","No Sandwitch Name");
            e.printStackTrace();
        }
        try {
            if(jsonObject.has("placeOfOrigin") && !jsonObject.getString("placeOfOrigin").isEmpty()){
                placeOfOrigin = jsonObject.getString("placeOfOrigin");
            }
        } catch (JSONException e) {
            Log.d("JsonUtil","No placeOforigin Name");
            e.printStackTrace();
        }

        try {
            if(jsonObject.has("description") && !jsonObject.getString("description").isEmpty()){
                description = jsonObject.getString("placeOfOrigin");
            }
        } catch (JSONException e) {
            Log.d("JsonUtil","No description in Json Data");
            e.printStackTrace();
        }

        try {
            if(jsonObject.has("image") && !jsonObject.getString("image").isEmpty()){
                image = jsonObject.getString("image");
            }
        } catch (JSONException e) {
            Log.d("JsonUtil","No image in Json Data");
            e.printStackTrace();
        }

        try {
            if(nameObject.has("alsoKnownAs")) {
                jsonArrayalsoKnownAs = nameObject.getJSONArray("alsoKnownAs");
            }
        } catch (JSONException e) {
            Log.d("JsonUtil","No jsonArrayalsoKnownAs Json Data");
            e.printStackTrace();
        }
        if(jsonArrayalsoKnownAs.length() != 0){

            for (int i = 0; i< jsonArrayalsoKnownAs.length();i++){
                try {
                    String alsoKnowas = (String) jsonArrayalsoKnownAs.get(i);
                    alsoKnownAs.add(alsoKnowas);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        try {
            jsoningredients = jsonObject.getJSONArray("ingredients");
        } catch (JSONException e) {
            Log.d("JsonUtil","No jsoningredients Json Data");
            e.printStackTrace();
        }
        if(jsoningredients.length() != 0){

            for (int i = 0; i< jsoningredients.length();i++){
                try {

                    String getJsonIngredients = (String) jsoningredients.get(i);
                    ingredients.add(getJsonIngredients);
                } catch (JSONException e) {
                    Log.d("JsonUtil","No index in jsoningredients Json Data");
                    e.printStackTrace();
                }
            }

        }
        return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
    }
}
