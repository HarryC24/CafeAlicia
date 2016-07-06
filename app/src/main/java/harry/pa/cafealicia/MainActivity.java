package harry.pa.cafealicia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harry.pa.cafealicia.login.Hash;
import harry.pa.cafealicia.ventas.VentasActivity;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btnFacturas)
    Button btnFacturas;
    @Bind(R.id.btnCreditos)
    Button btnCreditos;
    @Bind(R.id.btnProductos)
    Button btnProductos;
    @Bind(R.id.btnVentas)
    Button btnVentas;
    @Bind(R.id.btnProveedores)
    Button btnProveedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        verificarPermisos();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                String user_pass = pref.getString("user_pass","0");
                String user_pass2 = "";
                try {
                    Hash hash = new Hash();
                    user_pass2 = hash.sha1(user_pass);
                } catch (Exception e) {
                    Log.i("YYYYYY",e.toString());
                }
                Snackbar.make(view,user_pass2, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
    }

    @OnClick(R.id.btnFacturas)
    public void handleBtnFacturas()
    {

    }
    @OnClick(R.id.btnCreditos)
    public void handleBtnCreditos()
    {
        Log.i("Handele","Creditos");
    }
    @OnClick(R.id.btnProductos)
    public void handleBtnProductos()
    {
        Log.i("Handele","Productos");
    }
    @OnClick(R.id.btnProveedores)
    public void handleBtnProveedores()
    {
        Log.i("Handele","Proveedores");
    }

    @OnClick(R.id.btnVentas)
    public void handleBtnVentas()
    {
        Intent intent = new Intent(this, VentasActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.menu_salir)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void verificarPermisos()
    {
        String user_nivel;
        SharedPreferences pref =  PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        user_nivel = pref.getString("user_nivel","0");
        if(user_nivel.equals("0"))
        {
            btnFacturas.setEnabled(false);
            btnProductos.setEnabled(false);
            btnProveedores.setEnabled(false);
        }
    }

}
