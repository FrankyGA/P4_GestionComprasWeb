package controlador;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Compras;
import modelo.ConexionCompras;

import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletCompras Servlet para gestionar
 * operaciones relacionadas con las compras.
 */
@WebServlet("/ServletCompras")
public class ServletCompras extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet() Constructor por defecto del servlet.
	 */
	public ServletCompras() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Obtener la sesión actual en un objeto HttpSession
		HttpSession session = request.getSession(false);

		// si la sesión es distinta a nula
		if (session != null) {
			// almacenamos en la variable idUsuario el usuario logado, que es el mismo del
			// campo idUsuarioFK
			int idUsuario = (int) session.getAttribute("idUsuarioFK");

			// Obtiene las compras del usuario logado desde la base de datos . Llama al
			// método obtenerComprasPorUsuario de la clase ConexionCompras
			List<Compras> compras = ConexionCompras.obtenerComprasPorUsuario(idUsuario);
			// Agrega la lista de compras al alcance de la solicitud (request)
			request.setAttribute("compras", compras);

			// Redirigimos a la página de compras
			RequestDispatcher dispatcher = request.getRequestDispatcher("Compras.jsp");
			dispatcher.forward(request, response);
		} else {
			// Si no hay sesión, redirigir a la página de inicio de sesión
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Maneja las solicitudes POST al servlet, incluyendo operaciones
	 *      como aÃ±adir, modificar o borrar compras.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// obtenemos el valor del parámetro operación de nuestro jsp.
		String operacion = request.getParameter("operacion");
		// si operación es distinto a nulo
		operacion = (operacion != null) ? operacion : "";
		// En función del parámetro que se le pasa, tendremos distintas opciones
		switch (operacion) {
		case "anadirCompra":
			try {
				anadirCompra(request, response);
			} catch (IOException | SQLException | ServletException e) {
				e.printStackTrace();
			}
			break;
		case "cargarDatosModificar":
			cargarDatosModificar(request, response);
			break;
		case "modificarCompra":
			modificarCompra(request, response);
			break;
		case "borrarCompra":
			borrarCompra(request, response);
			break;
		case "filtrarComprasPorMesYAnio":
			filtrarComprasPorMesYAnio(request, response);
			break;
		default:
			// Manejar cualquier otra operaciÃ³n o error aquÃ­
			break;
		}

		// DespuÃ©s de realizar la operación, redirigir a la página de compras, que a
		// su vez nos redirige a "Compras.jsp"
		response.sendRedirect("ServletCompras");
	}

	// ----------------------------------------métodos del Servlet---------------------------------------- //

	// método para borrar la compra
	private void borrarCompra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// obtenemos el idCompra del formulario
		int idCompra = Integer.parseInt(request.getParameter("idCompra"));

		// llamamos al método borrarCompra de la clase ConexionCompras y le pasamos el
		// idCompra
		ConexionCompras.borrarCompra(idCompra);

		// Establece un atributo en la solicitud para indicar que la compra se ha
		// modificado con éxito
		request.setAttribute("compraBorrada", "La compra se ha borrado con éxito");
		// redirigimos a "Compras.jsp"
		request.getRequestDispatcher("Compras.jsp").forward(request, response);
	}

	// Método para aÃ±adir Compra

	private void anadirCompra(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException {

		// Obtener los parámetros del formulario
		String importeformulario = request.getParameter("importeCompra");
		String fechaformulario = request.getParameter("fechaCompra");
		String idTiendaformulario = request.getParameter("idTiendaFK");
		String idUsuarioformulario = request.getParameter("idUsuarioFK");

		// comprobamos que todos los campos del formulario estén rellenos
		if (importeformulario == null || fechaformulario == null || idTiendaformulario == null
				|| idUsuarioformulario == null || importeformulario.isEmpty() || fechaformulario.isEmpty()
				|| idTiendaformulario.isEmpty() || idUsuarioformulario.isEmpty()) {
			// si hay algún campo vació, nos lanza un error
			request.setAttribute("errorAnadirCompra", "Todos los campos son obligatorios.");
			// nos redirige a la página "AñadirCompra.jsp"
			RequestDispatcher dispatcher = request.getRequestDispatcher("AñadirCompra.jsp");
			dispatcher.forward(request, response);
			return;
		}
		// manejamos posibles errores
		try {
			// Parseamos los datos introducidos por el usuario para que coincidan con el
			// tipo de datos de la BBDD
			double importe = Double.parseDouble(importeformulario);

			// Ajustamos el formato de fecha esperado en la bbdd
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			// Parseamos la fecha del formulario y la formateamos al formato de la base de
			// datos
			Date fecha = new Date(dateFormat.parse(fechaformulario).getTime());
			// parseamos los datos introducidos en idTienda y en idUsuario
			int idTienda = Integer.parseInt(idTiendaformulario);
			int idUsuario = Integer.parseInt(idUsuarioformulario);

			// instanciamos un objeto de la clase compras y le pasamos los datos obtenidos
			// en el formulario
			Compras nuevaCompra = new Compras(0, importe, fecha, idTienda, idUsuario);

			// llamamos al método anadirCompra de la clase ConexionCompras y le pasamos el
			// objeto compras
			ConexionCompras.anadirCompra(nuevaCompra);
			// Establece un atributo en la solicitud para indicar que la compra se ha
			// agregado con éxito
			request.setAttribute("compraAgregada", "La compra se ha agregado con éxito");

			// redirigimos a "Compras.jsp"
			RequestDispatcher dispatcher = request.getRequestDispatcher("Compras.jsp");
			dispatcher.forward(request, response);

			// manejamos una excepción numérica, ya que la bbdd admite . pero no , en los
			// decimales
		} catch (NumberFormatException | ParseException e) {
			// Si el usuario introduce el importe con coma, le sale un mensaje avisando de
			// que el importe se pone con punto
			request.setAttribute("errorAnadirCompra",
					"Error al procesar la compra. El importe debe introducirse con punto. Ejemplo: 12.50.");
			// redirigimos a la página de agregar compra para que ponga bien el importe
			RequestDispatcher dispatcher = request.getRequestDispatcher("Compras.jsp");
			dispatcher.forward(request, response);
		}
	}

	// método para filtrar las compras por meses y aÃ±o
	private void filtrarComprasPorMesYAnio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtener el mes y el aÃ±o seleccionados en el desplegable del formulario
		String mesAnioSeleccionado = request.getParameter("mesYAnio");

		// Verificamos que mesAnioSeleccionado no sea nulo o esté vacÃ­o
		if (mesAnioSeleccionado != null && !mesAnioSeleccionado.isEmpty()) {
			// Separamos el mes y el aÃ±o

			String[] partes = mesAnioSeleccionado.split(" ");
			// manejo de errores
			try {
				// parseamoos el mes y el aÃ±o y lo pasamos a enteros
				int mes = Integer.parseInt(partes[0]);
				int anyo = Integer.parseInt(partes[1]);

				// Obtenemos la sesión del usuario logado
				HttpSession session = request.getSession(false);
				// si la sessiÃ³n es distinta a nula
				if (session != null) {
					// almacenamos en la variable idUsuario el usuario logado, que es el mismo del
					// campo idUsuarioFK
					int idUsuario = (int) session.getAttribute("idUsuarioFK");

					// hacemos un array list denominado comprasFiltradas y llamamos al metodo
					// obtenerComprasPorMesYAnioUsuario de la clase conexionCompras
					List<Compras> comprasFiltradas = ConexionCompras.obtenerComprasPorMesYAnioUsuario(mes, anyo,
							idUsuario);

					// Establece un atributo en la solicitud y le asignamos la lista de compras
					// filtradas
					request.setAttribute("compras", comprasFiltradas);

					// Redirigimos a la página Compras.jsp
					RequestDispatcher dispatcher = request.getRequestDispatcher("Compras.jsp");
					dispatcher.forward(request, response);
				} else {
					// Si no hay sesión, redirigimos a la página de inicio de sesión
					response.sendRedirect("login.jsp");
				}
				// manejo de excepciones
			} catch (NumberFormatException e) {
				// Manejar la excepción si no se pueden convertir las partes a enteros
				e.printStackTrace(); // O manejo adecuado según tu aplicación
			}
		} else {
			// Manejo de errores en el caso en que mesAnioSeleccionado es nulo o vacío
			// Establece un atributo en la solicitud para indicar no se ha seleccionado ni
			// un mes ni un año
			request.setAttribute("errorInforme", "Por favor, selecciona un mes y año.");

			// Redirigimos a la página Compras.jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("Compras.jsp");
			dispatcher.forward(request, response);
		}
	}

	//Método que carga los datos de la compra a modificar antes de redirigir a la página de modificación
	private void cargarDatosModificar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// obtenemos el idCompra del formulario
		int idCompra = Integer.parseInt(request.getParameter("idCompra"));
		// llamamos al método obtenerCompraPorId de la clase ConexionCompras y le
		// pasamos el idCompra
		Compras nuevaCompra = ConexionCompras.obtenerCompraPorId(idCompra);

		// Colocar la tienda seleccionada en el alcance de la solicitud para su uso en
		// la página de modificación
		request.setAttribute("nuevaCompra", nuevaCompra);
		// Redirigir a la página de modificar compra
		request.getRequestDispatcher("ModificarCompra.jsp").forward(request, response);

	}

	// método para modificar la compra
	private void modificarCompra(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Obtener la sesión actual del usuario logado
		HttpSession session = request.getSession(false);
		// obtenemos los datos introducidos en el formulario
		String idCompraFormulario = request.getParameter("idCompra");

		// verificamos que el idCompra no es nulo o está vacío y que la sessión no sea
		// nula
		if (idCompraFormulario != null && !idCompraFormulario.isEmpty() && session != null) {
			// Verificamo si el usuario no está logueado
			if (session.getAttribute("idUsuarioFK") == null) {
				// manejo de errores del usuario no logueado
				request.setAttribute("errorModificarCompra", "Usuario no ha iniciado Sesión.");
				// redirigimos a modificar compra
				request.getRequestDispatcher("ModificarCompra.jsp").forward(request, response);
				return;
			}
			// parseamos el idCompra y lo convertimos en un entero
			int idCompra = Integer.parseInt(idCompraFormulario);

			// Obtenemos los parámetros del formulario
			String importeFormulario = request.getParameter("importeCompra");
			String fechaFormulario = request.getParameter("fechaCompra");
			String idTiendaFormulario = request.getParameter("idTiendaFK");

			// Validmos que los campos no estan vacío ni sean nulos
			if (importeFormulario == null || fechaFormulario == null || idTiendaFormulario == null
					|| importeFormulario.isEmpty() || fechaFormulario.isEmpty() || idTiendaFormulario.isEmpty()) {
				// Manejo de errores si un campo está vacío o es nulo
				request.setAttribute("errorModificarCompra", "Todos los campos son obligatorios.");

				// redirigimos a modificarCompra.jsp
				RequestDispatcher dispatcher = request.getRequestDispatcher("ModificarCompra.jsp");
				dispatcher.forward(request, response);
				return;
			}
			// manejo de errores
			try {
				// Parseamos los datos introducidos por el usuario para que coincidan con el
				// tipo de datos de la BBDD
				double importe = Double.parseDouble(importeFormulario);

				// Ajustamos el formato de fecha esperado en la bbdd
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				// Parseamos la fecha del formulario y la formateamos al formato de la base de
				// datos
				Date fecha = new Date(dateFormat.parse(fechaFormulario).getTime());

				// almacenamos en la variable idUsuario el usuario logeado, que es el mismo del
				// campo idUsuarioFK
				int idUsuario = (int) session.getAttribute("idUsuarioFK");

				int idTienda = Integer.parseInt(idTiendaFormulario);

				// Creamos un objeto Compras con los datos introducido por el usuario en el
				// formulario
				Compras nuevaCompra = new Compras(idCompra, importe, fecha, idTienda, idUsuario);

				// pasamos el objeto compras al metodo modificarCompra de la clase
				// ConexionComoras
				ConexionCompras.modificarCompra(nuevaCompra);
				request.setAttribute("compraModificada", "La compra se ha modificado con éxito");

				// Redirigimos a la página de compras
				request.getRequestDispatcher("Compras.jsp").forward(request, response);
			} catch (NumberFormatException | ParseException e) {
				// Manejar la excepción por entrada inválida en el importe, si el usuario pone
				// , le salta este error
				request.setAttribute("errorAnadirCompra",
						"Error al procesar la modificación. El importe debe introducirse con punto. Ejemplo: 12.50.");
				// redirigimos a la página de modificar compra
				RequestDispatcher dispatcher = request.getRequestDispatcher("ModificarCompra.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			// Manejo de errores en el caso de que el iDCompra no sea válido
			request.setAttribute("errorModificarCompra", "ID de compra no válido.");
			// redirigimos a mododificarCompra.jsp
			request.getRequestDispatcher("ModificarCompra.jsp").forward(request, response);
		}
	}

}
