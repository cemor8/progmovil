package com.example.sudoku;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ControllerDificultad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dificultad);
    }
    /**
     * Método que dependiendo del boton que se invoque, crea
     * un sudoku de una dificultad u otra
     * */
    public void jugar(View view){
        Button btn = (Button) view;
        int id = btn.getId();
        int casillas = 0;
        if(id == R.id.btnfa){
        casillas = 10;
        }else if(id == R.id.btnme){
        casillas = 35;
        }else if(id == R.id.btndi){
        casillas = 50;
        }else if(id == R.id.btnexp){
        casillas = 65;
        }
        Intent intent = new Intent(this,ControllerJuego.class);
        intent.putExtra("casillas", casillas);
        startActivity(intent);
    }
}
