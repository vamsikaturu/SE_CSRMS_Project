<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="css/view-task-page.css" rel="stylesheet" type="text/css">
<link href="js/userpage.js" type="text/javascript">
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="search-task">
        <form:form>
            <input id="search" type="text" placeholder="Enter Category Name" required>
            <button id="search-button">Search</button>
        </form:form>
    </div>
    <div class="view-task">
        <form>
            <table class="scrolldown">
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Time</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="tbody">
                    <c:forEach var="row" items="${reminder}">
                        <tr>
                            <td>${row.category.categoryName}</td>
                            <td>${row.title}</td>
                            <td style="text-align: justify;">${row.description}</td>
                            <td>${row.dateTime}</td>
                            <td>${row.status}</td>
                            <td>
                                <a><img onclick="onClickEditButton('${row.taskId}','${row.title}', '${row.description}', '${row.dateTime}', '${row.category.categoryName}', '${row.repeat}')" 
                                    src="css\images\pen.png" width="32" height="32"></a>
                                <a href="deleteTask?taskId=${row.taskId}&id=${id}"><img src="css\images\bin.png" width="32" height="32"></a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>
    </div>

    <div class="pop-up" id="pop-up">
        <form:form id="form-edit" action="updateTask" method="post">
            <select name="category.categoryName" id="categoryName">
                <option id="null">Choose Category</option>
                <c:forEach var="row" items="${categories}">
                    <option id="${row.categoryName}" value="${row.categoryName}">${row.categoryName}</option>
                </c:forEach>
            </select>

            <input id="edit-title" type="text" name="title" placeholder="Title" required>
            <textarea name="description" minlength="5" required id="edit-des" rows="6" placeholder="description"></textarea>
            <input id="edit-time" type="datetime-local" name="dateTime">
            <input type="hidden" name="user.username" value="${username}">
            <select id="repeat" onchange="onChangeRepeat()">
                <option>Repeat</option>
                <option id="dont" value="dont">Don't Repeat</option>
                <option id="min" value="min">Repeat Every Minute</option>
                <option id="hr" value="hr">Repeat Every Hour</option>
                <option id="day" value="day">Repeat Every Day</option>
            </select>

            <div id="repeat-input">
            </div>

            <input type="number" name="user.id" value="${id}" hidden>

            <input id="taskId" type="hidden" name="taskId" >
            <input type="hidden" name="status" value="Inprogress">
            <div class="buttons">
                <button onclick="onClickTaskUpdate()">Update</button>
                <button type="button" onclick="onClickCancel()">Cancel</button>
            </div>
        </form:form>
    </div>

</body>
</html>


<script type="text/javascript">
    
    const onClickTaskUpdate = () =>{
        const value = document.getElementById("repeat").value
        if(value == "min"){
            let min = document.getElementById("minutes").value
            document.getElementById("minutes").value = min+" min"
            console.log(document.getElementById("minutes").value)
        }else if(value == "hr"){
            let hr = document.getElementById("hours").value
            document.getElementById("hours").value = hr+" hr"
        }else if(value == "day"){
            let day = document.getElementById("days").value
            document.getElementById("days").value = day+" day"
        }
    }

    const onClickEditButton = (taskId, title, desc, dateTime, name, repeat)=>{
        document.getElementById('pop-up').style.display = 'block';

        document.getElementById('taskId').value = taskId
        document.getElementById('edit-title').value = title
        document.getElementById('edit-des').value = desc
        document.getElementById('edit-time').value = dateTime
        document.getElementById(name).selected = true
        let value = repeat.split(' ')
        document.getElementById(value[1]).selected = true
        
    }

    const onClickCancel = () =>{
        document.getElementById ('pop-up').style.display = 'none'
    }

    document.getElementById("search-button").onclick = function(e){
        e.preventDefault()
        let value = document.getElementById('search').value
        
        if(value.length != 0){
            window.location.href = "searchTask?value="+value+"&id=${id}"
        }
    }

    const tbody = document.getElementById('tbody')
    if("${data}" == "null"){
        tbody.innerHTML=`
            <tr>
                <td colspan="6" > No data </td>
            </tr>
        `
    }

    const onChangeRepeat = ()=>{
        const value = document.getElementById("repeat").value
        let repeat = document.getElementById("repeat-input")
        if(value == "min"){
            repeat.innerHTML = `
                    <input type="text" id="minutes" name="repeat" placeholder="Enter minutes">
            `
        }else if(value == "hr"){
            repeat.innerHTML = `
                    <input type="number" id="hours" name="repeat" placeholder="Enter hours">
            `
        }else if(value == "day"){
            repeat.innerHTML = `
                    <input type="number" id="days" name="repeat" placeholder="Enter Days">
            `
        }else{
            repeat.innerHTML = `
                    <input type="text" value="don't Repeat" name="repeat" hidden>
            `
        }
    }
</script>

