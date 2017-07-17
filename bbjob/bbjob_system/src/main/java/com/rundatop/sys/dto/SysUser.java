package com.rundatop.sys.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class SysUser extends User implements UserDetails{
	private com.rundatop.sys.model.User user;
	public SysUser(com.rundatop.sys.model.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		this.user=user;
	}
	@Override
	public String getUsername() {
		return this.user.getUsername();
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return  this.user.getPassword();
	}
	public Integer getUserType(){
		return this.user.getType();
	}
	public Integer getUserId(){
		return this.user.getId();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

}
