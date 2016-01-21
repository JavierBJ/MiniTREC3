package minitrec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.w3c.dom.NodeList;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.xml.sax.SAXException;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * Genera el grafo RDF de la coleccion
 */
public class SemanticGenerator {

	private static final String SUBJECT_URI = "http://minitrec/subject";
	private static final String DOCUMENTO_URI = "http://minitrec/documento";
	private static final String TFG_URI = "http://minitrec/tfg";
	private static final String TFM_URI = "http://minitrec/tfm";
	private static final String PFC_URI = "http://minitrec/pfc";
	private static final String TESIS_URI = "http://minitrec/tesis";

	private static HashMap<String, String> listaCreadores = new HashMap<>();
	private static Property subject;
	private static Resource recursoTFG;
	private static Resource recursoTFM;
	private static Resource recursoPFC;
	private static Resource recursoTesis;
	private static Resource recursoDocumento;
	
	
	public static void main(String[] args) {
		/*String rdfPath = null;
		String skosPath = null;
		String docsPath = null;
		
		for (int i = 0; i < args.length; i++) {
			if ("-skos".equals(args[i])) {
				skosPath = args[i + 1];
				i++;
			} else if ("-docs".equals(args[i])) {
				docsPath = args[i + 1];
				i++;
			} else if ("-rdf".equals(args[i])) {
				rdfPath = args[i + 1];
				i++;
			}
		}*/
		String rdfPath = "";
		String skosPath = "skos.rdf";
		String docsPath = "recordsdc";
		
		if ((rdfPath==null) || (skosPath==null) || (docsPath==null)) {
			System.out.println("Error en la introduccion de parametros.");
			System.exit(1);
		}
		
		File docsDirectory = new File(docsPath);
		try {
			Model skos = FileManager.get().loadModel(skosPath);
			Model model = ModelFactory.createDefaultModel();
			subject = model.createProperty(SUBJECT_URI);
			recursoTFG = model.createResource(TFG_URI);
			recursoTFM = model.createResource(TFM_URI);
			recursoPFC = model.createResource(PFC_URI);
			recursoTesis = model.createResource(TESIS_URI);
			recursoDocumento = model.createResource(DOCUMENTO_URI);
			
			System.out.println("Metiendo rdfs");
			model = generateRDF(docsDirectory, model, skos);
			
			/* Guarda el modelo en un fichero XML*/
			try {
				model.write(new FileOutputStream(new File("model.rdf")));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Metidos");
		} catch (IOException e) {
			System.out.println("Error generando rdf.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Realiza la indexacion a partir de una carpeta de ficheros xml.
	 */
	static Model generateRDF(File path, Model model, Model skos) throws IOException {
		
		String[] files = path.list();
		for (int j=0; j<files.length; j++) {
			File file = new File(path + "/" + files[j]);
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException fnfe) {
				System.out.println("Error en " + j);
				throw new IOException();
			}
			
			try {
				/* Parsea un documento xml para poder acceder a su contenido */
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder parser;
				org.w3c.dom.Document docParseado = null;
				parser = dbf.newDocumentBuilder();
				docParseado = parser.parse(file);
				
				String type = "";
				String uri = "";
				
				/* Obtiene la URI y el tipo de documento a traves del tag dc:identifier */
				NodeList list = docParseado.getElementsByTagName("dc:identifier");
				for (int i=0; i<list.getLength(); i++) {
					String element = list.item(i).getTextContent();
					if (element.startsWith("http")) {
						uri = element;
					} else if (!element.startsWith("oai:")) {
						type = checkType(element);
					}
				}
				
				/* Obtiene los campos principales del documento */
				String title = docParseado.getElementsByTagName("dc:title").item(0).getTextContent();
				String date = docParseado.getElementsByTagName("dc:date").item(0).getTextContent();
				String description = docParseado.getElementsByTagName("dc:description").item(0).getTextContent();
				
				NodeList creatorsNodes = docParseado.getElementsByTagName("dc:creator");
				ArrayList<String> creators = new ArrayList<>();
				for (int i=0; i<creatorsNodes.getLength(); i++) {
					creators.add(creatorsNodes.item(i).getTextContent());
				}
				
				/* Crea el recurso Documento */
				Resource document = model.createResource(uri)
						.addProperty(DC.title, title)
						.addProperty(DC.date, date)
						.addProperty(DC.description, description);
				
				if (type.equals("tfg")) {
					document.addProperty(RDF.type, recursoTFG);
				} else if (type.equals("tfm")) {
					document.addProperty(RDF.type, recursoTFM);
				} else if (type.equals("pfc")) {
					document.addProperty(RDF.type, recursoPFC);
				} else if (type.equals("tesis")) {
					document.addProperty(RDF.type, recursoTesis);
				} else {
					document.addProperty(RDF.type, recursoDocumento);
				}
				
				/* Recorre todos los terminos del modelo skos */
				ResIterator it = skos.listSubjectsWithProperty(RDFS.label);
				while (it.hasNext()) {
					Resource res = it.next();
					Statement st = res.getProperty(RDFS.label);
					
					String label = "";
					if (st.getObject().isLiteral()) {
						label = st.getLiteral().toString();
					} else {
						label = st.getResource().getURI();
					}
					
					/* Si el termino skos aparece en la descripcion del documento, lo mete como subject */
					if (description.contains(label.trim().toLowerCase())) {
						document.addProperty(subject, res);
					}
				}
				
				for (String creator : creators) {
					if (listaCreadores.containsKey(creator)) {
						document.addProperty(DC.creator, listaCreadores.get(creator));
					} else {
						String personURI = "http://imred.es/" + creator.replace(" ", "").replace(",", "");
						Resource person = model.createResource(personURI)
								.addProperty(FOAF.name, creator);
						listaCreadores.put(creator, personURI);
						document.addProperty(DC.creator, person);
					}
				}
				
			} catch (ParserConfigurationException | SAXException e) {
				e.printStackTrace();
			} finally {
				fis.close();
			}
		}
		System.out.println("done");
		return model;
	}
	
	private static String checkType(String identifier) {
		if (identifier.contains("TFG")) {
			return "tfg";
		} else if (identifier.contains("TFM")) {
			return "tfm";
		} else if (identifier.contains("PFC")) {
			return "pfc";
		} else if (identifier.contains("TESIS")) {
			return "tesis";
		} else return "documento";
	}
	
}
