
<% String mspin2="", user="", password=""; 
mspin2=session.getAttribute("mspin").toString().trim();
user=session.getAttribute("user").toString().trim();
password=session.getAttribute("password").toString().trim();
String msg="";
if(session.getAttribute("msgEmail") != null){
	msg = session.getAttribute("msgEmail").toString().trim();
}
%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="shortcut icon" type="image/svg" href="http://staging.irecruit.org.in:8080/irecruit/img/iRecruit-favicon.ico"/>
    <title>iRecruit</title>
    <link rel="stylesheet" type="text/css" href="./css/login.css" />
    <style>
      .left-panel > ul > li:nth-child(1) a::after{content: ''; position: absolute; right: 0; top: 50%; transform: translate(0, -50%); border-right: 6px solid #fff; border-top: 6px solid transparent; border-bottom: 6px solid transparent;}
    
    .error-chage-email{
	margin: 20px 0;
color: #f00;
text-align: centre;
font-size: 14px;
line-height: 20px;
display:none;
}
    </style>
    <script src="./js/jquery-3.4.1.min.js"></script>
    <script>
    function addEmail(){
    	
		var email     =  $("#email").val();		
		var user      =  $("#user").val();		
		var password  =  $("#password").val();		
		var mspin     =  $("#mspin").val();		
		var errorEmail=$("#errorEmail");
		
		if (!validateEmail(email)) {
			   $('.error-chage-email').append('Please enter valid email.');
    		   $('.error-chage-email').show();   
	          return false;
	      }
		document.forms[0].action="addEmailAddress";
		document.forms[0].method="post";
		document.forms[0].submit(); 
		 
	}
    
    function validateEmail(email) 
	 {
	     var re = /\S+@\S+\.\S+/;
	     return re.test(email);
	 }
    <%if(msg.equals("I")){%>
    $('.error-chage-email').append('Enter Not added.');
	   $('.error-chage-email').show();   
   <% }%>
    </script>
    
  </head>
  <body>
   

    <div >
        <div class="form" style="max-width: 600px!important;">
            <form class="form__section"   id="testForm">
                    <input type="hidden" name="mspin" id="mspin"	value="<%=mspin2%>" />
					<input type="hidden" name="user" id="user"	value="<%=user%>" />
					<input type="hidden" name="password" id="password"	value="<%=password%>" />
                <h1 style="font-size:25px !important;">Please provide your official email ID to proceed.</h1>
                <div class="form__block">
                    <input type="email" name="email" id="email" placeholder="eg: john@example.com">
                </div>
                 <div class="error-chage-email" id="errorEmail"></div>
                <div class="form__block">
                    <input type="button" class="form__button form__button--submit" onclick="addEmail()" value="Proceed">
                </div>
            </form>
        </div>
    </div>

  </body>
</html>
