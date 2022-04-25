package es.eoi.proyectoEmail;

import java.sql.SQLException;

public class ABS_correoAPP {

	public static void main(String[] args) throws SQLException {
		
		ABS_correo SuperMail = new ABS_correo();
		
		SuperMail.abrirCorreo();

	}

}
