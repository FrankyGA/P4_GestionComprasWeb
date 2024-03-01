package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ConexionCompras extends ConexionBD {

	// método para obtener el nombre de las tiendas pos su id
	public static String obtenerNombreTiendaPorId(int idTienda) {
		String nombreTienda = "";
		try (Connection conn = conectar()) {
			// consulta para obtener el nombre de la tienda por si id
			String query = "SELECT nombreTienda FROM tiendas WHERE idTienda=?";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setInt(1, idTienda);
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						nombreTienda = rs.getString("nombreTienda");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// devuelve el nombre de la tienda
		return nombreTienda;
	}

	// método para añadir compra
	public static void anadirCompra(Compras nuevaCompra) {
		try (Connection conn = conectar()) {
			// sentencia insert into para agregar compra a la tabla compras
			String query = "INSERT INTO compras (importeCompra, fechaCompra, idTiendaFK, idUsuarioFK) VALUES (?, ?, ?, ?)";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setDouble(1, nuevaCompra.getImporteCompra());
				stmt.setDate(2, nuevaCompra.getFechaCompra());
				stmt.setInt(3, nuevaCompra.getIdTiendaFK());
				stmt.setInt(4, nuevaCompra.getIdUsuarioFK());

				stmt.executeUpdate();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 
	 * // método para filtrar las compras por meses public static List<Compras>
	 * obtenerComprasPorMes(int mes) { // creamos un arrayList para meter la lista
	 * de las compras List<Compras> compras = new ArrayList<>();
	 * 
	 * try (Connection conn = conectar()) { // llamamos al método private static
	 * String nombreMes(int mes) de esta misma clase String consultaMesCompra =
	 * nombreMes(mes);
	 * 
	 * 
	 * try (PreparedStatement stmt = conn.prepareStatement(consultaMesCompra)) { try
	 * (ResultSet rs = stmt.executeQuery()) { // recorremos las tablas en busca de
	 * las compras del mes solititado en la consulta del método private static
	 * String nombreMes(int mes) while (rs.next()) { int idCompra =
	 * rs.getInt("idCompra"); double importeCompra = rs.getDouble("importeCompra");
	 * java.sql.Date fechaCompra = rs.getDate("fechaCompra"); int idTiendaFK =
	 * rs.getInt("idTiendaFK"); int idUsuarioFK = rs.getInt("idUsuarioFK"); // en
	 * cada vuelta nos devuelve un objeto de la clase compra Compras compra = new
	 * Compras(idCompra, importeCompra, fechaCompra, idTiendaFK, idUsuarioFK); //
	 * añadimos el objeto compra al array compras compras.add(compra); } } } }
	 * catch (SQLException e) { e.printStackTrace(); } // nos devuelve el array list
	 * compras con todos los objetos compra return compras; }
	 * 
	 * // método con la consulta de un mes en concreto seleccionado por el usuario
	 * en el formulario private static String nombreMes(int mes) { String
	 * consultaMesConcreto =
	 * "SELECT c.idCompra, c.importeCompra, c.fechaCompra, c.idTiendaFK, t.nombreTienda "
	 * + "FROM compras c " + "INNER JOIN tiendas t ON c.idTiendaFK = t.idTienda";
	 * 
	 * switch (mes) { case 1: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 1 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 2: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 2 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 3: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 3 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 4: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 4 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 5: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 5 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 6: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 6 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 7: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 7 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 8: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 8 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 9: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 9 AND YEAR(c.fechaCompra) = YEAR(NOW())"; case
	 * 10: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 10 AND YEAR(c.fechaCompra) = YEAR(NOW())";
	 * case 11: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 11 AND YEAR(c.fechaCompra) = YEAR(NOW())";
	 * case 12: return consultaMesConcreto +
	 * " WHERE MONTH(c.fechaCompra) = 12 AND YEAR(c.fechaCompra) = YEAR(NOW())";
	 * default: return consultaMesConcreto + " WHERE 1=0"; // Si el mes no es
	 * válido, devuelve una consulta que no devolverá resultados } }
	 */

	// método para borrar compra
	public static void borrarCompra(int idCompra) {
		try (Connection conn = conectar()) {
			// sentencia para borrar una compra
			String query = "DELETE FROM compras WHERE idCompra=?";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setInt(1, idCompra);
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// método para obtener la compra por su id para poder modificarla
	public static Compras obtenerCompraPorId(int idCompra) {
		Compras compra = null;

		try (Connection conn = conectar()) {
			// nos devuelve la información de una compra a partir de su id
			String query = "SELECT * FROM compras WHERE idCompra=?";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setInt(1, idCompra);

				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						double importeCompra = rs.getDouble("importeCompra");
						java.sql.Date fechaCompra = rs.getDate("fechaCompra");
						int idTiendaFK = rs.getInt("idTiendaFK");
						int idUsuarioFK = rs.getInt("idUsuarioFK");
						// nos devuelve un objeto de la clase comppras con la información de la compra seleccionada
						compra = new Compras(idCompra, importeCompra, fechaCompra, idTiendaFK, idUsuarioFK);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return compra;
	}

	// método para modificar una compra
	public static void modificarCompra(Compras compra) {
		try (Connection conn = conectar()) {
			// sentencia para hacer la modificacion de una compra

			String query = "UPDATE compras SET importeCompra=?, fechaCompra=?, idTiendaFK=?, idUsuarioFK=? WHERE idCompra=?";
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setDouble(1, compra.getImporteCompra());
				stmt.setDate(2, compra.getFechaCompra());
				stmt.setInt(3, compra.getIdTiendaFK());
				stmt.setInt(4, compra.getIdUsuarioFK());
				stmt.setInt(5, compra.getIdCompra());
				int rowsAffected = stmt.executeUpdate();

				System.out.println(rowsAffected + " fila(s) afectada(s)."); // Agregar mensaje de impresión
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Error SQL: " + e.getMessage()); // Agregar mensaje de impresión
		}
	}

	// consulta de las compras de un usuario en concreto
	public static List<Compras> obtenerComprasPorUsuario(int idUsuario) {
		// creamos un arrayList para meter la lista de las compras
		List<Compras> compras = new ArrayList<>(); // ArrayList para cargar los datos

		try (Connection conn = conectar()) {
			// consulta para obtener todas las compras de un usuario en concreto ordenadas
			// de mas recientes a mas antiguas
			String query = "SELECT * FROM compras WHERE idUsuarioFK = ? ORDER BY fechaCompra DESC";

			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setInt(1, idUsuario);
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int idCompra = rs.getInt("idCompra");
						double importeCompra = rs.getDouble("importeCompra");
						java.sql.Date fechaCompra = rs.getDate("fechaCompra");
						int idTiendaFK = rs.getInt("idTiendaFK");
						// instanciamos un objeto de la clase compra y vamos metiendo toda la
						// informacion de cada compra
						Compras compra = new Compras(idCompra, importeCompra, fechaCompra, idTiendaFK, idUsuario);
						// en cada vuelta del bucle while vamos metiendo un objeto de la clase compra en
						// el array list compras
						compras.add(compra);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return compras; // nos devuelve el arraylist
	}

	// Método para obtener el nombre del mes a partir de su n�mero
	public static String obtenerNombreMes(Integer numeroMes) {
		String nombreMes;
		switch (numeroMes) {
		case 1:
			nombreMes = "Enero";
			break;
		case 2:
			nombreMes = "Febrero";
			break;
		case 3:
			nombreMes = "Marzo";
			break;
		case 4:
			nombreMes = "Abril";
			break;
		case 5:
			nombreMes = "Mayo";
			break;
		case 6:
			nombreMes = "Junio";
			break;
		case 7:
			nombreMes = "Julio";
			break;
		case 8:
			nombreMes = "Agosto";
			break;
		case 9:
			nombreMes = "Septiembre";
			break;
		case 10:
			nombreMes = "Octubre";
			break;
		case 11:
			nombreMes = "Noviembre";
			break;
		case 12:
			nombreMes = "Diciembre";
			break;
		default:
			nombreMes = "Mes Desconocido";
		}
		return nombreMes;
	}

	// m�todo para obtener la compra del mes actual, es la primera consulta que
	// sale en compras.jsp
	public static List<Compras> obtenerComprasDelMesActualPorUsuario(int idUsuario) {
		// creamos un array para meter todas las compras
		List<Compras> compras = new ArrayList<>();

		try (Connection conn = conectar()) {

			// usamos el m�todo calendar para obtener la fecha actual
			Calendar calendario = Calendar.getInstance();
			int mesActual = calendario.get(Calendar.MONTH) + 1; // Sumar 1 ya que los meses se indexan desde 0

			// consulta para obtener las compras del mes actual
			String consultaMesActual = "SELECT * FROM compras WHERE MONTH(fechaCompra) = ? AND idUsuarioFK = ? ORDER BY fechaCompra DESC";
			// a la consulta le pasamos el mes actual y el idUsuario
			try (PreparedStatement stmt = conn.prepareStatement(consultaMesActual)) {
				stmt.setInt(1, mesActual);
				stmt.setInt(2, idUsuario);

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int idCompra = rs.getInt("idCompra");
						double importeCompra = rs.getDouble("importeCompra");
						java.sql.Date fechaCompra = rs.getDate("fechaCompra");
						int idTiendaFK = rs.getInt("idTiendaFK");
						int idUsuarioFK = rs.getInt("idUsuarioFK");
						// creamos una instancia de un objeto compra con toda la informaci�n de la compra
						Compras compra = new Compras(idCompra, importeCompra, fechaCompra, idTiendaFK, idUsuarioFK);
						// en cada vuelta metemos un objeto compra en el arraylist compras
						compras.add(compra);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return compras; // nos devuelve el arraylist de compras
	}

	// filtrar por mes y a�o
	// este m�todo es el encargado de rellenar el despegable de los informes, para
	// que solo salgan los meses y a�os con compra
	public static List<String> obtenerMesesYAniosConComprasPorUsuario(int idUsuario) {
		// creamos un arraylist
		List<String> mesesYAniosConCompras = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = conectar();
			// consulta para filtrar las compras por meses y años para un usuario en
			// concreto
			String consultaMesesYAnio = "SELECT DISTINCT CONCAT(MONTH(fechaCompra), ' ', YEAR(fechaCompra)) AS mesYAnio FROM compras WHERE idUsuarioFK = ?";
			stmt = conn.prepareStatement(consultaMesesYAnio);
			stmt.setInt(1, idUsuario); // agregamos el usuario
			rs = stmt.executeQuery();

			while (rs.next()) {
				String mesYAnio = rs.getString("mesYAnio");
				mesesYAniosConCompras.add(mesYAnio);
				// en cada vuelva vamos metiendo una compra en el arraylist
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mesesYAniosConCompras; // nos devuelve el array con los meses y años que tienen compras asociadas
	}

	//M�todo para filtrar compras por meses y usuario, este método es el que se encarga de generar los informes mensuales
	// Se llama desde el servlet
	public static List<Compras> obtenerComprasPorMesYAnioUsuario(int mes, int anyo, int idUsuario) {

		// creamos un array list para guardar las compras filtradas
		List<Compras> comprasInforme = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = conectar();
			// consulta para obtener los datos y rellenar los informes
			String consultaInformeMensual = "SELECT * FROM compras WHERE MONTH(fechaCompra) = ? AND YEAR(fechaCompra) = ? AND idUsuarioFK = ?";
			stmt = conn.prepareStatement(consultaInformeMensual);
			stmt.setInt(1, mes);
			stmt.setInt(2, anyo);
			stmt.setInt(3, idUsuario);

			rs = stmt.executeQuery();

			while (rs.next()) {
				int idCompra = rs.getInt("idCompra");
				double importeCompra = rs.getDouble("importeCompra");
				java.sql.Date fechaCompra = rs.getDate("fechaCompra");
				int idTiendaFK = rs.getInt("idTiendaFK");
				int idUsuarioFK = rs.getInt("idUsuarioFK");
				// creamos un objeto de la clase compra en cada una de las vueltas del bucle
				// while
				Compras compra = new Compras(idCompra, importeCompra, fechaCompra, idTiendaFK, idUsuarioFK);
				// añadimos el objeto compra a al arraylist
				comprasInforme.add(compra);

			}

		} catch (SQLException e) {
			e.printStackTrace(); // manejo de errores
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return comprasInforme; // devolvemos el array list con el informe
	}

}
