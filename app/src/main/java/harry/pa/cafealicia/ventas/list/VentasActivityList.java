package harry.pa.cafealicia.ventas.list;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import harry.pa.cafealicia.R;
import harry.pa.cafealicia.connection.Connection;
import harry.pa.cafealicia.util.util;

public class VentasActivityList extends AppCompatActivity {

//    public static Ventas ventas = new VentasVector();

    public static Ventas ventas;
    public ArrayList<Venta> ventasArray = new ArrayList<Venta>();
    private RecyclerView recyclerView;
    public VentasAdapter adaptador;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressBar loadProgress;
    private View myView;
    Map<Integer,Integer> indices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ventas_activity_list);

            loadProgress = (ProgressBar)findViewById(R.id.load_progress);
            myView = findViewById(R.id.layout_list_ventas) ;
            showProgress(true);
            getDatos();

        } catch (Exception e) {
           Log.i("ERROR",e.toString());
        }
    }

    private void mostrarDatos()
    {
        ventas = new VentasVector(ventasArray);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_ventas);
        adaptador = new VentasAdapter(this, ventas);
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int itemPosition = recyclerView.getChildLayoutPosition(v);
                int id = indices.get(recyclerView.getChildLayoutPosition(v));
                Toast.makeText(VentasActivityList.this, String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showProgress(boolean isVisible) {
        loadProgress.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }


    private void getDatos() {

        class getDatosFromServer extends AsyncTask<Void, Void, Boolean>
        {
        private int error_code;

            @Override
            protected void onPostExecute(final Boolean success) {
                showProgress(false);
                if(success)
                {
                    mostrarDatos();
                }
                else
                    util.makeSnackBar(myView,"ErrorCode: "+error_code,0);
            }

            @Override
            protected  Boolean doInBackground(Void... params) {
                String respuesta = null;
                try {
                    Map<String,String> parametros = new HashMap<>();
                    parametros.put("key",getResources().getString(R.string.connection_key));
                    parametros.put("peticion","ventas_showList");
                    Connection connection = new Connection(parametros);
                    respuesta = connection.peticion();
                } catch (Exception e) {
                    error_code=0;
                    return false;
                }
                if (respuesta.equals("-1")) {
                    util.makeSnackBar(myView,getString(R.string.error_connection_timeout),0);
                    error_code=1;
                    return false;
                }
                if (respuesta.equals("0") || respuesta.length() == 0 ) {
                    util.makeSnackBar(myView,getString(R.string.error_connection_timeout),0);
                    error_code=2;
                    return false;
                }

                try {
                    JSONArray jsonarray = new JSONArray(respuesta);
//                    ArrayList<Venta> ventas = new ArrayList<Venta>();

                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        int id = Integer.parseInt(jsonobject.getString("id"));
                        String fecha = jsonobject.getString("fecha");
                        String hora = jsonobject.getString("hora");
                        String turno = jsonobject.getString("turno");
                        Float venta = Float.parseFloat(jsonobject.getString("venta"));
                        String empleado = jsonobject.getString("empleado");
                        String imagen = jsonobject.getString("imagen");
                        ventasArray.add(new Venta(id,fecha,hora,turno,venta,empleado,imagen));
                        indices.put(i,id);
                    }
                } catch (JSONException e) {
                    error_code=3;
                    return false;
                }
                return true;
            }
        }
        getDatosFromServer g = new getDatosFromServer();
        g.execute();
    }


}
