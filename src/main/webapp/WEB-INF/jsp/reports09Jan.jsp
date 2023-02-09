<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
try{
      if(session.getAttribute("role") != null)
	  {

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
    <link rel="stylesheet" type="text/css" href="./css/hiring-in-process.css" />
	 <link rel="stylesheet" type="text/css" href="./css/sweetalert.css"/>
    <script type="text/javascript" src="./js/sweetalert.min.js"></script>
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.min.css">
    <link rel="stylesheet" type="text/css" href="./css/datatable.css">
   
    <link href='https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/ui-lightness/jquery-ui.css' rel='stylesheet'>
  <link rel="stylesheet" type="text/css" href="./css/page-filter.css" />
  <script src="./js/jquery-3.4.1.min.js"></script>
  <script src="./js/jquery.dataTables.min.js"></script>
  <script src="https://cdn.datatables.net/fixedcolumns/4.2.1/js/dataTables.fixedColumns.min.js"></script>
  <script src="./js/datatable.js"></script>
    
    <style>
      
	   .collapsible {background-color: #777;color: white;cursor: pointer;padding: 18px;width: 100%;border: none;text-align: left;outline: none;font-size: 15px;}
        .collapsible:after {content: '\002B';color: white;font-weight: bold;float: right;margin-left: 5px;}
        .active:after {content: "\2212";}
        .content {padding: 0 18px;max-height: 0;overflow: hidden;transition: max-height 0.2s ease-out;background-color: #f1f1f1;}
        .ui-datepicker {width: 12em;}
        h1{color:green;}
        .report-block-half p {font-size:15px;font-weight: 500; margin-left: 20px;}
        .report-section .report-block-v2 {width: calc(100% - 30px);border-radius: 10px;box-sizing: border-box;display: flex;justify-content: space-between;align-items: center;}
        .report-section{margin: 0 !important;}
        .report-block-half{width: calc(50% - 30px);margin: 10px 15px 15px;background-color: #fff;border-radius: 10px;display: flex;justify-content: space-between;align-items: center;}
        .report-section{display: flex; flex-wrap: wrap; margin: 0 -15px;}
        .report-section .report-block{width: calc(50% - 30px); margin: 0 15px 30px; background-color: #fff; border-radius: 10px; padding: 25px; box-sizing: border-box; display: flex; justify-content: space-between; align-items: center;}
        .report-section .report-block h3{margin: 0; font-weight: 500; font-size: 18px; line-height: 21px; color: #000;}
        .csv-icn-button{text-decoration: none; display: flex; align-items: center; color: #fff; font-size: 13px; line-height: 15px;margin-right: 20px; padding: 10px 15px; border-radius: 30px; background-color: #2D3393; text-transform: uppercase;}
        .csv-icn-button img{margin-right: 8px;}
        .collapsible {background-color:#fff; color: #000; cursor: pointer;padding: 18px;width: 100%;border: none;text-align: left;outline: none;font-size: 15px;border-radius: 10px;margin: 20px 0px 20px 0px;}
        .collapsible:after {content: '\002B';color:#000;font-weight: bold;margin-left: 5px;}
        /* .collapsible:after:hover {content: '\002B';color: white;font-weight: bold;margin-left: 5px;} */
        .collapsible:hover {background-color:#2D3392; color: #fff !important;}
        .active:after {content: "\2212";color: #fff;} 
        .content {width: 100%;padding: 0 18px;max-height: 0;overflow: hidden;transition: max-height 0.2s ease-out;background-color: #f1f1f1;display: flex;}
        .report-section.ir-report .collapsible.active ~ .content{max-height: unset !important;}
        button.collapsible {font-weight: 600;font-size: 17px;}
        .ir-report-tab-wrap{display:flex;align-items:center;flex-wrap: wrap;}
        .tablink {
            margin: 5px 5px 0px 0px;
            border-radius: 30px;
    border: 1px solid #2D3392;
  /* background-color: #fff; */
  color: #000;
  float: left;
  /* border: none; */
  outline: none;
  cursor: pointer;
  padding:14px 5px;
  font-size: 15px;
  width:calc(20% - 5px);
}

.tablink:hover {
  color: #fff;
  background-color: #2D3392;
}

/* Style the tab content (and add height:100% for full page content) */
.tabcontent {
  color: white;
  display: none;
  /* padding-top: 100px; */
  padding-bottom: 30px;
  height: 100%;
}
.export-to-csv{
    position: absolute;
    top: -50px !important;
    right: 0 !important;
    display: flex !important;
    justify-content: end !important;
    margin-bottom: 15px;
}
.table-date table tr, .table-date table tr td{
    color:#000;
}
.tabcontent h3{color:#000;}
.export-to-csv .ecsvbutton{
    background: #2D3393 !important;
    color: #fff !important;
}

@media (max-width: 767px) {.filter{margin-top: 50px;}
.page-filters-block{margin: 0 !important;}
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
        <div class="page-filter-include">

            <div class="page-filters">
                <div class="page-filters-block">
                    <div class="filter">
                        <!-- <h1>Reports</h1> -->
                        <!-- <div class="report-section"> -->
                            <!-- <div class="report-block">
                                <h3>Detailed Report</h3>
                                <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                            </div> -->
                          <!--   <div class="report-block">
                                <h3>Summery Report</h3>
                                <a href="#" class="csv-icn-button" onclick="exportTocsv('summeryReport')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                            </div> -->
                             <!-- <%
                    if(userId.equalsIgnoreCase("HO")){
                    %>
                            <div class="report-block">
                                <h3>Data science Report</h3>
                                <a href="#" class="csv-icn-button" onclick="exportTocsv('dataScienceCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                            </div>
                    <%}%>
                        </div>  -->

                    <%@include file="./filter/dashboardReportFilter.jsp"%>
                       <!-- <button class="page-filters-date">Date:
                            <span id="reportrange">1 November, 2022 - 30 November, 2022</span>
                        </button>   -->   
                    </div>
                </div>
            </div>
		</div> 
        <div class="ir-report-tab-wrap">
            <button class="tablink" onclick="openPage('overview', this, '#2D3392 '); getAnalyticsByAccesskeyList('${overviewSet}','overview')">Overview</button>
            <button class="tablink" onclick="openPage('action-points', this, '#2D3392 ')">Action Points</button>
            <button class="tablink" onclick="openPage('recruitment-source', this, '#2D3392 '); getAnalyticsByAccesskeyList('${sourceSet}','recruitment-source')">Recruitment Source</button>
            <button class="tablink" onclick="openPage('sales-non-sales', this, '#2D3392 '); getAnalyticsByAccesskeyList('${desgSet}','sales-non-sales')">Sales-Non-Sales</button>
            <button class="tablink" onclick="openPage('gender-diversity', this, '#2D3392 ')">Gender Diversity</button>
            <button class="tablink" onclick="openPage('candidate-experience', this, '#2D3392 '); getAnalyticsByAccesskeyList('${expAutoSet}','candidate-experience')">Candidate Experience</button>
            <button class="tablink" onclick="openPage('age-wise', this, '#2D3392 '); getAnalyticsByAccesskeyList('${ageSet}','age-wise')">Age Wise</button>
            <button class="tablink" onclick="openPage('assessment-report', this, '#2D3392 '); getAnalyticsByAccesskeyList('${assessmentSet}','assessment-report')">Assessment Report</button>
            <button class="tablink" onclick="openPage('question-wise-report', this, '#2D3392 ')">Question Analysis</button>
            <button class="tablink" onclick="openPage('competency-wise-analysis', this, '#2D3392 ')">Competency Analysis</button>
        </div>


<div id="overview" class="tabcontent">
  <h3>Overview</h3>
  <div class="table-date">
      <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
      <%-- <table id="data-ov" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
          <thead>
              <tr>
                  <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                  <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                  <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                  <th data-head="Profile" class="sorting"><em>Profile</em></th>

                  <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                  <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                  <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                  <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                  <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                  <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                  <th data-head="Candidate Status" class="sorting"><em>Candidate Status</em></th>
              </tr>
          </thead>
          <tbody>
          <%int i=1; %>
          <c:forEach items="${participantList}" var="participant">
              <tr>
                  <td>1</td>
                  <td>Vrushali Thakre</td>
                  <td>Vrushali Thakre</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>Test</td>
                  <td>Test</td>
              </tr>
            <%i++; %>
            </c:forEach>
          </tbody>
      </table> --%>
  </div> 
</div>

<div id="action-points" class="tabcontent">
  <h3>Action Points</h3>
  <div class="table-date">
      <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
      <table id="data-ap" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
          <thead>
              <tr>
                  <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                  <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                  <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                  <th data-head="Profile" class="sorting"><em>Profile</em></th>
                  <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                  <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                  <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                  <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                  <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                  <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                  <th data-head="Candidate Status" class="sorting"><em>Candidate Status</em></th>
              </tr>
          </thead>
          <tbody>
                <%int i=1; %>
                <c:forEach items="${actionAll}" var="participant">
                    <tr>
                        <td><%=i %></td>
                        <td>${participant.participantName }</td>
                        <td>${participant.designation}</td>
                        <td>${participant.mobile}</td>
                        <td>${participant.accesskey}</td>
                        <td>${participant.dateOfRegistration}</td>
                    <!--	<td>${participant.assessment_Completion_date}</td>        -->
                    <c:choose>
                        <c:when  test="${empty participant.assessment_Completion_date}">
                        <td><span >NA</span></td>
                        </c:when>
                        <c:when test="${not empty participant.assessment_Completion_date}">
                        <td>${participant.assessment_Completion_date}</td>
                        </c:when>
                        </c:choose>
                            <c:choose>
                            <c:when  test="${empty participant.assessment_Completion_date && empty participant.testScore }">
                        <td><span >NA</span></td>
                        </c:when>
                            <c:when test="${participant.passFailStatus == '1' }">
                            <td><span class="green">${participant.testScore} </span></td>
                                </c:when>
                            <c:when test="${participant.passFailStatus == '0' }">
                            <td><span class="red">${participant.testScore} </span></td>
                            </c:when>
                            </c:choose>
                        
                            <c:choose>
                            <c:when test="${participant.passFailStatus == '1' }">
                                <td><span class="green">Pass</span></td>
                            </c:when>
                            <c:when test="${not empty participant.assessment_Completion_date  && participant.passFailStatus == '0' }">
                                <td ><span class="red" >Fail</span> </td>
                            </c:when>
                            <c:when test="${empty participant.assessment_Completion_date && participant.passFailStatus == '0' }">
                                <td ><span >NA</span> </td>
                            </c:when>
                            </c:choose>	
                            <td>${participant.status}</td>												
                </tr>
                <%i++; %>
                </c:forEach>
            </tbody>
      </table>
  </div> 
</div>

<div id="recruitmen-source" class="tabcontent">
  <h3>Recruitmen Source</h3>
  <div class="table-date">
      <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
      <table id="data-rs" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
          <thead>
              <tr>
                  <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                  <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                  <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                  <th data-head="Profile" class="sorting"><em>Profile</em></th>

                  <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                  <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                  <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                  <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                  <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                  <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
              </tr>
          </thead>
          <tbody>
              <tr>
                  <td>1</td>
                  <td>Vrushali Thakre</td>
                  <td>Vrushali Thakre</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>Test</td>
              </tr>
          </tbody>
      </table>
  </div> 
</div>

<div id="sales-non-sales" class="tabcontent">
  <h3>Sales Non-Sales</h3>
  <div class="table-date">
      <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
      <table id="data-sns" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
          <thead>
              <tr>
                  <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                  <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                  <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                  <th data-head="Profile" class="sorting"><em>Profile</em></th>

                  <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                  <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                  <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                  <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                  <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                  <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
              </tr>
          </thead>
          <tbody>
              <tr>
                  <td>1</td>
                  <td>Vrushali Thakre</td>
                  <td>Vrushali Thakre</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>987654321</td>
                  <td>Test</td>
              </tr>
          </tbody>
      </table>
  </div> 
</div>

<div id="gender-diversity" class="tabcontent">
    <h3>Gender Diversity</h3>
    <div class="table-date">
        <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
        <table id="data-gd" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
            <thead>
                <tr>
                    <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                    <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                    <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                    <th data-head="Profile" class="sorting"><em>Profile</em></th>

                    <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                    <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                    <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                    <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                    <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                    <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>Vrushali Thakre</td>
                    <td>Vrushali Thakre</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>Test</td>
                </tr>
            </tbody>
        </table>
    </div> 
  </div>

  <div id="candidate-experience" class="tabcontent">
    <h3>Candidate Experience</h3>
    <div class="table-date">
        <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
        <table id="data-ce" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
            <thead>
                <tr>
                    <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                    <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                    <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                    <th data-head="Profile" class="sorting"><em>Profile</em></th>

                    <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                    <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                    <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                    <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                    <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                    <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>Vrushali Thakre</td>
                    <td>Vrushali Thakre</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>Test</td>
                </tr>
            </tbody>
        </table>
    </div> 
  </div>

  <div id="age-wise" class="tabcontent">
    <h3>Age Wise</h3>
    <div class="table-date">
        <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
        <table id="data-aw" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
            <thead>
                <tr>
                    <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                    <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                    <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                    <th data-head="Profile" class="sorting"><em>Profile</em></th>

                    <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                    <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                    <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                    <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                    <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                    <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>Vrushali Thakre</td>
                    <td>Vrushali Thakre</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>Test</td>
                </tr>
            </tbody>
        </table>
    </div> 
  </div>

  <div id="assessment-report" class="tabcontent">
    <h3>About</h3>
    <div class="table-date">
        <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
        <table id="data-ar" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
            <thead>
                <tr>
                    <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                    <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                    <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                    <th data-head="Profile" class="sorting"><em>Profile</em></th>

                    <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                    <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                    <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                    <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                    <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                    <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>Vrushali Thakre</td>
                    <td>Vrushali Thakre</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>Test</td>
                </tr>
            </tbody>
        </table>
    </div> 
  </div>
  <div id="question-wise-report" class="tabcontent">
    <h3>question Wise Analysis</h3>
    <div class="table-date">
        <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
        <table id="data-qwa" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
            <thead>
                <tr>
                    <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                    <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                    <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                    <th data-head="Profile" class="sorting"><em>Profile</em></th>

                    <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                    <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                    <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                    <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                    <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                    <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>Vrushali Thakre</td>
                    <td>Vrushali Thakre</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>Test</td>
                </tr>
            </tbody>
        </table>
    </div> 
</div>
<div id="final-report" class="tabcontent">
    <h3>Competency-Wise Analysis</h3>
    <div class="table-date">
        <div class="export-to-csv"><input type="button" class="ecsvbutton" value="Export To CSV"></div>
        <table id="data-etc" cellspacing="0" cellpadding="0" border="0" class="stripe display nowrap cell-border" width="50" style="width: 100% !important;height: auto !important;">
            <thead>
                <tr>
                    <th data-head="Sr.No." class="sorting" style="z-index: 1 !important;"><em>Sr.No.</em></th>
                    <th data-head="Candidate Name" class="sorting" style="z-index: 1 !important;"><em>Candidate Name</em></th>
                    <!-- <th data-head="Designation" class="sorting"><em>Designation</em></th> -->
                    <th data-head="Profile" class="sorting"><em>Profile</em></th>

                    <th data-head="Mobile Number" class="sorting"><em>Mobile Number</em></th>
                    <th data-head="Access Key" class="sorting"><em>Access Key</em></th>
                    <th data-head="Registration Date" class="sorting"><em>Registration Date</em></th>
                    <th data-head="Assessment Date" class="sorting"><em>Assessment Date</em></th> 
                    <th data-head="Marks Obtained" class="sorting"><em>Marks Obtained</em></th>				
                    <th data-head="Assessment Status" class="sorting"><em>Assessment Status</em></th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>Vrushali Thakre</td>
                    <td>Vrushali Thakre</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>987654321</td>
                    <td>Test</td>
                </tr>
            </tbody>
        </table>   <!-- TBL -->
    </div> 
  </div>


   
		<!-- <h1>Reports</h1> -->
            <!-- <div class="report-section"> -->
                <!-- <div class="report-block">
                    <h3>Detailed Report</h3>
                    <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                </div> -->
              <!--   <div class="report-block">
                    <h3>Summery Report</h3>
                    <a href="#" class="csv-icn-button" onclick="exportTocsv('summeryReport')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                </div> -->
				 <!-- <%
        if(userId.equalsIgnoreCase("HO")){
        %>
                <div class="report-block">
                    <h3>Data science Report</h3>
                    <a href="#" class="csv-icn-button" onclick="exportTocsv('dataScienceCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                </div>
		<%}%>
            </div>  -->

                <!-- <div class="report-section ir-report">
                <button class="collapsible">Reports</button>
                    <div class="content">
                <div class="report-block-v2">
                    <div class="report-block-half">
                        <p>Detailed Report</p>
                        <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                </div>
                    </div> 
                    
                    <div class="content">
                        <%
                        if(userId.equalsIgnoreCase("HO")){
                        %>
                        <div class="report-block-v2">
                            <div class="report-block-half">
                                <p>Data science Report</p>
                                <a href="#" class="csv-icn-button" onclick="exportTocsv('dataScienceCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                            </div>
                        </div>
                        <%}%>
                    </div> 
                </div>

                <div class="report-section ir-report">
                    <button class="collapsible">Overview</button>
                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Registered</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${overview.registered}','registered')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                        <div class="report-block-half">
                            <p>Assessments</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${overview.assessments}','assessment')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>    
                 </div>  


                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Passed</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${overview.pass}','passed')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                        <div class="report-block-half">
                            <p>Offered</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${overview.offer}','offred')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>
                 </div>

                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Recruited</p>
                            <a href="#" class="csv-icn-button"  onclick="getAnalyticsByAccesskeyList('${overview.recruited}','recruited')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>    
                  </div>
                </div>

                <div class="report-section ir-report">
                    <button class="collapsible">Action Points (Pending)</button>
                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Assessments</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${action.assessmentStatus}','pendingAssessment')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                        <div class="report-block-half">
                            <p>Interview</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${action.interviewStatus}','interviewStatus')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>    
                 </div>  


                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Documents</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${action.documentUploadStatus}','documentUploadStatus')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                        <div class="report-block-half">
                            <p>Praarambh</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${action.prarambhStatus}','prarambhStatus')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>
                 </div>

                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>FSDM Approval</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${action.fsdmApproval}','fsdmApproval')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>    
                  </div>
                </div>

                <div class="report-section ir-report">
                    <button class="collapsible">Recruitment Source</button>
                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Referrals</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${source.referrals}','referrals')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                        <div class="report-block-half">
                            <p>Direct Walk In</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${source.directWalkIn}','directWalkIn')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>    
                 </div>  
                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Advertisement</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${source.advertisement}','advertisement')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                        <div class="report-block-half">
                            <p>Job Consultant</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${source.jobConsultant}','jobConsultant')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>
                 </div>
                 <div class="content">
                    <div class="report-block-v2">
                        <div class="report-block-half">
                            <p>Social Media</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${source.socialMedia}','socialMedia')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                        <div class="report-block-half">
                            <p>Other</p>
                            <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${source.others}','others')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                        </div>
                    </div>    
                  </div>
                </div>
                
                <div class="report-section ir-report">
                <button class="collapsible">Sales-Non-Sales</button>
             <div class="content">
                <div class="report-block-v2">
                    <div class="report-block-half">
                        <p>Sales</p>
                        <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${designation.sales}','sales')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                    <div class="report-block-half">
                        <p>Non-Sales</p>
                        <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${designation.nonSales}','nonSales')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                </div>
             </div>  
                </div>

                <div class="report-section ir-report">
                <button class="collapsible">Gender Diversity</button>
             <div class="content">
                <div class="report-block-v2">
                    <div class="report-block-half">
                        <p>Male</p>
                        <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${gender.male}','pendingAssessment')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                    <div class="report-block-half">
                        <p>Female</p>
                        <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${gender.female}','pendingAssessment')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                </div>
             </div>  
                </div>

                 <div class="report-section ir-report">
                <button class="collapsible">Candidate Experience</button>
             <div class="content">
                <div class="report-block-v2">
                    <div class="report-block-half">
                        <p>Total Experience (Non Auto)</p>
                        <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${expAutoSet}','expAutoSet')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                    <div class="report-block-half">
                        <p>Total Experience (Non Industry)</p>
                        <a href="#" class="csv-icn-button" onclick="getAnalyticsByAccesskeyList('${expNonAutoSet}','expNonAutoSet')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                </div>
             </div>  
                </div>

                <div class="report-section ir-report">
                <button class="collapsible">Age Wise</button>
             <div class="content">
                <div class="report-block-v2">
                    <div class="report-block-half">
                        <p>Less than 20 Years</p>
                        <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                    <div class="report-block-half">
                        <p>Between 20 To 25 Years</p>
                        <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                </div>
             </div>  
             <div class="content">
                <div class="report-block-v2">
                    <div class="report-block-half">
                        <p>Between 30 To 35 Years</p>
                        <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                    <div class="report-block-half">
                        <p>Between 35 To 40 Years</p>
                        <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                </div>
             </div>  
         </div>

                <div class="report-section ir-report">
                <button class="collapsible">Assessment Report</button>
                    <div class="content">
                  <div class="report-block-v2">
                    <div class="report-block-half">
                        <p>Less than 40%</p>
                        <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                    <div class="report-block-half">
                        <p>Between 40% To 60% </p>
                        <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                    </div>
                </div>
                    </div>  
                    <div class="content">
                        <div class="report-block-v2">
                          <div class="report-block-half">
                              <p>Between 60% To 80%</p>
                              <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                          </div>
                          <div class="report-block-half">
                              <p>More than 80%</p>
                              <a href="#" class="csv-icn-button" onclick="exportTocsv('detailedCSV')"><img src="./img/csv-file-icn.svg" />Export to CSV</a>
                          </div>
                      </div>
                          </div>  
                </div> -->

         
                <script>
                    function openPage(pageName,elmnt,color) {
                        console.log(document.getElementById(pageName));
                        console.log(document.getElementsByClassName("tabcontent"));
                      var i, tabcontent, tablinks;
                      tabcontent = document.getElementsByClassName("tabcontent");
                      for (i = 0; i < tabcontent.length; i++) {
                        tabcontent[i].style.display = "none";
                      }
                      tablinks = document.getElementsByClassName("tablink");
                      for (i = 0; i < tablinks.length; i++) {
                        tablinks[i].style.backgroundColor = "";
                        tablinks[i].style.color = "";
                      }
                      document.getElementById(pageName).style.display = "block";
                      elmnt.style.backgroundColor = color;
                      elmnt.style.color = '#fff';
                    }
                    
                    // Get the element with id="defaultOpen" and click on it
                    document.getElementById("defaultOpen").click();
                    </script>
            

            
   
    <script>
        $(document).ready(function(){
            /* Date filter */
            var start = moment().startOf('month');
            var end = moment().endOf('month');

            function cb(start, end) {
                $('#reportrange').html(start.format('D MMMM, YYYY') + ' - ' + end.format('D MMMM, YYYY'));
            }

            $('#reportrange').daterangepicker({
                opens: 'left',
                format: 'DD/MM/YYYY',
                startDate: start,
                endDate: end,
                locale: {
                    format: 'DD/MM/YYYY',
                }
            }, cb);
            cb(start, end);
            /* Date filter */
        });
        function exportTocsv(reportName){
        	dateRange2=document.getElementById("reportrange").innerText;
        	if(dateRange2!='' && dateRange2.length>0){
        	url="./"+reportName+"?dateRange="+dateRange2;
        	}
        	else{
        	url="./"+reportName;
        	}
        	window.location.href=url;     	
        }

        console.log("Vrushali");
    </script>

<!-- <script>
    var coll = document.getElementsByClassName("collapsible");
    var i;
    
    for (i = 0; i < coll.length; i++) {
      coll[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.maxHeight){
          content.style.maxHeight = null;
        } else {
          content.style.maxHeight = content.scrollHeight + "px";
        } 
      });
    }
    </script> -->
    <script type="text/javascript">
    	function getAnalyticsByAccesskeyList(list,status){
    		var accesskeyList = JSON.stringify(list);
    		accesskeyList=accesskeyList.replace('[','');
    		accesskeyList=accesskeyList.replace(']','');
    		if(accesskeyList.length>5){	
    			mywindow=window.open("analyticsByAccesskey?accesskeyList="+accesskeyList+"&status="+status, "detailwindow","resizable=1,scrollbars=1,width=1170,height=600");
 	       		mywindow.moveTo(120,90);  
    		}    		
    	}
    	
    </script>
      <script type="text/javascript">
    	function getAnalyticsByAccesskeyList(list,id){
    		var status = "Overview";
    		var accesskeyList = JSON.stringify(list);
    		accesskeyList=accesskeyList.replace('[','');
    		accesskeyList=accesskeyList.replace(']','');
    		 $.ajax({
    			 type:"get",
    			 url:"analyticsByAccesskey",
    			 data:"accesskeyList="+accesskeyList+"&status="+status,
    			 success:function(response){
    				 $("#"+id).html(response);
    			 },
    			 error:function(resp){
    			 }
    		 });    		
    	}
    	
    </script>
  
    </body>  

</html>
<%     }else{
	       response.sendRedirect("login");
       }
	 }catch(Exception e)
    {
    	  response.sendRedirect("login");
    }
%>