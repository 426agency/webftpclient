<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
        
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ftp Browser</title>
<meta name="viewport" content="width=device-width, inital-scale=1.0, maximal-scale=1.0, user-scalable=no;"/>
<link rel="stylesheet" href="jquery.mobile-1.0rc1.min.css" />
<script src="jquery-1.6.4.min.js"></script>
<script src="section_mobile.js"></script>
<script src="jquery.mobile-1.0rc1.min.js"></script>
<script src="jquery.validate.min.js"></script>
</head>
<body>

<div data-role="page" id="login"> 
    <div data-role="header">
        <h1>Login</h1>
<c:if test="${not empty sessionScope.currentSessionUser}">    
	<a href="index.jsp#login" id="logoutlink" data-icon="delete" onclick="">Logout</a></c:if>
    </div>
    <div data-role="content"> 
    <c:choose>
    <c:when test="${empty sessionScope.currentSessionUser}">    
        <form action="" id="loginForm" method="post">
                        <div data-role="field-contain" class="required">
                <label for="username">Username</label>
                <input type="text" name="login" value="" class="text-box"  />            </div>
            <div data-role="field-contain" class="required">
                <label for="password">Password</label>

                <input type="password" name="password" id="password" value="" />
            </div>
            <button data-role="button" data-theme="b">Login</button>
            
        </form>
        <a href="#signup" class="signup-button" data-role="button" data-theme="e">New Account</a>
        </c:when>
        <c:otherwise>
        <c:out value="Welcome ${sessionScope.currentSessionUser.username}"></c:out>
        </c:otherwise>
        </c:choose>
        </div>
    </div> 

<div data-role="page" id="signup"> 
    <div data-role="header">

        <h1> Create Account</h1>
        <a href="#login" data-icon="home" data-iconpos="notext" data-direction="reverse">Home</a>
    </div>
    <div data-role="content"> 
    <c:choose>
    <c:when test="${empty sessionScope.currentSessionUser}">    
        <input class="setup" type="hidden" value="Signup"/>
        <form action="" method="post" id="signupForm">

            <div data-role="field-contain" class="required">

                <label for="username">Username</label>
                <input type="text" name="username" value="" class="text-box"  />            </div>
            <div data-role="field-contain">
                <label for="password">Password</label>
                <input type="password" name="password" value="" class="text-box"  />                <p class="description"> 4 characters minimum</p>
            </div>

            <button data-role="button" data-theme="e">Sign up!</button>
        </form>
        </c:when>
        <c:otherwise>
        <c:out value="You cannot register while logged in!"></c:out>
        </c:otherwise>
        </c:choose>
    </div> 
</div> 


 <div id="ftpConnections" data-role="page">
 <a href="" id="suserID" title="" ></a>
 
            <div data-role="header">
                <h1>Your Connections</h1>
	<a href="index.jsp" id="logoutlink" data-icon="delete" onclick="">Logout</a>
            </div>
            <div class="content" data-role="content" id="ftpConnections_content">
                <ul id="ftpConnections_list" data-role="listview" data-theme="c">
                    
                </ul>
                <div id="errorDiv"></div>
            </div>
        </div>

<script type="text/javascript">
    $(document).ready( function(){
        htb.Login.setup();
        htb.Signup.setup();
        htb.Logout.setup();
    });
</script>
</body>
	</html>