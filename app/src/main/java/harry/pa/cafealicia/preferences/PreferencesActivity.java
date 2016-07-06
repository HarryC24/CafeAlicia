package harry.pa.cafealicia.preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


/**
 * Created by harri on 06/30/2016.
 */
public class PreferencesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            getFragmentManager().beginTransaction().replace(android.R.id.content,new PreferencesFragment()).commit();
        } catch (Exception e) {
            Log.i("WWWWWWWW",e.toString());
        }
    }
}
