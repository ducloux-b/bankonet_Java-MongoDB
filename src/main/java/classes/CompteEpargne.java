package classes;

public class CompteEpargne extends Compte
	{
	private String	libelle;

	public CompteEpargne(String nom, String prenom,int nbComptesCourants,float solde)
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
