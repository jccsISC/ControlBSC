package com.jccsisc.controlbsc.utilidades;
import com.jccsisc.controlbsc.model.Movimiento;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Aritmetica {

    //sumamos el total del peso de todas las entradas del producto
    public static double sumaMovimiento(ArrayList<Movimiento> recibido) {
        double total = 0;
        double totalNegative = 0.0;
        for (Movimiento nuevo : recibido) {
            if(nuevo.getType().equals("positive")){
                total += nuevo.getWeight();
            }
        }
        for(Movimiento nuevo : recibido){
            if(nuevo.getType().equals("negative")){
                totalNegative += nuevo.getWeight();
            }
        }
        return total - totalNegative;
    }


    //sumamos el total de los movimientos del mismo dia
    public static double sumaMovimientoFecha(ArrayList<Movimiento> recibido){
        double total = 0;
        String dateEntrada;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
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
       int totalNegative = 0;
        for(Movimiento nuevo : recibido){
            if(nuevo.getType().equals("positive")){
                total += nuevo.getQuantity();
            }
        }
       for(Movimiento nuevo : recibido){
           if(nuevo.getType().equals("negative")){
               totalNegative += nuevo.getQuantity();
           }
       }
        return total - totalNegative;
    }

    public static int sumaCajaFecha(ArrayList<Movimiento> recibido){
        int total = 0;
        String dateEntrada;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
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
