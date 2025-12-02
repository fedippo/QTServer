package data;

import java.io.Serializable;

/**
 * La classe astratta Item modella un'associazione tra un valore specifico e l'attributo a cui appartiene.
 * In pratica, rappresenta una singola cella di una tupla (riga) nel dataset.
 * Serve da base per le implementazioni concrete di Item discreti e continui.
 * Implementa l'interfaccia Serializable per consentire il salvataggio degli oggetti su file.
 * @see DiscreteItem
 * @see ContinuousItem
 */
abstract class Item implements Serializable {

    /**
     * Riferimento all'attributo descrittivo.
     * Dichiarato transient: questo campo non verrà salvato durante la serializzazione.
     */
    private transient Attribute attribute;

    /**
     * Il valore effettivo (grezzo) dell'item. Può essere una String (per discreto) o un valore numerico (per continuo).
     */
    private Object value;

    /**
     * Costruttore della classe astratta Item.
     * Inizializza l'item con il suo attributo di riferimento e il valore grezzo.
     * @param attribute L'attributo a cui il valore si riferisce.
     * @param value Il valore assunto dall'attributo.
     */
    public Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Restituisce l'attributo associato all'item.
     * @return L'oggetto Attribute di riferimento.
     */
    public Attribute getAttribute() {
        return attribute;
    }

    /**
     * Restituisce il valore grezzo contenuto nell'item.
     * @return L'oggetto Object contenente il valore.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Restituisce una rappresentazione testuale dell'item, usando il metodo toString() del valore contenuto (value).
     * @return La stringa contenente il valore dell'item.
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Metodo astratto per calcolare la distanza tra l'item corrente e un altro item.
     * Questo metodo deve essere implementato nelle sottoclassi (DiscreteItem e ContinuousItem).
     * @param a L'oggetto (Item) con cui calcolare la distanza.
     * @return La distanza calcolata come double.
     */
    abstract double distance(Object a);
}
