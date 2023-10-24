package com.example.calculadora;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String mostrarOperacion="";
    String numeroConstruir;
    private ArrayList<String> operadores = new ArrayList<>();
    private ArrayList<String> numeros = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * Método que construye el numero, obtiene el texto del numero y lo mete
     * en la variable numeroConstruir,si es un punto, hace lo mismo, para
     * construir el numero decimal.
     * @param view view
     * */
    public void meterNumero(View view){
        Button btn= (Button) view;
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        this.numeroConstruir+=btn.getText();
        this.mostrarOperacion+=btn.getText();
        viewOperacion.setText(this.mostrarOperacion);
    }
    /**
     * Método que elimina el contenido de la calculadora.
     * @param view view
     * */
    public void eliminarTotal(View view){
        this.operadores=new ArrayList<>();
        this.numeros=new ArrayList<>();
        this.mostrarOperacion="";
        this.numeroConstruir="";
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        viewOperacion.setText("");
    }
    public void eliminarUltimo(){
        if(Character.isDigit(this.mostrarOperacion.charAt(this.mostrarOperacion.length()-1))||
                String.valueOf(this.mostrarOperacion.charAt(this.mostrarOperacion.length()-1)).equals("√")){
                  this.numeroConstruir=this.numeroConstruir.substring(0,this.numeroConstruir.length()-2);
        }else {

        }
    }
}