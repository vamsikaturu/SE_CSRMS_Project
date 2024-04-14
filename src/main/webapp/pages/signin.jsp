<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link href="css/signin.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="bg-image"></div>
    
    <div class="signin">
        <h1>Create account</h1>
        <form:form id="formaction" modelAttribute="signinData" method="post">
            <input type="text" name="username" placeholder="UserName" required>
            <input type="text" name="first_name"  placeholder="First Name" required>
            <input type="text" name="last_name" placeholder="Last Name" required>
            <input type="password" name="password" placeholder="Password" required>
            <input type="email" name="email" placeholder="Email" required>
            <div class="buttons">
                <button onclick="onClickSubmitAction()">Submit</button>
                <button formnovalidate onclick="onClickCancelAction()"> Cancel</button>
            </div>
        </form:form>
    </div>
</body>
</html>

<script type="text/javascript">
    const onClickSubmitAction = () =>{
		document.getElementById("formaction").action = "/signin_process"
	}
	
	const onClickCancelAction = () =>{
        
		document.getElementById("formaction").action = "/login"
	}
</script>