package mining;

import data.Data;
import data.Tuple;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * La classe Cluster modella un cluster (gruppo), definito da un centroide
 * e da un insieme di indici di tuple che appartengono a tale cluster.
 * Implementa Iterable<Integer> per permettere lo scorrimento delle tuple.
 * Implementa Comparable<Cluster> per confrontare i cluster in base alla dimensione.
 * Implementa Serializable per consentire il salvataggio su file.
 * @see Tuple
 * @see Data
 */
class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {

    /**
     * Il centroide del cluster, rappresentato da una Tupla.
     */
    private Tuple centroid;

    /**
     * Insieme degli indici (ID) delle tuple del dataset che appartengono a questo cluster.
     * Dichiarato transient: non viene salvato durante la serializzazione.
     */
    private transient Set<Integer> clusteredData;

    /**
     * Costruttore della classe Cluster.
     * Inizializza il centroide e crea il set vuoto di indici delle tuple.
     * @param centroid La tupla scelta come centroide del cluster.
     */
	public Cluster(Tuple centroid){
		this.centroid=centroid;
		clusteredData=new HashSet();
		
	}

    /**
     * Restituisce il centroide del cluster.
     * @return L'oggetto Tuple che rappresenta il centroide.
     */
	public Tuple getCentroid(){
		return centroid;
	}

    /**
     * Aggiunge l'indice di una tupla al set di dati clusterizzati.
     * @param id L'indice della tupla da aggiungere.
     * @return true se la tupla è stata aggiunta (cioè se è cambiata di cluster), false altrimenti.
     */
	public boolean addData(int id){
		return clusteredData.add(id);
		
	}

    /**
     * Verifica se una tupla, identificata dal suo indice, è contenuta nel cluster corrente.
     * @param id L'indice della tupla da controllare.
     * @return true se la tupla appartiene al cluster, false altrimenti.
     */
	public boolean contain(int id){
		return clusteredData.contains(id);
	}


    /**
     * Rimuove l'indice di una tupla dal set di dati clusterizzati.
     * Utilizzato quando una tupla cambia assegnazione di cluster.
     * @param id L'indice della tupla da rimuovere.
     */
	public void removeTuple(int id){
		clusteredData.remove(id);
		
	}

    /**
     * Restituisce la dimensione del cluster, ovvero il numero di tuple contenute.
     * @return La dimensione del cluster.
     */
	public int  getSize(){
		return clusteredData.size();
	}

    /**
     * Restituisce un iteratore per scorrere gli indici delle tuple che compongono il cluster.
     * @return L'iteratore sull'insieme di indici.
     */
	public Iterator<Integer> iterator(){
		return clusteredData.iterator();
	}

    /**
     * Confronta il cluster corrente con il cluster 'c' in base alla loro dimensione (numero di tuple).
     * @param c Il cluster con cui effettuare il confronto.
     * @return 1 se il cluster corrente è più grande di 'c', -1 altrimenti (compresi i casi di uguaglianza).
     */
	public int compareTo(Cluster c){
		if(this.getSize()>c.getSize()){
			return 1;
		}else {
			return -1;
		}
	}

    /**
     * Restituisce una rappresentazione testuale sintetica del cluster, contenente solo la tupla che funge da centroide.
     * @return Una stringa formattata con il centroide.
     */
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i);
		str+=")";
		return str;
		
	}

    /**
     * Restituisce una rappresentazione testuale completa del cluster.
     * Include il centroide, la lista degli indici delle tuple appartenenti al cluster
     * con la loro distanza dal centroide e la distanza media del cluster.
     * @param data Il dataset completo per recuperare i valori delle tuple.
     * @return Una stringa formattata contenente tutti i dettagli del cluster.
     */
	public String toString(Data data){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";

		for(Integer id : clusteredData){
			str+="[";
			for(int j=0;j<data.getNumberOfAttributes();j++)
				str+=data.getValue(id, j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(id))+"\n";
		}

		str+="\nAvgDistance="+getCentroid().avgDistance(data, clusteredData);
		return str;
		
	}

}
