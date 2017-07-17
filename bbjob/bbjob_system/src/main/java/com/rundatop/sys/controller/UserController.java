package com.rundatop.sys.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.rundatop.sys.model.User;
import com.rundatop.sys.service.IUserService;

@RequestMapping("sys")
@RestController
public class UserController {
	@Resource
	private IUserService userService;
	@Resource
	private SaltSource saltSource;
	@RequestMapping(value="users",method=RequestMethod.GET)
	public PageInfo<User> selectUserList(User user,@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int rows){
		List<User> list=userService.selectUserByUser(user, page, rows);
		return new PageInfo<User>(list);
	}
	
	@RequestMapping(value="user",method=RequestMethod.GET)
	public User getUser( Integer userId){
		User user= userService.selectByKey(userId);
		return user;
	}
	@RequestMapping(value="user",method=RequestMethod.PATCH)
	public String editUser(@RequestBody User user){
		String pwd= new Md5PasswordEncoder().encodePassword(user.getPassword(),saltSource.toString());
		user.setPassword(pwd);
		userService.updateNotNull(user);
		return "修改成功！";
	}
	@RequestMapping(value="user",method=RequestMethod.PUT)
	public String saveUser(@RequestBody User user){
		user.setCreateTime(new Date());
		String pwd= new Md5PasswordEncoder().encodePassword(user.getPassword(),saltSource.toString());
		user.setPassword(pwd);
		userService.save(user);
		return "保存成功！";
	}
	@RequestMapping(value="user",method=RequestMethod.DELETE)
	public String deleteUser(Integer userId){
		userService.delete(userId);
		return "删除成功";
	}
	
}
