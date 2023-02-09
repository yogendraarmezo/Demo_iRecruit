<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.msil.irecruit.Entities.ParticipantRegistration"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String role="";
if(session.getAttribute("role") != null){
	role = session.getAttribute("role").toString().trim();
	 int count=0;
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
    <link rel="stylesheet" type="text/css" href="./css/work-experience.css" />
    <link rel="stylesheet" type="text/css" href="./css/style.css" />
	<link rel="stylesheet" type="text/css" href="./css/sweetalert.css"/>
    <script type="text/javascript" src="./js/sweetalert.min.js"></script>
    
  <!--  <style>
        .left-panel > ul > li:nth-child(2) > a, .left-panel > ul > li:nth-child(2) > ul > li:nth-child(2) > a{opacity: 1;}
        .left-panel > ul > li:nth-child(2) > ul > li:nth-child(2) > a::after{content: ''; position: absolute; right: 0; top: 50%; transform: translate(0, -50%); border-right: 6px solid #fff; border-top: 6px solid transparent; border-bottom: 6px solid transparent;}
        ul li{margin: 0px !important;}
    </style> -->

    <script src="./js/jquery-3.4.1.min.js"></script>
    <script src="./js/jquery.dataTables.min.js"></script>
    <script src="./js/jquery.scrolltabs.js"></script>
	  <style>
      .checkbox {
        color: #4D4D4D;
        margin-right: 40px;
        font-size: 13px;
        line-height: 18px;
        position: relative;
        top: -2px;
        margin-left: 7px;
         }
 .h5 {
  font-weight: 500;
  font-size: 13px;
  line-height: 18px;
  margin: 0 0 9px;
  position: relative;
  color: #777777;
}
      </style>
  </head>
  <body>
    <div class="left-panel-include">
    <%@include file="./header/left-panel.jsp"%>
    </div>
    <div class="user-panel-include">
	<%@include file="./header/user-panel.jsp"%>
	</div>

    <div class="right-section">
        <h1>Employment Details</h1>
        <div class="container-1100">
            <div class="profile-container">
                <%@include file="./header/profileMenu.jsp"%>

              <div class="profile-content">
			   <%if(role.equalsIgnoreCase("DL")) { %>
			  <div class="form-block checkbox">
                     <h5 class="h5">Add work details</h5>
                     
                      <input type="radio" name="experience" id="fresher" value = "fresher"><label for="fresher">Fresher</label>
                      <input type="radio" style="margin-left: 30px;" name="experience" id="experience" value = "experience"><label for="experience">Experience</label>
                    </div>
			   <%}%>
              <c:if test="${(workexperienceExp.documents_status != 'final' || workexperienceExp.fsdmApprovalStatus == '1') && workexperienceExp.status !='H'}">
               <%if(role.equalsIgnoreCase("DL")) { %>
                <form action="./saveWorkExperienceExp" class="form" method = "post" id = "testForm">
                  <div class="form-section">
                    <div class="form-block">
                   

                      <h5>Auto-Industry Experience</h5>
                     <!--   <input type="text" name ="autoIndustryExperience"  id="autoIndustryExperience"/> -->
                       <select id = "autoIndustryExperience" name="autoIndustryExperience" style="color: black !important">
                        <option  value = "">Select</option>
                        <option value="Yes">Yes</option>
                        <option value="No">No</option>
                      </select> 
                    </div>
                    <div class="form-block">
                      <h5>Company Name</h5>
                      <input type="text" placeholder="Company Name"   id="companyName" name="companyName"/>
                    </div>
                    <div class="form-block">
                      <h5>Experience In Months</h5>
                      <input type="text"  id="expInMths" placeholder="Experience In Months" maxlength="3"  name="expInMths" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
                    </div>

                    <div class="form-block">
                      <h5>Designation</h5>
                      <input type="text" placeholder="Designation"   id="previousDesignation" name="previousDesignation"/>
                    </div>
                    <div class="form-block">
                      <h5>Work Area</h5>
                      <input type="text"  placeholder="Work Area"  id="workArea" name="workArea"/>
                    </div>
                    <div class="form-block">
                      <div class="form-btn">
                         <input type = "hidden" name="accessKey"  value = "${workexperienceExp.accessKey}" />
                          <!--<a href="#" class="btn outline-btn" onclick="save()">Save</a> -->						 
						    <input class="btn blue-btn" type="submit" value="Save" id="submitnext" />                         
                      </div>
                    </div>
                  </div>
                </form>
                 <%} %>
                 </c:if>
                 <div class="other-exp">
                  <h2>Work Experience</h2>
                  <div class="other-exp-section">
                    <div class="other-exp-block">
                      <h4>Total Work Experience (in Months)</h4>
                      
                      <h5>${workexperienceExp.total}</h5>
                    </div>
                    <div class="other-exp-block">
                      <h4>Experience in Automobile Industry</h4>
                    
                      <h5>${workexperienceExp.autoIndustryExperience}</h5>
                    </div>
                    <div class="other-exp-block">
                      <h4>Experience in Non-Automobile Industry</h4>
                     
                      <h5>${workexperienceExp.nonAutoIndustryExperience}</h5>
                    </div>
					
					 <div class="other-exp-block">
                      <h4>MSIL Experience (in months) </h4>
                     
                      <h5>${workexperienceExp.msilExp}</h5>
                    </div>
                  </div>
                </div>
                
                <div class="table-date">
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" id="data">
                      <thead>
                          <tr>
                              <th><span><em>Sr. No.</em></span></th>
                              <th data-head="Auto-Industry Experience"><span><img src="./img/filter-icn.svg" /></span></th>
                              <th data-head="Company Name"><span><img src="./img/filter-icn.svg" /></span></th>
                              <th data-head="Experience"><span><img src="./img/filter-icn.svg" /></span></th>
                              <th data-head="Designation"><span><img src="./img/filter-icn.svg" /></span></th>
                              <th data-head="Work Area"><span><img src="./img/filter-icn.svg" /></span></th>
							   <c:if test="${(workexperienceExp.documents_status != 'final' || workexperienceExp.fsdmApprovalStatus == '1') && workexperienceExp.status !='H'}">
                               <%if(role.equalsIgnoreCase("DL")) { %>
                              <th></th>
                              <th></th>
                              <%} %>
							    </c:if>
                          </tr>
                      </thead>
                      <tbody>
                     
                      <c:forEach var="workExp" items="${participantWorkExp}" varStatus="status">
                          <tr>
                              <td>${status.index + 1}</td>
                              <td>${workExp.autoIndustryExperience}</td>
                              <td>${workExp.companyName}</td>
                              <td>${workExp.expInMths}</td>
                              <td>${workExp.previousDesignation}</td>
                              <td>${workExp.workArea}</td>
							   <%count++;%>
                           <c:if test="${(workexperienceExp.documents_status != 'final' || workexperienceExp.fsdmApprovalStatus == '1') && workexperienceExp.status !='H'}">
                             <%if(role.equalsIgnoreCase("DL")) { %>
                               <td><a onclick="editPop('${workExp.wid}','${workExp.autoIndustryExperience}','${workExp.companyName}','${workExp.expInMths}','${workExp.previousDesignation}','${workExp.workArea}')"><img src="./img/edit-icn.svg" /></a></td>
                              <td><a onclick="delelet('${workExp.wid}')"><img src="./img/delete-icn.svg" /></a></td>
                              <%} %>
	                      </c:if>
                          </tr>
                          </c:forEach>
                      </tbody>
                  </table>
                </div>

                <!-- <div class="other-exp">
                  <h2>Work Experience</h2>
                  <div class="other-exp-section">
                    <div class="other-exp-block">
                      <h4>Total Work Experience (in Months)</h4>
                      
                      <h5>${workexperienceExp.total}</h5>
                    </div>
                    <div class="other-exp-block">
                      <h4>Experience in Automobile Industry</h4>
                    
                      <h5>${workexperienceExp.autoIndustryExperience}</h5>
                    </div>
                    <div class="other-exp-block">
                      <h4>Experience in Non-Automobile Industry</h4>
                     
                      <h5>${workexperienceExp.nonAutoIndustryExperience}</h5>
                    </div>
					
					 <div class="other-exp-block">
                      <h4>MSIL Experience (in months) </h4>
                     
                      <h5>${workexperienceExp.msilExp}</h5>
                    </div>
                  </div>
                </div> -->
         <c:if test="${(workexperienceExp.documents_status != 'final' || workexperienceExp.fsdmApprovalStatus == '1') && workexperienceExp.status !='H'}">
           <%if(role.equalsIgnoreCase("DL")) { %>
                <div class="form-btn" style="margin: 60px 0px 0 0; position: relative; right: -10px;">
                <%if(count > 0){%>
                  <a href="#" class="btn blue-btn" onclick="openFamilyMemberDetails()">Next</a>
				  <%}else{%>
				    <a href="#" class="btn blue-btn" onclick="checkValidation()">Next</a>
				  <%}%>
              </div>
             <%} %>
        </c:if>
              </div>
    </div>
    </div></div>
    
    <div class="edit-popup">
      <h3>Work Experience</h3>
      <form class="form-section" action="">
          <div class="form-block">
            <h5>Auto-Industry Experience</h5>
            <select id="ed_autoIndustryExperience">
              <option value="">Select Auto-Industry</option>
              <option value="Yes">Yes</option>
              <option value="No">No</option>
            </select>
          </div>
          <div class="form-block">
            <h5>Company Name</h5>
              <input type="text" value=""  placeholder="Company Name"  id="ed_companyName"/>
          </div>
          <div class="form-block">
            <h5>Experience In Months</h5>
              <input type="text" value="" placeholder="Experience In Months" id="ed_expInMths" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"/>
          </div>
          <div class="form-block">
            <h5>Designation</h5>
              <input type="text" value="" placeholder="Designation" id="ed_previousDesignation"/>
          </div>
          <div class="form-block">
            <h5>Work Area</h5>
              <input type="text" value="" placeholder="Work Area"  id="ed_workArea"/>
          </div>
          <div class="form-button">
               <input type="button" class="cancel-btn" onclick="closePop(event)" value="Cancel"/>
               <input type="button" class="submit-btn" onclick="submitEntry()" value="Submit"/>
               <input type = "hidden"  id="ed_wid" value = ""/>
          </div>
      </form>
  </div>

    <div class="delete-popup">
      <p>Are you sure you want to delete?</p>
      <div class="form-button">
          <input type="button"  class="cancel-btn outline-btn" onclick="closePop(event)" value="No"/>
          <input type="button" class="submit-btn" onclick="deleteEntry()" value="Yes" /> 


      </div>
  </div>
  <div class="blk-bg"></div>
    <input type = "hidden"  id="accesskey" value = "${workexperienceExp.accessKey}"/>
	
	 <input type="hidden" id="temp_experience" value="${workexperienceExp.experience}">
     <input type="hidden" id="temp_fresher" value="${workexperienceExp.fresher}">
     <input type = "hidden"  id="wid" value = ""/>
    
    
  <script src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.min.js"></script>
  <script type="text/javascript" src="./js/workEmp.js"></script>
    
    <script>
      $(document).ready(function () {
    	
        $('#tabs').scrollTabs();
        var profile = document.getElementById("profile");
		var work_experience = document.getElementById("work_experience");
		
		profile.className -= 'tab-btn tab_selected scroll_tab_first';
        work_experience.className += 'tab-btn tab_selected scroll_tab_first';
		  $("#experience").prop('checked',true);
        var table = $('#data').DataTable({
                "orderCellsTop": true,
                "responsive": true,
                autoWidth: false
            });

            $('#data thead tr')
                .clone(true)
                .find('th')
                .removeClass('sorting_asc sorting_asc sorting')
                .off('click')
                .end()
                .appendTo('#data thead');

            $('#data thead tr:eq(1) th').each(function (i) {
                var title = $(this).data('head');
                if(title){
                    $(this).html('<input type="text" placeholder="' + title + '" />');
                } else{
                    $(this).html('');
                }

                $('input', this).on('keyup change', function () {
                    if (table.column(i).search() !== this.value) {
                        table
                            .column(i)
                            .search(this.value)
                            .draw();
                    }
                });
            });
			
			
		  var temp1 = $("#temp_experience").val();
		  var temp2 = $("#temp_fresher").val();
		if(temp2 == 'fresher'){ 
           $("#fresher:checked").val();
           $("#fresher").prop('checked',true);
		}
		if(temp1 =="experience"){
		  $("#experience:checked").val();
          $("#experience").prop('checked',true);	
		}
			
			
      });
      
      function editPop(id,autoIndustryExperience,companyName,expInMths,previousDesignation,workArea) {
    	  $("#ed_autoIndustryExperience").val(autoIndustryExperience);
    	  $("#ed_companyName").val(companyName);
    	  $("#ed_expInMths").val(expInMths);
    	  $("#ed_previousDesignation").val(previousDesignation);
    	  $("#ed_workArea").val(workArea);
    	  $("#ed_wid").val(id);
    	  
    	  $('.edit-popup, .blk-bg').show();
      }

      function submitEntry() {
    	var autoIndustryExperience =  $("#ed_autoIndustryExperience").val();
    	var companyName=  $("#ed_companyName").val();
    	var expInMths =  $("#ed_expInMths").val();
    	var previousDesignation =  $("#ed_previousDesignation").val();
    	var workArea =  $("#ed_workArea").val();
    	var id =  $("#ed_wid").val();
    	var accesskey= $("#accesskey").val();
    	 
    	 	$.ajax({
			  type: "POST",
			  url:  "updateWorkExperience",
			  data: "id="+id+"&workArea="+workArea+"&previousDesignation="+previousDesignation+"&expInMths="+expInMths+"&companyName="+companyName+"&autoIndustryExperience="+autoIndustryExperience+"&accesskey="+accesskey,
			  success: function(data){
				 swal({   
				  title: 'Record Updated Successfully',     
				  showCancelButton: false,
				  confirmButtonColor: "#2D3392",   
				  confirmButtonText: "OK",   
				  closeOnConfirm: true },
				  function(isConfirm){			  
					   window.location.reload();
				}); 
			  },
			  error: function(errorThrown){
				  alert("something Went Wrong");
			  }
		  }); 
	    $('.edit-popup, .blk-bg').hide();
	}
      
      
      function delelet(id){
    	  $("#wid").val(id);
    	  $('.delete-popup, .blk-bg').show();
       }

     /*  function deleteP(id) {
    	
    	var a =  $("#wid").val();
    	$('.delete-popup, .blk-bg').show();
      } */
      function deleteEntry(event) {
          $('.delete-popup, .blk-bg').hide();
      }
      function closePop(event) {
          $('.edit-popup, .delete-popup, .blk-bg').hide();
      }
      
      function save() {
      	
      	var accesskey = $("#accesskey").val();
      	var autoIndustryExperience  = $("#autoIndustryExperience").val();
      	var companyName = $("#companyName").val();
      	var expInMths = $("#expInMths").val();
      	var previousDesignation = $("#previousDesignation").val();
      	var workArea = $("#workArea").val();
    		$.ajax({
  	 			  type: "POST",
  	 			  url:  "saveWorkExperienceExp",
  	 			  data: "accessKey="+accesskey+"&autoIndustryExperience="+autoIndustryExperience+"&companyName="+companyName+"&expInMths="+expInMths+"&previousDesignation="+previousDesignation+"&workArea="+workArea,
  	 			  success: function(data){
  	 				 window.location.reload();
  	 			  },
  	 			  error: function(errorThrown){
  	 			  }
  	 		  });
    	    $('.delete-popup, .blk-bg').hide();
    	}
      
      function deleteEntry() {
    	  
  		var wid= $('#wid').val(); 
		var accesskey= $("#accesskey").val();
  		$.ajax({
	 			  type: "POST",
	 			  url:  "deleteWorkExperience",
	 			  data: "wid="+wid+"&accesskey="+accesskey,
	 			  success: function(data){
	 				 // alert(data);
	 				// window.location.reload();
					swal({   
				  title: 'Record Deleted Successfully',     
				  showCancelButton: false,
				  confirmButtonColor: "#2D3392",   
				  confirmButtonText: "OK",   
				  closeOnConfirm: true },
				  function(isConfirm){			  
					   window.location.reload();
				}); 	
	 			  },
	 			  error: function(errorThrown){
	 			  }
	 		  });
  	    $('.delete-popup, .blk-bg').hide();
  	}
	
	function checkValidation(){
	 swal({   
				  title: 'Please add Company Name, Experience In Months, Designation, Work Area',     
				  showCancelButton: false,
				  confirmButtonColor: "#2D3392",   
				  confirmButtonText: "OK",   
				  closeOnConfirm: true },
				  function(isConfirm){			  
					  return false; 
				}); 	
	}
	
	$("#fresher").change(function(){
          var accesskey= $("#accesskey").val();
      	 window.location.href="getWorkExperience?accesskey="+accesskey+"&param=param";
      	
      });
      </script>
    </body>
  </html>
    
  </body>
</html>

<%}else{
	 response.sendRedirect("login");
}%>
