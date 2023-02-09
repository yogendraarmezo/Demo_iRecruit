package com.msil.irecruit.dms.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.msil.irecruit.dms.utils.PrarambhTokenUtil;

@Component
public class PrarambhFilter extends OncePerRequestFilter {
	
	@Autowired
	private PrarambhTokenUtil tokenUtil;
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if(token!=null) {
			String username = tokenUtil.getUserName(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				//load current user from Database 
				UserDetails user = userDetailsService.loadUserByUsername(username);
				//Authentication Impl object
				UsernamePasswordAuthenticationToken authKeyToken = 
						new UsernamePasswordAuthenticationToken(
								user.getUsername(), 
								user.getPassword(),
								user.getAuthorities());
				authKeyToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authKeyToken);
			}
		}
		filterChain.doFilter(request, response);
		
		
	}

}
