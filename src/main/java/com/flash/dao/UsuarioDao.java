package com.flash.dao;

import java.util.List;

import com.flash.entity.Folio;
import com.flash.entity.Usuario;

public interface UsuarioDao {

	
	public List<Usuario> obtieneUsuarios();
	
	
	public Usuario save (Usuario us);
	
	public Folio getNumFolio ();


	void updateFolio(Folio folio);


	public Usuario obtieneUsuarios(String cod);


	public List<Usuario> obtieneUsuariosList(String cod);
	
	public Usuario getUsuarioLogin (String cod, String password);
	
	
}
