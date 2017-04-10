package com.rundatop.sys.service;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.rundatop.core.service.IService;
import com.rundatop.sys.model.User;
public interface IUserService extends IService<User>{
	public List<User> selectUserByUser(User user, int page,int rows);
}
