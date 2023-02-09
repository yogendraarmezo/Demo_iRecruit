
<%
String userId="";
if(session.getAttribute("role") != null){
	userId = session.getAttribute("role").toString().trim();
}

%>
<link rel="stylesheet" type="text/css" href="./includes/left-panel/left-panel.css" />
<div class="left-panel">
<div class="menu-left"></div>
    <!-- <div class="logo"><a href="#"><img src="./img/iRecruit-logo.svg" alt="" /></a> -->
    <div class="logo">
        <h1 class="LOGO" style="color: #fff;"><b>iRecruit</b></h1>

	<span style="color:white; font-size:14px"> An Intelligent Recruitment System</span></a>
	</div>
    <ul>
        
            <a href="./analytics"><img src="./img/icn01.svg" alt="" style="color: #fff;" />  Dashboard</a>
        
        <li>
        <%if(userId.equalsIgnoreCase("DL") || userId.equalsIgnoreCase("FS")) { %>
            <a href="#"><img src="./img/icn02.svg" alt="" /> Recruitment</a>
            <%} %>
            
            <ul>
            <%if(userId.equalsIgnoreCase("DL")) { %>
                <li>
                    <a href="./generateAccesskey">New Candidate </a>
                </li>
                <%}%>
                <% if(userId.equalsIgnoreCase("DL") ) {%>
                <li>
                    <a href="./viewParticapantProcess">In Progress </a>
                </li>
				 <%}%>
				 
				  <% if(userId.equalsIgnoreCase("DL") ) {%>
                <li>
                    <a href="./viewHoldEmployeeByDealer">On Hold </a>
                </li>
				 <%}%>
				 
				  <%if( userId.equalsIgnoreCase("FS")) { %>
                
                
                <%-- <li>
                    <a href="./viewParticapantIprocess">In Progress </a>
                </li> --%>
				  <%} %>
				  
				   <%if( userId.equalsIgnoreCase("HO")) { %>
                
                <%-- 
                <li>
                    <a href="./viewAllParticapants">In Progress </a>
                </li> --%>
				  <%} %>
				
                 <%if( userId.equalsIgnoreCase("FS")) { %>
                
                
                <li>
                    <a href="./viewParticapant">Pending For Approval</a>
                </li>
				  <%} %>
				   <%if( userId.equalsIgnoreCase("HO")) { %>
                
                
                <%-- <li>
                    <a href="./viewAllPendingApprovalParticipant">Pending For Approval</a>
                </li> --%>
				  <%} %>
				  
				   <% if(userId.equalsIgnoreCase("FS") ) {%>
                <%-- <li>
                    <a href="./viewHoldEmployeeByFSDM">On Hold </a>
                </li> --%>
				 <%}%>
				 
				  <% if(userId.equalsIgnoreCase("HO") ) {%>
               <%--  <li>
                  <a href="./viewAllHoldParticipant">On Hold </a> 
                </li> --%>
				 <%}%>
				    
              
            </ul>
        </li>
        <%
        if(userId.equalsIgnoreCase("DL")){
        %>
       <li>
            <a href="./getEmployeeByDealer"><img src="./img/icn03.svg" alt="" /> Employee Master</a>
			
			<ul>
			
			<% if(userId.equalsIgnoreCase("DL") ) {%>
                <li>
                    <a href="./searchMspin">Search Old MSPIN</a>
                </li> 
				  <%} %>
				
			</ul>
		</li>
		<li>
			<a href="./showAllLinksCSV?flag"><img src="./img/csv-file-icn.svg" alt="" /> Reports </a>
			<!--<ul>
			     <li>
                    <a href="./showAllLinksCSV">CSV</a>
                </li>
			</ul>-->
		</li>
			</ul>
		
		
		
       <!--  <li>
            <a href="#"><img src="./img/icn04.svg" alt="" /> Data Manager</a>
        </li> -->
        <%} %>
		 <%
        if(userId.equalsIgnoreCase("FS")){
        %>
       <li>
            <a href="./getEmployeeByFSDM"><img src="./img/icn03.svg" alt="" /> Employee Master</a>
			<ul>
			
			<% if(userId.equalsIgnoreCase("DL") ) {%>
                <li>
                    <a href="./searchMspin">Search Old MSPIN</a>
                </li> 
				  <%} %>
				 
				
			</ul>
			
        </li>
		<li>
			<a href="./showAllLinksCSV?flag"><img src="./img/csv-file-icn.svg" alt="" /> Reports </a>
			 
		</li>
       <!--  <li>
            <a href="#"><img src="./img/icn04.svg" alt="" /> Data Manager</a>
        </li> -->
        <%} %>
		
		 <%
        if(userId.equalsIgnoreCase("HO")){
        %>
       <li>
            <a href="./viewAllCompletedParticipant"><img src="./img/icn03.svg" alt="" /> Employee Master</a>
			<ul>
			
			<% if(userId.equalsIgnoreCase("DL") ) {%>
                <li>
                    <a href="./searchMspin">Search Old MSPIN</a>
                </li> 
				  <%} %>
				 
				
			</ul>
			
        </li>
		<li>
			<a href="./showAllLinksCSV?flag"><img src="./img/csv-file-icn.svg" alt="" /> Reports </a>
			  
			
			 
		</li> 
			    <%-- <li>
                    <a href="./qb-analysis"> Question-wise Analysis</a>
                </li>  --%>
			 
			
     <!--   <li>
            <a href="#"><img src="./img/icn03.svg" alt="" /> QB-Analysis </a>
			<ul>			-->
<!-- 			
			<% if(userId.equalsIgnoreCase("DL") ) {%>
                <li>
                    <a href="./searchMspin">Question-wise Analysis</a>
                </li> 
				  <%} %> -->

             <!--     <li>
                    <a href="./qb-analysis"> Question-wise Analysis</a>
                </li> 
				
				 -->
              <!--  <li>
                    <a href="./showAllLinksCSV"> Competency-wise- Analysis</a>
                </li> -->
			<!--	
			</ul>
			
        </li>				-->

       <!--  <li>
            <a href="#"><img src="./img/icn04.svg" alt="" /> Data Manager</a>
        </li> -->
        <%} %>
    </ul>





	 <!-- <p style="font-style: italic;">An Intelligent <span>Recruitment System</span></p> -->
