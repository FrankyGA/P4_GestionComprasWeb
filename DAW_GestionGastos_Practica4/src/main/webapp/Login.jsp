<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <link rel="icon" type="image/png" id="favicon" href="imagenes\icono.jpg">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="./css/login.css">
     <title>Login</title>
</head>
<body>

    <div class="card">
        <h1>Login</h1>

        <%-- Mostrar alerta en caso de error --%>
        <% if (request.getAttribute("error") != null) { %>
            <script>
                alert("<%= request.getAttribute("error") %>");
            </script>
        <% } %>

		<!-- Formulario de acceso para el usuario -->
        <form action="ServletUsuario" method="post">

            <div class="mb-3">
                <label for="nombreUsuario" class="form-label">Nombre de Usuario:</label>
                <input type="text" class="form-control" id="nombreUsuario" name="nombreUsuario" required>
            </div>

            <div class="mb-3">
                <label for="claveUsuario" class="form-label">Contraseña:</label>
                <input type="password" class="form-control" id="claveUsuario" name="claveUsuario" required>
            </div>

            <button type="submit" class="btn btn-primary">Iniciar Sesión</button>

        </form>
    </div>

</body>
</html>
