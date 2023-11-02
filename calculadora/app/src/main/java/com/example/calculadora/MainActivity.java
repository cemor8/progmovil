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
    /**
     * Método que se encarga de meter el signo a los operadores bajo unos criterios, si el signo puede pertenecer al
     * numero, lo comprueba y si puede pertenecer a este, si es un + o un - lo añade, si no , no hace nada. Si el signo no
     * pertenece al numero, se añade a la lista de los operadores
     * */
    public void meterSigno(View view) {
        System.out.println(this.numeroConstruir);
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        Button btn = (Button) view;

        if(this.mostrarOperacion.isEmpty() ||
                (!Character.isDigit(this.mostrarOperacion.charAt(this.mostrarOperacion.length()-1)) && this.mostrarOperacion.length()>=2 && Character.isDigit(this.mostrarOperacion.charAt(this.mostrarOperacion.length()-2)))
        ) {
            switch (String.valueOf(btn.getText())) {
                case "+" :
                case "-":
                    this.numeroConstruir += btn.getText();
                    this.mostrarOperacion+=btn.getText();
                    viewOperacion.setText(this.mostrarOperacion);
                    return;
                case "√":
                    System.out.println("hola");
                    break;
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
    /**
     * Método que se encarga de meter la raiz cuadrada en la calculadora, esta podra meterse solo
     * si el numero se ha empezado a construir o hay un signo en el primero y la posicion de la string anterior
     * no es un numero, ya que esta siempre esta al inicio del numero o seguida del signo.
     * */
    public void meterRaiz(View view){
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        Button btn = (Button) view;
        if((this.numeroConstruir.isEmpty() || !Character.isDigit(this.numeroConstruir.charAt(0)) &&
                !Character.isDigit(this.numeroConstruir.charAt(this.numeroConstruir.length()-1)))

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
        System.out.println(this.numeroConstruir);
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
    /**
     * Método que se encarga de eliminar el ultimo caracter introducido.
     * */
    public void eliminarUltimo(View view){
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        System.out.println(this.numeros);
        System.out.println(this.operadores);
        System.out.println(this.numeroConstruir);
        if(this.numeroConstruir.isEmpty()){
            if(this.operadores.isEmpty()){
                return;
            }
            this.operadores.remove(this.operadores.get(this.operadores.size()-1));
            this.mostrarOperacion=this.mostrarOperacion.substring(0,this.mostrarOperacion.length()-1);
            viewOperacion.setText(this.mostrarOperacion);
            if (this.numeros.isEmpty()){
                return;
            }
            this.numeroConstruir=this.numeros.get(this.numeros.size()-1);
            this.numeros.remove(this.numeros.get(this.numeros.size()-1));


        }else{
            this.numeroConstruir=this.numeroConstruir.substring(0,this.numeroConstruir.length()-1);
            this.mostrarOperacion=this.mostrarOperacion.substring(0,this.mostrarOperacion.length()-1);
            viewOperacion.setText(this.mostrarOperacion);


        }
    }
    /**
     * Método que se encarga de ir haciendo las operaciones, comprueba que la sintaxis este correcta
     * */
    public void calcular(View view){
        TextView viewOperacion= findViewById(R.id.mostrarOperacion);
        Double resultado=0.0;
        if (this.numeroConstruir.isEmpty()){
            this.eliminarTotal(view);
            return;
        }
        this.numeros.add(this.numeroConstruir);
        this.numeroConstruir = "";
        if ((this.operadores.size() != (this.numeros.size() - 1))) {
            this.eliminarTotal(view);
            return;
        }
        if(this.operadores.isEmpty()){
            /**
             * si no hay operadores devuelve el valor del numero introducido, comprueba que el primer caracter
             * sea el signo o la raiz cuadrada para operar y luego volve a almacenar su valor en la lista
             * sin el signo de la raiz.
             * */
            if(this.numeros.get(0).length()>1&&String.valueOf(this.numeros.get(0).charAt(0)).equals("√")){

                try {
                    String numero=this.numeros.get(0).substring(1);
                    double numeroParseadoPrimero=Double.parseDouble(numero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    resultado=numeroParseadoPrimero;
                }catch (Exception err){
                    this.eliminarTotal(view);
                    return;
                }

            }else if(this.numeros.get(0).length()>1&&String.valueOf(this.numeros.get(0).charAt(1)).equals("√")) {
                try {
                    String singo=String.valueOf(this.numeros.get(0).charAt(0));
                    String numero=this.numeros.get(0).substring(2);
                    double numeroParseadoPrimero=Double.parseDouble(numero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    resultado=Double.parseDouble(singo+numeroParseadoPrimero);
                }catch (Exception err){
                    this.eliminarTotal(view);
                    return;
                }
            }else {
                try {
                    resultado=Double.parseDouble(this.numeros.get(0));
                }catch (Exception err){
                    this.eliminarTotal(view);
                    return;
                }
            }

            viewOperacion.setText(String.valueOf(resultado));
            this.numeroConstruir="";
            this.mostrarOperacion=("");
            this.operadores=new ArrayList<>();
            this.numeros=new ArrayList<>();
            return;
        }
        /**
         * Se recorre la lista de los operadores y se accede al valor i e i+1 de la lista de los numeros
         * */
        for (int i = 0; i < this.operadores.size(); i++) {
            /**
             * Se comprueba si hay raices para operarlas y meter el resultado en la posicion del numero
             * */
            if(this.numeros.get(i).length()>1&&String.valueOf(this.numeros.get(i).charAt(0)).equals("√")){
                try {
                    String numeroStringPrimero=this.numeros.get(i).substring(1);
                    Double numeroParseadoPrimero=Double.parseDouble(numeroStringPrimero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    this.numeros.set(i,String.valueOf(numeroParseadoPrimero));
                }catch (Exception err){
                    this.eliminarTotal(view);
                    return;
                }

            }else if(this.numeros.get(i).length()>2 && String.valueOf(this.numeros.get(i).charAt(1)).equals("√")){
                try {
                    String signo=String.valueOf(this.numeros.get(i).charAt(0));
                    String numeroStringPrimero=this.numeros.get(i).substring(2);
                    double numeroParseadoPrimero=Double.parseDouble(numeroStringPrimero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    System.out.println(signo+numeroParseadoPrimero);
                    this.numeros.set(i,signo+numeroParseadoPrimero);
                }catch (Exception err){
                    this.eliminarTotal(view);
                    return;
                }

            }
            if(this.numeros.get(i+1).length()>1&&String.valueOf(this.numeros.get(i+1).charAt(0)).equals("√")){
                try {
                    String numeroStringSegundo=this.numeros.get(i+1).substring(1);
                    Double numeroParseadoSegundo=Double.parseDouble(numeroStringSegundo);
                    numeroParseadoSegundo=Math.sqrt(numeroParseadoSegundo);
                    this.numeros.set(i+1,String.valueOf(numeroParseadoSegundo));
                }catch (Exception err){
                    this.eliminarTotal(view);
                    return;
                }

            }else if(this.numeros.get(i+1).length()>2&&String.valueOf(this.numeros.get(i+1).charAt(1)).equals("√")){
                try {
                    String signo=String.valueOf(this.numeros.get(i+1).charAt(0));
                    String numeroStringPrimero=this.numeros.get(i+1).substring(2);
                    double numeroParseadoPrimero=Double.parseDouble(numeroStringPrimero);
                    numeroParseadoPrimero=Math.sqrt(numeroParseadoPrimero);
                    System.out.println(signo+numeroParseadoPrimero);
                    this.numeros.set(i+1,signo+numeroParseadoPrimero);
                }catch (Exception err){
                    this.eliminarTotal(view);
                    return;
                }

            }

            Double num1;
            Double num2;
            try {
                num1 = Double.parseDouble(this.numeros.get(i));
                num2 = Double.parseDouble(this.numeros.get(i + 1));
            }catch (Exception err){
                this.eliminarTotal(view);
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
                        String texto="Error";
                        viewOperacion.setText(texto);
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