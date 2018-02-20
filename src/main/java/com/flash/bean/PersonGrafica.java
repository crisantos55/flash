package com.flash.bean;

import java.util.List;

public class PersonGrafica{
	
	public String name;
	public String cod;
	public List<PersonGrafica> children;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public List<PersonGrafica> getChildren() {
		return children;
	}
	public void setChildren(List<PersonGrafica> children) {
		this.children = children;
	}
}
