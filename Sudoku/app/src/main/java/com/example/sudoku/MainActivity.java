package com.example.sudoku;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Método que pasa a la activity de la dificultad
     * */
    public void cambiar(View view){
        Intent intent = new Intent(this,ControllerDificultad.class);
        startActivity(intent);
    }
}