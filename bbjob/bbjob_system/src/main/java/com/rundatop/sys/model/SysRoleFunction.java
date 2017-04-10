package com.rundatop.sys.model;

import javax.persistence.*;

@Table(name = "sys_role_function")
public class SysRoleFunction {
    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Id
    @Column(name = "function_id")
    private Integer functionId;

    /**
     * @return role_id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * @return function_id
     */
    public Integer getFunctionId() {
        return functionId;
    }

    /**
     * @param functionId
     */
    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }
}