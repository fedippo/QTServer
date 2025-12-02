package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * La classe DiscreteAttribute estende Attribute e modella un attributo simbolico (categorico).
 * Mantiene l'insieme ordinato dei valori distinti (dominio) che l'attributo può assumere nel dataset.
 * L'utilizzo di TreeSet assicura che i valori siano unici e ordinati.
 * Implementa l'interfaccia Iterable per consentire lo scorrimento dei valori distinti.
 * @see Attribute
 */
public class DiscreteAttribute extends Attribute implements Iterable<String>{

    /**
     * Insieme ordinato dei valori distinti (dominio) dell'attributo discreto.
     */
    private TreeSet<String> values;

    /**
     * Costruttore della classe DiscreteAttribute.
     * Inizializza l'insieme dei valori distinti (values) copiando i valori dal Set fornito.
     * Richiama il costruttore di Attribute per i parametri name e index.
     * @param name  Il nome da assegnare all'attributo.
     * @param index L'indice posizionale dell'attributo.
     * @param values L'insieme dei valori distinti (dominio) da assegnare all'attributo.
     */
    public DiscreteAttribute(String name, int index, Set<String> values) {
        super(name, index);
        this.values = new TreeSet<>(values);
    }

    /**
     * Restituisce il numero totale di valori distinti (dimensione del dominio) gestiti dall'attributo.
     * @return Il numero di valori distinti come intero.
     */
    public int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Restituisce un iteratore per scorrere l'insieme dei valori distinti dell'attributo.
     * L'iteratore scorre i valori in ordine lessicografico.
     * @return L'iteratore sui valori.
     */
    @Override
    public Iterator<String> iterator(){
        return values.iterator();
    }

}
