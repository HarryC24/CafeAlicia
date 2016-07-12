package harry.pa.cafealicia.connection;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import harry.pa.cafealicia.R;

/**
 * Created by harri on 07/04/2016.
 */
public class Connection  {

    private String uri;

    private static final String URI_REQUEST = "http://cafealicia.sytes.net/CafeAlicia/Services/services.php?";
    private static final String URI_POST_INFORME = "http://cafealicia.sytes.net/CafeAlicia/Services/upload_informe.php?";

    private static final int TIME_OUT = 10000;
    private static final int TIME_OUT_POST = 30000;

//    private Map<String,String> params;
    private String stringParams;

    public Connection(Map<String, String> params) {
        stringParams = getDataString(params);
    }
    public Connection() {
        stringParams = "";
    }

    @NonNull
    private String getDataString(Map<String, String> params) {

        try {
            StringBuilder result = new StringBuilder();
            boolean primerParametro = true;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (primerParametro)
                    primerParametro = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();

        }catch(UnsupportedEncodingException e)
        {
            Log.i("ERROR",e.toString());
            return null;
        }

    }


    @Nullable
    private InputStream HttpRequest() {
        try {

            uri = URI_REQUEST + stringParams;
            Log.i("URI",uri);
            URL url = new URL(uri);
            URLConnection urlConexion = url.openConnection();
            HttpURLConnection httpConexion= (HttpURLConnection) urlConexion;
            httpConexion.setConnectTimeout(TIME_OUT);
            return httpConexion.getInputStream();
        } catch (Exception e) {
            Log.i("EROOR",e.toString());
            return null;
        }
    }


    public String peticion() {
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
    public String sendPostRequest(HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(URI_POST_INFORME);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT_POST);
            conn.setConnectTimeout(TIME_OUT_POST);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                response = sb.toString();
            } else {
                response = "Error Registering";
            }
        } catch (Exception e) {
            response = e.toString();

        }
        return response;
    }
}
