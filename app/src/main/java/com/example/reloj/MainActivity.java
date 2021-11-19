package com.example.reloj;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    int[] id_foto={R.drawable.cat_typing,R.drawable.cuchillos, R.drawable.febrero,
            R.drawable.vara, R.drawable.rojo};
    int pos=0;
    int total_fotos;
    Integer hora,minuto,segundo;
    Integer hora_cambio=0,minuto_cambio=0,segundo_cambio=0;
    ImageView Album_normal;
    //pl.droidsonroids.gif.GifImageView Album_gif;
    TextView Nombre_imagen,Reloj;
    String imageName;
    RadioGroup Formato;
    RadioButton hr_12; //formatos
    final AtomicBoolean terminate= new AtomicBoolean(true);
    int i;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        total_fotos= id_foto.length;
        Button anterior=findViewById(R.id.Anterior);
        Button siguiente=findViewById(R.id.Siguiente);
        ImageButton IB_menos_hr=findViewById(R.id.IB_menos_hr);
        ImageButton IB_menos_min=findViewById(R.id.IB_menos_min);
        ImageButton IB_menos_seg=findViewById(R.id.IB_menos_seg);
        ImageButton IB_mas_hr=findViewById(R.id.IB_mas_hr);
        ImageButton IB_mas_min=findViewById(R.id.IB_mas_min);
        ImageButton IB_mas_seg=findViewById(R.id.IB_mas_seg);

        anterior.setOnClickListener(this);
        siguiente.setOnClickListener(this);
        IB_menos_hr.setOnClickListener(this);
        IB_menos_min.setOnClickListener(this);
        IB_menos_seg.setOnClickListener(this);
        IB_mas_hr.setOnClickListener(this);
        IB_mas_min.setOnClickListener(this);
        IB_mas_seg.setOnClickListener(this);

        Reloj=findViewById(R.id.Reloj);
        Formato= findViewById(R.id.formato);
        hr_12=findViewById(R.id.hr_12);
        Formato.check(hr_12.getId());
        Formato.setOnCheckedChangeListener(this);


        Album_normal= findViewById(R.id.Album_normal);
        //Album_gif=  (pl.droidsonroids.gif.GifImageView) findViewById(R.id.Album_gif);
        Nombre_imagen = findViewById(R.id.Nombre_imagen);

        //if(id_foto[pos]==){
        //}else{
        Album_normal.setImageResource(id_foto[pos]);
        //}

        String imageName = getResources().getResourceName(id_foto[pos]);
        imageName=imageName.substring(27);
        Nombre_imagen.setText(imageName);

        Reloj_actualizacion_12();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pos", pos);
        outState.putInt("i",i);
        outState.putInt("hora",hora_cambio);
        outState.putInt("minuto",minuto_cambio);
        outState.putInt("segundo",segundo_cambio);

    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        pos= savedInstanceState.getInt("pos",0);
        Album_normal.setImageResource(id_foto[pos]);
        imageName = getResources().getResourceName(id_foto[pos]);
        imageName=imageName.substring(27);
        Nombre_imagen.setText(imageName);
        i=savedInstanceState.getInt("i",0);
        hora_cambio=savedInstanceState.getInt("hora",0);
        minuto_cambio=savedInstanceState.getInt("minuto",0);
        segundo_cambio=savedInstanceState.getInt("segundo",0);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

        switch (id){
            case R.id.Siguiente:
                pos++;
                if(pos==total_fotos) pos=0;
                Album_normal.setImageResource(id_foto[pos]);
                imageName = getResources().getResourceName(id_foto[pos]);
                imageName=imageName.substring(27);
                Nombre_imagen.setText(imageName);
                break;
            case R.id.Anterior:
                pos--;
                if(pos==-1) pos=total_fotos-1;
                Album_normal.setImageResource(id_foto[pos]);
                imageName = getResources().getResourceName(id_foto[pos]);
                imageName=imageName.substring(27);
                Nombre_imagen.setText(imageName);
                break;
            case R.id.IB_menos_hr:
                hora_cambio--;
                break;
            case R.id.IB_menos_min:
                minuto_cambio--;
                break;
            case R.id.IB_menos_seg:
                segundo_cambio--;
                break;
            case R.id.IB_mas_hr:
                hora_cambio++;
                break;
            case R.id.IB_mas_min:
                minuto_cambio++;
                break;
            case R.id.IB_mas_seg:
                segundo_cambio++;
                break;
        }
    }



    void Reloj_actualizacion_12(){
        final String[] AM_PM = new String[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (terminate.get()){ //Reloj 12 horas
                    calendar=GregorianCalendar.getInstance();
                    hora=calendar.get(Calendar.HOUR_OF_DAY)+hora_cambio;
                    minuto=calendar.get(Calendar.MINUTE)+minuto_cambio;
                    segundo=calendar.get(Calendar.SECOND)+segundo_cambio;
                    if(segundo>60){
                        segundo=calendar.get(Calendar.SECOND)+segundo_cambio-60;
                        if(segundo_cambio>59)
                        segundo_cambio=0;
                    }
                    if(segundo<0){
                        segundo=segundo+60;
                        if(segundo_cambio<59)
                            segundo_cambio=0;
                    }
                    if(minuto>60){
                        minuto=calendar.get(Calendar.MINUTE)+minuto_cambio-60;
                        if(minuto_cambio>59)
                            minuto_cambio=0;
                    }
                    if(minuto<0){
                        minuto=minuto+60;
                        if(minuto_cambio<-59)
                            minuto_cambio=0;
                    }
                    if(hora>23){
                        hora=0+hora_cambio-1;
                        if(hora_cambio>23)
                            hora_cambio=0;
                    }
                    if(hora<0){
                        hora=23+hora_cambio;
                        if(hora_cambio<-23)
                            hora_cambio=0;

                    }

                    if(hora<12){
                        AM_PM[0] ="AM";

                    }else{
                        AM_PM[0] ="PM";
                        hora=hora-12;
                    }
                    //AM_PM=
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Reloj.setText(hora+":"+minuto+":"+segundo+" "+AM_PM[0]);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }
    void Reloj_actualizacion_24(){

        new Thread(new Runnable() {
            @Override
            public void run(){
                while (!terminate.get()){ //Reloj 24 horas
                    calendar=GregorianCalendar.getInstance();
                    hora=calendar.get(Calendar.HOUR_OF_DAY)+hora_cambio;
                    minuto=calendar.get(Calendar.MINUTE)+minuto_cambio;
                    segundo=calendar.get(Calendar.SECOND)+segundo_cambio;
                    if(segundo>59){
                        segundo=calendar.get(Calendar.SECOND)+segundo_cambio-60;
                        if(segundo_cambio>59)
                            segundo_cambio=0;
                    }
                    if(segundo<0){
                        segundo=segundo+60;
                        if(segundo_cambio<59)
                            segundo_cambio=0;
                    }
                    if(minuto>59){
                        minuto=calendar.get(Calendar.MINUTE)+minuto_cambio-60;
                        if(minuto_cambio>59)
                            minuto_cambio=0;
                    }
                    if(minuto<0){
                        minuto=minuto+60;
                        if(minuto_cambio<-59)
                            minuto_cambio=0;
                    }
                    if(hora>23){
                        hora=0+hora_cambio;
                        if(hora_cambio>23)
                            hora_cambio=0;
                    }
                    if(hora<0){
                        hora=23+hora_cambio;
                        if(hora_cambio<-23)
                            hora_cambio=0;

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Reloj.setText(hora+":"+minuto+":"+segundo);
                        }
                    });
                    i=i+2;
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedID) {
        RadioButton checked =(RadioButton)radioGroup.findViewById(checkedID);


            if(checked.getId()== R.id.hr_12){
                terminate.set(true);
                Reloj_actualizacion_12();
            }
            if(checked.getId()== R.id.hr_24){
                terminate.set(false);
                Reloj_actualizacion_24();
            }


    }
}