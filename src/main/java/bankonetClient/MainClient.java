package bankonetClient;

import java.util.Scanner;

import classes.MongoDbAccess;

public class MainClient
	{
	static Scanner clavier= new Scanner(System.in);
	static MongoDbAccess dbAccess = new MongoDbAccess();

	public static void main(String[] args)
		{
		login();
		System.exit(0);//trouver autre chose, ça a pas l'air net...
		}

	public static void login()
		{
		System.out.println("login?:");
		String login = sysInString();
		System.out.println("password?:");
		String password = sysInString();
		
		if(dbAccess.loginAndPasswordAccepted(login,password))
			{
			logged(login);
			}
		else
			{
			System.out.println("Connexion impossible");
			login();
			}
		
		}
	
	public static void logged(String login)
		{
		System.out.println("*****APPLICATION CONSEILLER BANCAIRE******");
		System.out.println("0.Arrêter le programme");
		System.out.println("1.Consulter les soldes des comptes");
		System.out.println("Veuillez choisir une action.");
		
		Integer action = sysInInt();
		switch(action)
			{
			case 0:
				terminerProgramme();
				break;
			case 1:
				afficherSoldeDesComptes(login);
				break;
			default:
				break;
			}
		
		}
	
	private static void afficherSoldeDesComptes(String login)
		{
		System.out.println(dbAccess.afficherSoldes(login));
		logged(login);
		}

	public static void terminerProgramme()
		{
		System.out.println(" Arrêt de l’application");
		clavier.close();
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
