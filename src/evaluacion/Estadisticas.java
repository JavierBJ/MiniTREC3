package evaluacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Estadisticas {
	
	/**
	 * Devuelve un array {P,R,F1} donde P es la precision, R es el recall y F1 es el F1 para
	 * un b determinado.
	 */
	public static double[] calcularStats(HashMap<String, String> relevancias, 
			ArrayList<String> resultados, double b) {
		int TP = 0;
		int FP = 0;
		int FN = 0;
		double P = 0;
		double R = 0;
		double F1 = 0;
		
		Iterator<Map.Entry<String,String>> it = relevancias.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String,String> pair = (Map.Entry<String,String>)it.next();
	        String clave = (String) pair.getKey();
	        String valor = (String) pair.getValue();
	        /* Calcula los true positive, false positive y false negative */
	        if(resultados.contains(clave) && Integer.parseInt(valor) == 1){
	        	TP++;
	        } else if(resultados.contains(clave) && Integer.parseInt(valor) == 0){
	        	FP++;
	        } else if(!resultados.contains(clave) && Integer.parseInt(valor) == 1){
	        	FN++;
	        }
	    }
	    
	    P = (TP+FP==0) ? (P = 0) : (P = TP / (double) (TP + FP));
	    R = (TP+FN == 0) ? (R = 0) : (R = TP / (double) (TP + FN));
	    F1 = (P==0 && R==0) ? (F1 = 0) : (F1 = ((Math.pow(b, 2)+1)*P*R) / ((Math.pow(b, 2))*P + R));	    
	    return new double[] {P,R,F1};
	}
	
	/**
	 * Calcula la precision a k, donde k es el numero de documentos
	 * recuperados que se analizan.
	 */
	public static double calcularPrec(HashMap<String, String> relevancias, ArrayList<String> resultados,
			int k) {
		int V = 0;
		int F = 0;
		/* Si hay menos documentos que k, hace la media por k */
		if (resultados.size() < k) {
			for (int i=0; i<resultados.size(); i++) {
				if (relevancias.get(resultados.get(i)).equals("1")) {
					V++;
				}
			}
			return (double) V / k;
		} else {
			for (int i = 0 ; i < k; i++){
				String res = relevancias.get(resultados.get(i));
				if(res==null || res.equals("0")){
					F++;
				} else {
					V++;
				}
			}
			return V / (double) (V + F);
		}
	}
	
	/**
	 * Calcula la precision media, es decir, la media de las precisiones para
	 * cada vez que se recupera un documento relevante.
	 */
	public static double calcularAvgPrec(HashMap<String, String> relevancias, ArrayList<String> resultados) {
		double acumulado = 0;
		int relevantesRec = 0;
		int recuperados = 0;
		double[] precisiones = new double[resultados.size()];
		
		if (resultados.size()==0) {
			return 0;
		} else {
			/* Calcula la precision acumulada cada vez que se recupera un documento relevante */
			for (int i = 0 ; i < resultados.size(); i++){
				recuperados++;
				if (relevancias.containsKey(resultados.get(i)) &&
						relevancias.get(resultados.get(i)).equals("1")) {
					relevantesRec++;
					precisiones[i] = (double) relevantesRec / recuperados;
				}				
			}
			
			/* Calcula la media de precisiones calculadas */
			for (int i=0; i < precisiones.length; i++) {
				acumulado += precisiones[i];
			}
			
			if (relevantesRec == 0) {
				return 0;
			} else return (double) acumulado / relevantesRec;
		}		
	}
	
	/**
	 * Calcula los pares recall-precision en un ArrayList. Cada elemento contiene
	 * el par representado como "recall espacio precision"
	 */
	public static ArrayList<String> calcularRecallPrecision(HashMap<String, String> relevancias, 
			ArrayList<String> resultados) {
		ArrayList<String> res = new ArrayList<String>();
		int totalRelevantes = Collections.frequency(new ArrayList<String>(relevancias.values()), "1");
		int relevantesYRecuperados = 0;
		
		for (int i = 0 ; i < resultados.size(); i++){
			/* Comprueba si el elemento es relevante */
			if (relevancias.containsKey(resultados.get(i)) &&
					relevancias.get(resultados.get(i)).equals("1")) {
				relevantesYRecuperados++;
				double recall = (double) relevantesYRecuperados / totalRelevantes;			
				double precision = (double) relevantesYRecuperados / (i+1);
				res.add(recall + " " + precision);
			}
		}
		return res;
	}
	
	/**
	 * Devuelve la precision maxima encontrada para un recall
	 * mayor o igual que x.
	 */
	public static double getMaxPrecision(ArrayList<Double> recalls,
			ArrayList<Double> precisiones, double x) {
		double max = 0;
		for (int i=0; i<recalls.size(); i++) {
			if (x <= recalls.get(i) && precisiones.get(i)>max) {
				max = precisiones.get(i);
			}
		}
		return max;
	}
	
}
