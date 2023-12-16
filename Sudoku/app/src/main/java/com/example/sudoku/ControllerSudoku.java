package com.example.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ControllerSudoku {
    private int[][] tablero;
    private int dimension = 9;
    public boolean rellenar(int y, int x){
        if (y == dimension - 1 && x == dimension) {
            return true;
        }else if (x == dimension) {
            y++;
            x = 0;
        }
        ArrayList<Integer> numerosAleatorios = obtenerNumerosAleatorios();
        for (int numero : numerosAleatorios) {
            if (!comprobarColumna(x,numero) && !comprobarFila(y,numero) && !comprobarCubo(y - y%3,x - x%3,numero)) {
                tablero[y][x] = numero;
                if (rellenar(y, x + 1)) {
                    return true;
                }
                tablero[y][x] = 0;
            }
        }
        return false;
    }
    private ArrayList<Integer> obtenerNumerosAleatorios() {
        ArrayList<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= dimension; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros);
        return numeros;
    }
    public boolean comprobarColumna(int columna, int numero){
        for( int y = 0;y<this.dimension;y++){
            if(this.tablero[y][columna] == numero){
                return true;
            }
        }
        return false;
    }
    public boolean comprobarFila(int fila, int numero){
        for( int x = 0;x<this.dimension;x++){
            if(this.tablero[fila][x] == numero){
                return true;
            }
        }
        return false;
    }
    public boolean comprobarCubo(int inicioFila, int inicioColumna, int numero){
        for (int y = 0 ; y<3;y++){
            for(int x = 0 ; x<3; x++){
                if(this.tablero[y + inicioFila][x+inicioColumna] == numero){
                    return true;
                }
            }
        }
        return false;
    }
    public int[][] devolverSudokuAleatorio(){
        this.tablero = new int[this.dimension][this.dimension];
        this.rellenar(0,0);
        System.out.println(Arrays.deepToString(this.tablero));
        return this.tablero;
    }
}


