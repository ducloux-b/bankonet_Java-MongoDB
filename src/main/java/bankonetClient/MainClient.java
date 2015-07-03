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
		try
			{
			clavier.close();
			}
		catch(IllegalStateException e)
			{
			}
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
		System.out.println("2.Effectuer un dépôt");
		System.out.println("3.Effectuer un retrait");
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
			case 2:
				effectuerDepot(login);
				break;
			case 3:
				effectuerRetrait(login);
				break;
			default:
				break;
			}
		
		}
	
	private static void effectuerRetrait(String login)
		{
		System.out.println(dbAccess.afficherComptesCourants(login));
		System.out.println("compte à débiter(utiliser le libelle):");
		String libelle = sysInString();
		System.out.println("montant à débiter:");
		float montant = sysInFloat();
		if(montant!=0)
			{
			if(!dbAccess.debiterCompteCourantClient(login,libelle,montant))
				{
				System.out.println("Retrait impossible!");
				effectuerRetrait(login);
				}
			else
				{
				System.out.println(dbAccess.afficherComptesCourants(login));
				logged(login);
				}
			}
		else
			{
			logged(login);
			}
		}

	private static void effectuerDepot(String login)
		{
		System.out.println(dbAccess.afficherComptesCourants(login));
		System.out.println("compte à créditer(utiliser le libelle):");
		String libelle = sysInString();
		System.out.println("montant à déposer:");
		float montant = sysInFloat();
		
		dbAccess.crediterCompteCourantClient(login,libelle,montant);
		logged(login);
		}

	private static void afficherSoldeDesComptes(String login)
		{
		System.out.println(dbAccess.afficherSoldes(login));
		logged(login);
		}

	public static void terminerProgramme()
		{
		System.out.println(" Arrêt de l’application");
		}
	
	public static String sysInString()
		{
		return clavier.next();
		}
	
	public static float sysInFloat()
		{
		return clavier.nextFloat();
		}
	
	public static int sysInInt()
		{
		return clavier.nextInt();
		}
	}
