package lucene;

import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

public class QueryBuild {//cette classe permet de construire une requete à partir de la chaine saisie
	/*
	 * permet de preciser le nombre de mots clé gérés à traiter
	 * permet de parametre la requete
	 */
	private	int nbreMot;
	/*motif de la recherche
	 * 
	 * */
	private String motSaisie;
	
	/*
	 * requete construite à partir des mots clé saisis
	 */
	private Query requete;
	/*
	 * liste des mots clés constituant la requête
	 */
	
	private QueryParser parser;
	
	public QueryBuild(int nbreMot,String motif){
		//la requete porte sur le champ description de la ressource
		QueryParser parser = new QueryParser("description", new StandardAnalyzer());
		ArrayList<String> listMot =  keywords(motif);
		String requeteString = "";
		//elements du motif different de film,actor maker
		String litteral1 ="" ;
		String litteral2="";
		try {	
			
			//si le nombre mots constituant le motif entré par l'utilisateur est egal a 3
	       	   if(listMot.size()==3){
	       		   //film realisé par un realisateur ou film joué par un acteur
			    if(listMot.contains("film") && (listMot.contains("maker") || listMot.contains("actor"))){
			    	//requete boostée de 5
			    	 requeteString = "("+listMot.get(0)+" AND "+listMot.get(1)+" AND "+listMot.get(2)+")^5";
		
			    	 //si le motif contient film and maker
			    	 if(!listMot.contains("actor")){
			            for(int i = 0;i<3;i++)
			            	//trouver le dernier mot du motif different de film et maker
			    	        if(!listMot.get(i).equals("film")  && !listMot.get(i).equals("maker"))
			    	            litteral1 = listMot.get(i);  
			            //construire une requete de mots puis une d'un mot
				        requeteString = requeteString+" OR ((film AND "+litteral1+") OR (maker AND "+litteral1+"))^2 OR "
								+ "("+listMot.get(0)+" OR "+listMot.get(1)+" OR "+listMot.get(2)+")";
			    	 }			    	 
			    	 
			    	 if(!listMot.contains("maker")){ //si le motif contient film and actor
				            for(int i = 0;i<3;i++)
				            	//trouver le dernier mot du motif different de film et maker
				    	        if(!listMot.get(i).equals("film") && !listMot.get(i).equals("actor"))
				    	            litteral1 = listMot.get(i);  
				            //construire une requete de mots puis une d'un mot
					        requeteString = requeteString+" OR ((film AND "+litteral1+") OR (actor AND "+litteral1+"))^2 OR "
									+ "("+listMot.get(1)+" OR "+listMot.get(1)+" OR "+listMot.get(2)+")";
				    	 }
			    	}
			    
			   
			    else if(listMot.contains("film")){
			    	int i= 0;
			    	boolean trouve =false;
			    	//trouve le mot différent de film en partant de la gauche
			    	while(trouve==false || i==3){
			    		 if(!listMot.get(i).equals("film")){
			    			 litteral1 = listMot.get(i);
			    			 trouve = true;
			    		 }
			    		  i++;
			    		  }
			    	i= 2; 
			    	
			    	//trouver le mot différent de film en partant de la droite
			    	while(trouve==true || i==0){
			    		 if(!listMot.get(i).equals("film")){
			    			 litteral2 = listMot.get(i);
			    			 trouve = false;
			    		 }
			    		  i--;
			    		  }
           			 requeteString = "(film AND maker AND "+litteral1+") OR "+"(film AND actor AND "+litteral2+")"
           			 		+ " OR (film AND actor AND "+litteral1+") OR "+"(film AND maker AND "+litteral2+")";
			    }
			    	
			    
			    
				}
	       	   
	       	 //une requete sur un mot clé de 2 élèments
	          	if(listMot.size() == 2){
			               if(listMot.contains("film"))
				requete = parser.parse("(film AND "+listMot.get(1)+")^5 "
					+ "(film OR "+listMot.get(0)+" OR "+listMot.get(1)+")");	
	          	}
		
	      	   if(listMot.size() == 1){
	      		   requete = parser.parse(listMot.get(0)); 
                   	}
	
	
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	//3 requetes.chaque requet constitué d'un seul 
	}

	
public  ArrayList<String> keywords(String motif){
		
		motif.toLowerCase();//mettre toute la chaine en minuscule
		
		ArrayList<String> setOfKeywords = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(motif," ");//pour extraire les mots clés du motifs
		while(tokenizer.hasMoreTokens())
			setOfKeywords.add(tokenizer.nextToken());//on ajoute chaque token à l'ensemble des mots-clé 
					
		return setOfKeywords;
			
	}


public int getNbreMot() {
	return nbreMot;
}


public void setNbreMot(int nbreMot) {
	this.nbreMot = nbreMot;
}


public String getMotSaisie() {
	return motSaisie;
}


public void setMotSaisie(String motSaisie) {
	this.motSaisie = motSaisie;
}


public Query getRequete() {
	return requete;
}


public void setRequete(Query requete) {
	this.requete = requete;
}


public QueryParser getParser() {
	return parser;
}


public void setParser(QueryParser parser) {
	this.parser = parser;
}
	
	
	
	
}
