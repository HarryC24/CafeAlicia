package harry.pa.cafealicia.ventas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import butterknife.Bind;
import harry.pa.cafealicia.R;

public class VentasActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.txtFecha)
    EditText txtFecha;
    @Bind(R.id.txtHora)
    EditText txtHora;
    @Bind(R.id.txtVenta)
    EditText txtVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtFecha = (EditText)findViewById(R.id.txtFecha);
        txtFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {

                    ponerFecha();
                }
                return true; // return is important...
            }
        });

        txtHora = (EditText)findViewById(R.id.txtHora);
        txtHora.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {

                    ponerHora();
                }
                return true; // return is important...
            }
        });
    }

    private void ponerFecha() {

        DialogoSelectorFecha dialogoSelectorFecha = new DialogoSelectorFecha();
        dialogoSelectorFecha.setOnDateSetListener(this);
        dialogoSelectorFecha.show(getSupportFragmentManager(), "selectorFecha");
    }

    private void ponerHora() {
        DialogoSelectorHora dialogoHora = new DialogoSelectorHora();
        dialogoHora.setOnTimeSetListener(this);
       /* Bundle args = new Bundle();
        args.putLong("fecha", lugar.getFecha());
        dialogoHora.setArguments(args);*/
        dialogoHora.show(getSupportFragmentManager(), "selectorHora");
    }


    int llamadas = 0;
    @Override
    public void onTimeSet(TimePicker vista, int hora, int minuto) {
        if(llamadas % 2 == 0){
            try {
                String text = String.format(getResources().getString(R.string.text_hourpicker), hora, minuto);
                txtHora.setText(text);
            } catch (Exception e) {
               Log.i("error",e.toString());
            }
        }
        llamadas++;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if(llamadas % 2 == 0){
            try {
                String text = String.format(getResources().getString(R.string.text_datepicker), month, day,year);
                txtFecha.setText(text);
            } catch (Exception e) {
                Log.i("error",e.toString());
            }
        }
        llamadas++;
    }
}
