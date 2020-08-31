package com.jccsisc.controlbsc.utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EventCalculator {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    Calendar calendar = Calendar.getInstance();

    public String ConvertirFecha(String fecha){
        Date fechaProceso;
        String fechita = null;
        try {
            fechaProceso = formatter.parse(fecha);
            fechita = formatter.format(fechaProceso);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechita;
    }


    public String daysRemaining(Date evento, Date today){

        int days;
        String Result = null;
        long diffInMillies = Math.abs(evento.getTime() - today.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        days = (int) diff;

        if(days == 1){
            Result = "Mañana";
        }else if( days == 2){
            Result = "Pasado mañana";
        }else if( days < 7 ){
            Result = "Faltan " + days + " dias";
        }else if( days == 7){
            Result = "Falta 1 semana";
        }else if( days <=30){
            Result = "Falta más de 1 semana ( " + days + " dias )";
        }else if(days <60){
            Result = "Falta más de 1 mes ( " + days + " dias )";
        }else if(days <90){
            Result = "Falta más de 2 meses ( " + days + " dias )";
        }else if(days <120){
            Result = "Falta más de 3 meses ( " + days + " dias )";
        } else if(days <150){
            Result = "Falta más de 4 meses ( " + days + " dias )";
        }else if(days <180){
            Result = "Falta más de 5 meses ( " + days + " dias )";
        }else if(days <210){
            Result = "Falta más de 6 meses ( " + days + " dias )";
        }else if(days <240){
            Result = "Falta más de 7 meses ( " + days + " dias )";
        }else if(days <270){
            Result = "Falta más de 8 meses ( " + days + " dias )";
        }else if(days <300){
            Result = "Falta más de 9 meses ( " + days + " dias )";
        }else if(days <330){
            Result = "Falta más de 10 meses ( " + days + " dias )";
        }else if(days <360){
            Result = "Falta más de 11 meses ( " + days + " dias )";
        } else if( days <365){
            Result = "Faltan " + days  + " dias";
        }else{
            Result = "Falta más de 1 año";
        }

        return Result;

    }

    public String HourRemaining(Date evento, Date today){
        int seconds;
        int minutes;
        int hours;
        String Result = null;
        long diffInMillies = Math.abs(evento.getTime() - today.getTime());
        long diff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        seconds = (int) diff;
        hours = seconds / 3600;
        minutes = seconds / 60;
        if(seconds <60){
            Result = "Ya tienes que estar en el lugar";
        }else{
            if(hours<1){
                if(minutes <2){
                    Result = "Falta 1 minuto";
                }else{
                    Result =  "Faltan " + minutes + " minutos";
                }
            }else if(hours<2){
                Result = "Falta 1 hora y " + (minutes - 60) + " minutos" ;
            }else if(hours<3){
                Result = "Faltan 2 horas y " + (minutes - (60 * 2)) + " minutos" ;
            }else if(hours<4){
                Result = "Faltan 3 horas y " + (minutes - (60 * 3)) + " minutos" ;
            }else if(hours<5){
                Result = "Faltan 4 horas y " + (minutes - (60 * 4)) + " minutos" ;
            }else if(hours<6){
                Result = "Faltan 5 horas y " + (minutes - (60 * 5)) + " minutos" ;
            }else if(hours<7){
                Result = "Faltan 6 horasy " + (minutes - (60 * 6)) + " minutos" ;
            }else if(hours<8){
                Result = "Faltan 7 horas y " + (minutes - (60 * 7)) + " minutos" ;
            }else if(hours<9){
                Result = "Faltan 8 horas y " + (minutes - (60 * 8)) + " minutos" ;
            }else if(hours<10){
                Result = "Faltan 9 horas y " + (minutes - (60 * 9)) + " minutos" ;
            }else if(hours<11){
                Result = "Faltan 10 horas y " + (minutes - (60 * 10)) + " minutos" ;
            }else if(hours<12){
                Result = "Faltan 11 horas y " + (minutes - (60 * 11)) + " minutos" ;
            }else if(hours<13){
                Result = "Faltan 12 horas y " + (minutes - (60 * 12)) + " minutos" ;
            }else if(hours<14){
                Result = "Faltan 13 horas y " + (minutes - (60 * 13)) + " minutos" ;
            }else if(hours<15){
                Result = "Faltan 14 horas y " + (minutes - (60 * 14)) + " minutos" ;
            }else if(hours<16){
                Result = "Faltan 15 horas y " + (minutes - (60 * 15)) + " minutos" ;
            }else if(hours<17){
                Result = "Faltan 16 horas y " + (minutes - (60 * 16)) + " minutos" ;
            }else if(hours<18){
                Result = "Faltan 17 horas y " + (minutes - (60 * 17)) + " minutos" ;
            }else if(hours<19){
                Result = "Faltan 18 horas y " + (minutes - (60 * 18)) + " minutos" ;
            }else if(hours<20){
                Result = "Faltan 19 horas y " + (minutes - (60 * 19)) + " minutos" ;
            }else if(hours<21){
                Result = "Faltan 20 horas y " + (minutes - (60 * 20)) + " minutos" ;
            }else if(hours<22){
                Result = "Faltan 21 horas y " + (minutes - (60 * 21)) + " minutos" ;
            }else if(hours<23){
                Result = "Faltan 22 hora y " + (minutes - (60 * 22)) + " minutos" ;
            }else if(hours<24){
                Result = "Faltan 23 hora y " + (minutes - (60 * 23)) + " minutos" ;
            }
        }
        return Result;
    }

    public String getToday(){
        String dia, mes, anio;
        anio = String.valueOf(calendar.get(Calendar.YEAR));
        if(calendar.get(Calendar.DAY_OF_MONTH) <10){
            dia = "0"+ calendar.get(Calendar.DAY_OF_MONTH);
        }else{
            dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }
        if((calendar.get(Calendar.MONTH)  + 1)<10){
            mes = "0"+ (calendar.get(Calendar.MONTH) + 1);
        }else{
            mes = String.valueOf((calendar.get(Calendar.MONTH) + 1));
        }
        return dia + "/" + mes + "/" + anio;
    }

    public String getHour(){
        String hora, minuto, segundo;
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute= rightNow.get(Calendar.MINUTE);
        int currentSecond = rightNow.get(Calendar.SECOND);
        if(currentHour<10){
            hora = "0" + currentHour;
        }else{
            hora = String.valueOf(currentHour);
        }
        if(currentMinute<10){
            minuto = "0" + currentMinute;
        }else{
            minuto = String.valueOf(currentMinute);
        }
        if(currentSecond<10){
            segundo = "0" + currentSecond;
        }else{
            segundo = String.valueOf(currentSecond);
        }

        return hora + ":" + minuto + ":" + segundo;
    }

    public String nombreMes(String numeroMes){
        String nombreMesCompleto = "";
        if(numeroMes.equals("01")){
            nombreMesCompleto = "enero";
        }else if(numeroMes.equals("02")){
            nombreMesCompleto = "febrero";
        }else if(numeroMes.equals("03")){
            nombreMesCompleto = "marzo";
        }else if(numeroMes.equals("04")){
            nombreMesCompleto = "abril";
        }else if(numeroMes.equals("05")){
            nombreMesCompleto = "mayo";
        }else if(numeroMes.equals("06")){
            nombreMesCompleto = "junio";
        }else if(numeroMes.equals("07")){
            nombreMesCompleto = "julio";
        }else if(numeroMes.equals("08")){
            nombreMesCompleto = "agosto";
        }else if(numeroMes.equals("09")){
            nombreMesCompleto = "septiembre";
        }else if(numeroMes.equals("10")){
            nombreMesCompleto = "octubre";
        }else if(numeroMes.equals("11")){
            nombreMesCompleto = "noviembre";
        }else if(numeroMes.equals("12")){
            nombreMesCompleto = "diciembre";
        }

        return nombreMesCompleto;
    }


    public String fechaCompleta(int dia, int mes, int anio){

        String diaN, mesN = null;
        if(dia<10){
            diaN = "0"+dia;
        }else{
            diaN = String.valueOf(dia);
        }

        if((mes + 1) <10){
            mesN = "0" + (mes + 1);
        }else{
            mesN = String.valueOf(mes + 1);
        }

        return diaN + "-" + mesN + "-" + anio;
    }

    public String nombreSemana(String dateString){
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fecha = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);
        if(fecha.equals("Monday")){
            fecha = "Lunes";
        }else if(fecha.equals("Tuesday")){
            fecha ="Martes";
        }else if(fecha.equals("Wednesday")){
            fecha ="Miercoles";
        }else if(fecha.equals("Thursday")){
            fecha ="Jueves";
        }else if(fecha.equals("Friday")){
            fecha ="Viernes";
        }else if(fecha.equals("Saturday")){
            fecha ="Sabado";
        }else if(fecha.equals("Sunday")){
            fecha ="Domingo";
        }
        return fecha;
    }

}