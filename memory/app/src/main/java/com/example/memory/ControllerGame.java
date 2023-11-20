package com.example.memory;

import androidx.gridlayout.widget.GridLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ControllerGame extends AppCompatActivity {
    private Carta cartaAnterior = null;
    private String cartaOculta = "cartabackwards";
    private TextView mostrarTurnos;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int cantidadCartasY = 4;
    private int cantidadCartasX = 3;
    private int turnos = cantidadCartasY * cantidadCartasX ;
    private int puntos = 0;
    private boolean ganador = false;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_game);
        mostrarTurnos = findViewById(R.id.mostrarTurnos);
        mostrarTurnos.setText("Turnos: " + this.turnos);

        this.inicializarCartas();
    }
    /**
     * Método que se encargta de crear todas las cartas, crea una carta y le asigna una imageview junto al nombre
     * de la imagen que debe de mostrar, al inicio todas las cartas estan dadas la vuelta y debes de ir emparejandolas
     * para sumar puntos. Este metodo tambien se usa para volver a empezar una partida.
     * **/
    public void inicializarCartas() {
        this.ganador = false;
        String[] nombresCartasArray = {
                "j_corazones", "j_corazones",
                "j_picas", "j_picas",
                "k_corazones", "k_corazones",
                "k_trebol", "k_trebol",
                "q_corazones", "q_corazones",
                "q_diamantes", "q_diamantes"
        };
        Collections.shuffle(Arrays.asList(nombresCartasArray));
        List<String> nombresCartas = Arrays.asList(nombresCartasArray);
        Collections.shuffle(nombresCartas);
        ArrayList<String> listaDeCartasNombre = new ArrayList<>(nombresCartas);
        this.gridLayout = findViewById(R.id.gridLayout);
        this.gridLayout.setColumnCount(4);

        for (int y = 0; y < this.cantidadCartasY; y++) {
            for (int x = 0; x < this.cantidadCartasX; x++) {
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();

                params.width = 300; // Ancho en píxeles
                params.height = 300; // Alto en píxeles

                params.rowSpec = GridLayout.spec(y);
                params.columnSpec = GridLayout.spec(x);

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(getResources().getIdentifier(this.cartaOculta, "drawable", getPackageName()));
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(this::darVuelta);
                String nombreImagen = listaDeCartasNombre.remove(0);
                cartas.add(new Carta(imageView, nombreImagen));


            }
        }
        for (Carta carta : this.cartas) {

            this.gridLayout.addView(carta.getUbicacionMostrarCarta());
        }
    }
    /**
     * Método que resta un turno
     * **/
    public void restarTurno() {
        this.turnos--;
        if (this.puntos > 0) {
            this.mostrarTurnos.setText("Turnos: " + this.turnos + "\n" + "Puntos: " + this.puntos);
        } else {
            this.mostrarTurnos.setText("Turnos: " + this.turnos);
        }
    }
    /**
     * Método que suma un punto
     * **/
    public void sumarPunto() {
        this.puntos++;
        this.mostrarTurnos.setText("Turnos: " + this.turnos + "\n" + "Puntos: " + this.puntos);

    }
    /**
     * Método que se encarga de dar la vuelta a una carta y comprobar si son iguales para sumar punto,
     * almacena la primera carta en una valiable llamada carta anterior y cuando se vuelva a ejecutar, comprueba si la
     * carta anterior es igual a la que se acaba de clickar, si es asi suma punto, si no, despues de 500ms, se le da la
     * vuelta a la carta.
     * */
    public void darVuelta(View view) {
        ImageView imageViewCarta = (ImageView) view;
        Carta cartaActual = null;
        Optional<Carta> cartaActualOptional = this.cartas.stream().filter(carta -> carta.getUbicacionMostrarCarta().equals(imageViewCarta)).findFirst();
        if (cartaActualOptional.isPresent()) {
            cartaActual = cartaActualOptional.get();
        }
        if (cartaActual == cartaAnterior) {
            return;
        }

        if (cartaAnterior != null && !this.comprobarFin()) {
            this.restarTurno();
            imageViewCarta.setImageResource(getResources().getIdentifier(cartaActual.getImagenMostrar(), "drawable", getPackageName()));
            this.modificarclick(false);
            if (!cartaActual.getImagenMostrar().equalsIgnoreCase(cartaAnterior.getImagenMostrar()) && cartaActual != cartaAnterior) {
                imageViewCarta.postDelayed(() -> {
                    if(this.turnos == 0){
                        return;
                    }
                    imageViewCarta.setImageResource(getResources().getIdentifier(cartaOculta, "drawable", getPackageName()));
                    cartaAnterior.getUbicacionMostrarCarta().setImageResource(getResources().getIdentifier(cartaOculta, "drawable", getPackageName()));
                    this.modificarclick(true);
                    this.cartaAnterior = null;

                }, 500);
                this.comprobar();
                return;
            }
            sumarPunto();
            this.cartas.remove(cartaActual);
            this.cartas.remove(cartaAnterior);
            this.modificarclick(true);
            this.cartaAnterior = null;
            this.comprobar();
        } else {
            if (!this.comprobarFin()) {
                this.cartaAnterior = cartaActual;
                imageViewCarta.setImageResource(getResources().getIdentifier(cartaActual.getImagenMostrar(), "drawable", getPackageName()));
            } else {
                this.comprobar();
            }
        }

    }
    /**
     * Método que se encarga de comprobar si ganas la partida o si se han acabado los turnos y has perdido. Si pierdes,
     * se muestran todas las cartas.
     * */
    public void comprobar() {
        System.out.println(this.puntos);
        if (this.puntos == cantidadCartasY * cantidadCartasX / 2) {
            this.mostrarTurnos.setText("Ganador!!!");
            this.desactivar();
        } else if (this.comprobarFin()) {
            this.mostrarTurnos.setText("Intentalo otra vez");
            for (Carta cada_carta : this.cartas) {
                cada_carta.getUbicacionMostrarCarta().setImageResource(getResources().getIdentifier(cada_carta.getImagenMostrar(), "drawable", getPackageName()));
            }
            this.desactivar();
        }
    }
    /**
     * Método que bloquea todas las cartas o las desbloquea, segun el boolean que recibe el metodo
     * @param estado boolean a establecer para dajar clickar la carta o no
     * */
    public void modificarclick(boolean estado) {
        for (Carta carta : this.cartas) {
            carta.getUbicacionMostrarCarta().setClickable(estado);
        }
    }
    /**
     * Método que se encarga de reiniciar la partida.
     * */
    public void reset(View view) {
        this.cartaAnterior = null;
        gridLayout.removeAllViews();
        this.turnos = cantidadCartasY * cantidadCartasX;
        this.puntos = 0;
        mostrarTurnos.setText("Turnos: " + this.turnos);
        this.cartas = new ArrayList<>();
        this.inicializarCartas();
    }
    /**
     * Método que se encarga de comprobar si se ha acabado la partida o no
     * */
    public boolean comprobarFin() {
        return this.turnos == 0;

    }
    /**
     * Método que bloquea todas las cartas.
     * */
    public void desactivar() {
        cartas.forEach(carta -> carta.getUbicacionMostrarCarta().setClickable(false));
    }


}
