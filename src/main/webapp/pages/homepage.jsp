<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/homepage.css" rel="stylesheet" type="text/css">
    <title>Document</title>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div id="category" class="category">
        <button type="button" onclick="onClickCreateCategory()">Create Category</button>
    </div>

    <div id="add-category" class="add-category">
        <form:form action="addcategory" modelAttribute="addcategory" method="post">
            <input type="text" name="categoryName" placeholder="Category Name" required>
            <input type="hidden" name="user.username" value="${username}">
            <button id="category-add" onclick="onClickAdd()">Add</button>
            <button type="button" id="category-cancel" onclick="onClickCancelAddCategory()">Cancel</button>
        </form:form>
    </div>

    <div class="add-task">
        <h2>Add Task</h2>
        <form:form action="addTask" modelAttribute="task" method="post">
            <select name="category.categoryName" id="categoryName">
                <option id="null">Choose Category</option>
                <c:forEach var="row" items="${category}">
                    <option value="${row.categoryName}">${row.categoryName}</option>
                </c:forEach>
            </select>
            <input type="text" name="title" placeholder="Title" required>
            <textarea name="description" minlength="5" required id="" rows="6" placeholder="description"></textarea>
            <input type="datetime-local" name="dateTime" >

            <select id="repeat" onchange="onChangeRepeat()">
                <option id="repeat">Repeat</option>
                <option value="dont">Don't Repeat</option>
                <option value="min">Repeat Every Minute</option>
                <option value="hr">Repeat Every Hour</option>
                <option value="day">Repeat Every Day</option>
            </select>

            <div id="repeat-input">
            </div>
    
            <input type="hidden" name="user.username" value="${username}">
            <input type="hidden" name="status" value="Inprogress">
            <div class="buttons">
                <button onclick="onClickAddTask()">Add</button>
                <button id="cancel" onclick="onClickCancel()" type="button">Cancel</button>
            </div>
        </form:form>
    </div>
    
</body>
</html>


<script type="text/javascript">

    const onClickCreateCategory = () =>{
        document.getElementById("add-category").style.display = "block"

        document.getElementById("category").style.display = "none"
    }

    const onClickCancelAddCategory = () =>{
        document.getElementById("add-category").style.display = "none"

        document.getElementById("category").style.display = "block"
    }

    const onClickCancel = () =>{

    }

    const onChangeRepeat = ()=>{
        console.log("hello")
        const value = document.getElementById("repeat").value
        let repeat = document.getElementById("repeat-input")
        if(value == "min"){
            repeat.innerHTML = `
                    <input type="text" id="min" name="repeat" placeholder="Enter minutes">
            `

        }else if(value == "hr"){
            repeat.innerHTML = `
                    <input type="number" id="hr" name="repeat" placeholder="Enter hours">
            `
        }else if(value == "day"){
            repeat.innerHTML = `
                    <input type="number" id="day" name="repeat" placeholder="Enter Days">
            `
        }else{
            repeat.innerHTML = `
                    <input type="text" id="repeat" value="don't Repeat" name="repeat" hidden>
            `
        }
    }

    const onClickAddTask = () =>{
        const value = document.getElementById("repeat").value
        if(value == "min"){
            let min = document.getElementById("min").value
            document.getElementById("min").value = min+" min"
            console.log(document.getElementById("min").value)
        }else if(value == "hr"){
            let hr = document.getElementById("hr").value
            document.getElementById("hr").value = hr+" hr"
        }else if(value == "min"){
            let day = document.getElementById("day").value
            document.getElementById("day").value = day+" day"
        }
    }

    
    
   
    
</script>