package data;

import java.io.Serializable;

/**
 * La classe DiscreteItem estende Item e modella un valore assunto da un attributo di tipo discreto (categorico).
 * Implementa l'interfaccia Serializable per consentire il salvataggio degli oggetti su file.
 * @see Item
 */
public class DiscreteItem extends Item implements Serializable {

    /**
     * Costruttore della classe DiscreteItem.
     * Richiama il costruttore della classe Item con l'attributo di riferimento e il valore simbolico specifico.
     * @param attribute L'attributo di tipo DiscreteAttribute a cui il valore si riferisce.
     * @param value Il valore simbolico (String) assunto dall'attributo.
     */
    public DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Calcola la distanza tra l'item corrente e un altro item discreto 'a'.
     * Viene utilizzata la metrica di distanza binaria (o "mismatch"):
     * La distanza è 0.0 se i valori dei due item sono uguali.
     * La distanza è 1.0 se i valori dei due item sono diversi.
     * @param a L'oggetto Item (che deve essere un DiscreteItem) con cui calcolare la distanza.
     * @return La distanza calcolata, che sarà 0.0 o 1.0.
     */
    @Override
    public double distance(Object a) {
        Item otherItem = (Item)a;

        if(this.getValue().equals(otherItem.getValue())) {return 0.0;}
        else {return 1.0;}
    }
}
