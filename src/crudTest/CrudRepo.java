package crudTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class CrudRepo {

	// JDBC Driver Name & Database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String JDBC_URL = "jdbc:mysql://localhost:3306/test";

	// JDBC Database Credentials
	static final String JDBC_USER = "root";
	static final String JDBC_PASSW = "root";

	static Scanner sc = new Scanner(System.in);

	static Statement st;
	static Connection cn;

	ResultSet rs;
	PreparedStatement ps;

	String risposta;
	String query;
	boolean check;
	String answer;

	int id;
	String name;
	String surname;
	int eta;


	public void connectDb() {
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("\n" 
					+ "+------------------------+" 
					+ "\n" 
					+ "| Connecting to Database |"
					+ "\n"
					+ "+------------------------+"
					+ "\n");
			cn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSW);
			st = cn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void insertPersona(String nome, String cognome, int eta) {
		try {
			st.execute("INSERT INTO persone(nome, cognome, eta) VALUES ('" + nome + "'" + ", '" + cognome + "', " + eta + ")");

			System.out.println("\n" 
					+ "\n" + "+-------------------------------------+" 
					+ "\n" + "| INSERIMENTO AVVENUTO CORRETTAMENTE! |" 
					+ "\n" + "+-------------------------------------+" 
					+ "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public boolean selezionaPersona() throws Exception {
		check = false;
		try {
			if(checkDb()) {
				System.out.println("Digita l'ID della persona che vuoi visualizzare:");
				String answer = sc.next();
				rs = st.executeQuery("SELECT id_persone, nome, cognome, eta FROM persone WHERE id_persone = " + answer + ";");

				while (rs.next()) {
					id = rs.getInt("id_persone"); 
					name = rs.getString("nome");
					surname = rs.getString("cognome");
					eta = rs.getInt("eta");
					System.out.print("\n" 
							+ " ID: " + id 	+ "\n"
							+ " NOME: " + name	+ "\n"
							+ " COGNOME: " + surname	+ "\n" 
							+ " ETA': " + eta
							+ "\n" + "+-------------------+" + "\n");
					check = true;
				}
				updateOrDelete();
			} else {
				System.out.println("Errore!");
				check = false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return check;
	}


	private void updateOrDelete() throws Exception {
		System.out.println("\n" + "Cosa vuoi fare?" + "\n" + 
				" 1 - modifica dati;" + "\n" +
				" 2 - elimina dati;" + "\n" + "\n" + 
				" 0 - menù principale" + "\n");

		risposta = sc.next();
		switch(risposta) {
		case "1":
			updateImmediately(id);
			break;
		case "2":
			deleteImmediately(id);
			break;

		default:
			System.out.println("Errore!");
		}
	}




	private void updateImmediately(int id) {
		try {
			System.out.println("\n" + "Cosa vuoi modificare?" + "\n" 
					+ "1 - Nome;" + "\n" 
					+ "2 - Cognome;" + "\n" 
					+ "3 - Età" + "\n");
			risposta = sc.next();
			switch (risposta) {
			case "1":
				System.out.println("\n" + "Inserisci nome:" + "\n");
				String newNome = sc.next();
				st.executeUpdate("UPDATE persone SET nome = '" + newNome + "' WHERE id_persone = " + id);
				System.out.println("\n" 
						+ "+---------------------+" + "\n" 
						+ "| MODIFICA COMPLETATA |" + "\n" 
						+ "+---------------------+" + "\n");				
				break;

			case "2":
				System.out.println("\n" + "Inserisci cognome:" + "\n");
				String newCognome = sc.next();
				st.executeUpdate("UPDATE persone SET cognome = '" + newCognome + "' WHERE id_persone = " + id);
				System.out.println("\n" 
						+ "+---------------------+" + "\n" 
						+ "| MODIFICA COMPLETATA |" + "\n" 
						+ "+---------------------+" + "\n");
				break;

			case "3":
				System.out.println("\n" + "Inserisci l'età:" + "\n");
				int newEta = sc.nextInt();
				st.executeUpdate("UPDATE persone SET eta = '" + newEta + "' WHERE id_persone = " + id);
				System.out.println("\n" 
						+ "+---------------------+" + "\n" 
						+ "| MODIFICA COMPLETATA |" + "\n" 
						+ "+---------------------+" + "\n");
				break;

			default:
				System.out.println("\n" 
						+ "+---------+" + "\n" 
						+ "| Errore! |" + "\n" 
						+ "+---------+" + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void deleteImmediately(int id) {
		try {
			st.executeUpdate("DELETE FROM test.persone WHERE id_persone = " + id);
			System.out.println("\n" 
					+ "+------------+" + "\n" 
					+ "| ELIMINATO! |" + "\n" 
					+ "+------------+" + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void selezionaLista() throws Exception {
		try {
			rs = st.executeQuery("SELECT id_persone, nome, cognome, eta FROM persone");
			while (rs.next()) {
				id = rs.getInt("id_persone"); 
				name = rs.getString("nome");
				surname = rs.getString("cognome");
				eta = rs.getInt("eta");
				System.out.println("\n" 
						+ " ID: " + id 	+ "\n"
						+ " NOME: " + name	+ "\n"
						+ " COGNOME: " + surname	+ "\n" 
						+ " ETA': " + eta
						+ "\n" + "+-------------------+");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	public void update() throws Exception {
		try {
			checkDb();
			System.out.println("\n" + "Digita l'ID della persona che vuoi modificare:");
			int answer = sc.nextInt();
			rs = st.executeQuery("SELECT id_persone, nome, cognome, eta FROM test.persone WHERE id_persone like " + answer);
			System.out.println("\n" + "Cosa vuoi modificare?" + "\n" 
					+ "1 - Nome;" + "\n" 
					+ "2 - Cognome;" + "\n" 
					+ "3 - Età" + "\n");
			risposta = sc.next();
			switch (risposta) {
			case "1":
				System.out.println("\n" + "Inserisci nome:" + "\n");
				String newNome = sc.next();
				st.executeUpdate("UPDATE persone SET nome = '" + newNome + "' WHERE id_persone = " + answer);
				System.out.println("\n" 
						+ "+---------------------+" + "\n" 
						+ "| MODIFICA COMPLETATA |" + "\n" 
						+ "+---------------------+" + "\n");				
				break;

			case "2":
				System.out.println("\n" + "Inserisci cognome:" + "\n");
				String newCognome = sc.next();
				st.executeUpdate("UPDATE persone SET cognome = '" + newCognome + "' WHERE id_persone = " + answer);
				System.out.println("\n" 
						+ "+---------------------+" + "\n" 
						+ "| MODIFICA COMPLETATA |" + "\n" 
						+ "+---------------------+" + "\n");
				break;

			case "3":
				System.out.println("\n" + "Inserisci l'età:" + "\n");
				int newEta = sc.nextInt();
				st.executeUpdate("UPDATE persone SET eta = '" + newEta + "' WHERE id_persone = " + answer);
				System.out.println("\n" 
						+ "+---------------------+" + "\n" 
						+ "| MODIFICA COMPLETATA |" + "\n" 
						+ "+---------------------+" + "\n");
				break;

			default:
				System.out.println("\n" 
						+ "+---------+" + "\n" 
						+ "| Errore! |" + "\n" 
						+ "+---------+" + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	public void eliminaPersona() throws Exception {	
		checkDb();
		try {
			System.out.println("\n" + "Digita l'ID della persona che vuoi eliminare:");
			int inputDel = sc.nextInt();
			st.executeUpdate("DELETE FROM test.persone WHERE id_persone like " + inputDel);
			System.out.println("\n" 
					+ "+------------+" + "\n" 
					+ "| ELIMINATO! |" + "\n" 
					+ "+------------+" + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void eliminaTutto() throws Exception {
		try {
			st.executeUpdate("DELETE FROM test.persone");
			System.out.println("\n" 
					+ "+--------------------------+" + "\n" 
					+ "| TUTTI I DATI CANCELLATI! |" + "\n" 
					+ "+--------------------------+" + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public boolean checkDb() throws Exception {

		boolean check = false;
		rs = st.executeQuery("SELECT id_persone, nome, cognome, eta FROM persone");
		if(rs.next()) {
			while (rs.next()) {		
				id = rs.getInt("id_persone"); 
				name = rs.getString("nome");
				surname = rs.getString("cognome");
				eta = rs.getInt("eta");
				System.out.println("\n" 
						+ " ID: " + id 	+ "\n"
						+ " NOME: " + name	+ "\n"
						+ " COGNOME: " + surname	+ "\n" 
						+ " ETA': " + eta
						+ "\n" + "+-------------------+" + "\n");
			}
			check = true;
		} else {
			check = false;
		}
		return check;
	}


	public boolean checkPresence() throws Exception {
		boolean checkPres = false;
		try {
			rs = st.executeQuery("SELECT id_persone, nome, cognome, eta FROM persone");
			if(rs.next()) {
				do {	
					checkPres = true;
				} while (rs.next());
			} else {
//				System.out.println("\n" + "Tabella vuota!" + "\n");
				checkPres = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			//			checkPres = false;
		}
		return checkPres;	
	}


	public void chiusuraApp() throws Exception {
		System.out.println("\n" 
				+ "+--------------+" + "\n" 
				+ "| ARRIVEDERCI! |" + "\n" 
				+ "+--------------+" + "\n");
		st.close();
		cn.close();
	}	
}