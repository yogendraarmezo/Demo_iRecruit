
package com.msil.irecruit.dms.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.msil.irecruit.Entities.Dealer;
import com.msil.irecruit.Entities.InterviewScore;
import com.msil.irecruit.Entities.Outlet;
import com.msil.irecruit.Entities.ParticipantRegistration;
import com.msil.irecruit.Services.DealerService;
import com.msil.irecruit.Services.InterviewScoreService;
import com.msil.irecruit.Services.OutletService;
import com.msil.irecruit.ServicesImpl.ParticipantServiceImpl;
import com.msil.irecruit.dms.entities.ApiResponse;
import com.msil.irecruit.dms.entities.DepartmentDms;
import com.msil.irecruit.dms.entities.DesignationDms;
import com.msil.irecruit.dms.entities.ListDms;
import com.msil.irecruit.dms.entities.LocationDms;
import com.msil.irecruit.dms.entities.Manpower;
import com.msil.irecruit.dms.entities.ManpowerApproval;
import com.msil.irecruit.dms.entities.StateCityPinDms;
import com.msil.irecruit.dms.entities.TehsilVillage;
import com.msil.irecruit.dms.payload.ChangeDesignation;
import com.msil.irecruit.dms.payload.CityList;
import com.msil.irecruit.dms.payload.CityListCursor;
import com.msil.irecruit.dms.payload.DepartmentDetails;
import com.msil.irecruit.dms.payload.DepartmentDetailsCursorPayload;
import com.msil.irecruit.dms.payload.DesignationCursorPayload;
import com.msil.irecruit.dms.payload.DesignationList;
import com.msil.irecruit.dms.payload.ListDmsCursorPayload;
import com.msil.irecruit.dms.payload.ListDmsList;
import com.msil.irecruit.dms.payload.LocationList;
import com.msil.irecruit.dms.payload.LocationListCursorPayload;
import com.msil.irecruit.dms.payload.LoginKey;
import com.msil.irecruit.dms.payload.PmcAuthKey;
import com.msil.irecruit.dms.payload.PmcMspinAuth;
import com.msil.irecruit.dms.payload.TehsilVillageCursor;
import com.msil.irecruit.dms.payload.TehsilVillagePayload;
import com.msil.irecruit.dms.payload.TehsilVillageRequest;
import com.msil.irecruit.dms.service.DepartmentDmsService;
import com.msil.irecruit.dms.service.DesignationServiceDms;
import com.msil.irecruit.dms.service.ListDmsService;
import com.msil.irecruit.dms.service.LocationDmsService;
import com.msil.irecruit.dms.service.StateCityPinDmsService;
import com.msil.irecruit.dms.service.TehsilVillageService;
import com.msil.irecruit.dms.service.impl.ApiResponseService;
import com.msil.irecruit.utils.DataProccessor;


@Controller
@RequestMapping("/dms")
public class DmsController {
	
	
	 @Autowired
	    private DesignationServiceDms desgService;
	    @Autowired
	    private ListDmsService listService;
	    @Autowired
	    private LocationDmsService locationService;
	    @Autowired
	    private DepartmentDmsService departmentService;
	    @Autowired
	    private StateCityPinDmsService stateCityPinDmsService;
	    @Autowired
	    private TehsilVillageService tService;
	    @Autowired
	    ParticipantServiceImpl participantService;
	    @Autowired
	    OutletService outletService;
	    @Autowired
	    InterviewScoreService interviewScoreService;
	    private String authenticationKey;
	    @Autowired
	    ApiResponseService apiResponseService;
	    
	    @Autowired
	    private DealerService dealerService;
	    
	    public DmsController() {
	        this.authenticationKey = "";
	    }
	    
