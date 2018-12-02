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
    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_MAINNAME = "mainName";
    private static final String JSON_KEY_PLACEOFORIGIN = "placeOfOrigin";
    private static final String JSON_KEY_DESC = "description";
    private static final String JSON_KEY_IMAGE = "image";
    private static final String JSON_KEY_ALSOKNOWNAS = "alsoKnownAs";
    private static final String JSON_KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        String mainName = "";
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin = "";
        String description = "";
        String image = null;
        List<String> ingredients = new ArrayList<>();
        try {
            jsonObject = new JSONObject(json);
            nameObject = jsonObject.getJSONObject(JSON_KEY_NAME);
            if (nameObject.has(JSON_KEY_MAINNAME) && !nameObject.getString(JSON_KEY_MAINNAME).isEmpty()) {
                mainName = nameObject.getString(JSON_KEY_MAINNAME);
                mainName = mainName.replaceAll("\n", " ");
            }
            if (jsonObject.has(JSON_KEY_PLACEOFORIGIN) && !jsonObject.getString(JSON_KEY_PLACEOFORIGIN).isEmpty()) {
                placeOfOrigin = jsonObject.getString(JSON_KEY_PLACEOFORIGIN);
            }
            if (jsonObject.has(JSON_KEY_DESC) && !jsonObject.getString(JSON_KEY_DESC).isEmpty()) {
                description = jsonObject.getString(JSON_KEY_DESC);
            }
            if (jsonObject.has(JSON_KEY_IMAGE) && !jsonObject.getString(JSON_KEY_IMAGE).isEmpty()) {
                image = jsonObject.getString(JSON_KEY_IMAGE);
            }
            if (nameObject.has(JSON_KEY_ALSOKNOWNAS)) {
                jsonArrayalsoKnownAs = nameObject.getJSONArray(JSON_KEY_ALSOKNOWNAS);
            }
            if (jsonArrayalsoKnownAs.length() != 0) {
                for (int i = 0; i < jsonArrayalsoKnownAs.length(); i++) {
                    String alsoKnowas = (String) jsonArrayalsoKnownAs.get(i);
                    alsoKnownAs.add(alsoKnowas);
                }
            }
            jsoningredients = jsonObject.getJSONArray(JSON_KEY_INGREDIENTS);
            if (jsoningredients.length() != 0) {
                for (int i = 0; i < jsoningredients.length(); i++) {
                    String getJsonIngredients = (String) jsoningredients.get(i);
                    ingredients.add(getJsonIngredients);
                }
            }
        } catch (Exception e) {
            Log.d("JsonUtils", e.toString());
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
