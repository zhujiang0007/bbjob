package com.rundatop.security.base.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class SecurityController {
	public Object getCurrentUser(){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		
		return authentication.getPrincipal();
	}
	public Object getCurrentUserName(){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	public Collection<? extends GrantedAuthority> getCurrentUserAuthorities(){
		Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities();
	}
}
