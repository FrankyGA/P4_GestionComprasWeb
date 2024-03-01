package controlador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.ConexionTienda;
import modelo.Tienda;

/**
 * Servlet implementation class ServletTienda Servlet para gestionar operaciones
 * relacionadas con las tiendas.
 */
@WebServlet("/ServletTienda")
public class ServletTienda extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet() Constructor por defecto del servlet.
	 */
	public ServletTienda() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) Maneja las solicitudes GET al servlet.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ServletTienda - Inside doGet");

		// Obtiene la lista de tiendas desde la base de datos. Llama al m�todo
		// obtenerTiendas de la clase ConexionTienda
		List<Tienda> tiendas = ConexionTienda.obtenerTiendas();
		// Agrega la lista de tiendas al alcance de la solicitud (request)
		request.setAttribute("tiendas", tiendas);
		// Redirigimos a la p�gina de tiendas
		request.getRequestDispatcher("Tiendas.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Maneja las solicitudes POST al servlet, incluyendo operaciones
	 *      como añadir, modificar o borrar tiendas.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// obtenemos el valor del par�metro operaci�n de nuestro jsp.
		String operacion = request.getParameter("operacion");
		// distintas opciones seg�n el valor del par�metro operaci�n
		switch (operacion) {
		case "anadirTienda":
			anadirTienda(request, response);
			break;
		case "cargarDatosModificar":
			cargarDatosModificar(request, response);
			break;
		case "modificarTienda":
			modificarTienda(request, response);
			break;
		case "borrarTienda":
			borrarTienda(request, response);
			break;
		default:

			break;
		}

		// Despu�s de realizar la operaci�n, redirigimos al ServletTienda y �ste a su
		// vez nos redirige a tiendas.jsp
		response.sendRedirect("ServletTienda");
	}
	// ---------------------------------------m�todos del Servlet--------------------------------------- //

	// m�todo para a�adir Tienda
	private void anadirTienda(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// obtiene el nombre de la tienda introducido en el formulario
		String nombreTienda = request.getParameter("nombreTienda");
		// obtiene la direcci�n de la tienda introducido en el formulario
		String direccionTienda = request.getParameter("direccionTienda");

		// instanciamos un objeto de la clase tienda y le pasamos un id por defecto, el
		// nombre y la direcci�n de la tienda obtenido del formulario
		Tienda tienda = new Tienda(0, nombreTienda, direccionTienda);
		ConexionTienda.anadirTienda(tienda); // llamamos al m�todo anadirTienda de la clase ConexionTienda y le pasamos
												// el objeto Tienda
		request.setAttribute("tiendaAgregada", "La tienda se ha agregado con �xito"); // Establece un atributo en la
																						// solicitud para indicar que la
																						// tienda se ha agregado con
																						// �xito
		request.getRequestDispatcher("Tiendas.jsp").forward(request, response); // nos redirige a tiendas.jsp
	}

	// M�todo que carga los datos de la tienda a modificar antes de redirigir a la
	// p�gina de modificaci�n
	private void cargarDatosModificar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idTienda = Integer.parseInt(request.getParameter("idTienda")); // obtenemos el idTienda del formulario
		Tienda tienda = ConexionTienda.obtenerTiendaPorId(idTienda); // llamamos al m�todo obtenerTiendaPorId de la
																		// clase ConexionTienda y le pasamos el idTienda

		// Colocar la tienda seleccionada en el alcance de la solicitud para su uso en
		// la p�gina de modificación
		request.setAttribute("tienda", tienda);

		// Redirigir a la p�gina de modificar tienda
		request.getRequestDispatcher("ModificarTienda.jsp").forward(request, response);
	}

	// m�todo para modificar Tienda
	private void modificarTienda(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// obtenemos los datos introducidos en el formulario
		int idTienda = Integer.parseInt(request.getParameter("idTienda"));
		String nombreTienda = request.getParameter("nombreTienda");
		String direccionTienda = request.getParameter("direccionTienda");
		// instanciamos un objeto de la clase tienda y le pasamos los datos obtenidos en el formulario
		Tienda tienda = new Tienda(idTienda, nombreTienda, direccionTienda);

		// if que verifica si la tienda modificada tiene compras asociadas
		if (ConexionTienda.tiendaTieneCompras(idTienda)) {
			// si la tienda tiene compras, no se puede modificar
			request.setAttribute("errorModificacion", "No se puede modificar la tienda, hay compras asociadas");
		} else {
			// si la tienda no tiene compra, llamamos al m�todo modificatTienda de la clase
			// ConexionTienda y le pasamos el objeto tienda
			ConexionTienda.modificarTienda(tienda);

			// Establece un atributo en la solicitud para indicar que la tienda se ha
			// modificaco con �xito
			request.setAttribute("tiendaModificada", "La tienda se ha modificado con �xito");
		}

		// Redirigimos a la p�gina de tiendas
		request.getRequestDispatcher("Tiendas.jsp").forward(request, response);
	}

	// m�todo para borrar la tienda
	private void borrarTienda(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// obtenemos el idTienda del formulario
		int idTienda = Integer.parseInt(request.getParameter("idTienda"));
		// llamamos al m�todo TiendaTieneCompras de la clase ConexionTienda y le pasamos el idTieda
		if (ConexionTienda.tiendaTieneCompras(idTienda)) {
			// Si la tienda tiene compras asociadas, no se puede borrar la tienda
			request.setAttribute("errorBorrado", "No se puede borrar la tienda, hay compras asociadas");
			request.getRequestDispatcher("Tiendas.jsp").forward(request, response); // redirigimos a tienda.jsp
			return;
		} else {
			// si no tiene compra asociadas, llamamos al método borrarTiienda y le pasamos
			// el idtienda.
			ConexionTienda.borrarTienda(idTienda);
			// Establece un atributo en la solicitud para indicar que la tienda se ha
			// modificado con �xito
			request.setAttribute("tiendaBorrada", "La tienda se ha borrado con �xito");
			request.getRequestDispatcher("Tiendas.jsp").forward(request, response);// redirigimos a tienda.jsp
		}
	}

}
