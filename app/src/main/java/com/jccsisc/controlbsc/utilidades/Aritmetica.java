package com.jccsisc.controlbsc.utilidades;

import com.jccsisc.controlbsc.model.Movimiento;

import java.util.ArrayList;

public class Aritmetica {

    public static double sumaMovimiento(ArrayList<Movimiento> recibido){
        double total = 0;

        for(Movimiento nuevo : recibido){
            total = total + (nuevo.getWeight());

        }

        return total;
    }

   public static int sumaCaja(ArrayList<Movimiento> recibido){
        int total = 0;

        for(Movimiento nuevo : recibido){
            total = total + (nuevo.getQuantity());

        }

        return total;
    }
}
