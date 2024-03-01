<%@page import="modelo.Compras"%>
<%@page import="modelo.ConexionCompras"%>
<%@page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>

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
<title>Lista de Compras</title>
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
						%> <a class="btn btn-danger" href="Logout.jsp">Salir
							APP</a> <%
 } else {
 %> <span class="btn btn-danger disabled">Salir
							APP</span> <%
 }
 %>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container tablacompras">
		<!-- Obtén el nombre del mes actual -->
		<%
		Calendar calendar = Calendar.getInstance();
		int mesActual = calendar.get(Calendar.MONTH) + 1; // Se suma 1 porque en Calendar, enero es 0
		int añoActual = calendar.get(Calendar.YEAR); // Obtén el año actual
		String nombreMesActual = ConexionCompras.obtenerNombreMes(mesActual);
		int idUsuario = (int) session.getAttribute("idUsuarioFK");
		double totalComprasMesActual = 0.0; // Inicializa la variable para almacenar el total de compras
		// Obtén las compras del mes actual
		List<Compras> comprasMesActual = ConexionCompras.obtenerComprasDelMesActualPorUsuario(idUsuario);
		%>

		<!-- Muestra el nombre del mes actual -->
		<h2 class="mx-auto text-center mt-3">
			Compras de
			<%=nombreMesActual%>
			<%=añoActual%></h2>

		<!-- Verifica si hay compras en el mes actual -->
		<%
		if (!comprasMesActual.isEmpty()) {
		%>
		<!-- Tabla de lista ordenada de compras -->
		<table
			class="table table-bordered table-hover mx-auto text-center mt-4"
			border="2">
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
				for (Compras compra : comprasMesActual) {
					// Sumar el importe de cada compra al total
					totalComprasMesActual += compra.getImporteCompra();
				%>
				<tr>
					<td><%=compra.getIdCompra()%></td>
					<td><%=String.format("%.2f", compra.getImporteCompra())%> €</td>
					<%
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					String fechaFormateada = dateFormat.format(compra.getFechaCompra());
					%>
					<td><%=fechaFormateada%></td>
					<td><%=ConexionCompras.obtenerNombreTiendaPorId(compra.getIdTiendaFK())%></td>
					<td>
						<!-- Botón para borrar la compra -->
						<form id="formBorrar_<%=compra.getIdCompra()%>"
							action="ServletCompras" method="post">
							<input type="hidden" name="operacion" value="borrarCompra">
							<input type="hidden" name="idCompra"
								value="<%=compra.getIdCompra()%>">
							<button type="button"
								onclick="confirmarBorrado(<%=compra.getIdCompra()%>)"
								class="btn btn-danger">Borrar</button>
						</form>
					</td>
				</tr>
				<%
				}
				%>
			</tbody>
		</table>
		<!-- Muestra el total de compras -->
		<h3 class="mx-auto text-center mt-3">
			Total Compras:
			<%=String.format("%.2f", totalComprasMesActual)%>
			€
		</h3>
		<%
		} else {
		%>
		<!-- Muestra un mensaje si no hay compras en el mes actual -->
		<p class="mx-auto text-center mt-4">
			No hay compras en
			<%=nombreMesActual%></p>
		<%
		}
		%>
	</div>

	<!---------------------Mensajes de éxito o error de operaciones --------------------->
	<!-- Agrega el bloque de código para mostrar el mensaje de éxito o error al agregar la compra -->
	<%
	if (request.getAttribute("compraAgregada") != null) {
	%>
	<script>
        alertify.success("<%=request.getAttribute("compraAgregada")%>");
        </script>
	<%
	}
	%>

	<!-- Agrega el bloque de código para mostrar el mensaje de éxito o error al borrar la compra -->
	<%
	if (request.getAttribute("compraBorrada") != null) {
	%>
	<script>
        alertify.success("<%=request.getAttribute("compraBorrada")%>");
        </script>
	<%
	}
	%>
	<%
	if (request.getAttribute("errorBorradoCompra") != null) {
	%>
	<script>
        alertify.error("<%=request.getAttribute("errorBorradoCompra")%>");
        </script>
	<%
	}
	%>

	<!-- Agrega el bloque de código para mostrar el mensaje de éxito al modificar la tienda -->
	<%
	if (request.getAttribute("compraModificada") != null) {
	%>
	<script>
        alertify.success("<%=request.getAttribute("compraModificada")%>");
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


	<!-- Agrega el bloque de código para mostrar el mensaje de error al modificar la tienda -->
	<%
	if (request.getAttribute("errorInforme") != null) {
	%>
	<script>
    alertify.error("<%=request.getAttribute("errorInforme")%>");
</script>
	<%
	}
	%>



	<!-- Formulario para seleccionar el mes y año -->
	<form class="mx-auto text-center mt-3" action="ServletCompras"
		method="post">
		<label for="mes"><h3>Informes mensual de compras:</h3></label> <br />
		<label class="mx-auto text-center">Seleccione un mes y año:</label> <select
			name="mesYAnio" id="mesYAnio">

			<%
			// Obtén la lista de meses y años que tienen compras de un usuario
			List<String> mesesYAniosConCompras = ConexionCompras
					.obtenerMesesYAniosConComprasPorUsuario((int) session.getAttribute("idUsuarioFK"));

			// Itera sobre los meses y años con compras y crea las opciones
			for (String mesYAnio : mesesYAniosConCompras) {
				// Dividir el valor en mes y año
				String[] partes = mesYAnio.split(" ");

				// Verifica si hay al menos dos partes antes de realizar la conversión
				if (partes.length >= 2) {
					int numeroMes = Integer.parseInt(partes[0]);
					String nombreMes = ConexionCompras.obtenerNombreMes(numeroMes);
			%>
			<option value="<%=mesYAnio%>"><%=nombreMes%>
				<%=partes[1]%></option>
			<%
			}
			}
			%>
		</select> <br />
		<button type="submit" class="btn btn-success mt-3" name="operacion"
			value="filtrarComprasPorMesYAnio">Mostrar Informes de
			Compras</button>
	</form>

	<table
		class="table table-bordered table-hover mx-auto text-center mt-4"
		border="2">

		<%
		String mesSeleccionado = request.getParameter("mes");
		double totalImporte = 0.0; // Inicializamos la variable antes de usarla

		if (mesSeleccionado != null) {
			int numeroMes = Integer.parseInt(mesSeleccionado);

			String nombreMes;

			switch (numeroMes) {
				case 1 :
			nombreMes = "Enero";
			break;
				case 2 :
			nombreMes = "Febrero";
			break;
				case 3 :
			nombreMes = "Marzo";
			break;
				case 4 :
			nombreMes = "Abril";
			break;
				case 5 :
			nombreMes = "Mayo";
			break;
				case 6 :
			nombreMes = "Junio";
			break;
				case 7 :
			nombreMes = "Julio";
			break;
				case 8 :
			nombreMes = "Agosto";
			break;
				case 9 :
			nombreMes = "Septiembre";
			break;
				case 10 :
			nombreMes = "Octubre";
			break;
				case 11 :
			nombreMes = "Noviembre";
			break;
				case 12 :
			nombreMes = "Diciembre";
			break;
				default :
			nombreMes = "Mes Desconocido";
			}
		%>
		<h1 class="mx-auto text-center mt-3">
			Compras de
			<%=nombreMes%></h1>
		<%
		}
		%>
		<thead>
			<tr>
				<th>ID Compra</th>
				<th>Importe Compra</th>
				<th>Fecha Compra</th>
				<th>Supermercado</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Compras> comprasFiltradas = (List<Compras>) request.getAttribute("compras");

			if (comprasFiltradas != null && !comprasFiltradas.isEmpty()) {
				for (Compras compra : comprasFiltradas) {
			%>
			<tr>
				<td><%=compra.getIdCompra()%></td>
				<td><%=String.format("%.2f", compra.getImporteCompra())%></td>
				<%
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String fechaFormateada = dateFormat.format(compra.getFechaCompra());
				%>
				<td><%=fechaFormateada%></td>
				<td><%=ConexionCompras.obtenerNombreTiendaPorId(compra.getIdTiendaFK())%></td>
				<td>
					<!-- Botón para borrar la compra -->
					<form id="formBorrar_<%=compra.getIdCompra()%>"
						action="ServletCompras" method="post">
						<input type="hidden" name="operacion" value="borrarCompra">
						<input type="hidden" name="idCompra"
							value="<%=compra.getIdCompra()%>">
						<button type="button"
							onclick="confirmarBorrado(<%=compra.getIdCompra()%>)"
							class="btn btn-danger">Borrar</button>
					</form>
				</td>

			</tr>
			<%
			//Sumar el importe de cada compra al total
			totalImporte += compra.getImporteCompra();
			}
			} else {
			%>
			<tr>
				<td colspan="4">No hay compras para el mes seleccionado</td>
			</tr>
			<%
			}
			%>
		</tbody>

	</table>

	<%
	if (comprasFiltradas != null && !comprasFiltradas.isEmpty()) {
	%>
	<%-- Formatear el total con dos decimales --%>
	<%
	String formattedTotal = String.format("%.2f", totalImporte);
	%>

	<!-- Total importe compras del mes seleccionado -->
	<h3 class="mx-auto text-center mt-3">
		Total:
		<%=formattedTotal%>
		€
	</h3>
	<%
	}
	%>
	</div>
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
</body>
</html>
