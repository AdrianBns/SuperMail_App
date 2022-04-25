package es.eoi.proyectoEmail;

import java.time.LocalDate;


import lombok.*;
@Getter @Setter	
public class ABS_Mail {
 
	int Id;
	LocalDate Date;
	String Subject;
	String Text;
	LocalDate Opened;
	String EmailSender;
	String EmailReciver;
	

	public ABS_Mail(int id, LocalDate date, String subject, String text, LocalDate opened, String emailSender,
			String emailReciver) {
		super();
		Id = id;
		Date = date;
		Subject = subject;
		Text = text;
		Opened = opened;
		EmailSender = emailSender;
		EmailReciver = emailReciver;
	}
	
	public ABS_Mail(String emailSender, String subject, String text) {
		super();
	
		Subject = subject;
		Text = text;
		EmailSender = emailSender;

	}
	
}
