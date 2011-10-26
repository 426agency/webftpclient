<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ftp Browser</title>
<meta name="viewport" content="width=device-width, inital-scale=1.0, maximal-scale=1.0, user-scalable=no;"/>
<link rel="stylesheet" href="jquery.mobile-1.0rc1.min.css" />
<script src="jquery-1.6.4.min.js"></script>
<script src="jquery.mobile-1.0rc1.min.js"></script>
</head>
<body>
<section id="homepage" data-role="page" >
<!-- start header -->
<div data-role="header" data-nobackbtn="true">
	<h1>Login</h1>
	<a href="login.aspx" data-rel="dialog" class="ui-btn-right">Login</a>
</div>
<!-- end header -->
<div data-role="content" data-inset="true">	

       <form action="LoginHandler" method="POST">
           <fieldset>
           
           <!--<asp:Literal runat="server" ID="litMessage"></asp:Literal>-->

           <label for="email">Email:</label>
           <input type="email" name="email" id="email" value=""  />

           <label for="password">Password:</label>
           <input type="password" name="password" id="password" value="" />

               <input id="Submit1" type="submit" value="Login" data-role="button" data-inline="true" data-theme="b" />

           <hr />
           Don't have a login? <a href="register.jsp">Sign Up</a>
           </fieldset>
       </form>
       
</div>
<footer data-role="footer"><h1>Footer</h1></footer>
</section>
<section id="page2" data-role="page">
<!-- start header -->
<div data-role="header">
	<h1>Login</h1>
	<a href="/" class="ui-btn-right">Cancel</a>
</div>
<!-- end header -->
<div class="content" data-role="content">
<p>Test</p>

<a href="#homepage">home</a>
</div>
<footer data-role="footer"><h1>Footer</h1></footer>
</section>
</body>
</html>