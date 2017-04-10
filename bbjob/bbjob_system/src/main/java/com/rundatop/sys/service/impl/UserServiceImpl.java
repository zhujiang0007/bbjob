package com.rundatop.sys.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.rundatop.core.service.impl.BaseService;
import com.rundatop.sys.model.User;
import com.rundatop.sys.service.IUserService;

import tk.mybatis.mapper.entity.Example;
@Service
public class UserServiceImpl extends BaseService<User> implements IUserService{

	
	public List<User> selectUserByUser(User user, int page,int rows) {
		Example example=new Example(User.class);
		Example.Criteria criteria=example.createCriteria();
		if(user!=null&&StringUtils.isNotBlank(user.getUsername())){
			criteria.andEqualTo("username", user.getUsername());
		}
		if(user!=null&&StringUtils.isNotBlank(user.getPhone())){
			criteria.andEqualTo("phone", user.getPhone());
		}
		if(user!=null&&StringUtils.isNotBlank(user.getEmail())){
			criteria.andEqualTo("email", user.getEmail());
		}
		if(user!=null&&user.getEnable()!=null){
			criteria.andEqualTo("enable", user.getEnable());
		}
		
		PageHelper.startPage(page, rows);
		List<User> resultList=mapper.selectByExample(example);
		return resultList;
	}
}
