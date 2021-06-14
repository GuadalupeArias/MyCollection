package com.example.mycollection;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mycollection.servicies.UsuarioService;

import java.util.Calendar;
import java.util.concurrent.CancellationException;

import entidades.Usuario;

public class EventoCompra extends AppCompatActivity {

    EditText nombreEvento;
    EditText placeEvento;
    EditText descripEvento;
    EditText horaIniElegida;
    EditText horaFinElegida;
    EditText fechaElegida;
    ImageButton pickDate;
    ImageButton pickTimeIni;
    ImageButton pickTimeFin;
    ImageButton agendar;
    Usuario u;
    UsuarioService usuarioService = new UsuarioService();

    //variables para guardar calendario
    int anio=0;
    int mes= 0;
    int dia=0;
    int horaIni=0;
    int minIni=0;
    int horaFin=0;
    int minFin=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_compra);
        u=usuarioService.getUserById(getIntent().getIntExtra("Id",0), EventoCompra.this);
        nombreEvento=(EditText)findViewById(R.id.etTituloEvento);
        placeEvento=(EditText)findViewById(R.id.etLugarEvento);
        descripEvento=(EditText)findViewById(R.id.etDescripEvento);
        horaIniElegida=(EditText)findViewById(R.id.etHoraInicio);
        horaFinElegida=(EditText)findViewById(R.id.etHoraFin);
        fechaElegida=(EditText)findViewById(R.id.etFechaEvento);
        fechaElegida.setFocusable(false);
        horaIniElegida.setFocusable(false);
        horaFinElegida.setFocusable(false);
        pickDate=findViewById(R.id.btnPickDate);
        pickTimeIni=findViewById(R.id.btnPickTimeStart);
        pickTimeFin=findViewById(R.id.btnPickTimeEnd);
        agendar=findViewById(R.id.btnAgendar);


        pickDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            anio=c.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EventoCompra.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year , int monthOfYear, int  dayOfMonth) {
                        anio=year;
                        dia=dayOfMonth;
                        mes=monthOfYear;
                        fechaElegida.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });

        pickTimeIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                horaIni=c.get(Calendar.HOUR_OF_DAY);
                minIni=c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventoCompra.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        minIni=minute;
                        horaIni=hourOfDay;
                        horaIniElegida.setText(hourOfDay+":"+minute);
                    }
                },horaIni,minIni,false);
                timePickerDialog.show();
            }
        });

        pickTimeFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c= Calendar.getInstance();
                horaFin=c.get(Calendar.HOUR_OF_DAY);
                minFin=c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(EventoCompra.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        minFin=minute;
                        horaFin=hourOfDay;
                        horaFinElegida.setText(hourOfDay+":"+minute);
                    }
                },horaFin,minFin,false);
                timePickerDialog.show();

            }
        });

        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agendarEventoCalendario();

                Intent i = new Intent(EventoCompra.this, Inicio.class);
                i.putExtra("Id", u.getId());
                startActivity(i);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i2 = new Intent(EventoCompra.this, Inicio.class);
        i2.putExtra("Id", u.getId());
        startActivity(i2);
        finish();
    }

    public void agendarEventoCalendario (){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        Intent intent = null;

        cal1.set(Calendar.YEAR, anio);
        cal1.set(Calendar.MONTH,mes);
        cal1.set(Calendar.DAY_OF_MONTH, dia);
        cal1.set(Calendar.HOUR_OF_DAY,horaIni);
        cal1.set(Calendar.MINUTE,minIni);

        cal2.set(Calendar.YEAR, anio);
        cal2.set(Calendar.MONTH,mes);
        cal2.set(Calendar.DAY_OF_MONTH, dia);
        cal2.set(Calendar.HOUR_OF_DAY,horaFin);
        cal2.set(Calendar.MINUTE,minFin);

        intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,cal1.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal2.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.TITLE,nombreEvento.getText().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION,descripEvento.getText().toString());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,placeEvento.getText().toString());

        startActivity(intent);
    }
}




