<%@page import="modelo.Tienda"%>
<%@page import="modelo.ConexionTienda"%>
<%@page import="controlador.ServletTienda"%>
<%@page import="java.util.List"%>

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
<title>Lista de tiendas</title>
</head>

<body>

	<!-- Navegador -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container mx-auto text-center">
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

	<div class="container tablatiendas">
		<h1 class=" mx-auto text-center mt-5">Lista de Tiendas</h1>

		<!-- Tabla de lista de tiendas -->
		<table class="table table-bordered table-hover mx-auto mt-2" border="2">

			<thead>
				<tr>
					<th>ID Tienda</th>
					<th>Nombre Tienda</th>
					<th>Dirección Tienda</th>
					<th>Eliminar Tienda</th>
				</tr>
			</thead>

			<tbody>
				<%
				List<Tienda> tiendas = ConexionTienda.obtenerTiendas();
				for (Tienda tienda : tiendas) {
				%>
				<tr>
					<td><%=tienda.getIdTienda()%></td>
					<td><%=tienda.getNombreTienda()%></td>
					<td><%=tienda.getDireccionTienda()%></td>
					<td class="text-center">
						<!-- Botón para borrar la tienda -->
						<form id="formBorrar_<%=tienda.getIdTienda()%>"
							action="ServletTienda" method="post">
							<input type="hidden" name="operacion" value="borrarTienda">
							<input type="hidden" name="idTienda"
								value="<%=tienda.getIdTienda()%>">
							<button type="button" class="btn btn-danger"
								onclick="confirmarBorrado(<%=tienda.getIdTienda()%>)">Borrar</button>
						</form>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
	</div>
	<!---------------------Mensajes de éxito o error de operaciones --------------------->
	<!-- Agrega el bloque de código para mostrar el mensaje de éxito al borrar la tienda -->
	<%
	if (request.getAttribute("tiendaBorrada") != null) {
	%>
	<script>
        alertify.notify("<%=request.getAttribute("tiendaBorrada")%>");
    </script>
	<%
	}
	%>

	<!-- Agrega el bloque de código para mostrar el mensaje de error -->
	<%
	if (request.getAttribute("errorBorrado") != null) {
	%>
	<script>
            alertify.error("<%=request.getAttribute("errorBorrado")%>");
        </script>
	<%
	}
	%>

	<!-- Agrega el bloque de código para mostrar el mensaje de éxito al modificar la tienda -->
	<%
	if (request.getAttribute("tiendaModificada") != null) {
	%>
	<script>
    alertify.success("<%=request.getAttribute("tiendaModificada")%>");
	</script>
	<%
	}
	%>

	<!-- Agrega el bloque de código para mostrar el mensaje de éxito al agregar la tienda -->
	<%
	if (request.getAttribute("tiendaAgregada") != null) {
	%>
	<script>
	 alertify.success("<%=request.getAttribute("tiendaAgregada")%>");
	</script>
	<%
	}
	%>

	<!-- Agrega el bloque de código para mostrar el mensaje de error al modificar la tienda -->
	<%
	if (request.getAttribute("errorModificacion") != null) {
	%>
	<script>
    alertify.error("<%=request.getAttribute("errorModificacion")%>");
	</script>
	<%
	}
	%>

	<!-- Función js para comfimación de borrado del registro -->
	<script>
    function confirmarBorrado(idTienda) {
        var confirmacion = confirm("¿Estás seguro de que quieres borrar el registro?");
        if (confirmacion) {
            // Si el usuario hizo clic en "Aceptar", envía el formulario
            document.getElementById("formBorrar_" + idTienda).submit();
        }
        // Si el usuario hizo clic en "Cancelar", no hagas nada
    }
	</script>


	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.1/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
		rel="stylesheet">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/AlertifyJS/1.13.1/css/alertify.min.css" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/AlertifyJS/1.13.1/css/themes/bootstrap.min.css" />
  

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/AlertifyJS/1.13.1/alertify.min.js"></script>
</body>
</html>

