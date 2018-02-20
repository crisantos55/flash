package com.flash.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "usuario")
public class Usuario {
	
	
	private String nombre;
	
	private String seg_nombre;
	
	private String nombre_p;
	
	private String nombre_m;
	
	private String pais;
	
	private String provincia;
	
	@Id
	@Column(name = "cod_distribuidor")
	private String cod_distribuidor;
	
	private String correo;
	
	private String password;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;
	
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;
	
	
	@Column(name="fk_cod_distribuido")
	private String fk_cod_distribuido_user;	
	
	
	
	private String telefono;
	
	private int generacion;


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getSeg_nombre() {
		return seg_nombre;
	}

	public void setSeg_nombre(String seg_nombre) {
		this.seg_nombre = seg_nombre;
	}

	public String getNombre_p() {
		return nombre_p;
	}

	public void setNombre_p(String nombre_p) {
		this.nombre_p = nombre_p;
	}

	public String getNombre_m() {
		return nombre_m;
	}

	public void setNombre_m(String nombre_m) {
		this.nombre_m = nombre_m;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCod_distribuidor() {
		return cod_distribuidor;
	}

	public void setCod_distribuidor(String cod_distribuidor) {
		this.cod_distribuidor = cod_distribuidor;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	


	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	

	public String getFk_cod_distribuido_user() {
		return fk_cod_distribuido_user;
	}

	public void setFk_cod_distribuido_user(String fk_cod_distribuido_user) {
		this.fk_cod_distribuido_user = fk_cod_distribuido_user;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getGeneracion() {
		return generacion;
	}

	public void setGeneracion(int generacion) {
		this.generacion = generacion;
	}
	
	
	
	
}
