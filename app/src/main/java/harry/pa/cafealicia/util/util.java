package harry.pa.cafealicia.util;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by harri on 07/15/2016.
 */
public class util {

    public static String parseFecha(String formatoEntrada,String formatoSalida,String fechaEntrada)
    {
        SimpleDateFormat inputFormat = new SimpleDateFormat(formatoEntrada, java.util.Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(formatoSalida, java.util.Locale.getDefault());
        Date fechaParse = null;
        try {
            fechaParse = inputFormat.parse( fechaEntrada );
        } catch (ParseException e) {
            return e.toString();
        }
        return outputFormat.format( fechaParse );
    }

    public static void logInfo(String mensaje)
    {
        Log.i("LOG",mensaje);
    }
    public static void logInfo(String tag,String mensaje)
    {
        Log.i(tag,mensaje);
    }

    @NonNull
    public static String turnoToString(int turno)
    {
        switch (turno)
        {
            case 1:
                return "Matutino";
            case 2:
                return "Nocturno";
            case 3:
                return "Domingo";
        }
        return String.valueOf(turno);
    }

    public static void makeSnackBar(View view,String mensaje,int duration){
        Snackbar.make(view,mensaje,duration).setAction("Action", null).show();
    }
}
