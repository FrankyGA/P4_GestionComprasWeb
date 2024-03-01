package modelo;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * M茅todo para autenticar a un usuario en la base de datos.
 *
 * @param conn           Conexi贸n a la base de datos.
 * @param nombreUsuario  Nombre de usuario proporcionado en el formulario de inicio de sesi贸n.
 * @param claveUsuario   Contrase帽a proporcionada en el formulario de inicio de sesi贸n.
 * @return true si la autenticaci贸n es exitosa, false en caso contrario.
 */

// los m閠odos extienden de la clase ConexionBD para conectarse a la BBDD
// m閠odo para la conexi髇
public class ConexionUsuario extends ConexionBD {
	
	// M閠odo que verifica la autenticaci髇 del usuario.
	// Se llama desde ServletUsuario y utiliza la conexi髇 a la base de datos (conn).
    public static boolean autentificarUsuario(Connection conn, String nombreUsuario, String claveUsuario) {
        try {
        	// consulta para obtener los datos de un usuario en concreto (el que se le pasa por el formulario)
            String query = "SELECT * FROM usuarios WHERE nombreUsuario = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nombreUsuario);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                   // Obtenemos la contrase馻 almacenada en la base de datos para el usuario proporcionado.
                        String claveAlmacenadaFormulario = rs.getString("claveUsuario");
                  // el m閠odo devuelve la autentificacion del usuario a trav閟 del m閠odo verificarClave
                        return verificarClave(claveUsuario, claveAlmacenadaFormulario); 
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * M閠odo privado para verificar la contrase馻 proporcionada con la contrase馻 almacenada.
     *
     * @param claveNoEncriptada Contrase馻 ingresada en texto plano.
     * @param claveEncriptada   Contrase馻 almacenada en forma encriptada.
     * @return true si la contrase馻 ingresada es v醠ida, false en caso contrario.
     */
    
    private static boolean verificarClave(String claveNoEncriptada, String claveEncriptada) {
        try {
        // Obtenemos una instancia del algoritmo de resumen criptogr醘ico MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
         // Actualizamos el objeto MessageDigest con  la contrase馻 no encriptada.
            md.update(claveNoEncriptada.getBytes());
         //declaramos un array de bytes y le pasamos el objeto MessageDigest con  la contrase馻 no encriptada.
            byte[] bytes = md.digest();
          // Convertimos el arrray de bytes a una representaci髇 hexadecimal.
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
           // Aseguramos que los valores sean tratados como n鷐eros positivos y completamos con ceros a la izquierda si es necesario.
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            // en una variable metemos la contrase馻 hexadecimal
            // Obtenemos la representaci髇 en hexadecimal de la contrase馻 no encriptada.
            String compararClaves = sb.toString();
            //comparamos la contrase馻 hexadecimal con la contrase馻 de la bbdd
            //Es decir, Comparamos la contrase馻 encriptada con la versi髇 generada a partir de la no encriptada.
            return compararClaves.equals(claveEncriptada);
            
            // manejo de errores
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene el ID de usuario correspondiente a un nombre de usuario en la base de datos.
     *
     * @param conn           Conexi贸n a la base de datos.
     * @param nombreUsuario  Nombre de usuario para el cual se desea obtener el ID.
     * @return ID de usuario o 0 si no se encuentra el usuario.
     */
    
    
    public static int obtenerIdUsuarioPorNombre(Connection conn, String nombreUsuario) {
        int idUsuario = 0; // Inicializamos la variable idUsuario a 0.

        try {
       //consulta para obtener el id del nombre del usuario introducido en el formulario inicial
            String query = "SELECT idUsuario FROM usuarios WHERE nombreUsuario=?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nombreUsuario);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idUsuario = rs.getInt("idUsuario"); // obtenemos el idusuario correspondiente al nombre de usuario.
                    }
                }
            }
            
            // manejo de excepciones
        } catch (SQLException e) {
            e.printStackTrace();
            
        }

        return idUsuario;
    }
 

		
}



