package com.flash.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flash.bean.Grafica;
import com.flash.bean.Prospecto;
import com.flash.bean.User;
import com.flash.dao.UsuarioDao;
import com.flash.dto.Transformer;
import com.flash.entity.Folio;
import com.flash.entity.Usuario;
import com.flash.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UsuarioService{

	
	@Autowired
	UsuarioDao usuarioDao ;
	
	@Autowired
	Transformer transformer;
	
	@Override
	public List<Usuario> getUsuarios() {
		List<Usuario> u = usuarioDao.obtieneUsuarios();
		return u;
	}	
	
	@Override
	public Grafica getFrafica(String cod) {
		Grafica grafica = new Grafica();
		grafica.setName(cod);
		grafica.setChildren(transformer.transformerList(usuarioDao.obtieneUsuariosList(cod)));
		
		return grafica;
	}
	
	
	public User obtenerTodosUsuariosHijos(String cod){
		User user = new User();
		try{
			List<Usuario> lstPrimera = new ArrayList<>();
			lstPrimera.add(usuarioDao.obtieneUsuarios(cod));
			user.setUsuario(lstPrimera.get(0));
			List<Usuario> lstSegunda = new ArrayList<>();
			user.setLstUser(lstSegunda=transformer.obtenerTodoHijosEnUno(lstPrimera,lstSegunda, -1));
		}catch (Exception e) {
			
		}				
		return user;
	}

	
	@Override
	public Usuario saveProspecto (Prospecto prospecto){
		Usuario nuevo =  transformer.transformer(prospecto, obtenerCodigoDistribuidor());
		try{		
			if (prospecto.isNoCuentaCodigo()){
				return  usuarioDao.save(nuevo);		
			}else{
				if (usuarioDao.obtieneUsuarios(nuevo.getFk_cod_distribuido_user()) != null){
					return  usuarioDao.save(nuevo);			
				}else{
					return  null;			
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
			return null;	
		}
	}
	
	@Override
	public User getUsuarios(String cod) {
		User user = new User();
		Usuario usuario = usuarioDao.obtieneUsuarios(cod);
		user.setUsuario(usuario);
		user.setLstUser(usuarioDao.obtieneUsuariosList(cod));
		return user;
	}

	@Override
	public Prospecto getUserLogin(String cod, String pass) {
		Usuario usuario = usuarioDao.getUsuarioLogin(cod, pass);
		Prospecto pros = null;
		if (usuario != null){
			try {
				pros= transformer.transformer(usuario);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return pros;
	}
	
	

	
	private String obtenerCodigoDistribuidor(){
		Folio folio = usuarioDao.getNumFolio();
		double numDis = Double.valueOf(folio.getFolio());
		numDis++;
		String newFolio = String.format  ("%.0f",numDis);
		folio.setFolio(String.valueOf(newFolio));
		 usuarioDao.updateFolio(folio);		
		return newFolio;
	}	
	
	
	@Override
	public Usuario obtenUsuarioCod (String cod){
		return usuarioDao.obtieneUsuarios(cod);
	}
	
}
