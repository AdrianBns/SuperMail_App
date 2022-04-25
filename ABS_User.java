package es.eoi.proyectoEmail;

import java.time.LocalDate;

public class ABS_User {
	
	protected static String Name;
	protected static String Surname;
	protected static String Email;
	protected static String Password;
	protected static LocalDate LastAccess;
	public ABS_User(String name, String surname, String email, String password, LocalDate lastAccess) {
		super();
		Name = name;
		Surname = surname;
		Email = email;
		Password = password;
		LastAccess = lastAccess;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getSurname() {
		return Surname;
	}
	public void setSurname(String surname) {
		Surname = surname;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public LocalDate getLastAccess() {
		return LastAccess;
	}
	public void setLastAccess(LocalDate lastAccess) {
		LastAccess = lastAccess;
	}

}
