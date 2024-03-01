<%@ page import="modelo.Compras"%>
<%@ page import="modelo.Tienda"%>
<%@ page import="modelo.ConexionCompras"%>
<%@ page import="modelo.ConexionTienda"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.DecimalFormatSymbols"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<link rel="icon" type="image/png" id="favicon" href="imagenes\icono.jpg">
<link
	href="https://fonts.googleapis.com/css?family=Titillium+Web:900&display=swap"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
	
<link rel="stylesheet" type="text/css" href="./css/Style.css">
 <link rel="stylesheet" href="./alert/alertify.min.css" />

<script src="./alert/alertify.min.js"></script>
<script src="./alert/alertify.js"></script>
<title>Modificar Compra</title>
</head>

<body>

	<!-- Navegador -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container">
			<div id="logo">
				<a href="Compras.jsp"> <img class="img-fluid logo-menu"
					src="imagenes/icono.jpg" alt="logo-menu">
				</a>
			</div>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarNav" aria-controls="navbarNav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link"
						href="Tiendas.jsp">Tiendas <span class="sr-only"></span>
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="AñadirTienda.jsp">Añadir Tienda</a></li>
					<li class="nav-item"><a class="nav-link"
						href="ModificarTienda.jsp">Modificar Tienda</a></li>
					<li class="nav-item"><a class="nav-link" href="Compras.jsp">Compras</a></li>
					<li class="nav-item"><a class="nav-link"
						href="AñadirCompras.jsp">Añadir Compra</a></li>
					<li class="nav-item"><a class="nav-link"
						href="ModificarCompra.jsp">Modificar Compra</a></li>
					<li class="nav-item">
						<%
						if (request.getSession(false) != null) {
						%> <a class="btn btn-danger" href="Logout.jsp">Salir APP</a> <%
 } else {
 %> <span class="btn btn-danger disabled">Salir APP</span> <%
 }
 %>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container mx-auto text-center">
		<h1 class="mx-auto text-center mt-5 ">Modificar Compras</h1>

		<!-- Muestra la tabla de compras para elegir registro a modificar-->
	<table
			class="table table-bordered table-hover mx-auto text-center mt-4 "
			border="2" >
			<thead>
				<tr>
					<th>ID Compras</th>
					<th>Importe Compra</th>
					<th>Fecha Compra</th>
					<th>Supermercado</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				   <%
                    

                    if (session != null) {
                        int idUsuario = (int) session.getAttribute("idUsuarioFK");
                        List<Compras> compras = ConexionCompras.obtenerComprasPorUsuario(idUsuario);

                        for (Compras compra : compras) {
                %>
                    <tr>
                        <td><%=compra.getIdCompra()%></td>
                        <td><%= String.format("%.2f", compra.getImporteCompra()) %> €</td>
                        <%
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String fechaFormateada = dateFormat.format(compra.getFechaCompra());
                        %>
                        <td><%=fechaFormateada%></td>
                        <td><%=ConexionCompras.obtenerNombreTiendaPorId(compra.getIdTiendaFK())%></td>
                        <td>
    <!-- Botón para modificar la compra -->
    <form id="formModificar_<%=compra.getIdCompra()%>" action="ModificarCompra.jsp" method="post">
        <input type="hidden" name="idCompra" value="<%=compra.getIdCompra()%>">
        <button type="submit" class="btn btn-warning">Modificar</button>
    </form>
</td>
                    </tr>
                <%
                        }
                    } else {
                        // Manejo de error o redirección si no hay sesión
                        response.sendRedirect("login.jsp");
                    }
                %>
            </tbody>
		</table>
		<!-- Formulario para seleccionar compra a modificar -->


		<!-- Mostrar el formulario para modificar los datos de la compra seleccionada -->
		<%
		String idCompraParam = request.getParameter("idCompra");
		if (idCompraParam != null) {
			int idCompraSeleccionada = Integer.parseInt(idCompraParam);
			Compras compraSeleccionada = ConexionCompras.obtenerCompraPorId(idCompraSeleccionada);

			// Obtén la tienda asociada a la compra
			Tienda tiendaSeleccionada = ConexionTienda.obtenerTiendaPorId(compraSeleccionada.getIdTiendaFK());
		%>

		<div class="container mx-auto text-center">
			<form action="ServletCompras" method="post">
				<!-- Campos ocultos para el ID de la compra -->
				<input type="hidden" name="operacion" value="modificarCompra">
				<input type="hidden" name="idCompra"
					value="<%=compraSeleccionada.getIdCompra()%>">
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.Locale"%>

<!-- ... (otro código) ... -->

<!-- Campos para mostrar y modificar los datos de la compra -->
<label for="importeCompra">Importe:</label>
<%
    // Usar DecimalFormat para formatear el importe con punto como separador decimal
    DecimalFormat decimalFormat = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
    String importeFormateado = decimalFormat.format(compraSeleccionada.getImporteCompra());
%>
<input type="text" name="importeCompra" value="<%= importeFormateado %>" required>

<!-- ... (resto del código) ... -->

			
				<div class="mb-3">
    <label for="fechaCompra" class="form-label">Fecha de la Compra:</label> 
    <input type="date" id="fechaCompra" name="fechaCompra" required
           value="<%=compraSeleccionada.getFechaCompra()%>">
</div>
				
				

				<!-- Campos para mostrar y modificar los datos de la tienda -->
				<label for="nombreTienda">Nombre:</label>
				<!-- Cambiado de input a select para mostrar un desplegable -->
				<select name="idTiendaFK" required>
					<!-- Obtener la lista de tiendas disponibles -->
					<%
					List<Tienda> listaTiendas = ConexionTienda.obtenerTiendas();
					for (Tienda tienda : listaTiendas) {
					%>
					<!-- Opción para cada tienda -->
					<option value="<%=tienda.getIdTienda()%>"
						<%=(tienda.getIdTienda() == tiendaSeleccionada.getIdTienda()) ? "selected" : ""%>><%=tienda.getNombreTienda()%></option>
					<%
					}
					%>
				</select> <input type="submit" class="btn btn-warning"
					value="Guardar cambios">
			</form>
		</div>
		<%
		}
		%>
	</div>
<%-- error manejo decimales importe --%>
<%
String errorAnadirCompra = (String)request.getAttribute("errorAnadirCompra");
if (errorAnadirCompra != null) {
%>
   
        <script>
        alertify.error("<%=request.getAttribute("errorAnadirCompra")%>");
        </script>
   
<%
}
%>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
		rel="stylesheet">
</body>
</html>


