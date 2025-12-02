package data;

import java.io.Serializable;

/**
 * La classe ContinuousItem estende Item e modella un valore numerico assunto da un attributo di tipo continuo.
 * Implementa l'interfaccia Serializable per consentire il salvataggio degli oggetti su file.
 * @see Item
 */
public class ContinuousItem extends Item implements Serializable {

    /**
     * Costruttore della classe ContinuousItem.
     * Richiama il costruttore della classe Item per inizializzare l'attributo di riferimento e il valore numerico specifico.
     * @param attribute L'attributo di tipo ContinuousAttribute a cui il valore si riferisce.
     * @param value Il valore numerico (Double) assunto dall'attributo.
     */
    ContinuousItem(Attribute attribute, Double value){
        super(attribute, value);
    }

    /**
     * Calcola la distanza tra l'item corrente e un altro item continuo 'a'.
     * La distanza viene calcolata come la differenza assoluta tra i valori normalizzati (scalati) di entrambi gli item.
     * Il calcolo avviene nel seguente modo:
     * 1. Estrae i valori grezzi.
     * 2. Ottiene l'attributo di tipo ContinuousAttribute.
     * 3. Scala entrambi i valori usando il metodo getScaledValue() dell'attributo.
     * 4. Restituisce il valore assoluto della differenza tra i valori scalati.
     * @param a L'oggetto Item (che deve essere un ContinuousItem) con cui calcolare la distanza.
     * @return La distanza calcolata come double, compresa tra 0.0 e 1.0.
     */
    @Override
    double distance(Object a){
        double thisValue = (double) this.getValue();
        double otherValue = (double) ((ContinuousItem)a).getValue();

        ContinuousAttribute attribute = (ContinuousAttribute) this.getAttribute();

        double thisScaledValue = attribute.getScaledValue(thisValue);
        double otherScaledValue = attribute.getScaledValue(otherValue);

        return Math.abs(thisScaledValue-otherScaledValue);
    }
}
