package modelo;

public class Usuario {

    private int idUsuario;
    private String nombreUsuario;
    private String claveUsuario;

    // Constructor vacío
    public Usuario() {
    	 // Inicializamos los valores por defecto del objeto Usuario
    	idUsuario=0;
    	nombreUsuario="";
    	claveUsuario="";
    }

    // Constructor por parámetros
    public Usuario(int idUsuario, String nombreUsuario, String claveUsuario) {
    	  // Inicializamos el objeto Usuario con valores específicos
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.claveUsuario = claveUsuario;
    }

    // Métodos get
    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    // Métodos set
    public void setIdUsuario(int idUsuario) {
    	// Establecemos el idUsuario del objeto Usuario
        this.idUsuario = idUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
    	 // Establecemos el nombreUsuario del objeto Usuario
        this.nombreUsuario = nombreUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
    	  // Establecemos la claveUsuario del objeto Usuario
        this.claveUsuario = claveUsuario;
    }
}

