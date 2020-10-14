package crudTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

public class CrudService {

	static Scanner sc = new Scanner(System.in);

	static ResultSet rs;
	static Connection cn;

	static CrudRepo repo = new CrudRepo();

	String insert;
	String input;

	// CrudMain 	--> 	Avvio applicazione
	// CrudService	-->		Gestione dati da scanner e invia parametri per le query
	// CrudRepo 	-->		Creazione e invio query al database test, nome tabella persone.

	/* 
	 * DATI : 
	 * 
	 * int id_persone(primary key), 
	 * String nome, 
	 * String cognome, 
	 * int eta
	 */

	public void avvio() throws Exception {
		boolean loadApp = true;
		repo.connectDb(); // Effettua la connessione al DB tramite classe CrudRepo;
		while (loadApp) {
			loadApp = Avviato(); 
		}
	}
	
	public boolean Avviato() throws Exception { 
		boolean option = false;
		System.out.println("\n" + "Quale operazione vuoi effettuare?" + "\n" + 
				"1 - Inserimento dati" + "\n" + 
				"2 - Visualizzazione dati" + "\n" + 
				"3 - Eliminazione dati" + "\n" + 
				"4 - Modifica dati" + "\n" + 
				"\n" + "0 - Esci" + "\n");
		String cmd = sc.next();
		switch(cmd) {
		case "1":
			inserimento();
			option = true;
			break;
		case "2":
			visualizza();
			option = true;
			break;
		case "3":
			eliminazione();
			option = true;
			break;
		case "4":
			modifica();
			option = true;
			break;
		case "0":
			repo.chiusuraApp();
			option = false;
			
		default:
			System.out.println("\n" 
					+ "+---------+" + "\n" 
					+ "| Errore! |" + "\n" 
					+ "+---------+" + "\n");
			option = true;
		}
		return option;
	}

	private void modifica() throws Exception {
		repo.checkPresence();
		if(repo.checkPresence()) {
			repo.update();
		} else {
			System.out.println("\n" 
					+ "+----------------+" + "\n" 
					+ "| Tabella vuota! |" + "\n"
					+ "+----------------+" + "\n");
		}
	}

	private void visualizza() throws Exception {
//		repo.checkPresence();
		if(repo.checkPresence()) {
			selezione();
		} else {
			System.out.println("\n" 
					+ "+----------------+" + "\n" 
					+ "| Tabella vuota! |" + "\n"
					+ "+----------------+" + "\n");
		}
	}

	public void inserimento() throws Exception {
		System.out.println("\n" + "Inserisci nome:");
		String name = sc.next();
		System.out.println("\n" + "Inserisci cognome");
		String surname = sc.next();
		System.out.println("\n" + "Età:");
		int eta = sc.nextInt();
		repo.insertPersona(name, surname, eta);
	}
	
	
	public void selezione() throws Exception {
		System.out.println("\n" 
				+ "Seleziona: " + "\n" 
				+ "1 - Singola persona;" + "\n" 
				+ "2 - Lista persone;" + "\n");
		input = sc.next();
		switch(input) {
		case "1":
			selectPersona();
			break;
		case "2":
			selectAll();
			break;
		default: 
			System.out.println("\n" 
					+ "+---------+" + "\n" 
					+ "| Errore! |" + "\n" 
					+ "+---------+" + "\n");
		} 
	}

	private void selectAll() throws Exception {
		repo.selezionaLista();
	}

	public void selectPersona() throws Exception {
		repo.selezionaPersona();
	}

	public void eliminazione() throws Exception {
		if(repo.checkDb()) {
			System.out.println("\n" 
					+ "Cosa vuoi eliminare?" + "\n"
					+ "1 - Singola persona;" + "\n"
					+ "2 - Tutti i dati nel DB;" + "\n");
			input = sc.next();
			switch (input) {
			case "1":
				deletePersona();
				break;
			case "2":
				deleteAll();
				break;
			default:
				System.out.println("\n" 
						+ "+---------+" + "\n" 
						+ "| Errore! |" + "\n" 
						+ "+---------+" + "\n");
				break;
			}
		} else {					
			System.out.println(
					"\n" + "+--------------------------------+" 
							+ "\n" + "| Non ci sono dati da eliminare! |" 
							+ "\n" + "+--------------------------------+" 
							+ "\n");
		}
	}

	private void deleteAll() throws Exception {
		repo.eliminaTutto();
	}
	private void deletePersona() throws Exception {
		repo.eliminaPersona();
	}
}
