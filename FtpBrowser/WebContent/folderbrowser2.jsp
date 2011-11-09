<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta name="viewport"
	content="width=device-width, inital-scale=1.0, maximal-scale=1.0, user-scalable=no;" />
<link rel="stylesheet" href="jquery.mobile-1.0rc1.min.css" />
<script src="jquery-1.6.4.min.js"></script>
<script src="loginValidation.js" type="text/javascript"></script>
<script src="jquery.mobile-1.0rc1.min.js"></script>

<script type="text/javascript">
function DoAction( id, name ){
	//alert( "Data Saved: " + name );
	var el = document.getElementById('cacca');
	el.innerHTML = name;
	//location.reload(true);
}

</script>

</head>
<body>

	<div data-role="page" id="home">
		<header data-role="header">
		  <h1>Browser</h1>
		  <a href='#' class='ui-btn-left ui-btn-back' data-icon='arrow-l'>Back</a>
		</header>
		<div data-role="content">
			<ul data-role="listview" data-inset="true" data-theme="c"
				data-dividertheme="f">
				<li data-role="list-divider" id="currentfolder">Page 2</li>
				<li><a class="folderclass" href="#home" OnClick="DoAction(1,'Jose');" > Jose </a></li>
				<li><a class="folderclass" href="#home" OnClick="DoAction(2,'Juan');" > Juan </a></li>
				<li><a class="folderclass" href="#home" OnClick="DoAction(3,'Pedro');" > Pedro </a></li>
			</ul>
		</div>
	</div>

</body>
</html>