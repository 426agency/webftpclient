<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="it.unibz.model.UserBean"
    %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ftp Browser</title>
<meta name="viewport" content="width=device-width, inital-scale=1.0, maximal-scale=1.0, user-scalable=no;"/>
<link rel="stylesheet" href="jquery.mobile-1.0rc1.min.css" />
<script src="jquery-1.6.4.min.js"></script>
<script src="loginValidation.js" type="text/javascript"></script>
<script src="jquery.mobile-1.0rc1.min.js"></script>
</head>
<body>
<%
					
					// Get the bean object from the session and cast it to a UserBean 
					UserBean currentUser = (UserBean)(session.getAttribute("currentSessionUser"));

	//Noone logged in
	 %>
<section id="homepage" data-role="page" >
<!-- start header -->
<div data-role="header" data-nobackbtn="true">
	<h1>Login</h1>
	<%if(currentUser!=null){ %>
	<a href="LoginServlet?logout=true" data-icon="delete">Cancel</a><%} %>
</div>
<!-- end header -->
<div data-role="content" data-inset="true">	
<%if(currentUser==null){
	
	if(session.getAttribute("msg")!=null){ 
	out.println(session.getAttribute("msg"));
	session.removeAttribute("msg");
	
} %>

	<form action="LoginServlet" method="POST">
           <fieldset>
           
           <!--<asp:Literal runat="server" ID="litMessage"></asp:Literal>-->

           <div data-role="fieldcontain">
               <label for="username">Username:</label>
               <input type="text" name="username" id="username" />
                             </div>    

              <div data-role="fieldcontain">
                             <label for="password">Password:</label>
               <input type="password" name="password" id="password" />
              </div>    

               <input id="Submit1" type="submit" value="Login" data-role="button" data-inline="true" data-theme="b" />

           <hr />
           Don't have a login? <a href="#page2">Sign Up</a>
           </fieldset>
       </form>
	<%
}
else{
				%>
			
				Welcome <%= currentUser.getUsername()%> 
							<%}
						%>

       
       
</div>
<footer data-role="footer"><h1>Universal FTP Browser</h1></footer>
</section>
<section id="logout" data-role="page">
<!-- start header -->
<div data-role="header">
	<h1>Logout</h1>
</div>
<!-- end header -->
<div class="content" data-role="content">

<a href="#homepage">Login</a>
</div>
<footer data-role="footer"><h1>Universal FTP Browser</h1></footer>
</section>
</body>
</html>