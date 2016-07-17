package harry.pa.cafealicia.ventas.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;

import harry.pa.cafealicia.R;
import harry.pa.cafealicia.util.util;

public class VentasAdapter extends   RecyclerView.Adapter<VentasAdapter.ViewHolder> {
    protected Ventas ventas;           //Ventas a mostrar
    protected LayoutInflater inflador;   //Crea Layouts a partir del XML
    protected Context contexto;          //Lo necesitamos para el inflador
    protected View.OnClickListener onClickListener;
    NumberFormat format = NumberFormat.getCurrencyInstance();

    public VentasAdapter(Context contexto, Ventas ventas) {
        this.contexto = contexto;
        this.ventas = ventas;
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtFecha, txtTurno, txtVenta;

        public ViewHolder(View itemView) {
            super(itemView);
            txtFecha = (TextView) itemView.findViewById(R.id.fecha_informe);
            txtTurno = (TextView) itemView.findViewById(R.id.turno_informe);
            txtVenta = (TextView) itemView.findViewById(R.id.venta_informe);

        }
    }

    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_venta, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int indice) {
        Venta venta = ventas.venta(indice);
        personalizaVista(holder, venta);
    }

    // Personalizamos un ViewHolder a partir de una venta
    public void personalizaVista(ViewHolder holder, Venta venta) {

        try {
            String strFecha = venta.getFecha();
            String strHora = venta.getHora();
            String fecha = util.parseFecha("yyyy-MM-dd","E, dd MMM, yyyy",strFecha);
            String hora = util.parseFecha("hh:mm:ss","hh:mm aa",strHora);
            holder.txtFecha.setText(fecha + "   " + hora);
            holder.txtTurno.setText(util.turnoToString(Integer.parseInt(venta.getTurno())));
            holder.txtVenta.setText(format.format(venta.getVenta()));
        } catch (Exception e) {
            util.logInfo(e.toString());
        }


    }

    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return ventas.lenght();
    }


}