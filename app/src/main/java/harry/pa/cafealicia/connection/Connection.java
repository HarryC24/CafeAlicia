package harry.pa.cafealicia.connection;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by harri on 07/04/2016.
 */
public class Connection  {

    private String uri;
    private static final String URI="http://cafealicia.sytes.net/CafeAlicia/Services/service.php?";
    private static final int TIME_OUT = 5000;
    private Map<String,String> params;

    public Connection(Map<String, String> params) {
        this.params = params;
        construirUriDePeticion(this.params);
    }

    private void construirUriDePeticion(Map<String, String> params) {
        uri = URI;
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            uri += entry.getKey()+"="+entry.getValue()+ "&";
        }

    }


    @Nullable
    private InputStream HttpRequest() {
        try {
            URL url = new URL(this.uri);
            URLConnection urlConexion = url.openConnection();
            HttpURLConnection httpConexion= (HttpURLConnection) urlConexion;
            httpConexion.setConnectTimeout(TIME_OUT);
            return httpConexion.getInputStream();
        } catch (Exception e) {
            return null;
        }
    }


    public String respuesta() {
        try {

            InputStream is = HttpRequest();

                if(is == null) return "-1"; // error en conexión

            InputStreamReader reader = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(reader);

            StringBuilder sb = new StringBuilder();


            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }

            br.close();

            if(sb == null) return "0"; // sin datos
            return sb.toString();
        } catch (Exception e) {
              return "";
        }
    }
}
