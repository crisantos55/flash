package com.flash.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "folio")
public class Folio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer id_folio;
	
	
	public String folio;

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Integer getId_folio() {
		return id_folio;
	}

	public void setId_folio(Integer id_folio) {
		this.id_folio = id_folio;
	}
	
	
}
