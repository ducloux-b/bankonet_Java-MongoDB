package bankonetConseiller;

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
		try
			{
			clavier.close();
			}
		catch(IllegalStateException e)
			{
			}
		System.exit(0);//trouver autre chose, ça a pas l'air net...
		}

	public static void menuPrincipal()
		{
		System.out.println("*****APPLICATION CONSEILLER BANCAIRE******");
		System.out.println("0.Arrêter le programme");
		System.out.println("1.Ouvrir	un compte");
		System.out.println("2.Lister tous les clients");
		System.out.println("3.Ajouter un compte courant");
		System.out.println("Veuillez choisir une action.");

		
		Integer choix= new Integer(-1);
		choix= sysInInt();
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
			case 3:
				ajouterUnCompteCourant();
			default:
				System.out.println("Choix invalide!");
				menuPrincipal();
				break;
			}
		}

	private static void ajouterUnCompteCourant()
		{
		System.out.println(dbAccess.getInfosClients());
		System.out.println("client(utiliser l'id):");
		String id = sysInString();
		
		dbAccess.ajouterCompteCourant(id);
		}

	private static void listerTousLesClients()
		{
		System.out.println(dbAccess.getInfosClients());
		menuPrincipal();
		}

	public static void terminerProgramme()
		{
		System.out.println("Arrêt de l’application");
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
		return clavier.next();
		}
	public static int sysInInt()
		{
		return clavier.nextInt();
		}
	
	}
