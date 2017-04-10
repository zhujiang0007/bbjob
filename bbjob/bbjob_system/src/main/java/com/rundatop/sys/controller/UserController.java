package com.rundatop.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RequestMapping(value="users",method=RequestMethod.POST)
	public PageInfo<User> selectUserList(@RequestBody User user,@RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int rows){
		List<User> list=userService.selectUserByUser(user, page, rows);
		return new PageInfo<User>(list);
	}
	
	@RequestMapping(value="user/{userId}",method=RequestMethod.GET)
	public User getUser(@PathVariable("userId") Integer userId){
		User user= userService.selectByKey(userId);
		return user;
	}
	@RequestMapping(value="user",method=RequestMethod.PATCH)
	public String editUser(@RequestBody User user){
		userService.updateNotNull(user);
		return "修改成功！";
	}
	@RequestMapping(value="user",method=RequestMethod.PUT)
	public String saveUser(@RequestBody User user){
		userService.save(user);
		return "保存成功！";
	}
	@RequestMapping(value="user/{userId}",method=RequestMethod.DELETE)
	public String deleteUser(@PathVariable("userId") Integer userId){
		userService.delete(userId);
		return "删除成功";
	}
}
