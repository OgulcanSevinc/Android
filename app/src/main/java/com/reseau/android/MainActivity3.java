package com.reseau.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity3 extends AppCompatActivity  {

    private Button buttonViderPanier;
    private Button buttonSupprimerArticle;
    private Button buttonConfirmerAchat;
    private ScrollView scrollViewPanier;
    private EditText editTextTextMontantTotal;
    private TextView textViewMontantTotal;

    private BottomNavigationView bottomNavigationView;

    private TableLayout panier;

    public ControllerView controllerView;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Connectez les widgets avec leurs ID
        buttonViderPanier = findViewById(R.id.buttonViderPanier);
        buttonSupprimerArticle = findViewById(R.id.buttonSupprimerArticle);
        buttonConfirmerAchat = findViewById(R.id.buttonConfirmerAchat);
        scrollViewPanier = findViewById(R.id.scrollViewPanier);
        editTextTextMontantTotal = findViewById(R.id.editTextTextMontantTotal);
        textViewMontantTotal = findViewById(R.id.textViewMontantTotal);
        panier= findViewById(R.id.panierId);

        controllerView = ControllerView.getInstance();
        controllerView.setMainActivity3(this);

        //bottomNavigationView = findViewById(R.id.navigation);
       // bottomNavigationView.setOnNavigationItemSelectedListener(this);


        // Ajoutez ici le reste du code de votre activité si nécessaire

        findViewById(R.id.buttonGoToMain).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            startActivity(intent);
        });

        buttonViderPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerView = ControllerView.getInstance();
                controllerView.ViderPanier();
            }
        });

        buttonSupprimerArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerView = ControllerView.getInstance();
                controllerView.SupprimerArticleSelec();


            }
        });

        buttonConfirmerAchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controllerView = ControllerView.getInstance();
                controllerView.ConfirmerAchat();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Votre code à exécuter lorsque l'activité reprend
        controllerView.Caddie();
        Log.println(Log.INFO,"ONRESUME","ICI");

    }



    public Button getButtonViderPanier() {
        return buttonViderPanier;
    }

    public void setButtonViderPanier(Button buttonViderPanier) {
        this.buttonViderPanier = buttonViderPanier;
    }

    public Button getButtonSupprimerArticle() {
        return buttonSupprimerArticle;
    }

    public void setButtonSupprimerArticle(Button buttonSupprimerArticle) {
        this.buttonSupprimerArticle = buttonSupprimerArticle;
    }

    public Button getButtonConfirmerAchat() {
        return buttonConfirmerAchat;
    }

    public void setButtonConfirmerAchat(Button buttonConfirmerAchat) {
        this.buttonConfirmerAchat = buttonConfirmerAchat;
    }

    public ScrollView getScrollViewPanier() {
        return scrollViewPanier;
    }

    public void setScrollViewPanier(ScrollView scrollViewPanier) {
        this.scrollViewPanier = scrollViewPanier;
    }

    public EditText getEditTextTextMontantTotal() {
        return editTextTextMontantTotal;
    }

    public void setEditTextTextMontantTotal(EditText editTextTextMontantTotal) {
        this.editTextTextMontantTotal = editTextTextMontantTotal;
    }

    public TextView getTextViewMontantTotal() {
        return textViewMontantTotal;
    }

    public void setTextViewMontantTotal(TextView textViewMontantTotal) {
        this.textViewMontantTotal = textViewMontantTotal;
    }

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public void setBottomNavigationView(BottomNavigationView bottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView;
    }

    public TableLayout getPanier() {
        return panier;
    }

    public void setPanier(TableLayout panier) {
        this.panier = panier;
    }
}
