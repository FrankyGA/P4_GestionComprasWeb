package modelo;

public class Tienda {

    private int idTienda;
    private String nombreTienda;
    private String direccionTienda;

    // Constructor vacío
    public Tienda() {
    	idTienda=0;
    	nombreTienda="";
    	direccionTienda="";
    }

    // Constructor por parámetros
    public Tienda(int idTienda, String nombreTienda, String direccionTienda) {
    	 // Inicializamos el objeto Tienda con valores específicos
        this.idTienda = idTienda;
        this.nombreTienda = nombreTienda;
        this.direccionTienda = direccionTienda;
    }

    // Métodos get
    public int getIdTienda() {
        return idTienda;
    }

    public String getNombreTienda() {
        return nombreTienda;
    }

    public String getDireccionTienda() {
        return direccionTienda;
    }

    // Métodos set
    public void setIdTienda(int idTienda) {
    	// Establecemos el idTienda del objeto Tienda
        this.idTienda = idTienda;
    }

    public void setNombreTienda(String nombreTienda) {
    	// Establecemos el nombreTienda del objeto Tienda
        this.nombreTienda = nombreTienda;
    }

    public void setDireccionTienda(String direccionTienda) {
    	// Establecemos el direccionTienda del objeto Tienda
        this.direccionTienda = direccionTienda;
    }
}
