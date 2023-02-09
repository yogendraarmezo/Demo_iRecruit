<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

  <script src="./js/moment.min.js"></script>
  <script src="/js/daterangepicker.js"></script>
  
  <c:set var="lastStart" value='${lastStart}'></c:set>
<c:set var="lastEnd" value='${lastEnd}'></c:set>
<c:set var="last3MStart" value='${last3MStart}'></c:set>
  
<style>
        .filters{display: flex;}
        .filters .filter{position: relative; font-size: 15px; line-height: 19px; font-weight: 500; color: #808080; padding: 12px 14px; background: #fff; border-radius: 5px; border: 1px solid #E8E8E8;}
        .filters .filter span{position: relative; display: inline-block; margin-left: 8px; padding-right: 25px; color: #259AEA; font-size: 15px; line-height: 19px; cursor: pointer;}
        .filters .filter span::after{content: ''; width: 10px; height: 6px; position: absolute; top: 50%; right: 0; transform: translate(0, -50%); background: url('../img/down-arrow.svg') no-repeat center top;}
        .filters .filter span.active::after{transform: translate(0, -50%) rotate(180deg);}
    </style>

<link rel="stylesheet" type="text/css" href="/includes/page-filter/page-filter.css" />
<link rel="stylesheet" type="text/css" href="/includes/page-filter/daterangepicker.css" />

<div class="page-filters">
    <div class="page-filters-block">
        <button class="page-filters-filter">Filter Page</button>
        <div class="page-filters-filter-block">
            <form action="employeeManagerFilterDealer" method="post" class="form-section" >
                 <div class="form-block">
                    <select name="outlet">
                    <option value="" >Outlets</option>
                    <c:forEach items="${outlets}" var="list">
                        <option value="${list.outletCode}">${list.outletName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-block">
                    <input type="text" name="candidateName" placeholder="Candidate Name" />
                </div>
                <div class="form-block">
                    <input type="text" name="uniqueCode"  placeholder="Cadidate unique code" />
                </div>
                <div class="form-block">
                     <select name="designation">
                     <option value="" >Designation</option>
                    <c:forEach items="${designations}" var="list">
                        <option value="${list.designationName}">${list.designationName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-block">
                     <select name="asmtStatus">
                     <option value="" >Assessment Status</option>
                     <option value="Started">Started</option>
                     <option value="Completed">Completed</option>
                     <option value="Not Started">Not Started</option>
                    <%-- <c:forEach items="${statusList}" var="list">
                        <option value="${list}">${list}</option>
                        </c:forEach> --%>
                    </select>
                </div>
                <div class="form-block">
                    <input type="text" name="mspin" placeholder="MSPIN" />
                </div>
                <div class="form-block">
                     <select name="testStatus">
                     <option value="" >Test Status</option>
                     <option value="Fail">Fail</option>
                     <option value="Pass">Pass</option>
                     <option value="Completed">Completed</option>
                    </select>
                    </div>
                  
                 	<div class="filters">
        <div class="filter">
            Duration: <span id="reportrange">Today</span>
        </div>
                </div>
                <div>
                <input type="hidden" id="dateRange" name="dateRange" value="" >
                </div>
                    
                <div class="form-button">
                    <button class="cancel-btn">Cancel</button>
                    <button type="submit" class="submit-btn" onclick="dateValue()">Submit</button>
                </div>
                
            </form>
        </div>
    </div>
     <div class="page-filters-block">
        <button class="page-filters-process">Completion Process</button>
        <div class="page-filters-process-block">
            <form action="completionProcess" method="post" class="form-section">
                <div class="form-block">
                   <input type="checkbox" name="assessment" id="assessment" value="assessment" /><label for="assessment">Assessment</label>
                </div>
                <div class="form-block">
                   <input type="checkbox" name="interview"  id="interview"  value="interview"/><label for="interview">Interview</label>
                </div>
                <div class="form-block">
                   <input type="checkbox" name="prarambh"   id="prarambh" value="prarambh" /><label for="prarambh">Prarambh</label>
                </div>
                <div class="form-block">
                   <input type="checkbox" name="fsdm"   id="fsdm" value="fsdm" /><label for="fsdm">FSDM</label>
                </div>
                <div class="form-button">
                    <button class="cancel-btn">Cancel</button>
                    <button type="submit" class="submit-btn">Submit</button>
                </div>
            </form>
        </div>
    </div>
</div>


<script>
    $(document).ready(function(){
        $('.page-filters-filter').on('click', function(){
            $(this).toggleClass('active');
            $('.page-filters-filter-block, .blk-bg').toggle();
        });
        $('.page-filters-process').on('click', function(){
            $(this).toggleClass('active');
            $('.page-filters-process-block, .blk-bg').toggle();
        });
        $('.cancel-btn').on('click', function(){
            $('.page-filters-filter, .page-filters-process').removeClass('active');
            $('.page-filters-filter-block, .page-filters-process-block, .blk-bg').hide();
            return false;
        });
  
    /* Date filter */
    var d = new Date();
    var start = moment().startOf('month');
    var end = moment().endOf('month');

    // Fiscal Year
    var currentYear = d.getFullYear();
    var nextYear = (d.getFullYear())+1;
    var fiscalStart = `01/04/${currentYear}`;
    var fiscalEnd = `31/03/${nextYear}`;

    // Last Month
    var lastMonth = d.getMonth();
    
    var totalDays;
    if(lastMonth == 1 || lastMonth == 3 ||  lastMonth == 5 ||  lastMonth == 7 ||  lastMonth == 8 ||  lastMonth == 10 ||  lastMonth == 12) {
        totalDays = 31;
    } else if(lastMonth == 2) {
        if(currentYear%4 == 0){
            totalDays = 29;
        } else {
            totalDays = 28;
        }
    } else {
        totalDays = 30;
    }

    var lastStart = `${lastStart}`;
    var lastEnd = `${lastEnd}`;

    // Last 3 Months
    var last3MStart = `${last3MStart}`;

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
        },
        ranges: {
            'Current Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [lastStart, lastEnd],
            'Last 3 Months': [last3MStart, lastEnd],
        }
    }, cb);
    cb(start, end);
    /* Date filter */
});
    </script>
    <script type="text/javascript">
    function dateValue(){
    	document.getElementById("dateRange").value = document.getElementById("reportrange").innerText;
    	console.log(document.getElementById("reportrange").inner);
    } 
    </script>