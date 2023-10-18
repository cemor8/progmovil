package com.example.holausuario;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String nombre = getIntent().getStringExtra("nombre");
        TextView textView = findViewById(R.id.textView);
        String string = textView.getText().toString();
        String string1=string+": "+nombre;
        textView.setText(string1);

    }
    public void volver(View view){
        Intent actividadMain = new Intent(this, MainActivity.class);
        startActivity(actividadMain);
    }
}