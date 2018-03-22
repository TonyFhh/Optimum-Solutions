<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JavaScript MCQ about Java and HTML</title>
<script type ="text/javascript">



/* function showHide(){
	if (!document.getElementById) return;
	var head1 = document.getElementById("pen");
	var head2 = document.getElementById("book");
	var head3 = document.getElementById("signpost");
	head1.style.display=(document.getElementById("toggle_pen").checked) ? "block" : "none"; //? -> if checked do set .display:"block" else set .display:"none"
	head2.style.display=(document.getElementById("toggle_book").checked) ? "block" : "none"; //toggles display of image. compared to visibility, "none" won't occupy the space
	head3.style.display=(document.getElementById("toggle_signpost").checked) ? "block" : "none";
		
	}  */

function submit(){ //if user select any one of the options
		if (!document.getElementById) return;
		document.getElementById("submitbtn").disabled=true;
		var correct_ans = 0;


	
	//first disable all the radio buttons
	document.getElementById("q1_a").disabled=true;
	document.getElementById("q1_b").disabled=true;
	document.getElementById("q1_c").disabled=true;
	document.getElementById("q1_d").disabled=true;
	//now mark the answer
	if (document.getElementById("q1_a").checked) document.getElementById("1a").style.visibility="visible";
	else if (document.getElementById("q1_b").checked) correct_ans++;
	else if (document.getElementById("q1_c").checked) document.getElementById("1c").style.visibility="visible";	
	else if (document.getElementById("q1_d").checked) document.getElementById("1d").style.visibility="visible";
		
	document.getElementById("1b").style.visibility="visible";


	
	document.getElementById("q2_a").disabled=true;
	document.getElementById("q2_b").disabled=true;
	document.getElementById("q2_c").disabled=true;
	document.getElementById("q2_d").disabled=true;
	//now mark the answer
	if (document.getElementById("q2_a").checked) document.getElementById("2a").style.visibility="visible";		
	else if (document.getElementById("q2_b").checked) document.getElementById("2b").style.visibility="visible";		
	else if (document.getElementById("q2_c").checked) document.getElementById("2c").style.visibility="visible";			
	else if (document.getElementById("q2_d").checked) correct_ans++;
		
document.getElementById("2d").style.visibility="visible";	
	


	document.getElementById("q3_a").disabled=true;
	document.getElementById("q3_b").disabled=true;
	document.getElementById("q3_c").disabled=true;
	document.getElementById("q3_d").disabled=true;
	//now mark the answer
	if (document.getElementById("q3_a").checked) correct_ans++;		
	else if (document.getElementById("q3_b").checked) document.getElementById("3b").style.visibility="visible";		
	else if (document.getElementById("q3_c").checked) document.getElementById("3c").style.visibility="visible";			
	else if (document.getElementById("q3_d").checked) document.getElementById("3d").style.visibility="visible";
		
document.getElementById("3a").style.visibility="visible";
	
/* using this as the original document.write lines cause a problem by loading a new page, which is not wanted here.
This approach uses requires a div assignment in intended space, calling its id and inputing a custom html. More mainstream approach apparently too */
document.getElementById("results").innerHTML = "Correct answers: " +correct_ans+" /3"; 

	}
	
		

</script>
</head>
<body>


	<form action="" name="question1">
	<div>
	<h3><b>1. Which of these about Application server vs Web server is wrong?</b></h3>
	<input type="radio" name="q1" id="q1_a"/>
	<span>a. Application Server provide security features.</span><img id="1a" style="visibility:hidden" src="wronge.jpg"><br/>
	<input type="radio" name="q1" id="q1_b"/>
	<span>b. Only Application Server can support a full java application.</span><img id="1b" style="visibility:hidden" src="righte.jpg"><br/>
	<input type="radio" name="q1" id="q1_c"/>
	<span>c. Application Servers provide transaction features.</span><img id="1c" style="visibility:hidden" src="wronge.jpg"><br/>
	<input type="radio" name="q1" id="q1_d"/>
	<span>d. Only Application Servers can synchronise between international time zones.</span><img id="1d" style="visibility:hidden" src="wronge.jpg"><br/>
	
	</div>
	</form>
	<br>
	
	<form action="" name="question2">
	<div>
	<h3><b>2. Which of these is not example of Casting in Java?</b></h3>
	<input type="radio" name="q2" id="q2_a" />
	<span>a. Autoboxing and Unboxing</span><img id="2a" style="visibility:hidden" src="wronge.jpg"><br/>
	<input type="radio" name="q2" id="q2_b"/>
	<span>b. Up and Down casting</span><img id="2b" style="visibility:hidden" src="wronge.jpg"><br/>
	<input type="radio" name="q2" id="q2_c"/>
	<span>c. Implicit and Explicit casting</span><img id="2c" style="visibility:hidden" src="wronge.jpg"><br/>
	<input type="radio" name="q2" id="q2_d"/>
	<span>d. Automatic and Manual casting</span><img id="2d" style="visibility:hidden" src="righte.jpg"><br/>
	</div>
	</form>
	<br>
	
	<form action="" name="question3">
	<div>
	<h3><b>3. Which of these statements about protected and default access modifiers is correct? </b></h3>
	<input type="radio" name="q3" id="q3_a"/>
	<span>a. For classes in different packages, inherited classes can call protected variables and not default variables.</span><img id="3a" style="visibility:hidden" src="righte.jpg"><br/>
	<input type="radio" name="q3" id="q3_b"/>
	<span>b. Unlike default variables, protected variables cannot be modified.</span><img id="3b" style="visibility:hidden" src="wronge.jpg"><br/>
	<input type="radio" name="q3" id="q3_c"/>
	<span>c. For classes within the same packages, only inherited classes can call protected variables and not default variables.</span><img id="3c" style="visibility:hidden" src="wronge.jpg"><br/>
	<input type="radio" name="q3" id="q3_d"/>
	<span>d. Default methods can override protected methods in inheritance.</span><img id="3d" style="visibility:hidden" src="wronge.jpg"><br/>
	</div>
	</form>
	<br>
	
	<div>
	<input type="button" id="submitbtn" value="Submit" onclick="submit()"/>
	</div>
	
	
	<div id="results"></div>
	

</body>
</html>