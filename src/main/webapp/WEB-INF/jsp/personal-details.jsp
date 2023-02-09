<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.msil.irecruit.Entities.ParticipantRegistration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
try
    {
String role="";
if(session.getAttribute("role") != null){
	role = session.getAttribute("role").toString().trim();

%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="icon" type="image/x-icon" href="https://staging.irecruit.org.in/irecruit/img/iRecruit-favicon.ico"/>
<title>iRecruit</title>
<link rel="stylesheet" type="text/css" href="./css/common.css" />
<link rel="stylesheet" type="text/css" href="./css/profile.css" />
<link rel="stylesheet" href="./css/scrolltabs.css">
<link rel="stylesheet" type="text/css" href="./css/style.css" />
<link rel="stylesheet" type="text/css" href="./css/sweetalert.css"/>
 <script type="text/javascript" src="./js/sweetalert.min.js"></script>
<style>
ul li {margin: 0 !important;}
.form-btn{width: 100% !important;}
</style>

<script src="./js/jquery-3.4.1.min.js"></script>
<script src="./js/jquery.dataTables.min.js"></script>
<script src="./js/jquery.scrolltabs.js"></script>
</head>
<body>
	<div class="left-panel-include">
	<%@include file="./header/left-panel.jsp"%>
	</div>
	<div class="user-panel-include">
	<%@include file="./header/user-panel.jsp"%>
	</div>

	<div class="right-section">
		<h1>Personal Details</h1>
		<div class="container-1100">
			<div class="profile-container">
				<%@include file="./header/profileMenu.jsp"%>

				<div class="profile-content">
					<form:form class="form" action="./savePersonalDetails" method="post"
						id="testForm" modelAttribute="personal">
						<div class="form-section">
							<!-- <div class="form-block"></div>
							<div class="form-block"></div> -->
							<div class="form-block">
								<form:input type="hidden" path="accessKey" placeholder="Dealer Code" />
								<h5>
									Dealer Code <span>*</span>
								</h5>
								<form:select path="outletCode" style="color: black !important">
									<form:option value="" label="Select" />
									 <c:forEach items="${outletLsit}" var="outlet">
									 
									  <c:choose>
                               <c:when test="${outlet.outletCode  == personal.outletCode }">
                                 <form:option value="${outlet.outletCode }" selected="selected"/>
                               </c:when>
                               <c:otherwise>
                              <form:option value="${outlet.outletCode }"  />
                               </c:otherwise>
                               </c:choose>
											   
											 </c:forEach>
								</form:select>
							</div>

							<div class="form-block">
								<h5>
									Employee Code <span>*</span>
								</h5>
								<form:input type="text" path="employeeCode" placeholder="Employee Code "  style=" background : #D3D3D3" readonly="true" />
							</div>
							

							<div class="form-block">
								<h5>
									Title <span>*</span>
								</h5>
								<form:select path="title" style="color: black !important">
									<form:option value="" label="Select" />
									<c:forEach items="${title }" var="title">
									 <c:choose>
                                        <c:when test="${title.listCode  eq personal.title } ">
                                           <form:option value="${title.listCode}" label="${title.listDesc}" selected="selected"/>
                                        </c:when>
                                        <c:otherwise>
                                           <form:option value="${title.listCode}" label="${title.listDesc}" />
                                       </c:otherwise>
                                   </c:choose>									
									</c:forEach>
								</form:select>
							</div>
							
							

							<div class="form-block">
								<h5>
									First Name <span>*</span>
								</h5> 
								<form:input type="text" path="firstName"  placeholder="First Name"  />
							</div>
							<div class="form-block">
								<h5>Middle Name</h5>
								<form:input type="text" path="middleName" placeholder="Middle Name"  />
							</div>
							<div class="form-block">
								<h5>
									Last Name <span>*</span>
								</h5>
								<form:input type="text" path="lastName" placeholder="Last Name" />
							</div>
							<div class="form-block">
								<h5>
									Address <span>*</span>
								</h5>
								<form:input type="text" path="address" placeholder="Address" />
							</div>
							<div class="form-block">
								 <h5>State <span>*</span></h5>
                                  <form:select path = "state" id = "state" onchange="getCity()" style="color: black !important">
                                 <c:forEach items="${stateList}" var="entr"> 
									  <c:choose>
                                          <c:when test="${entr.key  eq personal.state}">
                                          <form:option value="${entr.key}" label="${entr.value}" selected="selected"/> 
                                          </c:when>
                                          <c:otherwise>
                                         <form:option value="${entr.key}" label="${entr.value}"/>
                                        </c:otherwise>
                                      </c:choose>
									</c:forEach>
                                 </form:select>
							</div>
							<div class="form-block">
								<h5>
									City <span>*</span>
								</h5>
								<form:select path="city" onchange="getPincodeByCity()" id="city" style="color: black !important">
									<c:forEach items="${city}" var="entr"> 
									  <c:choose>
                                          <c:when test="${entr.key  eq personal.city}">
                                          <form:option value="${entr.key}" label="${entr.value}" selected="selected"/> 
                                          </c:when>
                                          <c:otherwise>
                                         <form:option value="${entr.key}" label="${entr.value}"/>
                                        </c:otherwise>
                                      </c:choose>
									</c:forEach>
								</form:select>
							</div>
							<div class="form-block">
								<h5>
									Pin Code <span>*</span>
								</h5>
								<form:input type="text" path="pin" list="pincodeList"  placeholder="Pin Code"  maxlength="10" id="pincode" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
								 <datalist id="pincodeList" >
                                 </datalist>
							</div>
                      <div class="form-block">
                        <h5>Tehsil <span>*</span></h5>
                        <form:select  path="tehsil"  id="tehshil" onchange="getVillage()" style="color: black !important">
						   <form:option value="" label="Select" /> 
						  <c:forEach items="${tehsil}" var="entr"> 
									  <c:choose>
                                          <c:when test="${entr.key  eq personal.tehsil}">
                                          <form:option value="${entr.key}" label="${entr.value}" selected="selected"/> 
                                          </c:when>
                                          <c:otherwise>
                                         <form:option value="${entr.key}" label="${entr.value}"/>
                                        </c:otherwise>
                                      </c:choose>
									</c:forEach>
                        </form:select>
                    </div>
                    <div class="form-block">
                        <h5>Village <span>*</span></h5>
                       <form:select  path="village"  id="village" style="color: black !important">
					   <form:option value="" label="Select" /> 
					     <c:forEach items="${village}" var="entr"> 
									  <c:choose>
                                          <c:when test="${entr.key  eq personal.village}">
                                          <form:option value="${entr.key}" label="${entr.value}" selected="selected"/> 
                                          </c:when>
                                          <c:otherwise>
                                         <form:option value="${entr.key}" label="${entr.value}"/>
                                        </c:otherwise>
                                      </c:choose>
									</c:forEach>
                        </form:select>
                    </div>
							<div class="form-block">
								<h5>
									ID Proof <span>*</span>
								</h5>
								<form:select path="idProof" id="idProof" style="color: black !important">
									<form:option value="" label="Select" />
									<c:forEach items="${ID}" var="id"> 
							  <c:choose>
                                    <c:when test="${id.listCode  eq personal.idProof } ">
                                       <form:option value="${id.listCode}" label="${id.listDesc}"  selected="selected"/>
                                    </c:when>
                                  <c:otherwise>
                                       <form:option value="${id.listCode}" label="${id.listDesc}" />
                                 </c:otherwise>
                              </c:choose>
									
							</c:forEach>
								</form:select>
							</div>
							<div class="form-block">
								<h5>
									Date of Birth<span>*</span>
								</h5>
								<form:input type="date" id="date" path="birthDate"  placeholder="Date of Birth" />
							</div>
							<div class="form-block">
							<h5>
									Mobile <span>*</span>
								</h5>
								<form:input type="text" maxlength="10"  placeholder="Mobile"  oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" path="mobile" />
							</div>

							<div class="form-block">
								<h5>Alternate/Personal Contact Number <span>*</span></h5>
								<form:input type="text" id="alt_co_no" maxlength="10"  placeholder="Alternate/Personal Contact Number" onkeyup="alternateCo()"   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" path="alternateContactNumber"  />
						       <!-- <br><span id="alt_no" style="display:none;color: #f00; margin-top: 5px;bottom: -20px;font-weight: 500;
                                font-size: 12px">Please enter your alternateContactNumber</span> -->
							</div>
							<div class="form-block">
								<h5>
									Email <span>*</span>
								</h5>
								<form:input type="email" path="email"  placeholder="Email" />
							 </div>
							<div class="form-block">
								<h5>
									UID/Aadhaar Number <span>*</span>
								</h5>
								<form:input type="text" maxlength="15" placeholder="UID/Aadhaar Number"   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" path="adharNumber" />
							</div>

							<div class="form-block">
								<h5>
									Drivery Licence <span>*</span></h5>
									<form:select path="DL"
									id="dl" onchange="getLicence()" style="color: black !important">
									<form:option value="" label="Select" />
									<form:option value="Yes" />
									<form:option value="No" />
								</form:select>
								
							</div>
							<div class="form-block license">
								<h5>
									License no. <span>*</span>
								</h5>
								<form:input type="text" path="licenseNo" id="licenseNo" placeholder="License no."  maxlength="15"/>
								
							</div>
							<div class="form-block license">
								<h5>
									Validity of License <span>*</span>
								</h5>
								<form:input type="date" path="validityOfLicence"   placeholder="Validity of License" id="vd_lc"/>
							</div>

							<div class="form-block">
								<h5>
									Highest Qualification <span>*</span>
								</h5>
								<form:select path="highestQualification" id="ql" style="color: black !important">
									<form:option value="" label="Select" />
									<c:forEach items="${qualification}" var="qualification">
									 <c:choose>
                                         <c:when test="${qualification.listCode  eq personal.highestQualification}">
                                            <form:option value="${qualification.listCode}" label="${qualification.listDesc}"  selected="selected"/>
                                         </c:when>
                                          <c:otherwise>
                                             <form:option value="${qualification.listCode}" label="${qualification.listDesc}" />
                                         </c:otherwise>
                                       </c:choose>
									
									</c:forEach>
								</form:select>
							</div>
							<div class="form-block">
								<h5>
									Primary Language <span>*</span>
								</h5>
								<form:select path="primaryLanguage" id="primery_lg" style="color: black !important">
									<form:option value="" label="Select" />
									<c:forEach items="${language}" var="language">
									 <c:choose>
                                         <c:when test="${language  eq personal.primaryLanguage}">
                                             <form:option value="${language.listCode}" label="${language.listDesc}" selected="selected"/>
                                         </c:when>
                                          <c:otherwise>
                                             <form:option value="${language.listCode}" label="${language.listDesc}" />
                                       </c:otherwise>
                                      </c:choose>
									
									</c:forEach>
								</form:select>
							</div>
							<div class="form-block">
								<h5>
									Secondary Language <span>*</span>
								</h5>
								<form:select path="secondaryLanguage" id="secondary_lg" style="color: black !important">
									<form:option value="" label="Select" />
									<c:forEach items="${language}" var="language">
									  <c:choose>
                                         <c:when test="${language.listCode  eq personal.secondaryLanguage}">
                                           <form:option value="${language.listCode }"  label="${language.listDesc}" selected="selected"/>
                                       </c:when>
                                       <c:otherwise>
                                          <form:option value="${language.listCode}" label="${language.listDesc}"/>
                                       </c:otherwise>
                                 </c:choose>									
									</c:forEach>
								</form:select>
							</div>
							<c:if test="${(personal.documents_status != 'final' || personal.fsdmApprovalStatus == '1') && personal.status !='H'}">
							 <%if(role.equalsIgnoreCase("DL")) { %>
							<div class="form-btn">
									   <input class="btn blue-btn" type="button" value="Save" id="submitbtn" onclick="savePesonal('save')"/>
								       <input class="btn blue-btn" type="submit" value="Next" id="submitnext" onclick="save('next')"/>
								       <input type="hidden" value="" id="btn_next" name="btnValue"/>
							</div>
							<%} %>
							</c:if>
						</div>

					</form:form>

				</div>

			</div>
			<form>
				    <input type="hidden" name="" id="accesskey" value="${personal.accessKey}">					
					<input type="hidden"  id="designation_selse" value="${personal.designation}">
					<input type="hidden" id="mobile" value="${personal.mobile}">
			</form>
			<div class="blk-bg"></div>
			<script
				src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
			<script type="text/javascript" src="./js/personalDetail.js"></script>
			<script>
      $(document).ready(function () {
		   getLicence();
		   if('${personal.tehsil}' !=''){
		   getTehshil();
		   }
		   validityOfLicence();
		   
		   $("#dl").val('${personal.DL}');
		   $("#tehshil").val('${personal.tehsil}');
	     
    	  <%if(role.equalsIgnoreCase("FS") || role.equalsIgnoreCase("HO") ) { %>
    	  $('input').attr('disabled', 'disabled');
    	  $('select').attr('disabled', 'disabled');
    	  <%}%>
        $('#tabs').scrollTabs();
		
		
		var profile = document.getElementById("profile");
		var personal_details = document.getElementById("personal_details");
		
	    profile.className -= 'tab-btn tab_selected scroll_tab_first';
        personal_details.className += 'tab-btn tab_selected scroll_tab_first';
		
		
      });
	   function alternateCo(){
		 var altCo=   $("#alt_co_no").val();
		 var mobile = $("#mobile").val();
		 if(altCo == mobile){
			showMSG("Alternate number cannot be same as primary number") ;
			$("#alt_co_no").val("");
		 }
		
	   }
	   
	   function showMSG(msg){
		 
		  swal({   
				  title: msg,     
				  showCancelButton: false,
				  confirmButtonColor: "#2D3392",   
				  confirmButtonText: "OK",   
				  closeOnConfirm: true },
				  function(isConfirm){			  
					  return false; 
				}); 
	 }
	   $(function(){
    	    var dtToday = new Date();
    	    
    	    var month = dtToday.getMonth();
    	    var day = dtToday.getDate();
    	    var year = dtToday.getFullYear()-18;
    	    if(month < 10)
    	        month = '0' + month.toString();
    	    if(day < 10)
    	        day = '0' + day.toString();
    	    if(month ==0)
    	    month = '01';
    	    var maxDate = year + '-' + month + '-' + day;
    	    // or instead:
    	    // var maxDate = dtToday.toISOString().substr(0, 10);
    	    $('#date').attr('max', maxDate);
    	    
    	    
    	});
		 function save(next){
			 var old_mspin_text  =$("#old_mspin").val();
			  var old_mspin = $("#old_mspin_hiiden").val();
    	if($('#divspin').is(':visible') && old_mspin_text=="" ){
    		 $('#div_msg').text('');
    		 $('#div_msg').append('Please enter  MSPIN');
    		 $('#div_msg').show(); 
    	    return false;
    	}
     	
       $("#btn_next").val(next);
      }
	  
	
		
		 function getLicence() {
			var dl = $('#dl').val()	;
			if(dl == 'Yes'){
				 $(".license").show();
				 $(".license").find(':input').attr('required', true);
			}
			if(dl == 'No'){
				 $(".license").find(':input').attr('required', false);
				 $(".license").hide();  
                	 				 
			 }
		}
		
		
		
		
		
		function getPincodeByCity(){
			var city=$("#city").val();
			$.ajax({
		         url: 'dms/getPinByCity',
		         type: 'POST',
		         data: 'cityCode='+city,
		         success:function(res){
		        	 console.log(res);
		        	 $('#pincodeList')
		        	    .find('datalist')
		        	    .remove()
		        	    .end()
		        	    .append(res);
		    	  },
		          error:function(ress){
		    	  }
	  			}); 
		}
		
		    function getCity(){
			var state = $("#state").val();
			 var fd = new FormData();
			  fd.append('state',state);
			$.ajax({
		         url: 'dms/getCity',
		         enctype: 'multipart/form-data',
		         type:'post',
		         data: fd,
		           processData: false,
		           contentType: false,
		           cache: false,
		         success:function(res){
		        	 $('#city')
		        	    .find('option')
		        	    .remove()
		        	    .end()
		        	    .append(res);
		    	  },
		          error:function(ress){
					
		    	  }
	  			}); 
			 $("#city").append("<option value =''>Select</option>");
	         getTehshil();
		}

		function validityOfLicence(){
	    	  var today = new Date().toISOString().split('T')[0];
	    	  document.getElementsByName("validityOfLicence")[0].setAttribute('min', today);
	      }
		
		function getStateCity(){
			var pincode = $("#pincode").val();
			var stateCode= "";
			if(pincode==""){
				getState();
			}
			
			
			$.ajax({
				 async: false,
		         url: 'dms/getStateByPinCode',
		         type: 'POST',
		         data: 'pincode='+pincode,
		         success:function(res){
		        	 stateCode=res;
		        	 $('#state').val(res);
		    	  },
		          error:function(ress){
		    	  }
	  			}); 
			
			/* $.ajax({
				 async: false,
		         url: 'dms/getCity',
		         type: 'POST',
		         data: 'state='+stateCode,
		         success:function(res){
		        	  $('#city')
		        	    .find('option')
		        	    .remove()
		        	    .end()
		        	    .append(res); 
		    	  },
		          error:function(ress){
		    	  }
	  			});  */
			
			$.ajax({
				 async: false,
		         url: 'dms/getCityByPinCode',
		         type: 'POST',
		         data: 'pincode='+pincode,
		         success:function(res){
		        	 $('#city').val(res);
		    	  },
		          error:function(ress){
		    	  }
	  			}); 
		}
		
		$("#pincode").on('input', function () {
			getStateCity();
		});
		
		function savePesonal(next)
		{
			 $("#btn_next").val(next);
			document.forms[0].action="savePersonalDetails";
			document.forms[0].method="post";
			document.forms[0].submit();
		}
		
		function getTehshil(){
			var stateCode = $("#state").val();
			 var fd = new FormData();
			  fd.append('stateCode',stateCode);
			$.ajax({
		         url: 'dms/getTehShil',
		         enctype: 'multipart/form-data',
		         type:'post',
		         data: fd,
		           processData: false,
		           contentType: false,
		           cache: false,
		         success:function(res){
		        	 $('#tehshil')
		        	    .find('option')
		        	    .remove()
		        	    .end()
		        	    .append(res);
		    	  },
		          error:function(ress){
					alert(ress);
		    	  }
	  			}); 
                $("#tehshil").append("<option value =''>Select</option>");				
		}
		
		function getVillage(){
			var tehshilCode = $("#tehshil").val();
			 var fd = new FormData();
			  fd.append('tehshilCode',tehshilCode);
			$.ajax({
		         url: 'dms/getVillage',
		         enctype: 'multipart/form-data',
		         type:'post',
		         data: fd,
		           processData: false,
		           contentType: false,
		           cache: false,
		         success:function(res){
		        	 $('#village')
		        	    .find('option')
		        	    .remove()
		        	    .end()
		        	    .append(res);
		    	  },
		          error:function(ress){
					
		    	  }
	  			}); 	
		}
    </script>
		</div>
	</div>
</body>
</html>
<%}else{
	 response.sendRedirect("login");
}
    	
    	
     }catch(Exception e)
    {
    	 out.println("Errror....."+e);
    	 // response.sendRedirect("sessionExpire");
    }
   %>
