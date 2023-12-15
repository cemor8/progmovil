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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablero);

        Intent intent = getIntent();
        int valorRecibido = 40;
        if (intent != null && intent.hasExtra("casillas")) {
            valorRecibido = intent.getIntExtra("casillas", 40);

        }
        this.gridLayout =findViewById(R.id.tablero);
        this.crearTableroIntefaz();

        ControllerSudoku controllerSudoku = new ControllerSudoku();
        this.tablero = controllerSudoku.devolverSudokuAleatorio();

        this.tableroMostrar = new int[9][9];

        for (int i = 0; i < 9; i++) {
            System.arraycopy(this.tablero[i], 0, this.tableroMostrar[i], 0, 9);
        }

        this.ocultarNumeros(valorRecibido);
        this.mostrarNumerosTablero();

    }
    private void ocultarNumeros(int cantidadOcultar) {
        Random random = new Random();
        while (cantidadOcultar > 0) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if (this.tableroMostrar[row][col] != 0) {
                this.tableroMostrar[row][col] = 0;
                cantidadOcultar--;
            }
        }
    }
    public void crearTableroIntefaz(){
        GridLayout gridLayout = findViewById(R.id.tablero);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText editText = new EditText(this);
                editText.setOnClickListener(this::seleccionarEditText);
                GridLayout.Spec rowSpec = GridLayout.spec(i, 1f);
                GridLayout.Spec columnSpec = GridLayout.spec(j, 1f);
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);



                editText.setTextSize(18);
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_NULL);
                int colorFondo;
                if ((i / 3 + j / 3) % 2 == 0) {
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
    public void mostrarNumerosTablero(){
        GridLayout gridLayout = findViewById(R.id.tablero);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                View view = gridLayout.getChildAt(i * 9 + j);
                if (view instanceof EditText) {
                    EditText editText = (EditText) view;
                    int value = this.tableroMostrar[i][j];
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
    public void color(){
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof EditText) {
                EditText editText = (EditText) view;
                int fila = i / 9;
                int columna = i % 9;

                int colorFondo;

                if ((fila / 3 + columna / 3) % 2 == 0) {
                    colorFondo = ContextCompat.getColor(this, R.color.azulClaro);
                } else {
                    colorFondo = ContextCompat.getColor(this, R.color.azulOscuro);
                }

                editText.setBackgroundColor(colorFondo);
            }
        }
    }

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

    public void seleccionarEditText(View view) {
        this.color();
        this.currentEditText = (EditText) view;
        int filaSeleccionada = obtenerFila(this.currentEditText);
        int columnaSeleccionada = obtenerColumna(this.currentEditText);
        colorearFila(filaSeleccionada);
        colorearColumna(columnaSeleccionada);
        int bloqueFila = filaSeleccionada / 3 * 3;
        int bloqueColumna = columnaSeleccionada / 3 * 3;
        colorearBloque(bloqueFila, bloqueColumna);
        this.colorearMismosNumeros(String.valueOf(this.currentEditText.getText()));
        System.out.println(String.valueOf(currentEditText.getText()));
        this.currentEditText.setBackgroundColor(ContextCompat.getColor(this, R.color.marcarEditText));
    }

    private int obtenerFila(EditText editText) {
        int index = gridLayout.indexOfChild(editText);
        int fila = index / 9;
        return fila;
    }

    private int obtenerColumna(EditText editText) {
        int index = gridLayout.indexOfChild(editText);
        int columna = index % 9;
        return columna;
    }


    private void colorearFila(int fila) {
        for (int j = 0; j < 9; j++) {
            EditText editText = (EditText) gridLayout.getChildAt(fila * 9 + j);
            editText.setBackgroundColor(ContextCompat.getColor(this, R.color.grisAzulado));
        }
    }

    private void colorearColumna(int columna) {
        for (int i = 0; i < 9; i++) {
            EditText editText = (EditText) gridLayout.getChildAt(i * 9 + columna);
            editText.setBackgroundColor(ContextCompat.getColor(this, R.color.grisAzulado));
        }
    }

    private void colorearBloque(int filaInicio, int columnaInicio) {
        for (int i = filaInicio; i < filaInicio + 3; i++) {
            for (int j = columnaInicio; j < columnaInicio + 3; j++) {
                EditText editText = (EditText) gridLayout.getChildAt(i * 9 + j);
                editText.setBackgroundColor(ContextCompat.getColor(this, R.color.grisAzulado));
            }
        }
    }


    public void meternumero(View view){
        if(this.currentEditText == null){
            return;
        }
        Button btn = (Button) view;
        this.currentEditText.setText(btn.getText());
        GridLayout gridLayout = findViewById(R.id.tablero);

        int indexOfEditText = gridLayout.indexOfChild(this.currentEditText);

        int columnCount = gridLayout.getColumnCount();

        int rowIndex = indexOfEditText / columnCount;
        int columnIndex = indexOfEditText % columnCount;

        Log.d("Posicion", "Fila: " + rowIndex + ", Columna: " + columnIndex);
        if(String.valueOf(this.tablero[rowIndex][columnIndex]).equalsIgnoreCase(String.valueOf(this.currentEditText.getText()))){
            System.out.println("bien");
            this.currentEditText.setEnabled(false);
            this.currentEditText.setTextColor(Color.parseColor("#77dd77"));
        }else {
            this.currentEditText.setTextColor(Color.parseColor("#ff0000"));
        }
        System.out.println(rowIndex);
        System.out.println(columnIndex);
        System.out.println(this.tablero[rowIndex][columnIndex]);
        System.out.println(Arrays.deepToString(this.tablero));


    }
    public void volver(View view){
        Intent intent = new Intent(this,ControllerDificultad.class);
        startActivity(intent);
    }
}
