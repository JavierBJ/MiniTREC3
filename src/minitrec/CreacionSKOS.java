package minitrec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class CreacionSKOS {
	
	private static String skosConceptURI = "http://www.w3.org/2004/02/skos/core#Concept";

	public static void main(String[] args) {
		/* Crea un nuevo modelo vacio */
		Model model = ModelFactory.createDefaultModel();
		
		/* Recursos sobre musica y sonido */
		
		Resource musica = model.createResource("http://imred.es/musica")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Musica");
		
		Resource sonido = model.createResource("http://imred.es/sonido")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Sonido");
		
		Resource ritmo = model.createResource("http://imred.es/ritmo")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Ritmo");
		
		Resource melodia = model.createResource("http://imred.es/melodia")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Melodía");
		
		Resource armonia = model.createResource("http://imred.es/armonia")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Armonía");
		
		Resource emocion = model.createResource("http://imred.es/emocion")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Emoción");
		
		Resource ruido = model.createResource("http://imred.es/ruido")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Ruido");
		
		Resource cancion = model.createResource("http://imred.es/cancion")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Canción");
		
		/* Recursos sobre la Guerra de la Independencia */
		
		Resource guerraIndependencia = model.createResource("http://imred.es/guerraIndependencia")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Guerra de la Independencia");
		
		Resource historiaEspana = model.createResource("http://imred.es/historiaEspana")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Historia de España");
		
		Resource ejercito = model.createResource("http://imred.es/ejercito")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Ejército");
		
		Resource tropasFrancesas = model.createResource("http://imred.es/tropasFrancesas")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Tropas Francesas");
		
		Resource guerrilla = model.createResource("http://imred.es/guerrilla")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Guerrilla");
		
		/* Recursos sobre energias renovables */
		
		Resource energiaRenovable = model.createResource("http://imred.es/energiaRenovable")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Energía renovable");
		
		Resource panel = model.createResource("http://imred.es/panel")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Panel");
		
		Resource solar = model.createResource("http://imred.es/solar")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Solar");
		
		Resource calor = model.createResource("http://imred.es/calor")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Calor");
		
		Resource medioAmbiente = model.createResource("http://imred.es/medioAmbiente")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Medio Ambiente");
		
		Resource bombaHidraulica = model.createResource("http://imred.es/bombaHidraulica")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Bomba Hidráulica");
		
		Resource energiaTermica = model.createResource("http://imred.es/energiaTermica")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Energia Térmica");
		
		Resource eolico = model.createResource("http://imred.es/eolico")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Eólico");
		
		/* Recursos sobre videojuegos */
		
		Resource videojuego = model.createResource("http://imred.es/videojuego")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Videojuego");
		
		Resource disenoPersonajes = model.createResource("http://imred.es/disenoPersonajes")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Diseño de personaje");
		
		Resource desarrollo = model.createResource("http://imred.es/desarrollo")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Desarrollo");

		Resource diseno = model.createResource("http://imred.es/diseno")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Diseño");

		Resource personaje = model.createResource("http://imred.es/personaje")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Personaje");

		Resource agente = model.createResource("http://imred.es/agente")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Agente");

		Resource motorGrafico = model.createResource("http://imred.es/motorGrafico")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Motor Gráfico");

		Resource virtual = model.createResource("http://imred.es/virtual")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Virtual");

		Resource tresD = model.createResource("http://imred.es/3d")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "3D");

		Resource jugabilidad = model.createResource("http://imred.es/jugabilidad")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Jugabilidad");
		
		/* Recursos sobre arquitectura */
		
		Resource arquitectura = model.createResource("http://imred.es/arquitectura")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Arquitectura");
		
		Resource espana = model.createResource("http://imred.es/espana")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "España");
		
		Resource edadMedia = model.createResource("http://imred.es/edadMedia")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Edad Media");
		
		Resource gotico = model.createResource("http://imred.es/gotico")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Gótico");
		
		Resource renacimiento = model.createResource("http://imred.es/renacimiento")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Renacimiento");
		
		Resource oligarquia = model.createResource("http://imred.es/oligarquia")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Oligarquía");
		
		Resource patrimonio = model.createResource("http://imred.es/patrimonio")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Patrimonio");
		
		Resource levantamiento = model.createResource("http://imred.es/levantamiento")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Levantamiento");
		
		Resource iglesia = model.createResource("http://imred.es/iglesia")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Iglesia");
		
		Resource aldea = model.createResource("http://imred.es/aldea")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Aldea");
		
		Resource castillo = model.createResource("http://imred.es/castillo")
				.addProperty(RDF.type, skosConceptURI)
				.addProperty(RDFS.label, "Castillo");
		
		Property narrower = model.createProperty("http://www.w3.org/2004/02/skos/core#narrower");
		Property broader = model.createProperty("http://www.w3.org/2004/02/skos/core#broader");
		Property related = model.createProperty("http://www.w3.org/2004/02/skos/core#related");
		
		/* Relaciones entre conceptos de musica y sonido */
		musica.addProperty(broader, sonido);
		sonido.addProperty(narrower, musica);
		ruido.addProperty(broader, sonido);
		sonido.addProperty(narrower, ruido);
		ritmo.addProperty(broader, musica);
		musica.addProperty(narrower, ritmo);
		ritmo.addProperty(related, melodia);
		melodia.addProperty(related, ritmo);
		melodia.addProperty(broader, musica);
		musica.addProperty(narrower, melodia);
		armonia.addProperty(related, musica);
		musica.addProperty(related, armonia);
		emocion.addProperty(related, musica);
		musica.addProperty(related, emocion);
		cancion.addProperty(related, musica);
		musica.addProperty(related, cancion);
		
		/* Relaciones entre conceptos de guerra de la independencia */
		guerraIndependencia.addProperty(broader, historiaEspana);
		historiaEspana.addProperty(narrower, guerraIndependencia);
		ejercito.addProperty(related, guerraIndependencia);
		guerraIndependencia.addProperty(related, ejercito);
		tropasFrancesas.addProperty(related, guerraIndependencia);
		guerraIndependencia.addProperty(related, tropasFrancesas);
		ejercito.addProperty(related, tropasFrancesas);
		tropasFrancesas.addProperty(related, ejercito);
		guerrilla.addProperty(related, guerraIndependencia);
		guerraIndependencia.addProperty(related, guerrilla);
		guerrilla.addProperty(related, ejercito);
		ejercito.addProperty(related, guerrilla);
		
		/* Relaciones sobre energias renovables */
		panel.addProperty(related, solar);
		solar.addProperty(related, panel);
		solar.addProperty(broader, energiaRenovable);
		energiaRenovable.addProperty(narrower, solar);
		medioAmbiente.addProperty(related, energiaRenovable);
		energiaRenovable.addProperty(related, medioAmbiente);
		energiaTermica.addProperty(related, energiaRenovable);
		energiaRenovable.addProperty(related, energiaTermica);
		eolico.addProperty(broader, energiaRenovable);
		energiaRenovable.addProperty(narrower, eolico);
		bombaHidraulica.addProperty(related, energiaRenovable);
		energiaRenovable.addProperty(related, bombaHidraulica);
		calor.addProperty(related, energiaRenovable);
		energiaRenovable.addProperty(related, calor);
		calor.addProperty(related, energiaTermica);
		energiaTermica.addProperty(related, calor);
		
		/* Relaciones sobre videojuegos */
		diseno.addProperty(narrower, disenoPersonajes);
		disenoPersonajes.addProperty(broader, diseno);
		disenoPersonajes.addProperty(broader, videojuego);
		videojuego.addProperty(narrower, disenoPersonajes);
		virtual.addProperty(related, videojuego);
		videojuego.addProperty(related, virtual);
		desarrollo.addProperty(related, videojuego);
		videojuego.addProperty(related, desarrollo);
		personaje.addProperty(related, videojuego);
		videojuego.addProperty(related, personaje);
		motorGrafico.addProperty(related, videojuego);
		videojuego.addProperty(related, motorGrafico);
		tresD.addProperty(related, videojuego);
		videojuego.addProperty(related, tresD);
		jugabilidad.addProperty(related, videojuego);
		videojuego.addProperty(related, jugabilidad);
		
		/* Relaciones sobre arquitectura */
		espana.addProperty(related, arquitectura);
		arquitectura.addProperty(related, espana);
		iglesia.addProperty(related, arquitectura);
		arquitectura.addProperty(related, iglesia);
		edadMedia.addProperty(related, arquitectura);
		arquitectura.addProperty(related, edadMedia);
		oligarquia.addProperty(related, edadMedia);
		edadMedia.addProperty(related, oligarquia);
		levantamiento.addProperty(related, edadMedia);
		edadMedia.addProperty(related, levantamiento);
		renacimiento.addProperty(related, arquitectura);
		arquitectura.addProperty(related, renacimiento);
		patrimonio.addProperty(related, arquitectura);
		arquitectura.addProperty(related, patrimonio);
		gotico.addProperty(broader, arquitectura);
		arquitectura.addProperty(narrower, gotico);
		castillo.addProperty(related, arquitectura);
		arquitectura.addProperty(related, castillo);
		aldea.addProperty(related, arquitectura);
		arquitectura.addProperty(related, aldea);
		
		/* Guarda el modelo en un fichero XML*/
		try {
			model.write(new FileOutputStream(new File("skos.rdf")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
