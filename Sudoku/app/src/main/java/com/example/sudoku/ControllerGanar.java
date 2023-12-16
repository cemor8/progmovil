package com.example.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ControllerGanar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completado);

    }
    public void salir(View view){
        finishAffinity();
    }
    public void nueva(View view){
        Intent intent = new Intent(this, ControllerDificultad.class);
        startActivity(intent);
    }
}
