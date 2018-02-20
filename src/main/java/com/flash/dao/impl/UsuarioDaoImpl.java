package com.flash.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.flash.dao.AbstractDao;
import com.flash.dao.UsuarioDao;
import com.flash.entity.Folio;
import com.flash.entity.Usuario;


@Component("usuarioDAO")
public class UsuarioDaoImpl extends AbstractDao<Long, Usuario> implements UsuarioDao{

	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional ( rollbackFor  =  Exception.class )
	public List<Usuario> obtieneUsuarios() {
		Query query = getSession().createQuery("from Usuario");
		return query.list(); 
	}
	
	
	@Override
	@Transactional ( rollbackFor  =  Exception.class )
	public Usuario obtieneUsuarios(String cod) {
		Query query = getSession().createQuery("from Usuario where cod_distribuidor = :id");
		query.setParameter("id", cod);
		return (Usuario)query.uniqueResult(); 
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional ( rollbackFor  =  Exception.class )
	public List<Usuario> obtieneUsuariosList(String cod) {
		Query query = getSession().createQuery("from Usuario where fk_cod_distribuido_user= :id");
		query.setParameter("id", cod);
		return query.list(); 
	}

	@Override
	@Transactional ( rollbackFor  =  Exception.class )
	public Usuario save(Usuario us) {	
		try{			
			getSession().save(us);
			return obtieneUsuarios(us.getCod_distribuidor());
		}catch(Exception e){
			e.printStackTrace();
			return null; 
		}
	
	}

	@Override
	@Transactional ( rollbackFor  =  Exception.class )
	public Folio getNumFolio() {
		Query query = getSession().createQuery("from Folio");
		return (Folio) query.list().get(0);
	}

	@Override
	@Transactional ( rollbackFor  =  Exception.class )
	public void updateFolio(Folio folio) {
		getSession().saveOrUpdate(folio);
	}


	@Override
	@Transactional ( rollbackFor  =  Exception.class )
	public Usuario getUsuarioLogin(String cod, String password) {
		Query query = getSession().createQuery("from Usuario where cod_distribuidor = :cod and password = :password");
		query.setParameter("cod", cod);
		query.setParameter("password", password);
		return (Usuario) query.uniqueResult();
	}
	
	
}
