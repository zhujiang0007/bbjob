package com.rundatop.sys.dto;

public class SysRoleEntity {
   
    private Integer id;

    private Integer userId;
    
	private String roleName;

    private Integer enable;


    private String remark;

   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

   
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public Integer getEnable() {
        return enable;
    }

    
    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}