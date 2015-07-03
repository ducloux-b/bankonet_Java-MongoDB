package classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client
	{

	private String			nom;
	private String			prenom;
	private String			login;
	private String			password;

	private List<Compte>	compteCourantList	= new ArrayList<Compte>();
	private List<Compte>	compteEpargneList	= new ArrayList<Compte>();

	public Client(String nom, String prenom, String login, String password)
		{
		super();
		this.nom= nom;
		this.prenom= prenom;
		this.login= login;
		this.password= password;
		}

	public void ajouterCompteCourant(float solde)
		{
		this.compteCourantList.add(new CompteCourant(this.getNom(), this.getPrenom(), this.compteCourantList.size()+1, solde));
		}

	public List<Compte> getCompteCourantList()
		{
		return Collections.unmodifiableList(compteCourantList);
		}

	public List<Compte> getCompteEpargneList()
		{
		return Collections.unmodifiableList(compteEpargneList);
		}

	public String getNom()
		{
		return nom;
		}

	public String getPrenom()
		{
		return prenom;
		}

	public String getLogin()
		{
		return login;
		}

	public String getPassword()
		{
		return password;
		}

//	public String infoClient()
//		{
//		
//		return (
//				this.get
//				);
//		}
	
	
	}
