package minitrec;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

public class SemanticSearcher {
	
	public static void main(String[] args) {
		String rdfPath = null;
		String rdfsPath = null;
		String infoNeeds = null;
		String output = null;
		
		for (int i = 0; i < args.length; i++) {
			if ("-rdf".equals(args[i])) {
				rdfPath = args[i + 1];
				i++;
			} else if ("-rdfs".equals(args[i])) {
				rdfsPath = args[i + 1];
				i++;
			} else if ("-infoNeeds".equals(args[i])) {
				infoNeeds = args[i + 1];
				i++;
			} else if ("-output".equals(args[i])) {
				output = args[i + 1];
				i++;
			}
		}
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(new File(output)));
		} catch (IOException e1) {
			System.out.println("Error: problema con fichero de salida.");
			System.exit(1);
		}
		
		Model model = FileManager.get().loadModel(rdfPath);
        Model rdfsModel = FileManager.get().loadModel(rdfsPath);
        Model skosModel = FileManager.get().loadModel("skos.rdf");
        model.add(rdfsModel);
        model.add(skosModel);
        
        ArrayList<String> queryStrings = new ArrayList<String>();
        ArrayList<String> queryCodes = new ArrayList<String>();
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(infoNeeds)));
			String consulta = reader.readLine();
	        while (consulta != null) {
	        	queryCodes.add(consulta.substring(0, consulta.indexOf(' ')));
	        	queryStrings.add(consulta.substring(consulta.indexOf(' ')));
	        	
	        	consulta = reader.readLine();
	        }
		} catch (FileNotFoundException e) {
			System.out.println("Error: fichero de infoNeeds no encontrado.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Error: fichero de infoNeeds incorrecto.");
			System.exit(1);
		}
        
        
        for (int i=0; i<queryStrings.size(); i++) {      	
        	Query query = QueryFactory.create(queryStrings.get(i));
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            try {
                ResultSet results = qexec.execSelect();
                for (; results.hasNext();) {
                    String sentencia = "";
                    QuerySolution soln = results.nextSolution();
                    Resource x = soln.getResource("x");
                    sentencia += x.getURI();
                    sentencia = sentencia.substring(sentencia.lastIndexOf("/")+1);
                    sentencia = "oai_zaguan.unizar.es_" + sentencia + ".xml"; 
                   
                    writer.println(queryCodes.get(i) + "\t" + sentencia);
     
                }
            } finally {
                qexec.close();
            }
        }
	}
	
}
