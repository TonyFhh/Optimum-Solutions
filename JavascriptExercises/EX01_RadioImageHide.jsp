<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type ="text/javascript">

 function showHide(){
if (!document.getElementById) return;
var head1 = document.getElementById("pen");
var head2 = document.getElementById("book");
var head3 = document.getElementById("signpost");
head1.style.display=(document.getElementById("toggle_pen").checked) ? "block" : "none"; //? -> if checked do set .display:"block" else set .display:"none"
head2.style.display=(document.getElementById("toggle_book").checked) ? "block" : "none"; //toggles display of image. compared to visibility, "none" won't occupy the space
head3.style.display=(document.getElementById("toggle_signpost").checked) ? "block" : "none";
	
} 

</script>
</head>
<body>


	<form action="" name="form1">
	<div>
	<input type="radio" name="toggle" id="toggle_pen" onclick="showHide()" checked/>
	<span style="font-weight:bold">Pen</span>
	<input type="radio" name="toggle" id="toggle_book" onclick="showHide()" />
	<span style="font-weight:bold">Book</span>
	<input type="radio" name="toggle" id="toggle_signpost" onclick="showHide()" />
	<span style="font-weight:bold">SignPost</span><br/>
	
	</div>
	</form>
	
	<img id = "pen" src = "pen.png"><img id = "book" style="display:none" src = "book.png"><img id = "signpost" style="display:none" src = "signpost.png">

</body>
</html>
