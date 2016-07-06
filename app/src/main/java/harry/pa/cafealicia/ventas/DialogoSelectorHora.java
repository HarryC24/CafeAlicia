package harry.pa.cafealicia.ventas;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by harri on 07/05/2016.
 */
public class DialogoSelectorHora extends DialogFragment {
    private TimePickerDialog.OnTimeSetListener escuchador;

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener escuchador){
        this.escuchador = escuchador;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), escuchador, hora, minuto, DateFormat.is24HourFormat(getActivity()));
    }
}
