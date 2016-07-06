package harry.pa.cafealicia.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import harry.pa.cafealicia.R;

/**
 * Created by harri on 06/30/2016.
 */
public class PreferencesFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
