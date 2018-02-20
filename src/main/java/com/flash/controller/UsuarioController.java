package com.flash.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.flash.bean.Grafica;
import com.flash.bean.Login;
import com.flash.bean.Prospecto;
import com.flash.bean.User;
import com.flash.entity.Usuario;
import com.flash.service.UsuarioService;
import com.flash.service.impl.EnvioEmailService;
import com.flash.util.Cifrar;


@Controller
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	EnvioEmailService email;
	
	String cuentaPrincipal= "estamosunidoslatam@gmail.com";

	
	//@CrossOrigin(origins = "http://localhost:9000")
	@RequestMapping(value="/obtenerGrafica", method= RequestMethod.GET)
	public @ResponseBody Grafica obtenerGrafica (HttpServletRequest req, HttpServletResponse resp) {
		String cod = req.getParameter("cod");
			return usuarioService.getFrafica(cod);
	}
	
	
	@RequestMapping(value="/saludopruebas", method= RequestMethod.GET)
	public @ResponseBody String prueba (HttpServletRequest req, HttpServletResponse resp) {
		String angel = "hello world";
		return "{\"data\":"+1+" , \"cod\":"+angel+"}";
	}
	
	//@CrossOrigin(origins = "http://localhost:9000")
	@RequestMapping(value="/get", method= RequestMethod.GET)
	public @ResponseBody User obtenerInfomacion(HttpServletRequest req, HttpServletResponse resp) {
		if (validCredenciales(req.getHeader("token"),req.getHeader("tokenR"))){
			resp.setStatus(400);
			return null;
		}else{
			//return usuarioService.getUsuarios(req.getParameter("cod"));
			return usuarioService.obtenerTodosUsuariosHijos(req.getParameter("cod"));
		}
		
	}
	
	
//	@CrossOrigin(origins = "http://localhost:9000")
	@RequestMapping(value="/save",   produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.POST)
	public @ResponseBody String saveProspecto (Prospecto prospecto, HttpServletRequest req, HttpServletResponse resp){	
		Usuario t = usuarioService.saveProspecto(prospecto);
		if(prospecto.noCuentaCodigo){
			  String asunto = "ALERTA SIN PATROCINADOR";
			  String cuerpo2 = "SE HA AFILIADO UN NUEVO SOCIO A ESTAMOS UNIDOS LATINOAMERICA SIN PATROCINADOR, CON NOMBRE  "+t.getNombre()+" "+t.getNombre_p()+" Y SU CODIGO DE DISTRIBUIDOR ES "+t.getCod_distribuidor()+" SU TELEFONO "+t.getTelefono()+" CON CORREO "+t.getCorreo();
			  email.enviarConGMail(cuentaPrincipal, asunto, cuerpo2);
			return "{\"data\":"+1+" , \"cod\":"+t.getCod_distribuidor()+"}";
		}else{		
		if (t != null){
				 String destinatario =  t.getCorreo(); //A quien le quieres escribir.
		    	    String asunto = "Flash Mobile";
		    	    String cuerpo = "BIENVENIDO.YA ERES PARTE DE EQUIPO ESTAMOS UNIDOS LATINOAMERICA.TU CODIGO DE DISTRIBUIDOR ES EL SIGUIENTE: %1 TU CONTRASEÑA ES LA SIGUIENTE: %2";
		    	    cuerpo=cuerpo.replaceAll("%1", t.getCod_distribuidor());
		    	    cuerpo = cuerpo.replaceAll("%2", t.getPassword());
		    	    email.enviarConGMail(destinatario, asunto, cuerpo);
		    	    Usuario patrocinado = usuarioService.obtenUsuarioCod(t.getFk_cod_distribuido_user());
		    	    String cuerpo2 = "SE HA AFILIADO UN NUEVO SOCIO A ESTAMOS UNIDOS LATINOAMERICA, CON NOMBRE  "+t.getNombre()+" "+t.getNombre_p()+" Y SU CODIGO DE DISTRIBUIDOR ES "+t.getCod_distribuidor()+" PATRACINADO POR "+patrocinado.getNombre()+" "+patrocinado.getNombre_p();
		    	    email.enviarConGMail(cuentaPrincipal, asunto, cuerpo2);
		    		return "{\"data\":"+1+" , \"cod\":"+t.getCod_distribuidor()+"}";
			}else{
				return "{\"data\":"+0+" , \"cod\":"+0+"}";
			}	
		}
	}
	
	
	
//	@CrossOrigin(origins = "http://localhost:9000")
	@RequestMapping(value="/loginUser",   produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.POST)
	public @ResponseBody Prospecto loginUser (Login login, HttpServletRequest request, HttpServletResponse resp){
		Prospecto p = usuarioService.getUserLogin(login.getCod(), login.getPassword());
		if (p == null){			
			resp.setStatus(400);
		}
		return p;
	}
	
//	@CrossOrigin(origins = "http://localhost:9000")
	@RequestMapping(value="/recuperaC",   produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.POST)
	public @ResponseBody String recuperarCuenta (HttpServletRequest request, HttpServletResponse resp){
		Usuario p = usuarioService.obtenUsuarioCod(request.getParameter("codigoRecuperacion")) ;
		if (p == null){			
			resp.setStatus(400);
			return "{\"data\":"+0+" , \"cod\":"+0+"}";
		}else{
			String destinatario = p.getCorreo();   //A quien le quieres escribir.
    	    String asunto = "Flash Mobile";
    	    String cuerpo = "Flash Mobile.  Tú código distribuidor es el siguiente:  %1 Tú contraseña es la siguiente: %2";
    	    cuerpo=cuerpo.replaceAll("%1", p.getCod_distribuidor());
    	    cuerpo = cuerpo.replaceAll("%2", p.getPassword());
    	    email.enviarConGMail(destinatario, asunto, cuerpo);
			return "{\"data\":"+1+" , \"cod\":"+1+"}";
		}
		
		
	}
	
	
	private boolean validCredenciales (String user, String pass){
		Prospecto p = null;
		try {
			if(user != null && pass != null){
				p = usuarioService.getUserLogin(Cifrar.decifrar(user), Cifrar.decifrar(pass));
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p == null;
	}
	
	
}
