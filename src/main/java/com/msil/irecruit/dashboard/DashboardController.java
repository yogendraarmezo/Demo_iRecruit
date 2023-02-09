package com.msil.irecruit.dashboard;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DashboardController {
	
	@GetMapping("/deshboard")
	public String getNewCandidate() {
		
		return "deshboard";
	}

}
