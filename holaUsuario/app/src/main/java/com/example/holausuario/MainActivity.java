package com.example.holausuario;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
     public void introducirNombre(View view){
        TextInputEditText textInputEditText=findViewById(R.id.textInputEditText);
        String nombre= Objects.requireNonNull(textInputEditText.getText()).toString();
        Intent actividad2 = new Intent(this, MainActivity2.class);
         actividad2.putExtra("nombre", nombre);
        startActivity(actividad2);

    }
}