package harry.pa.cafealicia.ventas;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


import butterknife.Bind;
import harry.pa.cafealicia.R;
import harry.pa.cafealicia.connection.Connection;

public class VentasActivity extends AppCompatActivity
        implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    AutoCompleteTextView txtFecha;
    AutoCompleteTextView txtHora;
    AutoCompleteTextView txtVenta;

    RadioButton optTurno;

    private ImageView imageView;
    final static int RESULTADO_EDITAR= 1;
    final static int RESULTADO_GALERIA= 2;
    final static int RESULTADO_FOTO= 3;
    private Uri uriFoto;
    private Bitmap bitmap = null;

    String strFecha;
    String strHora;
    String strVenta;
    String strTurno = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        optTurno = (RadioButton) findViewById(R.id.optTurno1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(preparandoEnvio()){
                    new AlertDialog.Builder(VentasActivity.this)
                            .setTitle("Confirmación de envío")
                            .setMessage("Para envíar el informe, presione OK")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    enviarInforme();
                                }})
                            .setNegativeButton("Cancelar", null)
                            .show();
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtFecha = (AutoCompleteTextView)findViewById(R.id.txtFecha);
        txtFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ponerFecha();
                }
                return true;
            }
        });

        txtHora = (AutoCompleteTextView)findViewById(R.id.txtHora);
        txtHora.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ponerHora();
                }
                return true;
            }
        });
        txtVenta = (AutoCompleteTextView)findViewById(R.id.txtVenta);

        imageView = (ImageView) findViewById(R.id.foto);
    }

    private boolean preparandoEnvio() {
        try {
            try {
                esconderTeclado();
                txtFecha.setError(null);
                txtHora.setError(null);
                txtVenta.setError(null);

                strFecha = txtFecha.getText().toString();
                strHora = txtHora.getText().toString();
                strVenta = txtVenta.getText().toString();


            } catch (Exception e) {
                Log.i("ERORR1",e.toString());
            }


            boolean cancel = false;
            View focusView = null;

            try {
                cancel = false;
                focusView = null;

                if (TextUtils.isEmpty(strFecha)) {
                    txtFecha.setError(getString(R.string.error_field_required));
                    focusView = txtFecha;
                    cancel = true;
                }

                if (TextUtils.isEmpty(strHora)) {
                    txtHora.setError(getString(R.string.error_field_required));
                    focusView = txtHora;
                    cancel = true;
                }

                if (TextUtils.isEmpty(strVenta)) {
                    txtVenta.setError(getString(R.string.error_field_required));
                    focusView = txtVenta;
                    cancel = true;
                }

            } catch (Exception e) {
                Log.i("ERORR2",e.toString());
            }
            try {
                if(strTurno == "0")
                {
                    focusView = optTurno;
                    Toast.makeText(this,"Seleccione el turno",Toast.LENGTH_LONG).show();
                    cancel = true;
                }
            } catch (Exception e) {
                Log.i("ERORR3",e.toString());
            }
            if (cancel)
                focusView.requestFocus();
            return !cancel;
        } catch (Exception e) {
            Log.i("ERORR4",e.toString());
            return false;
        }
    }

    public void camara(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        uriFoto = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "img_" + (System.currentTimeMillis() / 1000) + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
        startActivityForResult(intent, RESULTADO_FOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTADO_GALERIA && resultCode == Activity.RESULT_OK) {
            ponerFoto(imageView, data.getDataString());
        }else if(requestCode == RESULTADO_FOTO && resultCode == Activity.RESULT_OK && uriFoto!=null) {
//            filePath = data.getData();
            ponerFoto(imageView, uriFoto.toString());
        }
    }
    private boolean foto = false;

    protected void ponerFoto(ImageView imageView, String uri) {
        if (uri != null) {
            bitmap = reduceBitmap(this, uri, 1024, 1024);
            imageView.setImageBitmap(bitmap);
            foto = true;
        }
        else foto = false;
    }

    @Nullable
    private Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto) {
        try {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contexto.getContentResolver().openInputStream(Uri.parse(uri)), null, options);
            options.inSampleSize = (int) Math.max( Math.ceil(options.outWidth / maxAncho),Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(contexto.getContentResolver().openInputStream(Uri.parse(uri)), null, options);

        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Foto no encontrada", Toast.LENGTH_LONG).show();
            foto = false;
            return null;
        }
    }

    public void eliminarFoto(View view) {
        ponerFoto(imageView, null);
    }
    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULTADO_GALERIA);
    }
    private void ponerFecha() {

        DialogoSelectorFecha dialogoSelectorFecha = new DialogoSelectorFecha();
        dialogoSelectorFecha.setOnDateSetListener(this);
        dialogoSelectorFecha.show(getSupportFragmentManager(), "selectorFecha");
    }

    private void ponerHora() {
        DialogoSelectorHora dialogoHora = new DialogoSelectorHora();
        dialogoHora.setOnTimeSetListener(this);
        dialogoHora.show(getSupportFragmentManager(), "selectorHora");
    }


    int llamadas = 0;

    @Override
    public void onTimeSet(TimePicker vista, int hora, int minuto) {
        if(llamadas % 2 == 0){
            try {
                Calendar calendario = Calendar.getInstance();
                calendario.set(Calendar.HOUR_OF_DAY, hora);
                calendario.set(Calendar.MINUTE, minuto);
                Date dateRepresentation = calendario.getTime();

                SimpleDateFormat formato = new SimpleDateFormat("HH:mm",java.util.Locale.getDefault());
                txtHora.setText(formato.format(dateRepresentation));

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
                Calendar calendario = Calendar.getInstance();
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, month);
                calendario.set(Calendar.DAY_OF_MONTH, day);
                Date dateRepresentation = calendario.getTime();
                DateFormat formato =  DateFormat.getDateInstance();
                txtFecha.setText(formato.format(dateRepresentation));
            } catch (Exception e) {
                Log.i("error",e.toString());
            }
        }
        llamadas++;
    }


    public void onTurnoClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.optTurno1:
                if (checked)
                   strTurno = "1";
                    break;
            case R.id.optTurno2:
                if (checked)
                    strTurno = "2";
                    break;
            case R.id.optTurno3:
                if (checked)
                    strTurno = "3";
                break;
        }
        esconderTeclado();
    }
    private void esconderTeclado()
    {
        try {
            View teclado = this.getCurrentFocus();
            if (teclado != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(teclado.getWindowToken(), 0);
            }
        }catch(Exception e)
        {}
    }
    private void enviarInforme() {

        class UploadInforme extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            Connection connection = new Connection();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VentasActivity.this, "Enviando Informe de Venta", "Espere...", true, true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                loading.dismiss();
                if(result.trim().equals("1"))
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.dialog_informe_ok), Toast.LENGTH_LONG).show();
                    finish();
                }

                else
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.dialog_informe_error)+": "+result, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {


                try {
                    HashMap<String, String> parametros = new HashMap<>();
                    parametros.put("key",getResources().getString(R.string.connection_key));
                    parametros.put("fecha",strFecha);
                    parametros.put("hora",strHora);
                    parametros.put("venta",strVenta);
                    parametros.put("turno",strTurno);
                    parametros.put("user_email",PreferenceManager.getDefaultSharedPreferences(VentasActivity.this).getString("user_email","?"));
                    if(foto){
                        Bitmap bmp = params[0];
                        String uploadImage = getStringImage(bmp);
                        parametros.put("imagen", uploadImage);
                        StringBuilder file = new StringBuilder();
                        file.append("IMG_");
                        file.append((System.currentTimeMillis() / 1000));
                        file.append(".jpg");
                        String fileName = file.toString();
                        parametros.put("file_name",fileName);
                    }

                    String result = connection.sendPostRequest(parametros);
                    return result;
                } catch (Exception e) {
                   Log.i("ERROR",e.toString());
                    return "MyError";
                }
            }

            private String getStringImage(Bitmap bmp){
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                return encodedImage;
            }
        }
        try {
            UploadInforme ui = new UploadInforme();
            ui.execute(bitmap);
        } catch (Exception e) {
            Log.i("ERROR",e.toString());
        }
    }
}
