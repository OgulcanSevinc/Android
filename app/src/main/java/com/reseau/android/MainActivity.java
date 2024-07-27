package com.reseau.android;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText utilisateur,mdp;

    private CheckBox Nvutilisateur;

    private Button connexion;
    public ControllerView controllerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.utilisateur = findViewById(R.id.editTextUsername);
        this.mdp = findViewById(R.id.editTextPassword);
        this.Nvutilisateur = findViewById(R.id.checkBoxNewCustomer);
        this.connexion = findViewById(R.id.buttonLogin);

        controllerView = ControllerView.getInstance();
        controllerView.setMainActivity(this);



        connexion.setOnClickListener(v -> controllerView.handleLoginButtonClick(utilisateur.getText().toString(), mdp.getText().toString(), Nvutilisateur.isChecked()? 1 : 0));
    }
}