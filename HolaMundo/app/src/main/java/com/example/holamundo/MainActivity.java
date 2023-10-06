package com.example.holamundo;

import android.telephony.TelephonyCallback;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private int contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onClickButton(View view){
        TextView textView = findViewById(R.id.textView);
        contador++;
        String string=Integer.toString(contador);
        textView.setText(string);
    }
}