package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView originTextView;
    TextView descriptionTextView;
    TextView alsoKnownAsTextView;
    TextView ingredientsTextView;
    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        // populate in function populateUI
        originTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        //titleTextView = findViewById(R.id.title_tv);


        // hide error text initialy


        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d("Balendra", "Inside position check");
            closeOnError();
            return;
        } else {
            Log.d("Balendra", "outside position check");
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        // get json string and retrun sandwitch object
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        // print out if we got all data from click
        Log.d("JsonUtil", sandwich.getDescription() + ":" + sandwich.getImage() + ":" + sandwich.getMainName() + ":" + sandwich.getPlaceOfOrigin() + ":" + sandwich.getIngredients().toString());
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }


    private void populateUI(Sandwich sandwich) {
        if (!sandwich.getDescription().isEmpty()) {
            descriptionTextView.setText(sandwich.getDescription());
        } else {
            descriptionTextView.setText("Data not available");
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            originTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            originTextView.setText("Data not available");
        }

        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            String listToString = TextUtils.join("\n", sandwich.getAlsoKnownAs());
            alsoKnownAsTextView.setText(listToString);

        } else {
            alsoKnownAsTextView.setText("Data not avilable");
        }

        if (!sandwich.getIngredients().isEmpty()) {
            String listToString = TextUtils.join("\n", sandwich.getIngredients());
            ingredientsTextView.setText(listToString);
        } else {
            ingredientsTextView.setText("Data not avilable");
        }

    }
}
