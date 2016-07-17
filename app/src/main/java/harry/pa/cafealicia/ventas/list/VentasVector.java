package harry.pa.cafealicia.ventas.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harri on 07/14/2016.
 */
public class VentasVector implements Ventas {

    protected List<Venta> vectorVentas;

    public VentasVector(List<Venta> vectorVentas) {
        this.vectorVentas = vectorVentas;
    }

    @Override
    public Venta venta(int id) {
        return vectorVentas.get(id);
    }
    @Override
    public void borrar(int id) {

    }

    @Override
    public void actualiza(int id, Venta venta) {

    }

    @Override
    public void nuevo() {

    }

    @Override
    public int lenght() {
        return vectorVentas.size();
    }

    public static ArrayList<Venta> ejemploVentas() {

        ArrayList<Venta> ventas = new ArrayList<Venta>();

        ventas.add(new Venta(1,"2016-07-09","13:00:00","1",200.99f,"IRIS","null"));
        ventas.add(new Venta(2,"2016-07-09","21:00:00","2",200.99f,"SIXTA","null"));
        ventas.add(new Venta(3,"2016-07-10","13:12:00","1",200.99f,"PATRY","null"));
        ventas.add(new Venta(4,"2016-07-11","21:09:00","2",200.99f,"SIXTA","null"));
        ventas.add(new Venta(5,"2016-07-12","14:40:00","1",200.99f,"IRIS","null"));
        ventas.add(new Venta(6,"2016-1-7","21:04:00","2",200.99f,"SIXTA","null"));
        ventas.add(new Venta(7,"2016-1-8","13:03:00","1",200.99f,"IRIS","null"));
        ventas.add(new Venta(8,"2016-1-9","13:35:00","1",200.99f,"PATRY","null"));
        ventas.add(new Venta(9,"2016-1-10","20:59:00","2",200.99f,"PATRY","null"));
        ventas.add(new Venta(10,"2016-1-11","14:30:00","1",200.99f,"IRIS","null"));
        return ventas;
    }
}
