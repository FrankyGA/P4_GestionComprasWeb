package modelo;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

public class ConexionBD {
	
	// Declaramos el DataSource  para el pool de conexiones
	private static DataSource pool;

    static {
        try {
            Context ctx = new InitialContext();
         // establece el pool para la base de datos gestiongastos
            pool = (DataSource) ctx.lookup("java:comp/env/jdbc/gestiongastos");
            
         // manejo de excepciones   en caso de errores
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
   
    
    // Método para establecer la conexi�n
    public static Connection conectar() throws SQLException {
    	  // Devolvemos la conexi�n obtenida desde el pool de conexiones
        return pool.getConnection(); 
    }
 
    
    // M�todo para desconectarse de la bbdd
    public static void desconectar(Connection conexion) {
        if (conexion != null) {
            try {
           // Cerramos la conexi�n si no es nula
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    
}
