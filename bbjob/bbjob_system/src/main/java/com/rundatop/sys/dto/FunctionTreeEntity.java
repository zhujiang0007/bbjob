package com.rundatop.sys.dto;

import java.util.ArrayList;

public class FunctionTreeEntity {
	private Integer id;
	private String name;
	private String isMenu;
	private String remkark;
	private ArrayList<FunctionTreeEntity> nodes=new ArrayList<FunctionTreeEntity>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIsMenu() {
		return isMenu;
	}
	public void setIsMenu(String isMenu) {
		this.isMenu = isMenu;
	}
	public String getRemkark() {
		return remkark;
	}
	public void setRemkark(String remkark) {
		this.remkark = remkark;
	}
	public ArrayList<FunctionTreeEntity> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<FunctionTreeEntity> nodes) {
		this.nodes = nodes;
	}
}
