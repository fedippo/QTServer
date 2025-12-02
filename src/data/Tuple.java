package data;

import java.io.Serializable;
import java.util.Set;

/**
 * La classe Tuple modella una tupla (o transazione) del dataset come una sequenza ordinata di oggetti Item.
 * Implementa l'interfaccia Serializable per consentire il salvataggio degli oggetti su file.
 */
public class Tuple implements Serializable {

    /**
     * Array di oggetti Item che compongono la tupla. L'ordine corrisponde all'indice degli attributi.
     */
    private Item [] tuple;

    /**
     * Costruttore della tupla.
     * Inizializza l'array interno con la dimensione specificata, pari al numero di attributi.
     * @param size La dimensione della tupla (numero di attributi/item).
     */
    public Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * Restituisce la lunghezza della tupla (il numero di item).
     * @return Il numero di item nella tupla.
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Restituisce l'item nella posizione specificata.
     * @param i L'indice dell'item da recuperare.
     * @return L'oggetto Item nella posizione i.
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Aggiunge (o imposta) un Item nella posizione specificata della tupla.
     * @param c L'Item da aggiungere.
     * @param i La posizione (indice) in cui inserire l'Item.
     */
    public void add(Item c, int i){
        tuple[i] = c;
    }

    /**
     * Calcola la distanza totale tra la tupla corrente e un'altra tupla specificata.
     * La distanza è la sommatoria delle distanze calcolate tra gli Item corrispondenti della tupla corrente e della tupla in ingresso (obj).
     * Usa il metodo Item.distance(Object a).
     *
     * @param obj La tupla target con cui calcolare la distanza.
     * @return La distanza totale calcolata come double.
     */
    public double getDistance(Tuple obj) {
        double distance=0.0;
        for(int i=0;i<getLength();i++) {
            distance += this.tuple[i].distance(obj.tuple[i]);
        }
        return distance;
    }

    /**
     * Calcola la distanza media tra la tupla corrente e l'insieme delle tuple (cluster) identificate dagli indici in clusteredData.
     * Viene utilizzato per determinare la qualità di un cluster o per scegliere il miglior centroide.
     * @param data Il dataset completo contenente tutte le transazioni.
     * @param clusteredData L'insieme degli indici delle tuple che appartengono al cluster.
     * @return La distanza media calcolata. Restituisce 0.0 se il set clusteredData è vuoto.
     */
    public double avgDistance(Data data, Set<Integer> clusteredData){
        double p=0.0,sumD=0.0;
        for (Integer id : clusteredData) {
            double d = getDistance(data.getItemSet(id));
            sumD += d;
        }
        if (!clusteredData.isEmpty()) {
            p = sumD / clusteredData.size();
        }
        return p;
    }

}
