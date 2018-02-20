package com.flash.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flash.bean.PersonGrafica;
import com.flash.bean.Prospecto;
import com.flash.dao.UsuarioDao;
import com.flash.entity.Usuario;
import com.flash.util.Cifrar;

@Service
public class Transformer {

	
	public  Usuario transformer (Prospecto p, String folio){
		Usuario user = new Usuario ();
		user.setNombre(p.getNombre());
		user.setSeg_nombre(p.getNombre_s());
		user.setNombre_p(p.getNombre_p());
		user.setNombre_m(p.getNombre_m());
		user.setCorreo(p.getEmail());
		user.setPassword(p.getPassword());
		user.setProvincia(p.getProvincia());
		user.setPais(p.getPais());
		user.setTelefono(p.getTelefono());
		user.setFechaCreacion(new Date());
		user.setFechaNacimiento(p.getFechaNacimiento());
		user.setCod_distribuidor(folio);
		user.setGeneracion(1);
		user.setFk_cod_distribuido_user(p.getLiderCodigoV());
		return user;
	}
	
	public  Prospecto transformer(Usuario user) throws Exception{
		Prospecto p = new Prospecto();
		p.setEmail(user.getCorreo());
		p.setCodVer(user.getCod_distribuidor());
		p.setNombre(user.getNombre());
		p.setNombre_p(user.getNombre_p());
		p.setTok(Cifrar.cifrar(user.getCod_distribuidor()));
		p.setTokR(Cifrar.cifrar(user.getPassword()));
		p.setLiderCodigoV(user.getFk_cod_distribuido_user());
		return p;
	}
	
	public List<PersonGrafica> transformerList ( List<Usuario> listUser){
		List<PersonGrafica> lstGrafica = new ArrayList<PersonGrafica>();		
		for (Usuario u : listUser){
			PersonGrafica pg = new PersonGrafica ();
			pg.setName(u.getNombre()+"-"+u.getNombre_p()+"-"+u.getCod_distribuidor());
			pg.setCod(u.getNombre()+" "+u.getNombre_p());
			pg.setChildren(transformerList(getChildren(u.getCod_distribuidor())));
			lstGrafica.add(pg);
		}
		return lstGrafica;
	}
	
	
	public List<Usuario> obtenerTodoHijosEnUno ( List<Usuario> listUser , List<Usuario> lstGrafica, int generacion){	
		generacion= generacion+1;
		for (Usuario u : listUser){
			u.setGeneracion(generacion);
			lstGrafica.add(u);
			obtenerTodoHijosEnUno(getChildren(u.getCod_distribuidor()), lstGrafica, generacion);
		}
		return lstGrafica;
	}
	
	
	@Autowired
	UsuarioDao usuarioDao ;
	
	
	private  List<Usuario> getChildren(String cod){	
		return usuarioDao.obtieneUsuariosList(cod);
	}
	
	
}
