package harry.pa.cafealicia.ventas;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by harri on 07/06/2016.
 */
public class DialogoSelectorFecha extends DialogFragment {
    private DatePickerDialog.OnDateSetListener escuchador;

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener escuchador) {
        this.escuchador = escuchador;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), escuchador, year, month, day);

    }
}
