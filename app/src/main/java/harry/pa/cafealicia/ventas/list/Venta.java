package harry.pa.cafealicia.ventas.list;

import harry.pa.cafealicia.util.util;

/**
 * Created by harri on 07/13/2016.
 */
public class Venta  {

    private String fecha;
    private String hora;
    private String turno;
    private Float venta;
    private String empleado;
    private int indice;
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setVenta(Float venta) {
        this.venta = venta;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public Venta(int indice, String fecha, String hora, String turno, Float venta, String empleado,String imgPath) {
        this.indice = indice;
        this.fecha = fecha;
        this.hora = hora;
        this.turno = turno;
        this.venta = venta;
        this.empleado = empleado;
        this.imagePath = imgPath;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getTurno() {
        return turno;
    }

    public Float getVenta() {
        return venta;
    }

    public String getEmpleado() {
        return empleado;
    }

    public int getIndice() {
        return indice;
    }
}
