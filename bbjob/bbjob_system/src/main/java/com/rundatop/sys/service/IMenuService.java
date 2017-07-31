package com.rundatop.sys.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.rundatop.sys.model.SysFunction;

public interface IMenuService {
	List<SysFunction> selectFuncByUserAuthority(Integer pid, Collection<? extends GrantedAuthority> authorties);
}
