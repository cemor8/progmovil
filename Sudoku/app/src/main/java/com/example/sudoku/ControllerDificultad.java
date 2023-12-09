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

    public void jugar(View view){
        Button btn = (Button) view;
        int id = btn.getId();
        int casillas = 0;
        if(id == R.id.btnfa){
        casillas = 40;
        }else if(id == R.id.btnme){
        casillas = 50;
        }else if(id == R.id.btndi){
        casillas = 60;
        }else if(id == R.id.btnexp){
        casillas = 65;
        }
        Intent intent = new Intent(this,ControllerJuego.class);
        intent.putExtra("casillas", casillas);
        startActivity(intent);
    }
}