	    @PostMapping({ "/login" })
	    public ResponseEntity<String> loginToDms(@RequestBody final LoginKey loginKey) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/Login";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/Login", loginKey, String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        if (!jsonObject.has("Message")) {
	            final String authkey = jsonObject.getString("AuthKey");
	            this.authenticationKey = authkey;
	        }
	        // Saving api response log in db
	       // ApiResponse apiResponse = new ApiResponse();
			//apiResponse.setApiResponce(jsonObject.toString());
			//apiResponse.setApiDate(new Date());
			//apiResponse.setAccesskey("");
			//apiResponse.setApiName("LoginToDms");
			//apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
			//apiResponseService.save(apiResponse);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/saveCityList" })
	    public ResponseEntity<String> saveCityData(@RequestBody final PmcAuthKey pmcAuthKey) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/GetCityList";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/GetCityList", pmcAuthKey, String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        CityListCursor cityCursor = new CityListCursor();
	        if (!jsonObject.has("Message")) {
	            final ObjectMapper mapper = new ObjectMapper();
	            try {
	                Integer addedCount = 0;
	                Integer skippedCount = 0;
	                cityCursor = (CityListCursor)mapper.readValue(responseEntity.getBody(), CityListCursor.class);
	                for (final CityList cityList : cityCursor.getP_LIST_CURSOR()) {
	                    final Optional<StateCityPinDms> scpDB = (Optional<StateCityPinDms>)this.stateCityPinDmsService.getStateCityPinByPinCode(cityList.getPIN_CD());
	                    if (!scpDB.isPresent()) {
	                        final StateCityPinDms stateCityPinDms = new StateCityPinDms();
	                        stateCityPinDms.setStateCode(cityList.getSTATE_CD());
	                        stateCityPinDms.setStateDesc(cityList.getSTATE_DESC());
	                        stateCityPinDms.setCityCode(cityList.getCITY_CD());
	                        stateCityPinDms.setCityDesc(cityList.getCITY_DESC());
	                        stateCityPinDms.setPinCode(cityList.getPIN_CD());
	                        this.stateCityPinDmsService.saveStateCityPin(stateCityPinDms);
	                        ++addedCount;
	                    }
	                    else {
	                        ++skippedCount;
	                    }
	                }
	            }
	            catch (JsonParseException e) {
	                e.printStackTrace();
	            }
	            catch (JsonMappingException e2) {
	                e2.printStackTrace();
	            }
	            catch (IOException e3) {
	                e3.printStackTrace();
	            }
	        }
	     // Saving api response log in db
	        ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiResponce(jsonObject.toString());
			apiResponse.setApiDate(new Date());
			apiResponse.setAccesskey("");
			apiResponse.setApiName("SaveCityList");
			apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
			apiResponseService.save(apiResponse);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/saveDesignations" })
	    public ResponseEntity<String> saveDesignationFromDms(@RequestBody final PmcAuthKey pmcAuthKey) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/GetDesgDtls";
	        final String msg = "";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/GetDesgDtls", pmcAuthKey, String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        DesignationCursorPayload cursor = new DesignationCursorPayload();
	        if (!jsonObject.has("Message")) {
	            final ObjectMapper mapper = new ObjectMapper();
	            try {
	                cursor = (DesignationCursorPayload)mapper.readValue((String)responseEntity.getBody(), (Class)DesignationCursorPayload.class);
	                for (final DesignationList desgDms : cursor.getP_LIST_CURSOR()) {
	                    final Optional<DesignationDms> desgDb = (Optional<DesignationDms>)this.desgService.getDesignationByCode(desgDms.getDESG_CD());
	                    if (!desgDb.isPresent()) {
	                        final DesignationDms designationDms = new DesignationDms();
	                        designationDms.setDesignationCode(desgDms.getDESG_CD());
	                        designationDms.setDesignationDesc(desgDms.getDESG_DESC());
	                        this.desgService.saveDesignation(designationDms);
	                    }
	                    else {
	                    }
	                }
	            }
	            catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	     // Saving api response log in db
	        ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiResponce(jsonObject.toString());
			apiResponse.setApiDate(new Date());
			apiResponse.setAccesskey("");
			apiResponse.setApiName("SaveDesignation");
			apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
			apiResponseService.save(apiResponse);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/saveList" })
	    public ResponseEntity<String> saveListDetailsFromDms(@RequestBody final PmcMspinAuth pmcMspinAuth) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/GetListDtls";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = (ResponseEntity<String>)template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/GetListDtls", (Object)pmcMspinAuth, (Class)String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        ListDmsCursorPayload listCursor = new ListDmsCursorPayload();
	        if (!jsonObject.has("Message")) {
	            final ObjectMapper mapper = new ObjectMapper();
	            try {
	                Integer addedCount = 0;
	                Integer skippedCount = 0;
	                listCursor = (ListDmsCursorPayload)mapper.readValue((String)responseEntity.getBody(), (Class)ListDmsCursorPayload.class);
	                for (final ListDmsList listDms : listCursor.getP_LIST_CURSOR()) {
	                    final Optional<ListDms> listDb = (Optional<ListDms>)this.listService.getListByListCode(listDms.getLIST_CODE());
	                    if (!listDb.isPresent()) {
	                        final ListDms list = new ListDms();
	                        list.setListCode(listDms.getLIST_CODE());
	                        list.setListName(listDms.getLIST_NAME());
	                        list.setListDesc(listDms.getLIST_DESC());
	                        this.listService.saveList(list);
	                        ++addedCount;
	                    }
	                    else {
	                        ++skippedCount;
	                    }
	                }
	            }
	            catch (JsonParseException e) {
	                e.printStackTrace();
	            }
	            catch (JsonMappingException e2) {
	                e2.printStackTrace();
	            }
	            catch (IOException e3) {
	                e3.printStackTrace();
	            }
	        }
	        else {
	        }
	     // Saving api response log in db
	        ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiResponce(jsonObject.toString());
			apiResponse.setApiDate(new Date());
			apiResponse.setAccesskey("");
			apiResponse.setApiName("SaveList");
			apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
			apiResponseService.save(apiResponse);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/saveLocation" })
	    public ResponseEntity<String> saveLocation(@RequestBody final PmcMspinAuth pmcMspinAuth) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/GetLocationList";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = (ResponseEntity<String>)template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/GetLocationList", (Object)pmcMspinAuth, (Class)String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        LocationListCursorPayload locationCursor = new LocationListCursorPayload();
	        if (!jsonObject.has("Message")) {
	            final ObjectMapper mapper = new ObjectMapper();
	            try {
	                Integer addedCount = 0;
	                Integer skippedCount = 0;
	                locationCursor = (LocationListCursorPayload)mapper.readValue((String)responseEntity.getBody(), (Class)LocationListCursorPayload.class);
	                for (final LocationList locationDms : locationCursor.getP_LIST_CURSOR()) {
	                    final Optional<LocationDms> locationDb = (Optional<LocationDms>)this.locationService.getLocationByLocCode(locationDms.getLOC_CD());
	                    if (!locationDb.isPresent()) {
	                        final LocationDms location = new LocationDms();
	                        location.setDealerMapCode(locationDms.getDEALER_MAP_CODE());
	                        location.setLocationCode(locationDms.getLOC_CD());
	                        location.setParentGroup(locationDms.getPARENT_GROUP());
	                        location.setLocationDesc(locationDms.getLOC_DESC());
	                        this.locationService.saveLocation(location);
	                        ++addedCount;
	                    }
	                    else {
	                        ++skippedCount;
	                    }
	                }
	            }
	            catch (JsonParseException e) {
	                e.printStackTrace();
	            }
	            catch (JsonMappingException e2) {
	                e2.printStackTrace();
	            }
	            catch (IOException e3) {
	                e3.printStackTrace();
	            }
	        }
	     // Saving api response log in db
	        ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiResponce(jsonObject.toString());
			apiResponse.setApiDate(new Date());
			apiResponse.setAccesskey("");
			apiResponse.setApiName("SaveLocation");
			apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
			apiResponseService.save(apiResponse);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/saveDepartmentDetails" })
	    public ResponseEntity<String> saveDepartmentDetails(@RequestBody final PmcMspinAuth pmcMspinAuth) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/GetDeptDtls";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = (ResponseEntity<String>)template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/GetDeptDtls", (Object)pmcMspinAuth, (Class)String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        DepartmentDetailsCursorPayload departmentCursor = new DepartmentDetailsCursorPayload();
	        if (!jsonObject.has("Message")) {
	            final ObjectMapper mapper = new ObjectMapper();
	            try {
	                Integer addedCount = 0;
	                Integer skippedCount = 0;
	                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	                mapper.setVisibility((VisibilityChecker)VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
	                departmentCursor = (DepartmentDetailsCursorPayload)mapper.readValue((String)responseEntity.getBody(), (Class)DepartmentDetailsCursorPayload.class);
	                for (final DepartmentDetails deptDms : departmentCursor.getP_LIST_CURSOR()) {
	                    final Optional<DepartmentDms> deptDb = (Optional<DepartmentDms>)this.departmentService.findDepartmentByDpCode(deptDms.getDEPT_CD());
	                    if (!deptDb.isPresent()) {
	                        final DepartmentDms dept = new DepartmentDms();
	                        dept.setDealerMapCode(deptDms.getDEALER_MAP_CD());
	                        dept.setDepartmentCode(deptDms.getDEPT_CD());
	                        dept.setDepartmentDesc(deptDms.getDEPT_DESC());
	                        this.departmentService.saveDepartmentDetails(dept);
	                        ++addedCount;
	                    }
	                    else {
	                        ++skippedCount;
	                    }
	                }
	            }
	            catch (JsonParseException e) {
	                e.printStackTrace();
	            }
	            catch (JsonMappingException e2) {
	                e2.printStackTrace();
	            }
	            catch (IOException e3) {
	                e3.printStackTrace();
	            }
	        }
	     // Saving api response log in db
	        ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiResponce(jsonObject.toString());
			apiResponse.setApiDate(new Date());
			apiResponse.setAccesskey("");
			apiResponse.setApiName("SaveDepartmentDetails");
			apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
			apiResponseService.save(apiResponse);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/saveTehsilVillage" })
	    public ResponseEntity<?> saveTehsilVillageDetails(@RequestBody final TehsilVillageRequest tRequest) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/GetTehsilVillage";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = (ResponseEntity<String>)template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/GetTehsilVillage", (Object)tRequest, (Class)String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        TehsilVillageCursor cursor = new TehsilVillageCursor();
	        if (!jsonObject.has("Message")) {
	            final ObjectMapper mapper = new ObjectMapper();
	            try {
	                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	                mapper.setVisibility((VisibilityChecker)VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
	                cursor = (TehsilVillageCursor)mapper.readValue((String)responseEntity.getBody(), (Class)TehsilVillageCursor.class);
	                final List<String> villageCodeList = (List<String>)this.tService.getVillageCodeByStateCode(tRequest.getP_STATE_CD());
	                for (final TehsilVillagePayload payload : cursor.getP_LIST_CURSOR()) {
	                    if (!villageCodeList.contains(payload.getVILLAGE_CD())) {
	                        final TehsilVillage tehsilVillage = new TehsilVillage();
	                        tehsilVillage.setTehsilCode(payload.getTEHSIL_CD());
	                        tehsilVillage.setTehsilDesc(payload.getTEHSIL_DESC());
	                        tehsilVillage.setVillageCode(payload.getVILLAGE_CD());
	                        tehsilVillage.setVillageName(payload.getVILLAGE_NAME());
	                        tehsilVillage.setActiveYN(payload.getACTIVE_YN());
	                        tehsilVillage.setStateCode(tRequest.getP_STATE_CD());
	                        this.tService.saveTehsilVillage(tehsilVillage);
	                    }
	                    else {
	                        villageCodeList.remove(payload.getVILLAGE_CD());
	                    }
	                }
	            }
	            catch (JsonParseException e) {
	                e.printStackTrace();
	            }
	            catch (JsonMappingException e2) {
	                e2.printStackTrace();
	            }
	            catch (IOException e3) {
	                e3.printStackTrace();
	            }
	        }
	     // Saving api response log in db
	        ApiResponse apiResponse = new ApiResponse();
			apiResponse.setApiResponce(jsonObject.toString());
			apiResponse.setApiDate(new Date());
			apiResponse.setAccesskey("");
			apiResponse.setApiName("SaveTehsilVillage");
			apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
			apiResponseService.save(apiResponse);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/searchMspin" })
	    public ResponseEntity<String> searchMSPIN(@RequestBody final PmcMspinAuth pmcMspinAuth) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/SearchMSPIN";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = (ResponseEntity<String>)template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/SearchMSPIN", (Object)pmcMspinAuth, (Class)String.class, new Object[0]);
	        return responseEntity;
	    }
	    
	    @PostMapping({ "/getCity" })
	    @ResponseBody
	    public String getCity(@RequestParam final String state) {
	        String result = "";
	        final List<Object> cityList = (List<Object>)this.stateCityPinDmsService.getAllCity(state);
	        for (final Object city : cityList) {
	            final Object[] obj = (Object[])city;
	            final String name = (String)obj[0];
	            final String code = (String)obj[1];
	            result = String.valueOf(String.valueOf(result)) + " <option value =" + code + ">" + name + "</option>";
	        }
	        return result;
	    }
	    
	    @GetMapping({ "/getState" })
	    @ResponseBody
	    public String getState() {
	        String result = "";
	        final List<Object> cityList = (List<Object>)this.stateCityPinDmsService.getAllState();
	        result = String.valueOf(String.valueOf(result)) + " <option value =''>Select</option>";
	        for (final Object state : cityList) {
	            final Object[] obj = (Object[])state;
	            final String name = (String)obj[0];
	            final String code = (String)obj[1];
	            result = String.valueOf(String.valueOf(result)) + " <option value =" + code + ">" + name + "</option>";
	        }
	        return result;
	    }
	    
	    @PostMapping({ "/getCityByPinCode" })
	    @ResponseBody
	    public String getCityByPincode(@RequestParam final String pincode) {
	        String result = "";
	        final Optional<StateCityPinDms> city = (Optional<StateCityPinDms>)this.stateCityPinDmsService.getStateCityPinByPinCode(pincode);
	        if (city.isPresent()) {
	            result = city.get().getCityCode();
	        }
	        else {
	            result = "0";
	        }
	        return result;
	    }
	    
	    @PostMapping({ "/getStateByPinCode" })
	    @ResponseBody
	    public String getStateByPincode(@RequestParam final String pincode) {
	        final Optional<StateCityPinDms> city = (Optional<StateCityPinDms>)this.stateCityPinDmsService.getStateCityPinByPinCode(pincode);
	        return city.get().getStateCode();
	    }
	    
	    @PostMapping({ "/geAlltCity" })
	    @ResponseBody
	    public String getAllCity() {
	        String result = "";
	        final List<Object> cityList = (List<Object>)this.stateCityPinDmsService.getAllCity();
	        for (final Object city : cityList) {
	            final Object[] obj = (Object[])city;
	            final String name = (String)obj[0];
	            final String code = (String)obj[1];
	            result = String.valueOf(String.valueOf(result)) + " <option value =" + code + ">" + name + "</option>";
	        }
	        return result;
	    }
	    
	    @PostMapping({ "/getPinByCity" })
	    @ResponseBody
	    public String getPinByCity(@RequestParam final String cityCode) {
	        String result = "";
	        final List<String> pinList = (List<String>)this.stateCityPinDmsService.getPinByCity(cityCode);
	        for (final String pin : pinList) {
	            result = String.valueOf(String.valueOf(result)) + " <option value =" + pin + ">" + pin + "</option>";
	        }
	        return result;
	    }
	    
	    @PostMapping({ "/getTehShil" })
	    @ResponseBody
	    public String getTehSheel(final String stateCode) {
	        final String username = "Administrator";
	        final String password = "m1cr0s0f+";
	        final LoginKey loginKey = new LoginKey("Administrator", "m1cr0s0f+");
	        final List<String> stateList = (List<String>)this.tService.getVillageCodeByStateCode(stateCode);
	        if (stateList.size() <= 0) {
	            final ResponseEntity<String> responseEntity = this.loginToDms(loginKey);
	            final TehsilVillageRequest t = new TehsilVillageRequest();
	            final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	            if (!jsonObject.has("Message")) {
	                final String authKey = jsonObject.getString("AuthKey");
	                t.setAuthKey(authKey);
	                t.setP_PMC("1");
	                t.setP_STATE_CD(stateCode);
	                this.saveTehsil(t);
	            }
	         // Saving api response log in db
		        ApiResponse apiResponse = new ApiResponse();
				apiResponse.setApiResponce(jsonObject.toString());
				apiResponse.setApiDate(new Date());
				apiResponse.setAccesskey("");
				apiResponse.setApiName("GetTehsil");
				apiResponse.setApiMessage(jsonObject.getString("P_ERR_MSG").toString());
				apiResponseService.save(apiResponse);
	        }
	        final List<Object> tehshilList = (List<Object>)this.tService.findByStateCode(stateCode);
	        String result = "";
	        for (final Object t2 : tehshilList) {
	            final Object[] obj = (Object[])t2;
	            final String name = (String)obj[0];
	            final String code = (String)obj[1];
	            result = String.valueOf(String.valueOf(result)) + " <option value =" + code + ">" + name + "</option>";
	        }
	        
	        return result;
	    }
	    
	    @PostMapping({ "/getVillage" })
	    @ResponseBody
	    public String getVillage(final String tehshilCode) {
	        final List<Object> tehshilList = (List<Object>)this.tService.findByTehsilCode(tehshilCode);
	        String result = "";
	        for (final Object t : tehshilList) {
	            final Object[] obj = (Object[])t;
	            final String name = (String)obj[0];
	            final String code = (String)obj[1];
	            result = String.valueOf(String.valueOf(result)) + " <option value =" + code + " >" + name + "</option>";
	        }
	        return result;
	    }
	    
	    public String saveTehsil(final TehsilVillageRequest tRequest) {
	        final String url = "http://staging.dmssales.co.in:9003/iRecruit/GetTehsilVillage";
	        final RestTemplate template = new RestTemplate();
	        final ResponseEntity<String> responseEntity = (ResponseEntity<String>)template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/GetTehsilVillage", (Object)tRequest, (Class)String.class, new Object[0]);
	        final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	        TehsilVillageCursor cursor = new TehsilVillageCursor();
	        if (!jsonObject.has("Message")) {
	            final ObjectMapper mapper = new ObjectMapper();
	            try {
	                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	                mapper.setVisibility((VisibilityChecker)VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
	                cursor = (TehsilVillageCursor)mapper.readValue((String)responseEntity.getBody(), (Class)TehsilVillageCursor.class);
	                final List<String> villageCodeList = (List<String>)this.tService.getVillageCodeByStateCode(tRequest.getP_STATE_CD());
	                for (final TehsilVillagePayload payload : cursor.getP_LIST_CURSOR()) {
	                    if (!villageCodeList.contains(payload.getVILLAGE_CD())) {
	                        final TehsilVillage tehsilVillage = new TehsilVillage();
	                        tehsilVillage.setTehsilCode(payload.getTEHSIL_CD());
	                        tehsilVillage.setTehsilDesc(payload.getTEHSIL_DESC());
	                        tehsilVillage.setVillageCode(payload.getVILLAGE_CD());
	                        tehsilVillage.setVillageName(payload.getVILLAGE_NAME());
	                        tehsilVillage.setActiveYN(payload.getACTIVE_YN());
	                        tehsilVillage.setStateCode(tRequest.getP_STATE_CD());
	                        this.tService.saveTehsilVillage(tehsilVillage);
	                    }
	                    else {
	                        villageCodeList.remove(payload.getVILLAGE_CD());
	                    }
	                }
	            }
	            catch (JsonParseException e) {
	                e.printStackTrace();
	            }
	            catch (JsonMappingException e2) {
	                e2.printStackTrace();
	            }
	            catch (IOException e3) {
	                e3.printStackTrace();
	            }
	        }
	        return "";
	    }
	    
	    public String generateMSPIN(final String accesskey) {
	        final Optional<ParticipantRegistration> particpant = (Optional<ParticipantRegistration>)this.participantService.findByAccesskey(accesskey);
	        Optional<ApiResponse>apResponse = apiResponseService.getResponse(accesskey, "CreateManpower");
			if(apResponse.isPresent()) {
				if(apResponse.get().getCount() >=10) {
					///return"";
				}
			}
	        if (particpant.isPresent()) {
	            final Manpower manpower = new Manpower();
	            manpower.setP_PMC("1");
	            final Optional<Outlet> outlet = (Optional<Outlet>)this.outletService.getOutletByOutletCodeAndDealerId(particpant.get().getOutletCode(), particpant.get().getDealerId());
	            manpower.setP_PARENT_GROUP(outlet.get().getParentDealer().getParentDealerCode());
	            manpower.setP_DEALER_MAP_CD(outlet.get().getDealer().getDealerMapCode());
	            manpower.setP_LOC_CD(outlet.get().getLocation());
	            
	           // manpower.setP_PARENT_GROUP("COMPT");
	           // manpower.setP_DEALER_MAP_CD("10061");
	          //  manpower.setP_LOC_CD("CNP");
	            
	            manpower.setP_EMP_CD(particpant.get().getId().toString());
	            manpower.setP_EMP_MSIL_YN(DataProccessor.changeYesNo(particpant.get().getWorkedWithMSILBefore()));
	            manpower.setP_TITLE_CD(particpant.get().getTitle());
	            manpower.setP_FIRST_NAME(particpant.get().getFirstName());
	            manpower.setP_MIDDLE_NAME(DataProccessor.getStringValue(particpant.get().getMiddleName()));
	            manpower.setP_LAST_NAME(particpant.get().getLastName());
	            manpower.setP_NICK_NAME("");
	            manpower.setP_DIGITAL_YN("");
	            manpower.setP_EMP_CATEG("CAL");
	            manpower.setP_EMP_DESG_CD(particpant.get().getFinalDesignation());
	            manpower.setP_EMP_ADDRESS1(particpant.get().getAddress());
	            manpower.setP_EMP_ADDRESS2("");
	            manpower.setP_EMP_ADDRESS3("");
	            manpower.setP_STATE_CD(particpant.get().getState());
	            manpower.setP_EMP_CITY_CD(particpant.get().getCity());
	            manpower.setP_EMP_PIN(particpant.get().getPin().toString());
	            manpower.setP_EMP_SEX(particpant.get().getGender());
	            manpower.setP_EMP_DOB(DataProccessor.parseDateDDMMYY(particpant.get().getBirthDate()));
	            manpower.setP_SFDC_USER("");
	            manpower.setP_MOBILE(DataProccessor.getStringValue(particpant.get().getMobile()));
	            manpower.setP_PHONE_NO("");
	            manpower.setP_EMP_EMAIL_ID(DataProccessor.getStringValue(particpant.get().getEmail()));
	            manpower.setP_ID_PROOF(DataProccessor.getStringValue(particpant.get().getIdProof()));
	            manpower.setP_ID_PROOF_NO(DataProccessor.getStringValue(particpant.get().getAdharNumber().toString()));
	            manpower.setP_PRIM_LANG_CD(DataProccessor.getStringValue(particpant.get().getPrimaryLanguage()));
	            manpower.setP_TWO_WH_LIC_NUM("");
	            manpower.setP_FOUR_WH_LIC_NUM("");
	            manpower.setP_VALIDITY_DL(DataProccessor.parseDateDDMMYY(particpant.get().getValidityOfLicence()));
	            manpower.setP_INSURANCE_POLICY_NUMBER("");
	            manpower.setP_EMP_QUALIFICATION(DataProccessor.getStringValue(particpant.get().getHighestQualification()));
	            manpower.setP_PRIM_LANG_CD(DataProccessor.getStringValue(particpant.get().getPrimaryLanguage()));
	            manpower.setP_SEC_LANG_CD(DataProccessor.getStringValue(particpant.get().getSecondaryLanguage()));
	            manpower.setP_SUPERVISOR_MSPIN("");
	            manpower.setP_KIOSK_NOTIFICATIONS("");
	            

	            if(particpant.get().getAddress() != null) {
	           	        	   String[] results = particpant.get().getAddress().split("(?<=\\G.{" + 30 + "})"); 
	           	        	   if(results.length>2) {
	           	        		   System.out.println("1...."+results[0]);
	           	        		     manpower.setP_EMP_ADDRESS1(results[0]);
	           	        			 manpower.setP_EMP_ADDRESS2("");
	           	        			 manpower.setP_EMP_ADDRESS3("");
	           	       		 }else if(results.length>1) {
	           	       			System.out.println("2...."+results[0]);
	           	                	 manpower.setP_EMP_ADDRESS1(results[0]);
	           	        			 manpower.setP_EMP_ADDRESS2("");
	           	        			 manpower.setP_EMP_ADDRESS3("");
	           	       		 }else if(results.length>0) {
	           	       			System.out.println("3...."+results[0]);
	           	                	 manpower.setP_EMP_ADDRESS1(results[0]);
	           	        			 manpower.setP_EMP_ADDRESS2("");
	           	        			 manpower.setP_EMP_ADDRESS3("");
	           	                }
	           	          
	           	         }else {
	           	        	 System.out.println("4....");
	           	        	 manpower.setP_EMP_ADDRESS1("");
	               			 manpower.setP_EMP_ADDRESS2("");
	               			 manpower.setP_EMP_ADDRESS2("");	 
	           	         }
	            manpower.setP_EMP_ADL_DESG("");
	            manpower.setP_EMP_ADL_DESG1("");
	            manpower.setP_EMP_ADL_DESG2("");
	            manpower.setP_EMP_DEPT_CD(DataProccessor.getStringValue(particpant.get().getDepartmentCode()));
	            manpower.setP_INTERVIEW_DATE(DataProccessor.parseDate1(particpant.get().getInterviewDate()));
	            manpower.setP_EMP_JOINING_DATE(particpant.get().getJoiningDate());
	            manpower.setP_PROBATION_PERIOD("");
	            manpower.setP_PROBATION_EXT_BY("");
	            manpower.setP_CONFIRMATION_DATE("");
	            manpower.setP_EMP_SALARY(DataProccessor.getStringValue(particpant.get().getEmpSalary()));
	            manpower.setP_EMP_LEAVING_DATE("");
	            manpower.setP_EMP_LEAVING_REASON("");
	            manpower.setP_EMP_LEAVING_SUB_REASON("");
	            manpower.setP_REMARKS("");
	            manpower.setP_JOINING_INDUS("");
	            manpower.setP_JOIN_COMP("");
	            manpower.setP_WEBEX_EMAIL("");
	            manpower.setP_WEBEX_FLAG("");
	            manpower.setP_PF_NUMBER(DataProccessor.getStringValue(particpant.get().getPfNumber()));
	            manpower.setP_DISCOUNT_AUTH_YN("");
	            manpower.setP_EMP_SIGNATORY_YN("N");
	            manpower.setP_MUL_EMP_CODE("");
	            manpower.setP_BANK_ACC_NUMBER(DataProccessor.getStringValue(particpant.get().getBankAccountNumber()));
	            manpower.setP_ESI_NUMBER(DataProccessor.getStringValue(particpant.get().getEsiNumber()));
	            manpower.setP_TRAN_ATTD("");
	            manpower.setP_INST_APP_ACTIVE("");
	            manpower.setP_OWN_2W_YN(DataProccessor.changeYesNo(particpant.get().getOwnTwoWheeler()));
	            manpower.setP_KNOWS_DRIVING_YN(DataProccessor.changeYesNo(particpant.get().getKnowDriving()));
	            manpower.setP_MDS_CERTIFY_YN(DataProccessor.changeYesNo(particpant.get().getMdsCertified()));
	            manpower.setP_ASSIGN_HYPER_FLAG("");
	            manpower.setP_DS_REF_ID("");
	            manpower.setP_EMP_APPTITUDE_SCORE(DataProccessor.getStringValue(particpant.get().getAptitudeScore().toString()));
	            manpower.setP_EMP_ATTITUDE_SCORE(DataProccessor.getStringValue(particpant.get().getAttitudeScore().toString()));
	            final Optional<InterviewScore> interviewScore = (Optional<InterviewScore>)this.interviewScoreService.findByAccesskey(accesskey);
	            if (interviewScore.isPresent()) {
	            	Double value = Double.parseDouble(interviewScore.get().getTotal_avt());
	            	String score = Math.round(value)+"";
	            	//System.out.println("inter......."+score);
	                manpower.setP_EMP_INTERVIEW_SCORE(score);
	            }
	            else {
	                manpower.setP_EMP_INTERVIEW_SCORE("");
	            }
	            manpower.setP_EMP_PSYCHOMETRIC_SCORE(DataProccessor.getStringValue(particpant.get().getAttitudeScore().toString()));
	            String name = particpant.get().getFirstName();
	            if (particpant.get().getMiddleName() != null && !particpant.get().getMiddleName().equals("")) {
	                name = String.valueOf(String.valueOf(name)) + " " + particpant.get().getMiddleName();
	            }
	            name = String.valueOf(String.valueOf(name)) + " " + particpant.get().getLastName();
	            manpower.setP_EMP_NAME(name);
	            String married = "";
	            if (DataProccessor.getStringValue(particpant.get().getMarried()).equals("Married")) {
	                married = "Y";
	            }
	            else {
	                married = "N";
	            }
	            manpower.setP_EMP_MARITAL_YN(married);
	            manpower.setP_ANNIVERSARY_DATE(DataProccessor.parseDateDDMMYY(particpant.get().getAnniversaryDate()));
	           // System.out.println("bl...." + particpant.get().getBloodGroup());
	            manpower.setP_BLOOD_GROUP(DataProccessor.getStringValue(particpant.get().getBloodGroup()));
	            if (particpant.get().getFresher() != null && !particpant.get().getFresher().equals("")) {
	                manpower.setP_WORK_EXP("F");
	            }
	            else if (particpant.get().getExperience() != null && !particpant.get().getExperience().equals("")) {
	                manpower.setP_WORK_EXP("E");
	            }
	            else {
	                manpower.setP_WORK_EXP("");
	            }
	            manpower.setP_WORK_EXP_CURSOR("");
	            manpower.setP_RELATION_CURSOR("");
	            manpower.setP_SPECIAL_SKILL_CURSOR("");
	            Optional<Dealer> dealer = dealerService.getById(particpant.get().getDealerId());
	            if(dealer.isPresent()) {
	            manpower.setP_USER_ID(dealer.get().getMspin());
	            }else {
	            	 manpower.setP_USER_ID("");	
	            }
	            if(particpant.get().getOldMspin() != null && !particpant.get().getOldMspin().equals("")) {
	            	  manpower.setP_MSPIN_UPD_DET(particpant.get().getOldMspin());	
	            }else if(particpant.get().getMspin() != null && !particpant.get().getMspin().equals("")){
	            	 manpower.setP_MSPIN_UPD_DET(particpant.get().getMspin());	
	            	
	            }
	            else {
	            	manpower.setP_MSPIN_UPD_DET("");	
	            }
	            //System.out.println("Login ...............1");
	            final String url = "http://staging.dmssales.co.in:9003/iRecruit/CreateManpower";
	            String mspin = "";
	            String msg = "";
	            final String username = "Administrator";
	            final String password = "m1cr0s0f+";
	            final DmsController dmsController = new DmsController();
	            final LoginKey loginKey = new LoginKey("Administrator", "m1cr0s0f+");
	            final ResponseEntity<String> responseEntity = dmsController.loginToDms(loginKey);
	            //System.out.println("Login ...............2"+manpower);       
	           // System.out.println("Login ..............."+(String)responseEntity.getBody());
	            final JSONObject jsonObject = new JSONObject((String)responseEntity.getBody());
	            if (!jsonObject.has("Message")) {
	                final String authKey = jsonObject.getString("AuthKey");
	                final RestTemplate template = new RestTemplate();
	                manpower.setAuthKey(authKey);
	                final ResponseEntity<String> responseEntity2 = template.postForEntity("http://staging.dmssales.co.in:9003/iRecruit/CreateManpower", manpower, String.class, new Object[0]);
	                final JSONObject jsonObject2 = new JSONObject((String)responseEntity2.getBody());
	                System.out.println("ress..........." + responseEntity2.getBody());
	                ApiResponse apiResponse= null;
	    			if(apResponse.isPresent()) {
	    				apiResponse= apResponse.get();
	    			  int count = 	apiResponse.getCount();
	    			  count++;
	    			  apiResponse.setCount(count);
	    			}else {
	    			 apiResponse = new ApiResponse();
	    			 apiResponse.setCount(1);
	    			}
	    			apiResponse.setApiResponce(jsonObject2.toString());
	    			apiResponse.setAccesskey(accesskey);
	    			apiResponse.setApiName("CreateManpower");
	    			apiResponse.setApiMessage(jsonObject2.getString("P_ERR_MSG").toString());
	    			 ObjectMapper mapper = new ObjectMapper();
	 	            //Converting the Object to JSONString
	 	            String jsonString;
	 				try {
	 					jsonString = mapper.writeValueAsString(manpower);
	 					//apiResponse.setRequest(jsonString);
	 					 System.out.println("json....."+jsonString);
	 				} catch (JsonProcessingException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	    			apiResponseService.save(apiResponse);
	                
	                
	                //System.out.println("obt..........." + jsonObject2);
	                //System.out.println("man..........." + manpower);
	                mspin = jsonObject2.getString("P_MSPIN").toString();
	                msg = jsonObject2.getString("P_ERR_MSG").toString();
	                System.out.println("MSPIN......" + mspin + "....msg...." + msg + "..js.." + jsonObject2);
	                particpant.get().setMspin(mspin);
	                this.participantService.saveData((ParticipantRegistration)particpant.get());
	            }
	        }
	        return "Success";
	    }
	
	    

		public String approverFSDM(String  accesskey,String status,String DMSURL) {
			
			Optional<ParticipantRegistration> particpant = participantService.findByAccesskey(accesskey);
			if(particpant.isPresent()) {
				System.out.println("mspin..........."+particpant.get().getMspin());
				ManpowerApproval approvel = new ManpowerApproval();
				approvel.setP_PMC("1");
				//approvel.setP_USERID("503679");
				  Optional<Dealer> dealer = dealerService.getById(particpant.get().getDealerId());
		            if(dealer.isPresent()) {
		            	approvel.setP_USERID(dealer.get().getMspin());
		            }else {
		            	approvel.setP_USERID("");	
		            }
				approvel.setP_TYPE(status);
				approvel.setP_MSPIN(particpant.get().getMspin());
				approvel.setP_DELR_EMP_LIST("");
				approvel.setP_STATUS("A");
			String authKey = "";
			String username="Administrator", password="m1cr0s0f+";
			LoginKey loginKey = new LoginKey(username, password);
			authKey= Commonlogin(loginKey,DMSURL+"Login");
			String url=DMSURL+"SaveManpowerApproval";
			if(!authKey.equals("")) {	
				RestTemplate template = new RestTemplate();
				approvel.setAuthKey(authKey);		 
				ResponseEntity<String> responseEntity1 = template.postForEntity(url, approvel, String.class);
				JSONObject jsonObject1 = new JSONObject(responseEntity1.getBody());
				
				ApiResponse apiResponse = new ApiResponse();
				apiResponse.setApiResponce(jsonObject1.toString());
				apiResponse.setAccesskey(accesskey);
				apiResponse.setApiName("SaveManpowerApproval");
				apiResponse.setApiMessage(jsonObject1.getString("P_ERR_MSG").toString());
				apiResponseService.save(apiResponse);
				System.out.println("obt..........."+jsonObject1);
			}
			
		}
			return "Success";
		}
		
		public String changeDesignation(String  accesskey,String DMSURL) {
			Optional<ParticipantRegistration> particpant = participantService.findByAccesskey(accesskey);
			if(particpant.isPresent()) {
			ChangeDesignation changeDesignation = new ChangeDesignation();
			changeDesignation.setP_PMC("1");
			changeDesignation.setP_MSPIN(particpant.get().getMspin());
			changeDesignation.setP_NEW_DESG_CD(particpant.get().getFinalDesignation());
			String authKey = "";
			String username="Administrator", password="m1cr0s0f+";
			LoginKey loginKey = new LoginKey(username, password);
			authKey= Commonlogin(loginKey,DMSURL+"Login");
			String url=DMSURL+"UpdateDesignation";
			if(!authKey.equals("")) {	
				RestTemplate template = new RestTemplate();
				changeDesignation.setAuthKey(authKey);		 
				ResponseEntity<String> responseEntity1 = template.postForEntity(url, changeDesignation, String.class);
				JSONObject jsonObject1 = new JSONObject(responseEntity1.getBody());
				
				ApiResponse apiResponse = new ApiResponse();
				apiResponse.setApiResponce(jsonObject1.toString());
				apiResponse.setAccesskey(accesskey);
				apiResponse.setApiName("UpdateDesignation");
				apiResponse.setApiMessage(jsonObject1.getString("P_ERR_MSG").toString());
				apiResponseService.save(apiResponse);
				System.out.println("obt..........."+jsonObject1);
			}
			}
			return "success";
		}
		
		public String Commonlogin( LoginKey loginKey,String url) {
			
			//String url = "http://staging.dmssales.co.in:9003/iRecruit/Login";
			RestTemplate template = new RestTemplate();
			ResponseEntity<String> responseEntity=template.postForEntity(url, loginKey, String.class);
			System.out.println(loginKey.toString());
			JSONObject jsonObject = new JSONObject(responseEntity.getBody());
			if(!jsonObject.has("Message")) {
			String authkey= jsonObject.getString("AuthKey");
			authenticationKey=authkey;
			}
			System.out.println(authenticationKey);
			return authenticationKey;
		}
	
		
		
		
		
}
