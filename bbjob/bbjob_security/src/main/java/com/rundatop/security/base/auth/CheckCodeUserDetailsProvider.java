package com.rundatop.security.base.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.rundatop.security.base.exception.BadUserDetailsException;
import com.rundatop.security.base.exception.CheckCodeException;

public class CheckCodeUserDetailsProvider extends AbstractUserDetailsAuthenticationProvider{

    private UserDetailsService userDetailsService;
	private SaltSource saltSource;
	
     
	

	public SaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails user,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		System.out.println("additionalAuthenticationChecks");
		String password=authentication.getCredentials()==null?"":authentication.getCredentials().toString();
		 if(user==null||!validPassword(user,password)){
			 throw new BadUserDetailsException("用户名或密码不正确。");
		 }
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails user=userDetailsService.loadUserByUsername(username);
		if(user==null)
		throw new BadUserDetailsException("用户不存在!");
		return user;
	}

	private boolean validPassword(UserDetails user,String password){
		return new Md5PasswordEncoder().isPasswordValid(user.getPassword(),password, saltSource.getSalt(user));
	}
	public static void main(String args[]){
		new Md5PasswordEncoder().encodePassword("admin", "aarua");
	}
}
