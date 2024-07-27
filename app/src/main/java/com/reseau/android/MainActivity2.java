package com.reseau.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {

    private EditText editTextTextArticle;
    private EditText editTextTextPrix;
    private EditText editTextTextStock;
    private EditText editTextTextQuantite;
    private EditText editTextTextNumber;
    private Button buttonSuivant;
    private Button buttonPrecedent;
    private Button buttonAcheter;
    private ImageView imageView;
    private TextView textViewArticle;
    private TextView textViewPrix;
    private TextView textViewStock;
    private TextView textViewQuantite;
    private BottomNavigationView bottomNavigationView;

    public ControllerView controllerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editTextTextArticle = findViewById(R.id.editTextTextArticle);
        editTextTextPrix = findViewById(R.id.editTextTextPrix);
        editTextTextStock = findViewById(R.id.editTextTextStock);
        editTextTextNumber = findViewById(R.id.editTextNumber);
        buttonSuivant = findViewById(R.id.buttonSuivant);
        buttonPrecedent = findViewById(R.id.button2);
        buttonAcheter = findViewById(R.id.buttonAcheter);
        imageView = findViewById(R.id.imageView);
        textViewArticle = findViewById(R.id.textViewArticle);
        textViewPrix = findViewById(R.id.textViewPrix);
        textViewStock = findViewById(R.id.textViewStock);
        textViewQuantite = findViewById(R.id.textViewQuantite);
        bottomNavigationView = findViewById(R.id.navigation);


        controllerView = ControllerView.getInstance();
        controllerView.setMainActivity2(this);

        buttonSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerView.Suivant();
            }
        });

        buttonPrecedent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerView.Precedent();
            }
        });

        buttonAcheter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerView.Achat();
            }
        });



        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        //bottomNavigationView.setSelectedItemId(R.id.shop);



    }



    @Override
    public void onResume() {
        super.onResume();
        // Votre code à exécuter lorsque l'activité reprend
        controllerView.OVESP_ConsultUpdate();
        Log.println(Log.INFO,"ONRESUME","ICI");

    }




    public EditText getEditTextTextArticle() {
        return editTextTextArticle;
    }

    public void setEditTextTextArticle(EditText editTextTextArticle) {
        this.editTextTextArticle = editTextTextArticle;
    }

    public EditText getEditTextTextPrix() {
        return editTextTextPrix;
    }

    public void setEditTextTextPrix(EditText editTextTextPrix) {
        this.editTextTextPrix = editTextTextPrix;
    }

    public EditText getEditTextTextStock() {
        return editTextTextStock;
    }

    public void setEditTextTextStock(EditText editTextTextStock) {
        this.editTextTextStock = editTextTextStock;
    }

    public EditText getEditTextTextQuantite() {
        return editTextTextQuantite;
    }

    public void setEditTextTextQuantite(EditText editTextTextQuantite) {
        this.editTextTextQuantite = editTextTextQuantite;
    }

    public Button getButtonSuivant() {
        return buttonSuivant;
    }

    public void setButtonSuivant(Button buttonSuivant) {
        this.buttonSuivant = buttonSuivant;
    }

    public Button getButtonPrecedent() {
        return buttonPrecedent;
    }

    public void setButtonPrecedent(Button buttonPrecedent) {
        this.buttonPrecedent = buttonPrecedent;
    }

    public Button getButtonAcheter() {
        return buttonAcheter;
    }

    public void setButtonAcheter(Button buttonAcheter) {
        this.buttonAcheter = buttonAcheter;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextViewArticle() {
        return textViewArticle;
    }

    public void setTextViewArticle(TextView textViewArticle) {
        this.textViewArticle = textViewArticle;
    }

    public TextView getTextViewPrix() {
        return textViewPrix;
    }

    public void setTextViewPrix(TextView textViewPrix) {
        this.textViewPrix = textViewPrix;
    }

    public TextView getTextViewStock() {
        return textViewStock;
    }

    public void setTextViewStock(TextView textViewStock) {
        this.textViewStock = textViewStock;
    }

    public TextView getTextViewQuantite() {
        return textViewQuantite;
    }

    public void setTextViewQuantite(TextView textViewQuantite) {
        this.textViewQuantite = textViewQuantite;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }


    public EditText getEditTextTextNumber() {
        return  editTextTextNumber;
    }

    public void setEditTextTextNumber(EditText editTextTextNumber) {
        this.editTextTextNumber = editTextTextNumber;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        Log.println(Log.INFO,"montag", String.valueOf(itemId));

        if (itemId == R.id.panier) {
            // L'utilisateur a sélectionné l'élément Panier
            Intent panierIntent = new Intent(this, MainActivity3.class);
            startActivity(panierIntent);
            finish();

            return true;
        } else if (itemId == R.id.settings) {
            // L'utilisateur a sélectionné l'élément Paramètres
            Intent settingsIntent = new Intent(this, MainActivity4.class);
            startActivity(settingsIntent);
            return true;
        } else if (itemId == R.id.shop) {
            // L'utilisateur a sélectionné l'élément Magasin
            Intent magasinIntent = new Intent(this, MainActivity2.class);
            startActivity(magasinIntent);
            finish();
            controllerView.OVESP_ConsultUpdate();
            return true;
        }


        return false;
    }
}