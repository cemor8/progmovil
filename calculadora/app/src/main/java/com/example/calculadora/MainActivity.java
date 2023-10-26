package com.example.calculadora;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String mostrarOperacion="";
    String numeroConstruir="";
    private ArrayList<String> operadores = new ArrayList<>();
    private ArrayList<String> numeros = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void meterSigno(View view) {
        System.out.println(this.numeroConstruir);
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        Button btn = (Button) view;
        if(!this.numeroConstruir.isEmpty()&&String.valueOf(this.numeroConstruir.charAt(0)).equals("√")){
            return;
        }
        if(this.mostrarOperacion.isEmpty() ||
                (!Character.isDigit(this.mostrarOperacion.charAt(this.mostrarOperacion.length()-1)) && this.mostrarOperacion.length()>=2 && Character.isDigit(this.mostrarOperacion.charAt(this.mostrarOperacion.length()-2)))
        ) {

            switch (String.valueOf(btn.getText())) {
                case "+" :
                case "-":
                    this.numeroConstruir += btn.getText();
                    System.out.println(this.numeroConstruir);
                    this.mostrarOperacion+=btn.getText();
                    viewOperacion.setText(this.mostrarOperacion);
                    return;
                default:
                    return;

            }

        }
        this.operadores.add(String.valueOf(btn.getText()));
        this.mostrarOperacion +=String.valueOf(btn.getText());
        viewOperacion.setText(this.mostrarOperacion);
        if(this.numeroConstruir.isEmpty()){
            return;
        }
        this.numeros.add(this.numeroConstruir);
        this.numeroConstruir = "";

    }

    public void meterRaiz(View view){
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        Button btn = (Button) view;
        if(this.numeroConstruir.isEmpty() || !Character.isDigit(this.mostrarOperacion.charAt(this.mostrarOperacion.length()-1))

        ){
            this.numeroConstruir+=String.valueOf(btn.getText());
            this.mostrarOperacion+=String.valueOf(btn.getText());
            viewOperacion.setText(mostrarOperacion);
        }
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
    public void calcular(View view){
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        Double resultado=0.0;
        this.numeros.add(this.numeroConstruir);
        this.numeroConstruir = "";
        if ((this.operadores.size() != (this.numeros.size() - 1))) {
            return;
        }

        if(this.operadores.isEmpty()){
            if(String.valueOf(this.numeros.get(0).charAt(0)).equals("√")){
                try {

                    String numero=String.valueOf(this.numeros.get(0).charAt(1));
                    System.out.println(numero);
                    double numeroParseadoPrimero=Double.parseDouble(numero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);

                    resultado=numeroParseadoPrimero;
                }catch (Exception err){
                    System.out.println("adios");
                    return;
                }

            }else {
                try {
                    resultado=Double.parseDouble(this.numeros.get(0));
                }catch (Exception err){
                    return;
                }
            }
            System.out.println(resultado);
            viewOperacion.setText(String.valueOf(resultado));
            this.numeroConstruir="";
            this.mostrarOperacion=("");
            this.operadores=new ArrayList<>();
            this.numeros=new ArrayList<>();
            return;
        }

        for (int i = 0; i < this.operadores.size(); i++) {
            if(String.valueOf(this.numeros.get(i).charAt(0)).equals("√")){
                try {
                    String numeroStringPrimero=this.numeros.get(i).substring(1);
                    Double numeroParseadoPrimero=Double.parseDouble(numeroStringPrimero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    this.numeros.set(i,String.valueOf(numeroParseadoPrimero));
                }catch (Exception err){
                    return;
                }

            }else if(String.valueOf(this.numeros.get(i+1).charAt(0)).equals("√")){
                try {
                    String numeroStringSegundo=this.numeros.get(i+1).substring(1);
                    Double numeroParseadoSegundo=Double.parseDouble(numeroStringSegundo);
                    numeroParseadoSegundo=Math.sqrt(numeroParseadoSegundo);
                    this.numeros.set(i+1,String.valueOf(numeroParseadoSegundo));
                }catch (Exception err){
                    return;
                }

            }else if(this.numeros.get(i).length()>2 &&String.valueOf(this.numeros.get(i).charAt(1)).equals("√")){
                try {
                    String numeroStringPrimero=this.numeros.get(i).charAt(0)+this.numeros.get(i).substring(2);
                    Double numeroParseadoPrimero=Double.parseDouble(numeroStringPrimero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    this.numeros.set(i,String.valueOf(numeroParseadoPrimero));
                }catch (Exception err){
                    System.out.println("bioe");
                    return;
                }

            }else if(this.numeros.get(i+1).length()>2&&String.valueOf(this.numeros.get(i+1).charAt(0)).equals("√")){
                try {
                    System.out.println(this.numeros.get(i));
                    System.out.println(this.numeros.get(i+1).charAt(0)+this.numeros.get(i+1).substring(2));
                    String numeroStringPrimero=this.numeros.get(i+1).charAt(0)+this.numeros.get(i+1).substring(2);
                    Double numeroParseadoPrimero=Double.parseDouble(numeroStringPrimero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    this.numeros.set(i+1,String.valueOf(numeroParseadoPrimero));
                }catch (Exception err){
                    System.out.println("bioe");
                    return;
                }

            }

            Double num1;
            Double num2;
            try {
                num1 = Double.parseDouble(this.numeros.get(i));
                num2 = Double.parseDouble(this.numeros.get(i + 1));
            }catch (Exception err){
                return;
            }

            switch (this.operadores.get(i)) {
                case "-" :
                    resultado+=num1 - num2;
                    this.numeros.set(i + 1, String.valueOf(num1 - num2));
                break;
                case "+" :
                    resultado+=num1+num2;
                    this.numeros.set(i + 1, String.valueOf(num1 + num2));

                    break;
                case "*" :
                    resultado+=num1*num2;
                    this.numeros.set(i + 1, String.valueOf(num1 * num2));

                    break;
                case "/" :
                    if(num2==0){
                        viewOperacion.setText("Resultado indefinido");
                        return;
                    }
                    resultado+=num1/num2;
                    this.numeros.set(i + 1, String.valueOf(num1 / num2));
                    break;
                case "^":
                    resultado+=Math.pow(num1,num2);
                    this.numeros.set(i + 1, String.valueOf(Math.pow(num1,num2)));
                    break;
                }
            }
            viewOperacion.setText(String.valueOf(resultado));
            this.numeroConstruir="";
            this.mostrarOperacion=("");
            this.operadores=new ArrayList<>();
            this.numeros=new ArrayList<>();
        }

}