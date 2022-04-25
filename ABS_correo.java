package es.eoi.proyectoEmail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ABS_correo {
	static Scanner sc = new Scanner(System.in);
	static Connection cn = null;
	static Statement st = null;
	static List<ABS_Mail> getInBox = new ArrayList<ABS_Mail>();

	public static void main(String[] args) throws SQLException {

	}

	public void abrirCorreo() throws SQLException {

		// Primer menú de bienvenida, dónde llámamos a los métodos dependiendo la acción
		// que queramos realizar.

		System.out.println("BIENVENIDO A SUPERMAIL");
		System.out.println("1.- LOGIN \n" + "2.- NUEVO USUARIO\n" + "3.- SALIR\n");

		int menuOption = sc.nextInt();
		switch (menuOption) {
		case 1:
			login();
			break;
		case 2:
			newUser();
			break;
		case 3:
			break;
		default:

			// "Bucle para no cerrar la app salvo acción 3"
			System.out.println("Elija una opción válida de las opciones\n");
			abrirCorreo();
		}
	}

	public static void login() throws SQLException {

		// Login del usuario, dónde introduce sus credenciales y llamamos al método
		// auxiliar de la clase ABS_UserService
		// dónde hacemos una llamada y búsqueda en la base de datos de sus credenciales
		// (ABS_UserService.getUser)
		// antes de dejarle entrar en su menú personal = menuUsuario(userName,
		// emailUser).

		String userName = "";

		sc.nextLine();
		System.out.println("Introduzca mail: ");
		String emailUser = sc.nextLine();

		System.out.println("Introduzca contraseña: ");
		String emailPassword = sc.nextLine();

		if (ABS_UserService.isCreate(emailUser, emailPassword)) {
			System.out.println("Logeado correctamente");
			userName = ABS_UserService.getUser(emailUser, emailPassword);
			menuUsuario(userName, emailUser);

		} else {
			System.out.println("Usuario incorrecto");
			login();
		}

	}

	public static void newUser() throws SQLException {

		// Creación de un nuevo usuario. En este método sólo recogemos los datos, que
		// luego se pasan a registrar por medio de
		// createNewUser(); al que le pasamos por parámetros los datos introducidos. Se
		// hace uso del método de
		// ABS_UserService.isExists() para comprobar que el mail introducido no esta en
		// nuestra BBDD.

		System.out.println("Introduzca a continuación los datos necesarios para crear su usuario");
		System.out.println("__________________________________________________________________");
		System.out.println("Introduzca su nombre: ");
		String newUserName = sc.nextLine();
		System.out.println("Introduzca su apellido: ");
		String newUserSurname = sc.nextLine();

		boolean checkEmail = true;

		do {
			System.out.println("Introduzca el email (acabado en @prueba.com) que desde usar: ");
			String newUserMail = sc.nextLine();
			if (ABS_UserService.isExists(newUserMail)) {
				System.out.println("El email introducido está ya en uso");
			} else {
				System.out.println("El email introducido está disponible");
				checkEmail = false;
				System.out.println("Introduzca la contraseña que desea usar: ");
				String newUserPassword = sc.nextLine();

				createNewUser(newUserMail, newUserName, newUserSurname, newUserPassword);
			}

		} while (checkEmail);

	}

	public static void createNewUser(String UserEmail, String Name, String Surname, String Password)
			throws SQLException {

		// Registro del usuario en nuestra BBDD tras comprobar que todos los datos son
		// correctos y no se repiten
		// Al final del método se cierra todo lo usado.

		conectarBBDD();

		try {
			st = cn.createStatement();
		} catch (SQLException e1) {
			System.out.println("Statement Error");
			;
		}

		StringBuilder addUserSql = new StringBuilder();
		addUserSql.append("Insert into Users ");
		addUserSql.append("(UserEmail, Name, Surname, Password) ");
		addUserSql.append("values ");
		addUserSql.append("(?, ?, ?, ?);");

		PreparedStatement sendInfoUserSql = cn.prepareStatement(addUserSql.toString());

		sendInfoUserSql.setString(1, UserEmail);
		sendInfoUserSql.setString(2, Name);
		sendInfoUserSql.setString(3, Surname);
		sendInfoUserSql.setString(4, Password);

		sendInfoUserSql.executeUpdate();

		try {
			if (st != null) {
				st.close();

			}
		} catch (SQLException e6) {
			System.out.println("No se ha podido cerrar Statement.");
		}
		try {
			if (cn != null) {
				cn.close();

			}
		} catch (SQLException e6) {
			System.out.println("No se ha podido cerrar Conexión.");
		}

	}

	public static void menuUsuario(String userName, String userMail) throws SQLException {

		// Menu interno de usuario imitando al de bienvenida. Opciones y un switch que
		// llama a los métodos

		System.out.println("\n\nBIENVENID@ A SUPERMAIL " + userName + "\n\n" + "1. – ENVIAR MAIL\n"
				+ "2. – BANDEJA DE ENTRADA\n" + "3. – BANDEJA DE SALIDA\n" + "4. – CERRAR SESIÓN\n\n");

		int userMenuOption = sc.nextInt();

		switch (userMenuOption) {
		case 1:
			createEmail(userMail);
			System.out.println("\n¿Desea realizar alguna opción más " + userName + " ?\n");
			menuUsuario(userName, userMail);
			break;
		case 2:
			inbox(userName, userMail);
			break;

		case 3:

			break;

		case 4:

			break;
		default:
			System.out.println("\nElija una opción válida \n");
			menuUsuario(userName, userMail);
		}

	}

	public static void createEmail(String userMail) {

		// Método para enviar emails.
		// Se toman todos los datos y se llama al método de sendEmail() dónde este hace
		// la pregunta de si estamos seguros
		// de que queremos hacer el envío o si queremos descartar todo

		String userSendMail = userMail;

		sc.nextLine();
		System.out.println("¿A quién desea enviar el email? \n");
		String UserReceiveMail = sc.nextLine();
		System.out.println("¿Cuál es el asunto de su email? \n");
		String subject = sc.nextLine();
		System.out.println("Introduzca el cuerpo del mensaje: \n");
		String textEmail = sc.nextLine();

		try {
			sendEmail(subject, textEmail, userSendMail, UserReceiveMail);
		} catch (SQLException e) {
			System.out.println("Error al intentar crear email");
		}

	}

	public static void sendEmail(String subject, String text, String UserMail, String UserReceiveMail)
			throws SQLException {

		boolean sendMailYesOrNot = true;

		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		date.format(formatter);

		try {
			conectarBBDD();
			cn.createStatement();

		} catch (SQLException j) {
			System.out.println("Statement sendMessage error");
		}

		StringBuilder userMessage = new StringBuilder();
		userMessage.append("insert into Email ");
		userMessage.append("(EmailDate, SubjectEmail, TextEmail, Opened, UserMail, UserRecibeMail) ");
		userMessage.append("values ");
		userMessage.append("(?,?,?,0,?,?);");

		PreparedStatement sendMessageSQL = cn.prepareStatement(userMessage.toString());
		sendMessageSQL.setString(1, date.toString());
		sendMessageSQL.setString(2, subject);
		sendMessageSQL.setString(3, text);
		sendMessageSQL.setString(4, UserMail);
		sendMessageSQL.setString(5, UserReceiveMail);

		do {

			System.out.println("¿Está seguro de que quiere enviar el email con asunto '" + subject + "' ?(si/no)\n");
			String yesOrNot = sc.nextLine();

			if (yesOrNot.equalsIgnoreCase("si") || yesOrNot.equalsIgnoreCase("Si")) {
				sendMessageSQL.executeUpdate();
				sendMailYesOrNot = false;
				System.out.println("Email enviado correctamente \n");
			} else if (yesOrNot.equalsIgnoreCase("no") || yesOrNot.equalsIgnoreCase("No")) {
				System.out.println("Email eliminado");
				sendMailYesOrNot = false;
			} else {
				System.out.println("No le he entendido \n");
			}

		} while (sendMailYesOrNot);

		try {
			if (st != null) {
				st.close();

			}
		} catch (SQLException e6) {
			System.out.println("No se ha podido cerrar Statement.");
		}
		try {
			if (cn != null) {
				cn.close();

			}
		} catch (SQLException e6) {
			System.out.println("No se ha podido cerrar Conexión.");
		}

	}

	public static void inbox(String userName, String Email) throws SQLException {

		// Bandeja simple de entrada. Se recogen todos los email recibidos por el
		// usuario y se ordenan según número de columna
		conectarBBDD();
		System.out.println("\n BANDEJA DE ENTRADA\n");
		Statement sendCheckMail = cn.createStatement();
		int i = 1;
		try {

			ResultSet rs = sendCheckMail.executeQuery("Select * from Email where UserRecibeMail = '" + Email + "';");

			if (rs.next()) {
				do {
					
					//Mostramos todos los correos por pantalla.
					
					System.out.println(i + " - " + rs.getString("SubjectEmail"));
					String subject = rs.getString("SubjectEmail");
					String senderEmail = rs.getString("UserMail");
					String text = rs.getString("TextEmail");

					ABS_Mail mail = new ABS_Mail(senderEmail, subject, text);
					getInBox.add(mail);
					i++;

				} while (rs.next());
			} else {

				System.out.println("No hay emails para mostrar");

			}

		} catch (

		Exception e) {
			System.out.println("Error al buscar emails en la bandeja de entrada");

		}

		sc.nextLine();
		System.out.print("\n Seleccione el email que quiera leer o pulse 0 para salir: ");
		String inboxOption = sc.nextLine();

		//Menu para leer emails. Dentro del if, la opcion para conservar o borrar el correo.
		
		
		if (ABS_MailService.isNumeric(inboxOption)) {

			if (inboxOption.equalsIgnoreCase("0")) {

				menuUsuario(userName, Email);
			} else {
				
				int checkEmailInbox = Integer.parseInt(inboxOption);
				System.out.println("\n\nASUNTO: " + getInBox.get(checkEmailInbox - 1).getSubject() + "\nDE: "
						+ getInBox.get(checkEmailInbox - 1).getEmailSender() + "\n" + "MENSAJE: "
						+ getInBox.get(checkEmailInbox - 1).getText() + "\n");

				System.out.println("\n\n¿Desea conservar este mensaje? (y/n)");
				String deleteOrNot = sc.nextLine();
				if (deleteOrNot.equalsIgnoreCase("y") || deleteOrNot.equalsIgnoreCase("Y")) {
					menuUsuario(userName, Email);
				} else {
					
					menuUsuario(userName, Email);
				}
			}

		} else {
			System.out.println("\nNo le he entendido\n");
			inbox(userName, Email);
		}

	}

	public static void conectarBBDD() {
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
}
