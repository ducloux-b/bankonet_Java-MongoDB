package classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDbAccess
	{

	public void setClient(Client client)
		{
		Logger.getLogger("").setLevel(Level.SEVERE);

		try(MongoClient mongoClient= new MongoClient())
			{
			MongoDatabase db= mongoClient.getDatabase("bankonetdb");

			List<Document> comptesCourants= new ArrayList<Document>();
			for(Compte compteCourant: client.getCompteCourantList())
				{
				comptesCourants.add(new Document().append("libelle",
																		compteCourant
																				.getLibelle())
						.append("solde", compteCourant.getSolde()));
				}

			List<Document> comptesEpargnes= new ArrayList<Document>();
			for(Compte compteEpargne: client.getCompteEpargneList())
				{
				comptesEpargnes.add(new Document().append("libelle",
																		compteEpargne
																				.getLibelle())
						.append("solde", compteEpargne.getSolde()));
				}

			Document nouveauClient= new Document().append("nom", client.getNom())
					.append("prenom", client.getPrenom())
					.append("login", client.getLogin())
					.append("password", client.getPassword())
					.append("comptesCourants", comptesCourants)
					.append("comptesEpargnes", comptesEpargnes);

			MongoCollection<Document> collection= db.getCollection("clients");

			collection.insertOne(nouveauClient);
			}
		finally
			{
			}
		}

	public String getInfosClients()
		{
		Logger.getLogger("").setLevel(Level.SEVERE);

		String res= new String();
		res+= "id | login | nom | prenom | nbComptesCourants | nbComptesEpargnes\n";
		try(MongoClient mongoClient= new MongoClient())
			{
			MongoDatabase db= mongoClient.getDatabase("bankonetdb");
			MongoCollection<Document> collection= db.getCollection("clients");

			BasicDBObject query= new BasicDBObject();

			for(Document document: collection.find(query))
				{
				String id= document.get("_id").toString();
				String login= document.get("login").toString();
				String nom= document.get("nom").toString();
				String prenom= document.get("prenom").toString();
				int nbComptesCourants= ((ArrayList)document.get("comptesCourants"))
						.size();
				int nbComptesEpargnes= ((ArrayList)document.get("comptesEpargnes"))
						.size();

				res+= id + " | " + login + " | " + nom + " | " + prenom + " | " +
						nbComptesCourants + " | " + nbComptesEpargnes + "\n";

				}

			mongoClient.close();
			}
		finally
			{
			}

		return res;
		}

	public boolean loginAndPasswordAccepted(String login, String password)
		{
		Logger.getLogger("").setLevel(Level.SEVERE);

		try(MongoClient mongoClient= new MongoClient())
			{
			MongoDatabase db= mongoClient.getDatabase("bankonetdb");
			MongoCollection<Document> collection= db.getCollection("clients");

			BasicDBObject query= new BasicDBObject().append("login", login)
					.append("password", password);

			for(Document document: collection.find(query))
				{
				mongoClient.close();
				return true;
				}

			mongoClient.close();
			}
		finally
			{
			}

		return false;
		}

	public String afficherSoldes(String login)
		{
		String res= new String();
		res+= "libelle | solde\n";

		Logger.getLogger("").setLevel(Level.SEVERE);

		try(MongoClient mongoClient= new MongoClient())
			{
			MongoDatabase db= mongoClient.getDatabase("bankonetdb");
			MongoCollection<Document> collection= db.getCollection("clients");

			BasicDBObject query= new BasicDBObject().append("login", login);

			for(Document document: collection.find(query))
				{
				List comptesCourants= (ArrayList)document.get("comptesCourants");
				for(Iterator iterator= comptesCourants.iterator() ; iterator
						.hasNext() ;)
					{
					Document compteCourant= (Document)iterator.next();
					res+= compteCourant.get("libelle").toString() + " | " +
							compteCourant.get("solde").toString() + "\n";
					}

				List comptesEpargnes= (ArrayList)document.get("comptesEpargnes");
				for(Iterator iterator= comptesEpargnes.iterator() ; iterator
						.hasNext() ;)
					{
					Document compteEpargne= (Document)iterator.next();
					res+= compteEpargne.get("libelle").toString() + " | " +
							compteEpargne.get("solde").toString() + "\n";
					}
				}

			mongoClient.close();
			}
		finally
			{
			}

		return res;
		}

	public String afficherComptesCourants(String login)
		{
		String res= new String();
		res+= "libelle | solde\n";

		Logger.getLogger("").setLevel(Level.SEVERE);

		try(MongoClient mongoClient= new MongoClient())
			{
			MongoDatabase db= mongoClient.getDatabase("bankonetdb");
			MongoCollection<Document> collection= db.getCollection("clients");

			BasicDBObject query= new BasicDBObject().append("login", login);

			for(Document document: collection.find(query))
				{
				List<Document> comptesCourants= (ArrayList<Document>)document.get("comptesCourants");
				for(Iterator<Document> iterator= comptesCourants.iterator() ; iterator
						.hasNext() ;)
					{
					Document compteCourant= (Document)iterator.next();
					res += compteCourant.get("libelle").toString() + " | " +
							compteCourant.get("solde").toString() + "\n";
					}
				}

			mongoClient.close();
			}
		finally
			{
			}

		return res;
		}

	
	
	public void crediterCompteCourantClient(	String login,
															String libelle,
															float montant)
		{
		Logger.getLogger("").setLevel(Level.SEVERE);

		try(MongoClient mongoClient= new MongoClient())
			{
			MongoDatabase db= mongoClient.getDatabase("bankonetdb");
			MongoCollection<Document> collection= db.getCollection("clients");

			BasicDBObject query= new BasicDBObject().append("login", login);

			for(Document document: collection.find(query))
				{
				List<Document> comptesCourants= (ArrayList<Document>)document.get("comptesCourants");
				List<Document> comptesCourantsUpdate = new ArrayList<Document>();
				for(Iterator<Document> iterator= comptesCourants.iterator() ; iterator
						.hasNext() ;)
					{
					Document compteCourant= (Document)iterator.next();
					if(compteCourant.get("libelle").toString().equals(libelle))
						{
						comptesCourantsUpdate.add(new Document()
						.append("libelle",compteCourant.get("libelle"))
						.append("solde", ((new Float(compteCourant.get("solde").toString()))+montant))
						);
						}
					else
						{
						comptesCourantsUpdate.add(compteCourant);
						}
					}
				
				collection.updateOne(query, new Document("$set",new Document().append("comptesCourants", comptesCourantsUpdate)));
				}

			mongoClient.close();
			}
		finally
			{
			}
		}

	public boolean debiterCompteCourantClient(String login,
														String libelle,
														float montant)
		{
		Logger.getLogger("").setLevel(Level.SEVERE);

		try(MongoClient mongoClient= new MongoClient())
			{
			MongoDatabase db= mongoClient.getDatabase("bankonetdb");
			MongoCollection<Document> collection= db.getCollection("clients");

			BasicDBObject query= new BasicDBObject().append("login", login);

			for(Document document: collection.find(query))
				{
				List<Document> comptesCourants= (ArrayList<Document>)document.get("comptesCourants");
				for(Iterator<Document> iterator= comptesCourants.iterator() ; iterator
						.hasNext() ;)
					{
					Document compteCourant= (Document)iterator.next();
					if(compteCourant.get("libelle").toString().equals(libelle))
						{
						if((new Float(compteCourant.get("solde").toString())-montant)<0)
							{
							return false;
							}
						}
					}
								}
			crediterCompteCourantClient(login, libelle, -montant);//debiter de X correspond à créditer de -X
			mongoClient.close();
			}
		finally
			{
			}
		return (true);
		}
	}
