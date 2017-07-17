package com.rundatop.security.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityUtils {
	public static List<String> authorties2String(Collection<? extends GrantedAuthority> authorties){
		if(authorties==null||authorties.size()==0)
			return Collections.emptyList();
		List<String> authortiesString=new ArrayList<String>();
		for(GrantedAuthority authority:authorties){
				authortiesString.add(authority.getAuthority().split("_")[1]);
		}
		return authortiesString;
	}
}
