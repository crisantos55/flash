package com.flash.bean;

import java.util.List;

public class Grafica {

	
	public String name;
	public List<PersonGrafica> children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<PersonGrafica> getChildren() {
		return children;
	}
	public void setChildren(List<PersonGrafica> children) {
		this.children = children;
	}
	
	
}

