package com.msil.irecruit.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/images/*")
public class FileServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

		@Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws IOException
	    {
	        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
	       // File file = new File("C:/ParticipantUploadedFiles/", filename);
	        File file = new File("/home/msilazuser01/irecruit/", filename);
	        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
	        response.setHeader("Content-Length", String.valueOf(file.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
	        Files.copy(file.toPath(), response.getOutputStream());
	    }
	}

