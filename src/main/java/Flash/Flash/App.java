package Flash.Flash;

import com.flash.service.impl.EnvioEmailService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    	    String destinatario =  "crisantos555@gmail.com"; //A quien le quieres escribir.
    	    String asunto = "Flash Mobile";
    	    String cuerpo = "Bienvenido a Flash Mobile";
EnvioEmailService email = new EnvioEmailService();
email.enviarConGMail(destinatario, asunto, cuerpo);
    	
    }
}
