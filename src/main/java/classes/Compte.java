package classes;

abstract public class Compte
	{
	protected float	solde;

	public Compte(float solde)
		{
		if(solde < 0)
			{
			this.setSolde(0);
			}
		else
			{
			this.setSolde(solde);
			}
		}

	abstract void setSolde(float solde2);

	public String toString()
		{
		String res= "solde:" + this.getSolde();
		return res;
		}

	public float getSolde()
		{
		return solde;
		}
	
	abstract String getLibelle();
	
	}
