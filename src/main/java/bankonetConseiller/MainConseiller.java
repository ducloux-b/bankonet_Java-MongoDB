package bankonetConseiller;

import java.util.List;
import java.util.Scanner;

import classes.Client;
import classes.MongoDbAccess;

public class MainConseiller
	{
	static Scanner clavier= new Scanner(System.in);
	static MongoDbAccess dbAccess = new MongoDbAccess();
	
	public static void main(String[] args)
		{
		menuPrincipal();
		}

	public static void menuPrincipal()
		{
		System.out.println("*****APPLICATION CONSEILLER BANCAIRE******");
		System.out.println("0.Arrêter le programme");
		System.out.println("1.Ouvrir	un compte");
		System.out.println("2.Lister tous les clients");
		System.out.println("Veuillez choisir une action.");

		
		Integer choix= new Integer(-1);
		choix= clavier.nextInt();
		switch(choix)
			{
			case 0:
				terminerProgramme();
				break;
			case 1:
				ouvrirCompte();
				break;
			case 2:
				listerTousLesClients();
				break;
			default:
				System.out.println("Choix invalide!");
				menuPrincipal();
				break;
			}
		}

	private static void listerTousLesClients()
		{
		System.out.println(dbAccess.getInfosClients());
		menuPrincipal();
		}

	public static void terminerProgramme()
		{
		System.out.println(" Arrêt de l’application");
		clavier.close();
		}
	
	public static void ouvrirCompte()
		{
		System.out.println("nom du client:");
		String nomClient = sysInString();
		System.out.println("prénom du client:");
		String prenomClient = sysInString();
		System.out.println("login:");
		String login = sysInString();
		String password = "secret";
		
		Client client = new Client(nomClient, prenomClient, login, password);
		client.ajouterCompteCourant(0);
		dbAccess.setClient(client);
		
		menuPrincipal();
		}
	
	public static String sysInString()
		{
		String chaine= new String();
		chaine= clavier.next();
		return chaine;
		}
	
	}
