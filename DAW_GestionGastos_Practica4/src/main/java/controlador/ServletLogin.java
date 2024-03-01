package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelo.ConexionBD;
import modelo.ConexionUsuario;

/**
 * Servlet para manejar la autenticación de usuarios
 */
@WebServlet("/ServletUsuario")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     * Constructor por defecto del servlet.
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *  Maneja las solicitudes GET al servlet.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *  Maneja las solicitudes POST al servlet (autenticaciÃ³n de usuario)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		 // variables para Obtener los parámetros de usuario y contraseña del formulario de inicio de sesión
        String nombreUsuario = request.getParameter("nombreUsuario"); 
        String claveUsuario = request.getParameter("claveUsuario");
        
        // llamamos al método conectar de la clase conexionBD
        try (Connection conn = ConexionBD.conectar()) {
        /* metemos en un boleano el método autentificarUsuario de la clase ConexionUsuario y le pasamos la conexión 
         * a la BBDD, el nombre de usuario y la clave de usuario recogida del formulario*/
            boolean autentificado = ConexionUsuario.autentificarUsuario(conn, nombreUsuario, claveUsuario); 
            //si esta bien autentificado
            if (autentificado) {
                // guardamos el idUsuario correspondiente al nombre de usuario en el método obtenerIdUsuarioPorNombre de la clase ConexionUsuario
                int idUsuario = ConexionUsuario.obtenerIdUsuarioPorNombre(conn, nombreUsuario);

                // Declaramos una variable de sesión y guardamos la respuesta verdadera
                HttpSession session = request.getSession(true);
                //añadimos a la variable de sesión el idUsuario
                session.setAttribute("idUsuarioFK", idUsuario);
                session.setAttribute("nombreUsuario", nombreUsuario);
                
                
                
                // Si nos identificamos bien, Redirigimos a la página principal (Compras.jsp)
                response.sendRedirect("Compras.jsp");
                
               // si nos identificamos mal
            } else {
            	
            	// nos lanza un mensaje de error
                request.setAttribute("error", "Nombre de usuario o contraseña incorrectos");
                request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
            //Manejo de errores
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
    }
}