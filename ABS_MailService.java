package es.eoi.proyectoEmail;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ABS_MailService {

	List<ABS_Mail> getInBox = new ArrayList<ABS_Mail>();
	List<ABS_Mail> getOutBox = new ArrayList<ABS_Mail>();
	static boolean SendMail;
	static boolean OpenMail;
	static boolean DeleteMail;

	public static boolean isSendMail(PreparedStatement sendMessage, boolean sendOrNot) {

		if (sendOrNot = true) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isOpenMail(int id) {
		return OpenMail;
	}

	public static boolean isDeleteMail(int id) {

		return DeleteMail;
	}

	public static boolean isNumeric(String inboxOption) {

		boolean resultado;

		try {
			Integer.parseInt(inboxOption);
			resultado = true;
		} catch (NumberFormatException excepcion) {
			resultado = false;
		}

		return resultado;
	}

	public static void openMail(String senderEmail, String subject, String text) {
		List<ABS_Mail> inbox = new ArrayList<ABS_Mail>();

		ABS_Mail mail = new ABS_Mail(senderEmail, subject, text);

		inbox.add(mail);

	}

}
