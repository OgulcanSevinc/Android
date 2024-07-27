package com.reseau.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {


    private Button deconnexion;
    private Button gohome2;

    private ControllerView controllerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        deconnexion = findViewById(R.id.buttonDec);
        gohome2 = findViewById(R.id.buttonGoMain2);

        controllerView = ControllerView.getInstance();
        controllerView.setMainActivity4(this);


        gohome2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
            startActivity(intent);
        });

        deconnexion.setOnClickListener(view -> {
            controllerView = ControllerView.getInstance();
            controllerView.Logout();
            Intent intent = new Intent(MainActivity4.this, MainActivity.class);
            startActivity(intent);

        });


    }

    public Button getDeconnexion() {
        return deconnexion;
    }

    public void setDeconnexion(Button deconnexion) {
        this.deconnexion = deconnexion;
    }

    public Button getGohome2() {
        return gohome2;
    }

    public void setGohome2(Button gohome2) {
        this.gohome2 = gohome2;
    }
}