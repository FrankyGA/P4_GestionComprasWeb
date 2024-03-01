package modelo;

import java.sql.Date;

public class Compras {
    private int idCompra;
    private double importeCompra;
    private Date fechaCompra;
    private int idTiendaFK;
    private String nombreTienda; // Nuevo atributo para el nombre de la tienda
    private int idUsuarioFK;

    // Constructor vacÃ­o
    public Compras() {
        this.idCompra = 0;
        this.importeCompra = 0.0;
        this.fechaCompra = new Date(System.currentTimeMillis()); // Fecha actual
        this.idTiendaFK = 0;
        this.nombreTienda = "";
        this.idUsuarioFK = 0; // Puedes ajustar el valor predeterminado según tus necesidades
    }

    // Constructor por parámetros
    public Compras(int idCompra, double importeCompra, Date fechaCompra, int idTiendaFK, int idUsuarioFK) {
        this.idCompra = idCompra;
        this.importeCompra = importeCompra;
        this.fechaCompra = fechaCompra;
        this.idTiendaFK = idTiendaFK;
        this.nombreTienda = "";
        this.idUsuarioFK = idUsuarioFK;
    }

    // Métodos get
    public int getIdCompra() {
        return idCompra;
    }

    public double getImporteCompra() {
        return importeCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public int getIdTiendaFK() {
        return idTiendaFK;
    }
    public String getNombreTienda() {
        return nombreTienda;
    }
    public int getIdUsuarioFK() {
        return idUsuarioFK;
    }

    // Métodos set
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public void setImporteCompra(double importeCompra) { // Corregir el tipo de parÃ¡metro a double
        this.importeCompra = importeCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public void setIdTiendaFK(int idTiendaFK) {
        this.idTiendaFK = idTiendaFK;
    }
    public void setNombreTienda(String nombreTienda) {
        this.nombreTienda = nombreTienda;
    }
    public void setIdUsuarioFK(int idUsuarioFK) {
        this.idUsuarioFK = idUsuarioFK;
    }

}

