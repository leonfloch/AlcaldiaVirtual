package com.uniandes.ecos.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.uniandes.ecos.dtos.CorreoElectronicoDto;

public class JavaMailSender {
	private static final ResourceBundle mailProperties = ResourceBundle.getBundle("resources.mail");
	
	public static void enviarCorreo(final CorreoElectronicoDto correo)throws Exception{
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", mailProperties.getString("mail.smtp.auth"));
		properties.put("mail.smtp.starttls.enable", mailProperties.getString("mail.smtp.starttls.enable"));
		properties.put("mail.smtp.host", mailProperties.getString("mail.smtp.host"));
		properties.put("mail.smtp.port", mailProperties.getString("mail.smtp.port"));
		
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(correo.getUsuario(), correo.getContrasenia());
			}
		});
				
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(correo.getUsuario()));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo.getPara()));
		message.setSubject(correo.getAsunto());
		message.setText(correo.getContenido());
		
		Transport.send(message);
	}
}
