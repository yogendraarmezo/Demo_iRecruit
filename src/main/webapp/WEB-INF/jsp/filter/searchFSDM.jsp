<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

  <script src="./js/moment.min.js"></script>
  <script src="./js/daterangepicker.js"></script>
  <script src="./js/filterPage.js"></script>
  
  
<c:set var="lastStart" value='${lastMStart}'></c:set>
<c:set var="lastEnd" value='${last3MEnd}'></c:set>
<c:set var="last3MStart" value='${last3MStart}'></c:set>
<c:set var="last3MEnd" value='${last3MEnd}'></c:set>
<c:set var="dateR" value='${payload.dateRange}'></c:set>
<c:set var="interview" value='${payload.interview}'></c:set>
<c:set var="praraambh" value='${payload.praraambh}'></c:set>
<c:set var="fsdmApproved" value='${payload.fsdmApproved}'></c:set>
<c:set var="dateFrom" value='${payload.dateFrom}'></c:set>
<c:set var="dateTo" value='${payload.dateTo}'></c:set>
  
<style>
        .filters{display: flex;}
        .filters .filter{position: relative; font-size: 15px; line-height: 19px; font-weight: 500; color: #808080; padding: 12px 14px; background: #fff; border-radius: 5px; border: 1px solid #E8E8E8;}
        .filters .filter span{position: relative; display: inline-block; margin-left: 8px; padding-right: 25px; color: #259AEA; font-size: 15px; line-height: 19px; cursor: pointer;}
        .filters .filter span::after{content: ''; width: 10px; height: 6px; position: absolute; top: 50%; right: 0; transform: translate(0, -50%); background: url('../img/down-arrow.svg') no-repeat center top;}
        .filters .filter span.active::after{ font-size: 45px;font-weight: 600;}
        .cross-btn {cursor: pointer;border-radius: 5px;margin-bottom: 10px;margin-left: 95%;top: 0;font-size: 20px;background-color: #fff !important;color: #2d3393 !important;border: 1px solid #2d3393 !important;}
        input#dateFromm {display: block !important;width: 100%;box-sizing: border-box !important; background-color: #F7F7F7 !important;border: 1px solid #D0D0D0 !important;border-radius: 7px!important;padding: 11px 15px!important;font-weight: 400;font-size: 13px!important;text-transform: uppercase!important;line-height: 18px!important;color: #333!important;display: block!important;}
        input#dateToo {display: block !important;width: 100%;box-sizing: border-box !important;background-color: #F7F7F7 !important;border: 1px solid #D0D0D0 !important;border-radius: 7px!important;padding: 11px 15px!important;font-weight: 400;font-size: 13px!important;text-transform: uppercase!important;line-height: 18px!important;color: #333!important;display: block!important;}
   </style>
<link rel="stylesheet" type="text/css" href="./css/daterangepicker.css" /> 
<link rel="stylesheet" type="text/css" href="./css/page-filter.css" /> 

<div class="page-filters">
    <div class="page-filters-block">
        <button class="page-filters-filter">Filter</button>
        <div class="page-filters-filter-block">
            <button class="cross-btn" aria-label="Dismiss alert" type="button" data-close>
                <span aria-hidden="true">&times;</span>
              </button>
            <form action="filterParticipantOnFSDM" method="post" class="form-section" id="formFilter" >
                 <div class="form-block">
                    <select name="outlet" id ="outlet">
                    <option value="" >Dealer Code</option>
                    <c:forEach items="${dealerCodeList}" var="list">
                        <option value="${list.outletCode}" <c:if test="${payload.outlet eq list.outletCode}"> selected</c:if>>${list.outletCode}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-block">
                    <input type="text" name="candidateName" id="candidateName" placeholder="Candidate Name" value="${payload.name}" />
                </div>
                <div class="form-block">
                    <input type="text" name="uniqueCode"  id="uniqueCode" placeholder="Access Key" value="${payload.uniqueCode}" />
                </div>
                <div class="form-block">
                     <select name="designation" id="desg">
                     <option value="" >Candidate Designation</option>
                    <c:forEach items="${designations}" var="list">
                        <option value="${list.designationCode}" <c:if test="${payload.designation eq list.designationCode}"> selected</c:if>>${list.designationName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-block">
                    <input type="text" name="mspin" id="mspinS" placeholder="MSPIN" value="${payload.mspin}"/>
                </div>
                <div class="form-block">
                     <select name="passFailStatus" id = "passFail">
                     <option value="" >Test Status</option>
                      <c:forEach items="${pass}" var="entry">
                        <option value="${entry.key}" <c:if test="${payload.passFailStatus eq entry.key}"> selected</c:if>>${entry.value}</option>
                        </c:forEach>
                         </select>
                    </div>
                      
                 	<div class="form-block">
                    From : <input type="date" id="dateFromm" name="dateFromm">
                    <span id="spanFrom"></span>
                </div>
                <div class="form-block">
                    To : <input type="date" id="dateToo" name="dateToo">
                    <span id="spanTo"></span>
                </div>
                <div class="form-button">
                    <!-- <button  class="cancel-btn">Cancel</button> -->
                    <input type="reset" id="reset" value="Reset" class="cancel-btn">
                    <button type="submit" class="submit-btn" >Submit</button>
                </div>
                
            </form>
        </div>
    </div>
    <!--######################################################################################  -->
    <div class="page-filters-block">
        <button class="page-filters-process">Status</button>
        <div class="page-filters-process-block">
        <!-- <span class="cross-btn">&times;</span> -->
        <button class="cross-btn" aria-label="Dismiss alert" type="button" data-close>
            <span aria-hidden="true">&times;</span>
          </button>

            <form action="statusFSDMInProcess" method="post" class="form-section">
                
                <div class="form-block">
                   <input type="checkbox" name="interview"  id="interview"  value="interview"/><label for="interview">Interview</label>
                </div>
                <div class="form-block">
                   <input type="checkbox" name="prarambh"   id="prarambh" value="prarambh" /><label for="prarambh">Prarambh</label>
                </div>
                <div class="form-block">
                   <input type="checkbox" name="fsdm"   id="fsdm" value="fsdm" /><label for="fsdm">FSDM Remark</label>
                </div>
                <div class="form-button">
                    <input type="reset" value="Reset" class="cancel-btn">
                    <button type="submit"  id="status" class="submit-btn">Submit</button>
                </div>
                
            </form>
        </div>
    </div>
</div>


    <script>
    $(document).ready(function(){
    	var dateRangePayload = `${dateR}`;
    	var dateFrom = `${dateFrom}`;
    	var dateTo = `${dateTo}`;
    	if(dateFrom.length >0){
    		$('#dateFromm').val(dateFrom);
    	}
    	if(dateFrom.length >0){
    		$('#dateToo').val(dateTo);
    	}
    	$('.page-filters-filter').on('click', function(){
            $(this).toggleClass('active');
            $('.page-filters-filter-block, .blk-bg').toggle();
        });
        $('.page-filters-process').on('click', function(){
            $(this).toggleClass('active');
            $('.page-filters-process-block, .blk-bg').toggle();
        });
        $('.cross-btn').on('click', function(){
            $('.page-filters-filter, .page-filters-process').removeClass('active');
            $('.page-filters-filter-block, .page-filters-process-block, .blk-bg').hide();
            return false;
        });

        // New JS
        $('.custom-select span').click(function(){
            $(this).addClass('active');
            $(this).parent().find('ul').show();
        });
        $(document).mouseup(function(e){
            var container = $(".custom-select ul");
            if (!container.is(e.target) && container.has(e.target).length === 0) 
            {
                container.hide();
                $('.custom-select span').removeClass('active');
            }
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
    console.log(lastMonth);
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

    console.log('last month date: '+'${totalDays}/${lastMonth}/${currentYear}');
    
    var lastStart=`${lastStart}`;
    var lastEnd=`${lastEnd}`;
    var last3MStart=`${last3MStart}`;
    var last3MEnd=`${last3MEnd}`;
	var selectDate="Select Date";
    function cb(start, end) {
    	console.log('end date :'+end);
        $('#reportrange').html(start.format('D MMMM, YYYY') + ' - ' + end.format('D MMMM, YYYY'));
        console.log('end..............:');
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
            'Last 3 Months': [last3MStart, last3MEnd],
        }
    }, cb);
    console.log(end);
    cb(start, end);
    /* Date filter */
    if(dateRangePayload.length==0){
        $('#reportrange').html('Select Date');}
        else{
        	$('#reportrange').html(dateRangePayload);
        }
});
    </script>
    <script type="text/javascript">
    function dateValue(){
    	document.getElementById("dateRange").value = document.getElementById("reportrange").innerText;
    	console.log(document.getElementById("reportrange").inner);
    } 
    </script>