</div>
<script>
 $(document).ready(function () {
	 
	  
   
window.onload=function(){
	console.log('Timer is working');
	


setInterval(function () {
			
			
			swal({
     title: "Warning!",
    //  text: "Your session will be closed in 30 seconds due to inactivity.\n Please click on the CANCEL button to stay on the page and continue.",
     text: "Your session will be closed in 30 seconds due to inactivity.\n Please click on the CANCEL button to stay on the page and continue.",
     type: "info",
     timer: 1000,
    showCancelButton: false,
	showConfirmButton: true,
   confirmButtonText: 'Cancel'
},function(isConfirm){
   if(isConfirm){
       window.location.reload();
   }
});
			
		}, 14.5 * 60 * 1000);
		setInterval(function () {
			
			
			 window.location.href='https://staging.irecruit.org.in/irecruit/session-expired?param=a';
			
		}, 15 * 60 * 1000);
	
console.log('onload'+localStorage.getItem('windows'));
if(localStorage.getItem('windows')==='1'){
	$('body').hide();
		alert('You\'re trying to open duplicate window. This is not allowed.');
	setInterval(function () {
		
		//window.location.href='https://staging.irecruit.org.in/irecruit/login';
		
	}, 3000);

}else{ 
localStorage.setItem("windows",1);
}

}
	
window.onbeforeunload=function(){
		if($('body:visible').length >0)
{
localStorage.setItem("windows",0);
}

}
 


            $('.menu-left').click(function() {
				
                $(this).toggleClass('left-moved');
                $('.left-panel').toggleClass('left-moved');
                $('.right-section').toggleClass('left-moved');
			
            });
			if($(window).innerWidth() < 768) {
				
                $('.left-panel, .menu-left').addClass('left-moved');
				$('.left-panel').show();
            }
        });
 </script>