package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

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
			
			List<Document> comptesCourants = new ArrayList<Document>();
			for(Compte compteCourant: client.getCompteCourantList())
				{
				comptesCourants.add(new Document().append("libelle", compteCourant.getLibelle()).append("solde", compteCourant.getSolde()));
				}
			
			List<Document> comptesEpargnes = new ArrayList<Document>();
			for(Compte compteEpargne: client.getCompteEpargneList())
				{
				comptesEpargnes.add(new Document().append("libelle", compteEpargne.getLibelle()).append("solde", compteEpargne.getSolde()));
				}
			
			Document nouveauClient = new Document()
			.append("nom", client.getNom())
			.append("prenom", client.getPrenom())
			.append("login", client.getLogin())
			.append("password", client.getPassword())
			.append("comptesCourants", comptesCourants)
			.append("comptesEpargnes", comptesEpargnes)
			;
			
			MongoCollection<Document> collection = db.getCollection("clients");
			
			collection.insertOne(nouveauClient);
			mongoClient.close();
			}
		finally
			{
			
			}
		}
	}
