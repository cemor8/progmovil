package com.example.calculadora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityResultado extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_resultado);
        //aqui cargo la informacion obtenida desde la actividad principal.
        double resultado = getIntent().getDoubleExtra("resultado",0);
        TextView textView = findViewById(R.id.mostrarResultado);
        String string1=String.valueOf(resultado);
        textView.setText(string1);

    }
    public void volver(View view){
        Intent actividad1 = new Intent(this, MainActivity.class);
        startActivity(actividad1);
    }
}
