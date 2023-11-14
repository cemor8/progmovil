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
    private Carta cartaAnterior=null;
    private String cartaOculta="cartabackwards";
    private TextView mostrarTurnos;
    private ArrayList<Carta> cartas = new ArrayList<>();
    private int cantidadCartasY=3;
    private int cantidadCartasX=4;
    private int turnos = cantidadCartasY * cantidadCartasX ;
    private int puntos = 0;
    private GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_game);
        mostrarTurnos = findViewById(R.id.mostrarTurnos);
        System.out.println("hola");
        mostrarTurnos.setText("Turnos: "+this.turnos);

        this.inicializarCartas();
    }
    public void inicializarCartas(){
        String[] nombresCartasArray = {
                "j_corazones", "j_corazones",
                "j_picas", "j_picas",
                "k_corazones", "k_corazones",
                "k_trebol", "k_trebol",
                "q_corazones", "q_corazones",
                "q_diamantes", "q_diamantes"
        };
        List<String> nombresCartas = Arrays.asList(nombresCartasArray);
        Collections.shuffle(nombresCartas);
        ArrayList<String> listaDeCartasNombre = new ArrayList<>(nombresCartas);
        this.gridLayout= findViewById(R.id.gridLayout);
        this.gridLayout.setColumnCount(4);
        int contador = 0;
        for(int y = 0 ; y<this.cantidadCartasY ; y++){
            for (int x = 0 ; x<this.cantidadCartasX; x++){
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                System.out.println(listaDeCartasNombre);
                System.out.println(contador);
                params.width = 200; // Ancho en píxeles
                params.height = 200; // Alto en píxeles

                params.rowSpec = GridLayout.spec(y);
                params.columnSpec = GridLayout.spec(x);

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(getResources().getIdentifier(this.cartaOculta, "drawable", getPackageName()));
                imageView.setLayoutParams(params);
                imageView.setOnClickListener(this::darVuelta);
                String nombreImagen=listaDeCartasNombre.remove(0);
                cartas.add(new Carta(imageView, nombreImagen));
                contador ++;

            }
        }
        this.barajar();
        for(Carta carta : this.cartas){

            this.gridLayout.addView(carta.getUbicacionMostrarCarta());
        }
    }
    public void restarTurno(){
        this.turnos--;
        this.mostrarTurnos.setText("Turnos: "+this.turnos);
    }
    public void sumarPunto(){
        this.puntos++;
        this.mostrarTurnos.setText("Turnos: "+this.turnos+"\n"+"Puntos: "+this.puntos);

    }


    public void darVuelta(View view){
        ImageView imageViewCarta = (ImageView) view;
        Carta cartaActual = null;
        Optional<Carta> cartaActualOptional = this.cartas.stream().filter(carta -> carta.getUbicacionMostrarCarta().equals(imageViewCarta)).findFirst();
        if(cartaActualOptional.isPresent()){
            cartaActual=cartaActualOptional.get();
        }
        if(cartaAnterior!=null){
            imageViewCarta.setImageResource(getResources().getIdentifier(cartaActual.getImagenMostrar(), "drawable", getPackageName()));

            if(!cartaActual.getImagenMostrar().equalsIgnoreCase(cartaAnterior.getImagenMostrar())){
               this.restarTurno();
                imageViewCarta.postDelayed(() -> {
                    imageViewCarta.setImageResource(getResources().getIdentifier(cartaOculta, "drawable", getPackageName()));
                    imageViewCarta.setClickable(true);
                    cartaAnterior.getUbicacionMostrarCarta().setImageResource(getResources().getIdentifier(cartaOculta, "drawable", getPackageName()));
                    cartaAnterior.getUbicacionMostrarCarta().setClickable(true);
                    this.cartaAnterior=null;

                }, 1000);

               return;
            }
            sumarPunto();
            imageViewCarta.setClickable(false);
            this.cartaAnterior = null;
            if(this.comprobarFin()){
                this.desactivar();
            }
        }else {
            this.cartaAnterior = cartaActual;
            imageViewCarta.setImageResource(getResources().getIdentifier(cartaActual.getImagenMostrar(), "drawable", getPackageName()));
        }
    }

    public void reset(View view){
        this.cartaAnterior=null;
        gridLayout.removeAllViews();
        this.turnos=cantidadCartasY * cantidadCartasX ;
        this.puntos=0;
        mostrarTurnos.setText("Turnos: "+this.turnos);
        this.cartas=new ArrayList<>();
        this.inicializarCartas();
    }

    public boolean comprobarFin(){
        return this.turnos == 0;
    }
    public void desactivar(){
        cartas.forEach(carta -> carta.getUbicacionMostrarCarta().setClickable(false));
    }
    public void barajar(){
        Collections.shuffle(this.cartas);
    }


}
