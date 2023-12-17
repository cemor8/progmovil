package com.example.sudoku;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import java.util.*;

public class ControllerJuego extends AppCompatActivity {
    private int[][] tablero;
    private int[][] tableroMostrar;
    private EditText currentEditText = null;
    private GridLayout gridLayout;
    private int valorRecibido = 0;
    private boolean terminado = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablero);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("casillas")) {
            this.valorRecibido = intent.getIntExtra("casillas", 40);

        }
        this.gridLayout =findViewById(R.id.tablero);
        this.crearTableroIntefaz();
        this.crearPartida();


    }
    /**
     * Método que se encarga de crear el sudoku, ocultar unos numeros y mostrarlos por pantalla
     * */
    public void crearPartida(){


        ControllerSudoku controllerSudoku = new ControllerSudoku();
        this.tablero = controllerSudoku.devolverSudokuAleatorio();

        this.tableroMostrar = new int[9][9];
        // copiar columnas
        for (int i = 0; i < 9; i++) {
            System.arraycopy(this.tablero[i], 0, this.tableroMostrar[i], 0, 9);
        }
        this.ocultarNumeros(this.valorRecibido);
        this.mostrarNumerosTablero();

    }

    /**
     * Método que se encarga de ocultar los numeros del sudoku,
     * busca una posicion aleatoria en el sudoku y si no es 0 el valor,
     * pasa a ser 0 para luego no mostrar esa posicion por pantalla
     * */
    private void ocultarNumeros(int cantidadOcultar) {
        Random random = new Random();
        while (cantidadOcultar > 0) {
            int y = random.nextInt(9);
            int x = random.nextInt(9);
            if (this.tableroMostrar[y][x] != 0) {
                this.tableroMostrar[y][x] = 0;
                cantidadOcultar--;
            }
        }
    }
    /**
     * Método que se encarga de crear la interfaz, crea los editText,
     * los modifica y los añade al
     * */
    public void crearTableroIntefaz(){
        GridLayout gridLayout = findViewById(R.id.tablero);

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                EditText editText = new EditText(this);
                editText.setOnClickListener(this::seleccionarEditText);
                GridLayout.Spec rowSpec = GridLayout.spec(y, 1f);
                GridLayout.Spec columnSpec = GridLayout.spec(x, 1f);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
                layoutParams.setMargins(1,0,1,1);

                editText.setTextSize(18);
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_NULL);
                int colorFondo;
                // si el bloque es par se colorea de azul claro, si es impar se colorea de azul oscuro.

                //se dividen las coordenadas por 3 para agrupar en bloques de 3 colmnas y 3 filas, luego se comprueba si el bloque es par o impar
                if ((y/ 3 + x / 3) % 2 == 0) {
                    colorFondo = ContextCompat.getColor(this, R.color.azulClaro);
                } else {
                    colorFondo = ContextCompat.getColor(this, R.color.azulOscuro);
                }
                editText.setBackgroundColor(colorFondo);
                editText.setTextColor(Color.WHITE);
                editText.setLayoutParams(layoutParams);
                gridLayout.addView(editText);
            }
        }
    }
    /**
     * Método que muestra los numeros del tablero en los editText
     * */
    public void mostrarNumerosTablero(){
        GridLayout gridLayout = findViewById(R.id.tablero);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                // se multiplica la fila actual por en numero de filas totales y se le suma x para ir a la columna
                View view = gridLayout.getChildAt(y * 9 + x);
                if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    int value = this.tableroMostrar[y][x];
                    editText.setTextColor(Color.WHITE);
                    if (value != 0) {
                        editText.setText(String.valueOf(value));
                        editText.setEnabled(false);
                    }else {
                        editText.setFocusable(true);
                        editText.setFocusableInTouchMode(true);
                    }
                }
            }
        }
    }
    /**
     * Método que vuelve a colorear el fondo del tablero
     * */
    public void color(){
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                // se divide entre cantidad de filas y el modulo para saber en la columna que se esta
                int y = i / 9;
                int x = i % 9;

                int colorFondo;

                if ((y / 3 + x / 3) % 2 == 0) {
                    colorFondo = ContextCompat.getColor(this, R.color.azulClaro);
                } else {
                    colorFondo = ContextCompat.getColor(this, R.color.azulOscuro);
                }

                editText.setBackgroundColor(colorFondo);
            }
        }
    }
    /**
     * Método que colorea los editText que tengan el mismo valor que la string recibida
     * @param numero numero en formato string
     * */
    public void colorearMismosNumeros(String numero){
        if(Objects.equals(numero, "")){
            return;
        }
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                if(String.valueOf(editText.getText()).equalsIgnoreCase(numero)){
                    editText.setBackgroundColor(ContextCompat.getColor(this,R.color.marcarEditText));
                }
            }
        }
    }
    /**
     * Método que se encarga de seleccionar un editText y colorear el bloque, fila , columna y editTexts que tengan relaccion con el seleccionado
     * */
    public void seleccionarEditText(View view) {
        this.color();
        this.currentEditText = (EditText) view;
        int y = this.obtenerFila(this.currentEditText);
        int x = this.obtenerColumna(this.currentEditText);
        this.colorearFila(y);
        this.colorearColumna(x);
        int bloqueY = y - y % 3;
        int bloqueX = x - x % 3;
        this.colorearBloque(bloqueY, bloqueX);
        this.colorearMismosNumeros(String.valueOf(this.currentEditText.getText()));
        this.currentEditText.setBackgroundColor(ContextCompat.getColor(this, R.color.marcarEditText));
    }
    /**
     * Método que devuelve la fila en la que esta el editText recibido
     * @param editText editText
     **/
    private int obtenerFila(EditText editText) {
        int posicion = gridLayout.indexOfChild(editText);
        int y = posicion / 9;
        return y;
    }
    /**
     * Método que devuelve la columna en la que esta el editText recibido
     * @param editText editText
     **/
    private int obtenerColumna(EditText editText) {
        int posicion = gridLayout.indexOfChild(editText);
        int x = posicion % 9;
        return x;
    }

    /**
     * Método que colorea todos los editText de una fila
     * @param y fila a pintar
     * */
    private void colorearFila(int y) {
        for (int i = 0; i < 9; i++) {
            EditText editText = (EditText) gridLayout.getChildAt(y * 9 + i);
            editText.setBackgroundColor(ContextCompat.getColor(this, R.color.grisAzulado));
        }
    }
    /**
     * Método que colorea todos los editText de una columna
     * @param x columna a pintar
     * */
    private void colorearColumna(int x) {
        for (int i = 0; i < 9; i++) {
            EditText editText = (EditText) gridLayout.getChildAt(i * 9 + x);
            editText.setBackgroundColor(ContextCompat.getColor(this, R.color.grisAzulado));
        }
    }
    /**
     * Método que colorea el bloque correspondiente
     * @param columnaInicio columna de inicio del bloque
     * @param filaInicio fila de inicio del bloque
     * */
    private void colorearBloque(int filaInicio, int columnaInicio) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x <  3; x++) {

                EditText editText = (EditText) gridLayout.getChildAt((y + filaInicio) * 9 + (x + columnaInicio));
                editText.setBackgroundColor(ContextCompat.getColor(this, R.color.grisAzulado));
            }
        }
    }

    /**
     * Método que mete un valor en un editText, si el valor es correcto se colorea de verde y
     * no se puede volver a seleccionar, si es incorrecto, se colorea de rojo
     * */
    public void meternumero(View view){
        if(this.currentEditText == null){
            return;
        }
        Button btn = (Button) view;
        this.currentEditText.setText(btn.getText());
        GridLayout gridLayout = findViewById(R.id.tablero);

        int ubicacionEditText = gridLayout.indexOfChild(this.currentEditText);
        // cantidad de columnas, daria 9
        int cantidadColumnas = gridLayout.getColumnCount();
        // el numero de filas y columnas es el mismo asi que se pueden usar las columnas para calcular la fila
        int y = ubicacionEditText / cantidadColumnas;
        // se usa modulo para buscar columna ya que es el resultado de la division entre la cantidad de columnas
        int x = ubicacionEditText % cantidadColumnas;
        if(String.valueOf(this.tablero[y][x]).equalsIgnoreCase(String.valueOf(this.currentEditText.getText()))){
            this.currentEditText.setEnabled(false);
            this.currentEditText.setTextColor(Color.parseColor("#77dd77"));
        }else {
            this.currentEditText.setTextColor(Color.parseColor("#ff0000"));
            this.currentEditText.setEnabled(true);
        }
        this.seleccionarEditText(this.currentEditText);


    }
    /**
     * Método que vuelve a la activity para seleccionar
     * una dificultad
     * */
    public void volver(View view){
        Intent intent = new Intent(this,ControllerDificultad.class);
        startActivity(intent);
    }
    /**
     * Método que borra el contenido de un editText
     * */
    public void borrar(View view){
        if(this.currentEditText==null ||String.valueOf(this.currentEditText.getText()).equalsIgnoreCase("")){
            return;
        }
        this.currentEditText.setText("");
        this.currentEditText.setTextColor(Color.WHITE);
        this.seleccionarEditText(this.currentEditText);
    }
    /**
     * Método que resetea el tablero para volver a empezar
     * */
    public void borrarTodo(View view){
        this.currentEditText = null;
        this.vaciarNumeros();
        this.color();
        this.mostrarNumerosTablero();
    }
    /**
     * Método que crea una nueva partida
     * */
    public void nuevaPartida(View view){
        this.terminado = false;
        this.currentEditText = null;
        this.vaciarNumeros();
        this.crearPartida();
        this.color();
    }
    /**
     * Método que recorre los editText y los limpia
     * */
    public void vaciarNumeros(){
        GridLayout gridLayout = findViewById(R.id.tablero);
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                View view = gridLayout.getChildAt(y * 9 + x);
                if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    editText.setText("");
                    editText.setEnabled(true);
                }
            }
        }
    }
    /**
     * Método que invoca un boton para resolver el tablero, si en la partida,
     * ya se ha invocado, no hace nada.
     * */
    public void resolver(View view){
        if(this.terminado){
            return;
        }
        this.terminado=true;
        this.comprobar();

    }
    /**
     * Método que se encarga de resolver el sudoku, si hay un error lo resuelve, si
     * no hay ningun error, lleva a una nueva activity.
     * */
    public void comprobar(){
        boolean error = false;
        for (int y = 0 ; y<9 ; y++){
            for (int x = 0; x<9; x++){
                View view = gridLayout.getChildAt(y * 9 + x);
                if (view instanceof EditText) {
                    EditText editText = (EditText)view;
                    if(!String.valueOf(editText.getText()).equalsIgnoreCase(String.valueOf(this.tablero[y][x]))){
                        error = true;
                        editText.setTextColor(Color.parseColor("#77dd77"));
                        editText.setText(String.valueOf(this.tablero[y][x]));
                        editText.setEnabled(false);
                    }
                    if(this.currentEditText!=null){
                        this.currentEditText.setTextColor(Color.parseColor("#77dd77"));
                        this.currentEditText.setEnabled(false);
                    }



                }

            }
        }
        if(!error){
            Intent intent = new Intent(this, ControllerGanar.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
