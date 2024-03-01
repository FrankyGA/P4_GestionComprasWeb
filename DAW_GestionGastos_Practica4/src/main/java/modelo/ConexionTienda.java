package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// Extendemos siempre de la clase ConexionBD

public class ConexionTienda extends ConexionBD {

    // Métodos específicos de ConexionTienda

    // Método consulta para obtener lista de tiendas
    public static List<Tienda> obtenerTiendas() {
    	// creamos un arrayList para meter la lista de las tiendas
        List<Tienda> tiendas = new ArrayList<>();

        try (Connection conn = conectar()) {
        	// consulta para obtener las tiendas
            String query = "SELECT * FROM tiendas";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                	// Iteramos sobre los resultados y creamos instancias de la clase Tienda
                    while (rs.next()) {
                        int idTienda = rs.getInt("idTienda");
                        String nombreTienda = rs.getString("nombreTienda");
                        String direccionTienda = rs.getString("direccionTienda");
                        // creamos instancias del objeto tienda con los datos obtenido de la bbdd
                        Tienda tienda = new Tienda(idTienda, nombreTienda, direccionTienda);
                        // metemos en el arrayList tiendas todas las instancias del objeto tienda             
                        tiendas.add(tienda);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tiendas; // nos devuelve el arrayList 
    }

  

    // Obtiene y devuelve la información de una tienda específica según su ID.  
    
    public static Tienda obtenerTiendaPorId(int idTienda) {
        Tienda tienda = null;

        try (Connection conn = conectar()) {
        	// consulta para obtener la información de un id específico
            String query = "SELECT * FROM tiendas WHERE idTienda=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idTienda);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("idTienda");
                        String nombre = rs.getString("nombreTienda");
                        String direccion = rs.getString("direccionTienda");
                        // Crea una instancia del objeto Tienda con la información de la tienda específica.
                        tienda = new Tienda(id, nombre, direccion); 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tienda; // nos devuelve esa tienda 
    }

    
    // método para agregar una tienda
    public static void anadirTienda(Tienda tienda) {
        try (Connection conn = conectar()) {
       // sentencia insert into para agregar una tienda
            String query = "INSERT INTO tiendas (nombreTienda, direccionTienda) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, tienda.getNombreTienda());
                stmt.setString(2, tienda.getDireccionTienda());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// método para modificar tienda
    public static void modificarTienda(Tienda tienda) {
        try (Connection conn = conectar()) {
      // sentencia para modificar una tienda
            String query = "UPDATE tiendas SET nombreTienda=?, direccionTienda=? WHERE idTienda=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, tienda.getNombreTienda());
                stmt.setString(2, tienda.getDireccionTienda());
                stmt.setInt(3, tienda.getIdTienda());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// método para borrar tienda
    public static void borrarTienda(int idTienda) {
        try (Connection conn = conectar()) {
       //sentencia para borrar una tienda
            String query = "DELETE FROM tiendas WHERE idTienda=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idTienda);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 // Verifica si una tienda especifica tiene compras asociadas.
    public static boolean tiendaTieneCompras(int idTienda) {
        boolean tieneCompras = false;

        try (Connection conn = conectar()) {
        	// consulta que cuenta las  compras de un idTiendaFK específico
            String query = "SELECT COUNT(*) FROM compras WHERE idTiendaFK=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idTienda);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                    	 // Obtiene la cantidad de compras asociadas a la tienda
                        int cantidadCompras = rs.getInt(1);
                        tieneCompras = cantidadCompras > 0; // compara si el numero de compras es mayor que 0
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tieneCompras; // Devuelve true si la tienda tiene compras, false en caso contrario.
    }

}
