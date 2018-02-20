package com.flash.service;

import java.util.List;

import com.flash.bean.Grafica;
import com.flash.bean.Prospecto;
import com.flash.bean.User;
import com.flash.entity.Usuario;

public interface UsuarioService {

	
	public List<Usuario> getUsuarios();
	
	public User getUsuarios(String cod);
	
	public Usuario saveProspecto (Prospecto prospecto);
	
	public Prospecto getUserLogin (String cod, String pass);
	
	public Grafica getFrafica (String cod);
	
	public Usuario obtenUsuarioCod (String cod);
	
	public User obtenerTodosUsuariosHijos(String cod);
	
}
