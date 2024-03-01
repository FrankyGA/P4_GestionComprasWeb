package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet para gestionar el cierre de sesión de usuarios
 */
@WebServlet("/ServletLogout")
public class ServletLogout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     * Constructor por defecto del servlet.
     */
    public ServletLogout() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * Maneja las solicitudes GET al servlet.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     *  Maneja las solicitudes POST al servlet (cierre sesión del usuario)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	 // Obtenemos la sesión actual o null si no existe
        HttpSession session = request.getSession(false);
        // si la sesión no es  nula
        if (session != null) {
            session.invalidate(); // la invalidamos
        }

        // Redirigimos a la página de logout
        response.sendRedirect("Logout.jsp");
    }
}
