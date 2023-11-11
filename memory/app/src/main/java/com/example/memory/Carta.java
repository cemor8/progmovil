package com.example.memory;

import android.widget.ImageView;
import android.widget.TextView;

public class Carta {
    private ImageView ubicacionMostrarCarta;
    private String imagenMostrar;

    public Carta(ImageView textView, String imagen) {
        this.imagenMostrar = imagen;
        this.ubicacionMostrarCarta=textView;

    }

    public ImageView getUbicacionMostrarCarta() {
        return ubicacionMostrarCarta;
    }

    public String getImagenMostrar() {
        return imagenMostrar;
    }
}
