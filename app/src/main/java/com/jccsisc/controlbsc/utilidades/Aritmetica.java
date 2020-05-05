package com.jccsisc.controlbsc.utilidades;
import com.jccsisc.controlbsc.model.Movimiento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Aritmetica {

    public static double sumaMovimiento(ArrayList<Movimiento> recibido) {
        double total = 0;
        for (Movimiento nuevo : recibido) {
            total += (nuevo.getWeight());
        }

        return total;
    }

    public static double sumaMovimientoFecha(ArrayList<Movimiento> recibido){
        double total = 0;
        String dateEntrada;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);
        for(Movimiento nuevo : recibido){
            if(nuevo.getDate().equals(dateEntrada)) {
                total = total + (nuevo.getWeight());
            }
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

    public static int sumaCajaFecha(ArrayList<Movimiento> recibido){
        int total = 0;
        String dateEntrada;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        dateEntrada = dateFormat.format(date);

        for(Movimiento nuevo : recibido){
            if(nuevo.getDate().equals(dateEntrada)) {
                total = total + (nuevo.getQuantity());
            }
        }

        return total;
    }


}
