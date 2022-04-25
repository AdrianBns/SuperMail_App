package es.eoi.proyectoEmail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ABS_UserService {

	static Connection cn = null;
	static Statement psEmail = null;
	static Statement psCheckEmailPassword = null;
	static String User;
	static boolean Exists;
	static boolean Create;
	static boolean UpdateLastAccess;

	public static boolean isExists(String Email) {

		boolean emailExists = false;

		String consultaSQL = "Select UserEmail from Users where UserEmail = '" +Email+ "';";

		try {
			connect();
			PreparedStatement psEmail = cn.prepareStatement(consultaSQL);
			ResultSet rs = psEmail.executeQuery();
			if (rs.next()) {
				emailExists = true;
			}
		} catch (Exception e) {
			System.out.println("Error al buscar email en BBDD");

		} finally {
			psEmail = null;
		}
		return emailExists;
	}

	public static boolean isCreate(String emailUser, String emailPassword) {
		
		boolean isUserCreate = false;
		String checkUser = "Select UserEmail, Password from Users where UserEmail = '"+emailUser+"' and Password = '" +emailPassword+"';";
		
		try {
			connect();
			PreparedStatement psCheckEmailPassword = cn.prepareStatement(checkUser);
			ResultSet rsCheckEmailPassword = psCheckEmailPassword.executeQuery();
			if (rsCheckEmailPassword.next()) {
				isUserCreate = true;
			} 
			}catch(Exception e) {
				System.out.println("Error al comprobar Email y Password");
		} finally {
			psCheckEmailPassword = null;
		}

		return isUserCreate;

	}

	public static void connect() {
		String url = "jdbc:mysql://localhost:3306/SQL_ABS_email?serverTimezone=UTC";
		String user = "root";
		String pass = "7193";

		try {
			cn = DriverManager.getConnection(url, user, pass);
			System.out.println("Conectado");
		} catch (SQLException e) {
			System.out.println("Algo ha fallado");
		}

	}

	public static String getUser(String Email, String Password) {
		
		
		String getUser = "Select Name from Users where UserEmail = '"+Email+"' and Password = '" +Password+"';";
		
		try {
			connect();
			PreparedStatement psCheckEmailPassword = cn.prepareStatement(getUser);
			ResultSet rsCheckEmailPassword = psCheckEmailPassword.executeQuery();
			if (rsCheckEmailPassword.next()) {
				getUser = rsCheckEmailPassword.getString("Name");
			} 
			}catch(Exception e) {
				System.out.println("Error al comprobar Email y Password");
				return getUser = null;
		} finally {
			psCheckEmailPassword = null;
		}
		return getUser;
		
	
	}

	public static boolean isUpdateLastAccess(String Email) {
		return UpdateLastAccess;
	}

}
