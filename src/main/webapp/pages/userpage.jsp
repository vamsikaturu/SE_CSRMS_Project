<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="css/userpage.css" rel="stylesheet" type="text/css">
<link href="js/userpage.js" type="text/javascript">
</head>
<body>
    <div class="header">
        <form:form action="/logout" style="
        padding: 0px;
        display: contents;" method="post">
       <div class="logout">
            <input type="submit" value="Logout">
        </div>
        </form:form>
    </div>
    <div class="task">
        <div class="add-task">
            <form:form action="addTask" modelAttribute="task" method="post">
                <input type="text" name="title" placeholder="Title" required>
                <textarea name="description" minlength="5" required id="" rows="6" placeholder="description"></textarea>
                <input type="datetime-local" name="dateTime">
                <input type="hidden" name="user.username" value="${username}">
                <input type="hidden" name="status" value="Inprogress">
                <button>Add</button>
            </form:form>
        </div>
        <div class="view-task">
            <form>
                <table class="scrolldown">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Time</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" items="${reminder}">
                            <tr>
                                <td>${row.title}</td>
                                <td style="text-align: justify;">${row.description}</td>
                                <td>${row.dateTime}</td>
                                <td>${row.status}</td>
                                <td>
                                    <a><img src="css\images\pen.png" width="32" height="32"></a>
                                    <a href="deleteTask?taskId=${row.taskId}"><img src="css\images\bin.png" width="32" height="32"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
</body>
</html>




