package harry.pa.cafealicia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import harry.pa.cafealicia.ventas.VentasActivity;
import harry.pa.cafealicia.ventas.list.VentasActivityList;

public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dialog_ventas);
        } catch (Exception e) {

        }
    }

    public void crear(View view)
    {
        Intent intent = new Intent(this, VentasActivity.class);
        startActivity(intent);
        finish();
    }

    public void ver(View view)
    {
        Intent intent = new Intent(this, VentasActivityList.class);
        startActivity(intent);
        finish();
    }
}
