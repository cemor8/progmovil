package com.example.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ControllerSudoku {
    private int[][] tablero;
    private int dimension = 9;
    /**
     * Método que se encarga de rellenar el sudoku aleatoriamente para crear uno
     * */
    public boolean rellenar(int y, int x){
        // si estamos en la fila 8 y la columna es igual a 9, es que hemos acabado de comprobar
        if (y == dimension - 1 && x == dimension) {
            return true;
        }else if (x == dimension) {
            // si se llega al final de la columna, se reinicia la columna y aumenta una fila
            y++;
            x = 0;
        }
        // obtenemos una lista de numeros del 1 al 9 por orden aleatorio para recorrerlos e intentar colocarlos en el sudoku
        ArrayList<Integer> numerosAleatorios = this.obtenerNumerosAleatorios();
        for (int numero : numerosAleatorios) {
            // se comprueba si el numero esta en la fila, columna o bloque
            // se pasa como parametro al cubo la fila y columna menos su modulo de tres para empezar siempre desde el inicio del bloque
            // la celda que se encuentra arriba a la izquierda.
            if (!this.comprobarColumna(x,numero) && !this.comprobarFila(y,numero) && !this.comprobarCubo(y - y%3,x - x%3,numero)) {
                // si no esta se coloca
                tablero[y][x] = numero;
                // se vuelve a llamar al metodo para empezar a comprobar todas las posibilidades en la siguiente celda
                if (rellenar(y, x + 1)) {
                    return true;
                }
                // si no hay valores posibles con este numero, se reinicia el numero a cero para volver a probar otro numero de la lista
                tablero[y][x] = 0;
            }
        }
        //si no se encuentran valores posibles, se devuelve false para cambiar el numero.
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
    /**
     * Método que se encarga de recorrer la columna a la que pertenece un numero
     * para comprobar que no este ya en ella.
     * @param columna columna donde se encuentra el numero
     * @param numero numero a comprobar
     * */
    public boolean comprobarColumna(int columna, int numero){
        for( int y = 0;y<this.dimension;y++){
            if(this.tablero[y][columna] == numero){
                return true;
            }
        }
        return false;
    }
    /**
     * Método que se encarga de recorrer la fila a la que pertenece un numero
     * para comprobar que no este ya en ella.
     * @param fila fila donde se encuentra el numero
     * @param numero numero a comprobar
     * */
    public boolean comprobarFila(int fila, int numero){
        for( int x = 0;x<this.dimension;x++){
            if(this.tablero[fila][x] == numero){
                return true;
            }
        }
        return false;
    }
    /**
     * Método que se encarga de comprobar el bloque de un numero para comprobar que no este
     * en el
     * @param inicioFila la fila del inicio del bloque
     * @param inicioColumna la columna que inicia el bloque
     * @param numero numero a comoprobar
     * */
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
    /**
     * Método que devuelve un sudoku aleatorio
     * */
    public int[][] devolverSudokuAleatorio(){
        this.tablero = new int[this.dimension][this.dimension];
        this.rellenar(0,0);

        return this.tablero;
    }
}


