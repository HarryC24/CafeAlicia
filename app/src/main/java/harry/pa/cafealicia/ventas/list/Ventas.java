package harry.pa.cafealicia.ventas.list;

/**
 * Created by harri on 07/13/2016.
 */
public interface Ventas {

    Venta venta(int id);
    void borrar(int id);
    void actualiza(int id,Venta venta);
    void nuevo();
    int lenght();
}
