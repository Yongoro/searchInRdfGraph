
package sparql;

import java.util.ArrayList;

import lucene.QueryBuild;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import views.ProjectFrame;
import views.SousGraph;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;

//import  com.hp.hpl.jena.datatypes.xsd.XSDDatatype;

/**
 * 
 * class which manages SPARQL query and execution
 * @author Daniel
 *
 */
public class SPARQL {

	public SPARQL(){}
	
	/**
	 * 
	 * @return the words entered by the user
	 */
	private static ArrayList<String> getUserWords(){
		return QueryBuild.listMot;
	}
	
	/**
	 * function that generate the SPARQL query based on keywords entered
	 * 
	 * @return The query string to execute
	 */
	public static String createQuerySPARQL(){
		
		String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "        
	    		+"PREFIX owl:  <http://www.w3.org/2002/07/owl#> "
	    		+"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#> "
	    		+"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
	    		+"PREFIX sem:  <http://www.semanticweb.org/mohamed/ontologies/2014/11/untitled-ontology-8#> ";		
	
		//gets the words entered by user
		ArrayList<String> userWords = getUserWords();
		
		//test on some words then deduce semantic
		
			//contains FILM
			if(userWords.contains("Film")){
				//First_Name Last_Name date
				
				String properties = "{ ?sub sem:First_Name ?lit .}"
								   +" UNION { ?sub sem:Last_Name ?lit .} "
								   +" UNION { ?sub sem:date ?lit .}"
								   +" UNION { ?sub sem:Age ?lit .}"
								   +" UNION { ?sub sem:title ?lit .} "
								   +" UNION { ?sub sem:Type_Film ?lit .}";
				String filters ="";
				
				//we consider all the words except the word FILM which is the class
				for(String user : userWords){					
					if(! user.equalsIgnoreCase("film") ){
						filters += filters +"FILTER regex(?lit,'"+user+"', 'i' )";
					}
				}				
				
				//for test with select/construct
					queryString += queryString  + " CONSTRUCT { ?sub ?prop ?lit }"
												+ " WHERE {"							
												+ " ?sub ?prop ?lit . "							
												+ properties
												+ filters											    									 
												+ "}";				
			
			}//end FILM
			
			//contains PRODUCER-- maison de production
			else if(userWords.contains("Producer")){
				//name
					
				String properties = "{ ?sub sem:name ?lit .}"
								    +" UNION { ?sub sem:date ?lit .}"								    
									+" UNION { ?sub sem:title ?lit .} "
									+" UNION { ?sub sem:Type_Film ?lit .}";									   
				String filters ="";
					
				//we consider all the words except the word FILM which is the class
				for(String user : userWords){						
					if(! user.equalsIgnoreCase("producer") ){
						filters += filters +"FILTER regex(?lit,'"+user+"', 'i' )";
					}
				}
					
				queryString += queryString  + " CONSTRUCT { ?sub ?prop ?lit }"
											+ " WHERE {"							
											+ " ?sub ?prop ?lit . "							
											+ properties
											+ filters											    									 
											+ "}";
				
			}//end PRODUCER	
			
			else if(userWords.contains("Actor")){
				
				String properties = "{ ?sub sem:First_Name ?lit }"
									   +" UNION { ?sub sem:Last_Name ?lit .} "
									   +" UNION { ?sub sem:Age ?lit .}"
									   +" UNION { ?sub sem:date ?lit .}"
									   +" UNION { ?sub sem:title ?lit .} "
									   +" UNION { ?sub sem:Type_Film ?lit .}";
				String filters ="";
					
				//we consider all the words except the word FILM which is the class
				for(String user : userWords){						
					if(! user.equalsIgnoreCase("Actor") ){
						filters += filters +"FILTER regex(?lit,'"+user+"', 'i' )";
					}
				}
					
				queryString += queryString  + "CONSTRUCT{ ?sub ?prop ?lit }"
											+ " WHERE {"							
											+ " ?sub ?prop ?lit . "							
											+ properties
											+ filters											    									 
											+ "}";
				
			}//end ACTOR
			
			else if(userWords.contains("Film") && userWords.contains("Maker")){
				
				String properties = "{ ?sub sem:First_Name ?lit }"
									 +" UNION { ?sub sem:Last_Name ?lit .} "
									 +" UNION { ?sub sem:Age ?lit .}"
									 +" UNION { ?sub sem:date ?lit .}"
									 +" UNION { ?sub sem:title ?lit .} "
									 +" UNION { ?sub sem:Type_Film ?lit .}";
				String filters ="";
					
				//we consider all the words except the word FILM which is the class
				for(String user : userWords){						
					if(!user.equalsIgnoreCase("film") && !user.equalsIgnoreCase("maker") ){
						filters += filters +"FILTER regex(?lit,'"+user+"', 'i' )";
					}
				}
					
				queryString += queryString  + "CONSTRUCT{ ?sub ?prop ?lit }"
											+ " WHERE {"							
											+ " ?sub ?prop ?lit . "							
											+ properties
											+ filters											    									 
											+ "}";
				
			}//end FILM MAKER
			
			else {
				// no of the class words in
				//System.out.println(" You have to enter a class keyword");
				return "ko";
			}
		
		return queryString;
	}
	
	/**
	 * function that execute the SPARQL query
	 * 
	 * @param queryString The query string to execute
	 */
	public static void executeQuerySPARQL(String queryString){
		
		Query query = QueryFactory.create(queryString) ;
		QueryExecution qexec = QueryExecutionFactory.create(query, ProjectFrame.ModelSparql);			 

		try {					  
			//ResultSet results = qexec.execSelect();
			Model resultModel = qexec.execConstruct() ;		
			Graph sparqlGraph = new SingleGraph("GraphSparql");

			//System.out.println("model sparql a:"+resultModel.size()+" "+"noeuds");
			
			@SuppressWarnings("unused")
			SousGraph resultSparqlGraph = new SousGraph(sparqlGraph,ProjectFrame.onglet,resultModel,false);
			
			//output query result <-- select query
			//ResultSetFormatter.out(System.out, results, query);					
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{					  
			qexec.close();
		}
	}
}
