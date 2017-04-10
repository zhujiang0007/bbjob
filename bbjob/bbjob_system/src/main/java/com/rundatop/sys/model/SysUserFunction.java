package com.rundatop.sys.model;

import javax.persistence.*;

@Table(name = "sys_user_function")
public class SysUserFunction {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "function_id")
    private Integer functionId;

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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