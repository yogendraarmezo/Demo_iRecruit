<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%try{ %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="icon" type="image/x-icon" href="https://staging.irecruit.org.in/irecruit/img/iRecruit-favicon.ico"/>
    <title>iRecruit</title>
    <link rel="stylesheet" type="text/css" href="./css/common.css" />
    <link rel="stylesheet" type="text/css" href="./css/fsdm.css" />
    <link rel="stylesheet" type="text/css" href="css/dashboard-filter.css">
      <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="./css/datatable.css">
    <style>
        .left-panel > ul > li:nth-child(1) > a, .left-panel > ul > li:nth-child(1) > ul > li:nth-child(3) > a{opacity: 1;}
        .left-panel > ul > li:nth-child(1) > ul > li:nth-child(3) > a::after{content: ''; position: absolute; right: 0; top: 50%; transform: translate(0, -50%); border-right: 6px solid #fff; border-top: 6px solid transparent; border-bottom: 6px solid transparent;}
        .dataTables_scrollBody {overflow-y: hidden !important;overflow-x: auto !important;}
    		.dataTables_wrapper .dataTables_filter input{padding: 10px 0px 10px 0px !important;}
    </style>

    <script src="./js/jquery-3.4.1.min.js"></script>
    <script src="./js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/fixedcolumns/4.2.1/js/dataTables.fixedColumns.min.js"></script>
	<script src="./js/datatable.js"></script>
  </head>
  <body>
    <div class="left-panel-include">
     <%@include file="./header/left-panel.jsp"%> 
    </div>
    <div class="user-panel-include">
	<%@include file="./header/user-panel.jsp"%>
	</div>

        <div class="right-section">
        <h1>On Hold </h1>
        <div class="page-filter-include">
		  <%@include file="./filter/searchFSDMHold.jsp"%>
		</div>

        <!-- <h1>On Hold </h1> -->
        <div class="container-1100">
            <div class="table-date">
			<div class="add-remove-columns">
                    <span>Add/Remove Columns</span>
                    <ul>
                        <li><a class="toggle-vis" data-column="0"><em>Sr. No.</em></a></li>
                        <li><a class="toggle-vis" data-column="1"><em>Candidate Name</em></a></li>
						<li><a class="toggle-vis" data-column="2"><em>Profile</em></a></li>
						<li><a class="toggle-vis" data-column="3"><em>Designation Code</em></a></li>
						<li><a class="toggle-vis" data-column="4"><em>Designation Desc</em></a></li>
						<li><a class="toggle-vis" data-column="5"><em>Mobile Number</em></a></li>
						<li><a class="toggle-vis" data-column="6"><em>Access Key</em></a></li>
						<li><a class="toggle-vis" data-column="7"><em>Registration Date</em></a></li>
						<li><a class="toggle-vis" data-column="8"><em>Assessment Date</em></a></li>				
                        <li><a class="toggle-vis" data-column="9"><em>Total Marks</em></a></li>
                        <li><a class="toggle-vis" data-column="10"><em>Marks Obtained</em></a></li>
                        <li><a class="toggle-vis" data-column="11"><em>Percentage</em></a></li>
                        <li><a class="toggle-vis" data-column="12"><em>Assessment Status</em></a></li>
                        <li><a class="toggle-vis" data-column="13"><em>Assessment Report</em></a></li>
						<li><a class="toggle-vis" data-column="11"><em>Data Sciencet</em></a></li>
                        <li><a class="toggle-vis" data-column="14"><em>Interview Date</em></a></li>
                        <li><a class="toggle-vis" data-column="15"><em>Interview Score</em></a></li>
						<li><a class="toggle-vis" data-column="16"><em>Registration Form</em></a></li>
						<li><a class="toggle-vis" data-column="17"><em>MSPIN</em></a></li>
						<li><a class="toggle-vis" data-column="18"><em>Praarambh Status</em></a></li>
						<li><a class="toggle-vis" data-column="19"><em>FSDM Approval</em></a></li>
						<li><a class="toggle-vis" data-column="20"><em>On-Hold</em></a></li>	
                    </ul>
                </div>
				 <div class="export-to-csv"><input type="button" onclick="funexport()" value="Export To CSV" class="ecsvbutton"></div>
                <!-- <table id="data" cellspacing="0" cellpadding="0" border="0" class="display nowrap cell-border" width="50"> -->
                    <table id="data" cellspacing="0" cellpadding="0" border="0" class="display nowrap cell-border" width="50" >
                    <thead>
                        <tr>
                             <th data-head="Sr. No." style="z-index: 1 !important;"><em>Sr. No.</em></th>
							<th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></span></th>
							<th data-head="Profile" class="sorting" ><em>Profile</em</span></th>
							<th data-head="Designation Code" class="sorting"><em>Designation Code</em</span></th>
							<th data-head="Designation Desc" class="sorting"><em>Designation Desc</em></th>   
							<th data-head="Mobile Number" class="sorting"><em>Mobile Number</em</span></th>
							<th data-head="Access Key" class="sorting"><em>Access Key</em</span></th>
							<th data-head="Registration Date" class="sorting"><em>Registration Date</em</span></th>
							<th data-head="Assessment Date" class="sorting"><em>Assessment Date</em</span></th>                       
                            <th data-head="Total Marks" class="sorting"><em>Total Marks</em></th>
                            <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>
                            <th data-head="Percentage Scored" class="sorting"><em>Percentage</em></th>						
                            <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
							<th data-head="Assessment Report" class="sorting"><em>Assessment Report</em></th>
                            <th data-head="Interview Date" class="sorting"><em>Interview Date</em></th>
                            <th data-head="Interview Score" class="sorting"><em>Interview Score</em></th>
                            <th data-head="Registration Form" class="sorting"><em>Registration Form</em></th>
                            <th data-head="MSPIN" class="sorting"><em>MSPIN<em></th>
                            <th data-head="Praarambh Status" class="sorting"><em>Praarambh Status</em></th>
							<th data-head="FSDM Approval" class="sorting"><em>FSDM Approval</em></th>
                            <th data-head="On-Hold" class="sorting"><em>On-Hold</em></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%int i=1; %>
                    <c:forEach items="${participantList}" var="participant">
                        <tr>
                            <td><%=i %></td>
							 	
							<td>${participant.participantName }</td>
							<td>${participant.designation}</td>
              <td>
							   <span>  ${participant.finalDesignationCode}  </span>
							 </td>
							  <td>
							   <span>  ${participant.finalDesignation}  </span>
							 </td>
							<td>${participant.mobile}</td>
							<td>${participant.accesskey}</td>
							<td>${participant.dateOfRegistration}</td>
							<td>${participant.assessment_Completion_date}</td>
							<td class="text-center"><center><span class="text-center">${participant.totalMark}</span></center></td>
                            <td><span>${participant.testScore}</span></td>
                            <td><span>${participant.percentageScore}%</span></td>
                           
                            <c:choose>
                               <c:when test="${participant.passFailStatus == '1' }">
                                  <td><span class="green">Pass</span></td>
                               </c:when>
                               <c:when test="${participant.passFailStatus == '0' }">
                                  <td ><span class="red" >Fail</span></td>
                               </c:when> 
                               </c:choose> 
                            <td><a href="#"><img src="./img/pdf-icn.svg" onclick="openReport('${participant.accesskey}','${participant.participantName }','${participant.email }','${participant.mobile }')" /></a></td >
                              <c:choose>
                               <c:when test="${participant.interViewDate != '' }">
                                 <td><span class="fixdate" style="cursor: default !important;" >${participant.interViewDate}</span></td>
                               </c:when>  
                                <c:otherwise>
                                <td> --</td>
                               </c:otherwise>
                               </c:choose> 
                             <td>
                             <c:choose>
                               <c:when test="${participant.interViewStatus == 'pass' || participant.interViewStatus == 'fail' }">
                                <a href="#" class="view-btn green"  onclick="openIterviewForm('${participant.accesskey}')">View</a>
                               </c:when>
                               <c:otherwise>
                              --
                               </c:otherwise>
                               </c:choose>
                             </td>
                              <td>
							  <c:choose>
                               <c:when test="${participant.interViewStatus == 'pass' }">
                                <a href="#" class="view-btn" onclick="openProfile('${participant.accesskey}')">View</a>
                               </c:when>
                               <c:otherwise>
                               --
                               </c:otherwise>
                               </c:choose>
							  
							  </td> 
                             <td>${participant.mspin }</td>
                             <td>
							 <c:choose>
                               <c:when test="${participant.prarambhStatus == '0' }">
							    <span class="green"></span>
							    </c:when>
								  <c:when test="${participant.prarambhStatus == '1' }">
							    <span></span>
							    </c:when>
								  <c:when test="${participant.prarambhStatus == '2' }">
							    <span class="green">Completed</span>
							    </c:when>
								 <c:otherwise>
                               <span></span>
                               </c:otherwise>
							    </c:choose>
							 </td>
							 
                             <td>
							<c:choose>
                               <c:when test="${participant.fsdmApprovalStatus == 'Rejected' }">
                               <span class="red">
							  <div title=" ${participant.fsdmReason}">${participant.fsdmApprovalStatus }</div>
							   </span>
                               </c:when>
                                 <c:when test="${participant.fsdmApprovalStatus == 'Pending'}">
                                <span class="blue">${participant.fsdmApprovalStatus }</span>
                                 </c:when>
                               <c:otherwise>
                               <span class="green">${participant.fsdmApprovalStatus }</span>
                               </c:otherwise>
							   </c:choose>
							 </td>  
                           
                            <td>Hold </td>

                        </tr>
                        <%i++; %>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script>
      $(document).ready(function () {
       // $(".left-panel-include").load("/includes/left-panel/left-panel.html");
       // $(".user-panel-include").load("./includes/user-panel/user-panel.html");
       // $(".page-filter-include").load("./includes/page-filter/page-filter.html");

       
      });
      
   
      
      function opentInterview(key){
  	        mywindow=window.open("interviewForm?accesskey="+key,"detailwindow","resizable=1,scrollbars=1,width=1170,height=600");
  	       mywindow.moveTo(120,90);
       }
      
      function openReport(key,name,email,mobile){
	        mywindow=window.open("https://staging.irecruit.org.in/iRecruit/player/viewAssessment.jsp?accesskey="+key+"&name="+name+"&email="+email+"&mobile="+mobile+"&testid=41&attemptid=1","detailwindow","resizable=1,scrollbars=1,width=1170,height=600");
	       mywindow.moveTo(120,90);
     }
      
      
      function openProfile(key){
    	  window.location.href="profileDetails?accesskey="+key;
      }
      
      function openIterviewForm(key){
    	  mywindow=window.open("printInterviewForm?accesskey="+key, "detailwindow","resizable=1,scrollbars=1,width=1170,height=600");
	       mywindow.moveTo(120,90);  
      }
        
      function funexport()
  	{	
      	var outlet = $('#outlet').val();
  		var name =$('#candidateName').val();
  		var uniqueCode =$('#uniqueCode').val();
  		var desg =$('#desg').val();
  		var mspinS =$('#mspinS').val();
  		var passFail =$('#passFail').val();
  		var dateFrom =$('#dateFromm').val();
		var dateTo =$('#dateToo').val();
  		document.forms[0].action="./onHoldCSV?outletCode="+outlet+"&candidate="+name+"&unique="+uniqueCode+"&desig="+desg+"&mspinS="+mspinS+"&pass="+passFail+"&dateFromm="+dateFrom+"&dateToo="+dateTo;
  		document.forms[0].method="post";
  		document.forms[0].submit();
  	}
       </script>
  </body>
</html>
<%
	
	} catch (Exception e) {
		
		response.sendRedirect("/login");
	}
%>