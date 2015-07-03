package classes;

final public class CompteCourant extends Compte
	{

	private String	libelle;

	public CompteCourant(String nom, String prenom,int nbComptesCourants,float solde)
		{
		super(solde);
		this.libelle= nom+"_"+prenom+"_COURANT_"+nbComptesCourants;
		}

	@Override
	void setSolde(float solde2)
		{
		this.solde = solde2;
		
		}

	@Override
	public String getLibelle()
		{
		return libelle;
		}

	}
