package com.example.sudoku;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;
import java.util.Random;

public class ControllerJuego extends AppCompatActivity {
    private int[][] tablero;
    private int[][] tableroMostrar;
    private EditText currentEditText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablero);
        this.crearTableroIntefaz();

        ControllerSudoku controllerSudoku = new ControllerSudoku();
        this.tablero = controllerSudoku.devolverSudokuAleatorio();

        this.tableroMostrar = new int[9][9];

        for (int i = 0; i < 9; i++) {
            System.arraycopy(this.tablero[i], 0, this.tableroMostrar[i], 0, 9);
        }

        this.ocultarNumeros(40);
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

                layoutParams.setMargins(10, 0, 10, 10);

                editText.setTextSize(18);
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_NULL);
                editText.setBackgroundColor(Color.BLACK);
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
    public void seleccionarEditText(View view){
        if(this.currentEditText != null){
            this.currentEditText.setBackgroundColor(Color.BLACK);
        }
        this.currentEditText = (EditText) view;
        this.currentEditText.setBackgroundColor(Color.BLUE);
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
        }else {

        }
        System.out.println(rowIndex);
        System.out.println(columnIndex);
        System.out.println(this.tablero[rowIndex][columnIndex]);
        System.out.println(Arrays.deepToString(this.tablero));


    }
}
