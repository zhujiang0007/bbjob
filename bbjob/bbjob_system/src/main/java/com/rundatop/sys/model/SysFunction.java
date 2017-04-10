package com.rundatop.sys.model;

import javax.persistence.*;

@Table(name = "sys_function")
public class SysFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 上级功能id，本表自递归，1级功能时本字段可留空
     */
    @Column(name = "p_id")
    private Integer pId;

    /**
     * 功能名称
     */
    private String name;

    /**
     * 功能类型：
1：模块名称，菜单上的分组，可以没有url
2：具体功能，必须指定属于哪个模块。
     */
    private String type;

    /**
     * 该模块的模块名+权限代码，如：用户模块的添加用户功能：(默认有XXX_VIEW权限)
     */
    @Column(name = "permission_code")
    private String permissionCode;

    /**
     * 该功能是否出现在菜单上
     */
    @Column(name = "is_menu")
    private String isMenu;

    /**
     * 该功能的打开方式：
1：tab打开
2：window
3：新页面
     */
    @Column(name = "open_type")
    private String openType;

    /**
     * 菜单的icon图标
保证icon_cls在icon.css存在，且图片存在。
     */
    @Column(name = "icon_cls")
    private String iconCls;

    /**
     * 如果以window方式打开，这里需要填写window的属性值，
     */
    @Column(name = "window_option")
    private String windowOption;

    /**
     * 如果是模块，此列可为空
     */
    private String url;

    /**
     * 该功能是否可,分配权限时是否可分配。该字段对超管无效。
     */
    @Column(name = "is_visible")
    private String isVisible;

    @Column(name = "sort_no")
    private Integer sortNo;

    private String remark;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取上级功能id，本表自递归，1级功能时本字段可留空
     *
     * @return p_id - 上级功能id，本表自递归，1级功能时本字段可留空
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * 设置上级功能id，本表自递归，1级功能时本字段可留空
     *
     * @param pId 上级功能id，本表自递归，1级功能时本字段可留空
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }

    /**
     * 获取功能名称
     *
     * @return name - 功能名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置功能名称
     *
     * @param name 功能名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取功能类型：
1：模块名称，菜单上的分组，可以没有url
2：具体功能，必须指定属于哪个模块。
     *
     * @return type - 功能类型：
1：模块名称，菜单上的分组，可以没有url
2：具体功能，必须指定属于哪个模块。
     */
    public String getType() {
        return type;
    }

    /**
     * 设置功能类型：
1：模块名称，菜单上的分组，可以没有url
2：具体功能，必须指定属于哪个模块。
     *
     * @param type 功能类型：
1：模块名称，菜单上的分组，可以没有url
2：具体功能，必须指定属于哪个模块。
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取该模块的模块名+权限代码，如：用户模块的添加用户功能：(默认有XXX_VIEW权限)
     *
     * @return permission_code - 该模块的模块名+权限代码，如：用户模块的添加用户功能：(默认有XXX_VIEW权限)
     */
    public String getPermissionCode() {
        return permissionCode;
    }

    /**
     * 设置该模块的模块名+权限代码，如：用户模块的添加用户功能：(默认有XXX_VIEW权限)
     *
     * @param permissionCode 该模块的模块名+权限代码，如：用户模块的添加用户功能：(默认有XXX_VIEW权限)
     */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    /**
     * 获取该功能是否出现在菜单上
     *
     * @return is_menu - 该功能是否出现在菜单上
     */
    public String getIsMenu() {
        return isMenu;
    }

    /**
     * 设置该功能是否出现在菜单上
     *
     * @param isMenu 该功能是否出现在菜单上
     */
    public void setIsMenu(String isMenu) {
        this.isMenu = isMenu;
    }

    /**
     * 获取该功能的打开方式：
1：tab打开
2：window
3：新页面
     *
     * @return open_type - 该功能的打开方式：
1：tab打开
2：window
3：新页面
     */
    public String getOpenType() {
        return openType;
    }

    /**
     * 设置该功能的打开方式：
1：tab打开
2：window
3：新页面
     *
     * @param openType 该功能的打开方式：
1：tab打开
2：window
3：新页面
     */
    public void setOpenType(String openType) {
        this.openType = openType;
    }

    /**
     * 获取菜单的icon图标
保证icon_cls在icon.css存在，且图片存在。
     *
     * @return icon_cls - 菜单的icon图标
保证icon_cls在icon.css存在，且图片存在。
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * 设置菜单的icon图标
保证icon_cls在icon.css存在，且图片存在。
     *
     * @param iconCls 菜单的icon图标
保证icon_cls在icon.css存在，且图片存在。
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * 获取如果以window方式打开，这里需要填写window的属性值，
     *
     * @return window_option - 如果以window方式打开，这里需要填写window的属性值，
     */
    public String getWindowOption() {
        return windowOption;
    }

    /**
     * 设置如果以window方式打开，这里需要填写window的属性值，
     *
     * @param windowOption 如果以window方式打开，这里需要填写window的属性值，
     */
    public void setWindowOption(String windowOption) {
        this.windowOption = windowOption;
    }

    /**
     * 获取如果是模块，此列可为空
     *
     * @return url - 如果是模块，此列可为空
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置如果是模块，此列可为空
     *
     * @param url 如果是模块，此列可为空
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取该功能是否可,分配权限时是否可分配。该字段对超管无效。
     *
     * @return is_visible - 该功能是否可,分配权限时是否可分配。该字段对超管无效。
     */
    public String getIsVisible() {
        return isVisible;
    }

    /**
     * 设置该功能是否可,分配权限时是否可分配。该字段对超管无效。
     *
     * @param isVisible 该功能是否可,分配权限时是否可分配。该字段对超管无效。
     */
    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }

    /**
     * @return sort_no
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * @param sortNo
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}