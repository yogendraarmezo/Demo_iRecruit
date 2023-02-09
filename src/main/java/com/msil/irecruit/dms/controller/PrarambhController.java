package com.msil.irecruit.dms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.msil.irecruit.dms.entities.PrarambhStatus;
import com.msil.irecruit.dms.entities.PrarambhUser;
import com.msil.irecruit.dms.payload.MessagePayload;
import com.msil.irecruit.dms.payload.PrarambhStatusPayloadRequest;
import com.msil.irecruit.dms.payload.PrarambhStatusResponse;
import com.msil.irecruit.dms.payload.PrarambhUserPayload;
import com.msil.irecruit.dms.payload.PrarambhUserRequest;
import com.msil.irecruit.dms.payload.PrarambhUserResponse;
import com.msil.irecruit.dms.service.PrarambhStatusService;
import com.msil.irecruit.dms.service.PrarambhUserService;
import com.msil.irecruit.dms.utils.PrarambhTokenUtil;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/prarambh")
public class PrarambhController {
	
	@Autowired
	private PrarambhUserService userService;	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PrarambhTokenUtil tokenUtil;
	@Autowired
	private PrarambhStatusService statusService;

	private  static String authKeyGenrated =null;
	private static Date keyDate=null;
	
	
	@PostMapping("/savePrarambhUser")
	@ApiOperation(value = "Save Prarambh Admin" ,hidden = true )
	public ResponseEntity<?> savePrarambhUser(@RequestBody PrarambhUserPayload payload){
		MessagePayload message= new MessagePayload();
		Optional<PrarambhUser> userOptional=userService.getByUsername(payload.getUsername());
		if(!payload.getName().equals(payload.getUsername())) {
		if(!userOptional.isPresent()) {
			PrarambhUser user = new PrarambhUser();
			user.setName(payload.getName());
			user.setUsername(payload.getUsername());
			user.setPassword(payload.getPassword());
			List<String> roles=new ArrayList<String>();
			roles.add(payload.getRole());
			user.setRoles(roles);
			userService.savePrarambhUser(user);
			message.setMessage("User Saved");
		}else {
			message.setMessage("User is already exist");
		}
		}else {
			message.setMessage("Username & Name are same, Try different username");
		}
		return ResponseEntity.ok(message);
	}
	
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid PrarambhUserRequest request) {
		ResponseEntity<?> responseEntity=null;
            try {
				Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );
            String authKey=tokenUtil.generateToken(request);
            authKeyGenrated=authKey;
            keyDate=new Date();
            responseEntity= new ResponseEntity<PrarambhUserResponse>(new PrarambhUserResponse(authKey), HttpStatus.ACCEPTED);
            }catch (Exception e) {
            	responseEntity=new ResponseEntity<MessagePayload>(new MessagePayload("Invalid Login Credentials"), HttpStatus.UNAUTHORIZED);
				e.printStackTrace();
			}
			return responseEntity;
	}
	
	
	@PostMapping("/savePrarambhStatus")
	private ResponseEntity<?> getPrarambhStatus(@RequestBody PrarambhStatusPayloadRequest pRequest){
		UserDetails userDetails = null;
		ResponseEntity<?> responseEntity=null;
		PrarambhStatusResponse response=new PrarambhStatusResponse();
		Optional<PrarambhUser> pUser= userService.getByUsername("armezo");   // here username will be static or which that is available in DB
		if(pUser.isPresent()) {
			userDetails = new User(pUser.get().getUsername(), pUser.get().getPassword(),
					pUser.get().getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toSet()));
		}else {
			throw new UsernameNotFoundException("Username Not Found");
		}
		//if(tokenUtil.validateToken(authKeyGenrated, userDetails)) {
		Date curDate = new Date();
		if(keyDate!=null ) {
		long duration = curDate.getTime()-keyDate.getTime();
		long minDiff = TimeUnit.MILLISECONDS.toMinutes(duration);
		if(authKeyGenrated.equals(pRequest.getAuthKey()) && minDiff<6) {
		Optional<PrarambhStatus> pstatus=statusService.getByMspin(pRequest.getMspin());
		if(pstatus.isPresent()) {
			response.setMspin(pRequest.getMspin());
			response.setMessage("MSPIN is already Exist");
			responseEntity=ResponseEntity.ok(response);
		}else {
			PrarambhStatus status = new PrarambhStatus();
			status.setMspin(pRequest.getMspin());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
			try {
				status.setCompletionDateTime(sdf.parse(pRequest.getCompletionDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			status.setPrarambhStatus(pRequest.getPrarambhStatus());
			statusService.savePrarambhStatus(status);
			response.setMspin(pRequest.getMspin());
			response.setMessage("MSPIN is Saved");
			responseEntity=ResponseEntity.ok(response);
		}
		}//validateToken
		else {
			responseEntity=new ResponseEntity<MessagePayload>(new MessagePayload("Unauthorized User"),HttpStatus.UNAUTHORIZED);
		}
		}else {
			responseEntity = new ResponseEntity<MessagePayload>(new MessagePayload("Login Required"),HttpStatus.UNAUTHORIZED);
		}
		return responseEntity;
	}
	
	

}
