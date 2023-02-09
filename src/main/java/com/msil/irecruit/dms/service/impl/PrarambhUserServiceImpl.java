package com.msil.irecruit.dms.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.msil.irecruit.dms.entities.PrarambhUser;
import com.msil.irecruit.dms.repository.PrarambhUserRepository;
import com.msil.irecruit.dms.service.PrarambhUserService;

@Service
public class PrarambhUserServiceImpl implements PrarambhUserService, UserDetailsService {
	
	@Autowired
	private PrarambhUserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	

	@Override
	public void savePrarambhUser(PrarambhUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}

	@Override
	public Optional<PrarambhUser> getByUsernameAndPassword(String username, String password) {
		return userRepository.findByUsernameAndPassword(username,password);
	}

	@Override
	public Optional<PrarambhUser> getByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<PrarambhUser> getByPassword(String password) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<PrarambhUser> user =getByUsername(username);
		UserDetails userDetails=null;
		if(user.isPresent()) {
			userDetails = new User(user.get().getUsername(), user.get().getPassword(),
					user.get().getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role))
					.collect(Collectors.toSet()));
		}else {
			throw new UsernameNotFoundException("Username Not Found");
		}
		return userDetails;
	}

}
