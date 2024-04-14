<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="css/login.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="login">
        <h1>Login</h1>  
        <form:form action="home" modelAttribute="user" method="post">
            UserName: <input type="text" name="username">
            Password: <input type="password" name="password">
            <button id="btn" >Submit</button>
        </form:form>
        No account? <a href="signin">SignIn</a>
    </div>
</body>
</html>

<script>


    console.log("${param.error}")
    if ( window.history.replaceState ) {
      window.history.replaceState( null, null, window.location.href );
    }
</script>