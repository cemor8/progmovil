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
    /**
     * Método que sale de la aplicacion
     * */
    public void salir(View view){
        finishAffinity();
    }
    /**
     * Método que vuelve a la activity de seleccionar la dificultad
     * */
    public void nueva(View view){
        Intent intent = new Intent(this, ControllerDificultad.class);
        startActivity(intent);
    }
}
