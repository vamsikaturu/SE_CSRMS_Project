<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="css/header.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="header">
        <nav class="navbar">
            <ul>
                <li><a id="home">Home</a></li>
                <li><a href= "viewtaskpage?id=${id}">ViewTask</a></li>
                <li>
                    <form:form action="logout" method="post">
                        <button id="logout">Logout</button>
                    </form:form>
                </li>
            </ul>
        </nav>
    </div>
</body>

<script>
    document.getElementById('home').onclick = function(){
        window.location.href = "userhome"
    }
</script>