<%@page import="modelo.Tienda"%>
<%@page import="modelo.ConexionTienda"%>

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
<title>Añadir Tienda</title>
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
	<div class="container mt-3 mx-auto text-center">
		<h1 class=" mx-auto text-center mt-5">Añadir Nueva Tienda</h1>

		<!-- Formulario para añadir tienda -->
		<form class="mx-auto text-center mt-4 " action="ServletTienda"
			method="post">
			<input type="hidden" name="operacion" value="anadirTienda">
			Nombre: <input type="text" name="nombreTienda" required>
			Dirección: <input type="text" name="direccionTienda" required>
			<input type="submit" class="btn btn-warning" value="Añadir">
		</form>
	</div>
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
