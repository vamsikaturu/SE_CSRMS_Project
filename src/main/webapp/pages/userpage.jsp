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

    <jsp:include page="header.jsp"/>

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
                                    <a><img onclick="onClickEditButton('${row.taskId}','${row.title}', '${row.description}', '${row.dateTime}')" 
                                        src="css\images\pen.png" width="32" height="32"></a>
                                    <a href="deleteTask?taskId=${row.taskId}"><img src="css\images\bin.png" width="32" height="32"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
    </div>

    <div class="pop-up" id="pop-up">
        <form:form id="form-edit" action="updateTask" method="post">
            <input id="edit-title" type="text" name="title" placeholder="Title" required>
            <textarea name="description" minlength="5" required id="edit-des" rows="6" placeholder="description"></textarea>
            <input id="edit-time" type="datetime-local" name="dateTime">
            <input type="hidden" name="user.username" value="${username}">
            <input id="taskId" type="hidden" name="taskId" >
            <input type="hidden" name="status" value="Inprogress">
            <div class="butons">
                <button>Update</button>
                <button onclick="onClickCancel()">Cancel</button>
            </div>
        </form:form>
    </div>

</body>
</html>


<script type="text/javascript">

    const onClickEditButton = (taskId, title, desc, dateTime)=>{
        document.getElementById('pop-up').style.display = 'block';
        document.getElementById('taskId').value = taskId
        document.getElementById('edit-title').value = title
        document.getElementById('edit-des').value = desc
        document.getElementById('edit-time').value = dateTime
    }

    const onClickCancel = (e) =>{
        e.preventDefault()
        document.getElementById ('pop-up').style.display = 'hide'
    }
</script>

