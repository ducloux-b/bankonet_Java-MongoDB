package classes;

import java.util.ArrayList;
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

			BasicDBObject query= new BasicDBObject()
				.append("login", login)
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
	}
