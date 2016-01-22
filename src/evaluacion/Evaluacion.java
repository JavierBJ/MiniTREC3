package evaluacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Evaluacion {
	
	private static int MAX_DOCS = 50;
	private static double B = 1;
	
	/**
	 * Dados los juicios y los resultados, evalua el
	 * sistema de recuperacion de informacion.
	 */
	public static void main(String[] args) {
		String juiciosP = "";
		String resultadosP = "";
		String salidaP = "";

		/* Obtiene los ficheros que recibe el programa */
		for (int i = 0; i < args.length; i++) {
			if ("-qrels".equals(args[i])) {
				juiciosP = args[i + 1];
				i++;
			} else if ("-results".equals(args[i])) {
				resultadosP = args[i + 1];
				i++;
			} else if ("-output".equals(args[i])) {
				salidaP = args[i + 1];
				i++;
			}
		}
		
		/* Crea el escritor */
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter(salidaP));
			
		} catch (IOException e1) {
			System.out.println("Error con el fichero de salida");
			System.exit(1);
		}
		
		/* Obtiene los resultados para cada necesidad */
		ArrayList<String> necesidades = new ArrayList<String>();
		HashMap<String, String> relevancias = new HashMap<String, String>();
		ArrayList<String> resultados = new ArrayList<String>();
		try {
			necesidades = leerNecesidades(juiciosP);
		} catch(FileNotFoundException e) {
			System.out.println("Error al leer los ficheros");
			System.exit(1);
		}
		
		/* Crea las variable que almacenan los resultados */
		int numNec = necesidades.size();
		double[] prec = new double[numNec];
		double[] recall = new double[numNec];
		double[] f1 = new double[numNec];
		double[] precA10 = new double[numNec];
		double[] avgPrec = new double[numNec];
		ArrayList<double[]> global = new ArrayList<double[]>();
		
		for (int i=0; i<numNec; i++) {
			String necesidad = necesidades.get(i);
			/* Pasa el contenido de los ficheros a estructuras de datos */
			try {
				relevancias = mapearJuicios(juiciosP, necesidad);
				resultados = mapearResultados(resultadosP, necesidad, MAX_DOCS);
			} catch(FileNotFoundException e) {
				System.out.println("Error al leer los ficheros");
				System.exit(1);
			}
			
			/* Calcula P, R, F1 */
			double[] stats = Estadisticas.calcularStats(relevancias, resultados, B);
			prec[i] = stats[0];
			recall[i] = stats[1];
			f1[i] = stats[2];
			
			/* Calcula PrecA10, AvgPrec */
			precA10[i] = Estadisticas.calcularPrec(relevancias, resultados, 10);
			avgPrec[i] = Estadisticas.calcularAvgPrec(relevancias, resultados);
			
			/* Calcula recall-precision */
			ArrayList<String> rp = Estadisticas.calcularRecallPrecision(relevancias, resultados);
			ArrayList<Double> recalls = new ArrayList<Double>();
			ArrayList<Double> precisiones = new ArrayList<Double>();
			for (String par: rp) {
				String[] aux1 = par.split(" ");
				recalls.add(Double.parseDouble(aux1[0]));
				precisiones.add(Double.parseDouble(aux1[1]));
			}
			
			/* Calcula la curva recall-precision interpolada */
			double[] xInter = new double[11];
			double[] yInter = new double[11];
			for (int j=0; j<=10; j++) {
				xInter[j] = (double) j / 10;
				yInter[j] = Estadisticas.getMaxPrecision(recalls, precisiones, xInter[j]);
				global.add(yInter);
			}
			
			/* Escribe en el fichero todos los datos */
			writer.println("INFORMATION NEED\t" + necesidad);
			write(writer, "precision", prec[i]);
			write(writer, "recall", recall[i]);
			write(writer, "F1", f1[i]);
			write(writer, "prec@10", precA10[i]);
			write(writer, "average_precision", avgPrec[i]);
			writer.println("recall_precision");
			for (int k=0; k<recalls.size(); k++) {
				write(writer, recalls.get(k), precisiones.get(k));
			}
			writer.println("interpolated_recall_precision");
			for (int k=0; k<xInter.length; k++) {
				write(writer, xInter[k], yInter[k]);
			}			
			writer.println();
		}
		
		/* Calcula la precision global */
		writer.println("TOTAL");
		double precGlobal = getPromedio(prec);
		write(writer, "precision", precGlobal);
		
		/* Calcula el recall global */
		double recallGlobal = getPromedio(recall);
		write(writer, "recall", recallGlobal);
		
		/* Calcula el f1 global */
		double f1Global = ((Math.pow(B, 2)+1) * precGlobal * recallGlobal) /
				(Math.pow(B, 2)*precGlobal + recallGlobal);
		write(writer, "F1", f1Global);
		
		/* Calcula la precision a 10 global */
		double precA10Global = getPromedio(precA10);
		write(writer, "prec@10", precA10Global);
		
		/* Calcula el MAP (mean average precision) */
		double MAP = getPromedio(avgPrec);
		write(writer, "MAP", MAP);
		
		/* Calcula el recall precision interpolado */
		double[] curva = new double[11];
		for (int i=0; i<curva.length; i++) {
			double valor = 0;
			for (int j=0; j<global.size(); j++) {
				valor += global.get(j)[i];
			}
			curva[i] = valor / global.size();	
		}
		
		writer.println("interpolated_recall_precision");
		for (int i=0; i<=10; i++) {
			write(writer, (double)i/10, curva[i]);
		}
		
		writer.close();
	}
	
	/**
	 * Devuelve una lista con todas las necesidades (sin repetir)
	 * a evaluar en el sistema.
	 */
	private static ArrayList<String> leerNecesidades(String juiciosP) 
			throws FileNotFoundException {
		Scanner lector = new Scanner(new File(juiciosP));
		ArrayList<String> arr = new ArrayList<String>();
		while (lector.hasNextLine()) {
			String[] linea = lector.nextLine().split("\t");
			if (!arr.contains(linea[0])) {
				arr.add(linea[0]);
			}
		}
		lector.close();
		return arr;
	}
	
	/**
	 * Carga los resultados del sistema en un ArrayList que contiene la necesidad
	 * y el documento en cada entrada.
	 */
	private static ArrayList<String> mapearResultados(String resultadosP, String necesidad, int max)
			throws FileNotFoundException {
		Scanner resultadosR = new Scanner(new File(resultadosP));
		ArrayList<String> arr = new ArrayList<String>();
		while(resultadosR.hasNextLine() && arr.size()<50){
			String linea = resultadosR.nextLine();
			String[] campos = linea.split("\t");
			if (campos[0].equals(necesidad)) {
				arr.add(campos[0] + " " + campos[1]);
			}			
		}		
		resultadosR.close();
		return arr;
	}
	
	/**
	 * Carga los juicios en un HashMap cuyas claves son la necesidad y el documento, y
	 * cuyos valores son un 1 si es relevante y un 0 si no lo es.
	 */
	private static HashMap<String, String> mapearJuicios(String juiciosP, String necesidad)
			throws FileNotFoundException {
		Scanner juiciosR = new Scanner(new File(juiciosP));
		HashMap<String, String> relevancias = new HashMap<String, String>();
		while(juiciosR.hasNextLine()){
			String linea = juiciosR.nextLine();
			String[] campos = linea.split("\t");
			if (campos[0].equals(necesidad)) {
				relevancias.put(campos[0] + " " + campos[1], campos[2]);
			}		
		}		
		juiciosR.close();
		return relevancias;
	}
	
	/**
	 * Escribe una medida en el fichero, de modo que el valor tenga 3
	 * decimales y el separador sea una coma.
	 */
	private static void write(PrintWriter writer, String text, double num) {
		DecimalFormat df = new DecimalFormat("#.###");
		String s = df.format(num);
		s = s.replace(".", ",");
		writer.println(text + "\t" + s);
	}
	
	/**
	 * Escribe un par recall-precision en el fichero, de modo que cada valor
	 * tenga 3 decimales y el separador sea una coma.
	 */
	private static void write(PrintWriter writer, double num1, double num2) {
		DecimalFormat df = new DecimalFormat("#.###");
		String s1 = df.format(num1);
		s1 = s1.replace(".", ",");
		String s2 = df.format(num2);
		s2 = s2.replace(".", ",");
		writer.println(s1 + "\t" + s2);
	}
	
	/**
	 * Dado un vector, calcula la media de sus elementos.
	 */
	private static double getPromedio(double[] lista) {
		double suma = 0;
		for (int i=0; i<lista.length; i++) {
			suma += lista[i];
		}
		return suma / lista.length;
	}
	
}
