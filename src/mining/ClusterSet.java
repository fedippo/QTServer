package mining;

import data.Data;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * La classe ClusterSet rappresenta l'insieme finale dei cluster (gruppi) scoperti dall'algoritmo di mining.
 * Mantiene i cluster in un TreeSet, in cui i cluster vengono mantenuti ordinati
 * secondo la loro dimensione grazie all'implementazione di Comparable nella classe Cluster.
 * Implementa Iterable<Cluster> per facilitare lo scorrimento dei cluster.
 * Implementa Serializable per consentire il salvataggio su file.
 */
public class ClusterSet implements Iterable<Cluster>, Serializable {

    /**
     * Insieme ordinato di oggetti Cluster, ordinati in base alla loro dimensione.
     */
    private Set<Cluster> C = new TreeSet<>();

    /**
     * Costruttore di default. Inizializza l'insieme dei cluster come un TreeSet vuoto.
     */
    public ClusterSet(){}

    /**
     * Aggiunge un cluster all'insieme.
     * Poiché C è un TreeSet, il cluster verrà inserito mantenendo l'ordinamento in base alla dimensione.
     * @param c Il cluster da aggiungere.
     */
    public void add(Cluster c){
        C.add(c);
    }

    /**
     * Restituisce un iteratore per scorrere l'insieme dei cluster.
     * I cluster vengono restituiti in ordine di dimensione (dall'elemento più piccolo a quello più grande).
     * @return L'iteratore sull'insieme dei cluster.
     */
    @Override
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }

    /**
     * Restituisce una rappresentazione testuale sintetica dell'insieme dei cluster.
     * La stringa contiene l'indice progressivo del cluster
     * e la sua rappresentazione sintetica (solo il centroide), ottenuta chiamando Cluster.toString().
     * @return Una stringa formattata con la lista dei centroidi.
     */
    public String toString() {
        String str = "";
        int i=1;
        for (Cluster c : this) {
            if (c != null) {
                str += i + ":" + c.toString() + "\n";
            }
            i++;
        }
        return str;
    }

    /**
     * Restituisce una rappresentazione testuale dettagliata dell'insieme dei cluster.
     * Per ogni cluster, viene richiamato il metodo Cluster.toString(Data data),
     * includendo quindi il centroide, le tuple appartenenti e la distanza media.
     * @param data Il dataset completo per recuperare i dettagli delle tuple.
     * @return Una stringa formattata con la lista completa dei dettagli di ogni cluster.
     */
    public String toString(Data data){
        String str="";
        int i=1;
        for(Cluster c : this){
            if (c != null){
                str += i + ":" + c.toString(data) + "\n";
            }
            i++;
        }
        return str;
    }
}
