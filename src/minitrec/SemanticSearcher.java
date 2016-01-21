package minitrec;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

public class SemanticSearcher {
	
	public static void main(String[] args) {
		Model model = FileManager.get().loadModel("model.rdf");
        Model model2 = FileManager.get().loadModel("esquemaRDFS.rdf");
        Model model3 = FileManager.get().loadModel("skos.rdf");
        model.add(model2);
        model.add(model3);
        
        String[] queryStrings = new String[5];
        queryStrings[0] = "PREFIX rdf: <" + RDF.getURI() + "> PREFIX rdfs: <" + RDFS.getURI() + "> PREFIX mt: <http://minitrec/> " +
        		"PREFIX im: <http://imred.es/> PREFIX dc: <http://purl.org/dc/elements/1.1/> " + 
        		"PREFIX sk: <http://www.w3.org/2004/02/skos/core#> PREFIX foaf: <"+FOAF.getURI()+"> " +
        		"SELECT distinct ?x WHERE { ?x dc:creator ?y . ?y foaf:name ?z . FILTER regex(?z, 'javier', 'i') ." +
        		" ?x rdf:type ?tipo . ?tipo rdfs:subClassOf mt:documento ." +
        		" {?x mt:subject im:sonido} UNION {?x mt:subject im:musica} UNION {?x mt:subject ?relacionado . ?relacionado sk:related|sk:broader im:musica} " +
        		"UNION {?x mt:subject ?relacionado . ?relacionado sk:related|sk:broader im:sonido} }";
        
        queryStrings[1] = "";
        
        queryStrings[2] = "";
        
        queryStrings[3] = "";
        
        queryStrings[4] = "";
        
        for (int i=0; i<1; i++) {	//CAMBIAR i<1 cuando metas mas consultas
        	System.out.println(queryStrings[i]);
        	
        	Query query = QueryFactory.create(queryStrings[i]);
            QueryExecution qexec = QueryExecutionFactory.create(query, model);
            try {
                ResultSet results = qexec.execSelect();
                for (; results.hasNext();) {
                    String sentencia = "";
                    QuerySolution soln = results.nextSolution();
                    Resource x = soln.getResource("x");
                    sentencia += x.getURI() + " - ";
                   
                    System.out.println(sentencia);
     
                }
            } finally {
                qexec.close();
            }
            System.out.println("----------------------------------------");
        }
	}
	
}
