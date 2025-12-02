package database;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Example modella una singola transazione (riga) letta da una tabella del database.
 * Contiene una lista ordinata di oggetti che rappresentano i valori degli attributi di quella riga.
 * Implementa l'interfaccia Comparable<Example> per consentire l'ordinamento e la gestione di set di transazioni distinte.
 */
public class Example implements Comparable<Example>{

    /**
     * Lista ordinata di oggetti che compongono l'esempio (tupla).
     * Ogni elemento rappresenta il valore assunto da un attributo.
     */
    private List<Object> example=new ArrayList<Object>();

    /**
     * Aggiunge un valore (Object) all'esempio, nella posizione successiva.
     * @param o L'oggetto da aggiungere (valore dell'attributo).
     */
	public void add(Object o){
		example.add(o);
	}

    /**
     * Restituisce il valore (Object) dell'attributo nella posizione specificata.
     * @param i L'indice (posizione) del valore da recuperare.
     * @return L'oggetto nella posizione i.
     */
	public Object get(int i){
		return example.get(i);
	}

    /**
     * Confronta l'esempio corrente con l'esempio 'ex' elemento per elemento.
     * Il confronto è lessicografico: se i valori di un attributo differiscono, la comparazione si basa
     * sul confronto di quei due valori (richiamando il loro metodo compareTo) e il confronto si interrompe.
     * Se tutti i valori sono uguali, restituisce 0.
     * @param ex L'esempio con cui effettuare il confronto.
     * @return Un intero negativo, zero o un intero positivo a seconda che questo oggetto sia
     * rispettivamente minore, uguale o maggiore dell'oggetto specificato.
     */
	public int compareTo(Example ex) {
		
		int i=0;
		for(Object o:ex.example){
			if(!o.equals(this.example.get(i)))
				return ((Comparable)o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}

    /**
     * Restituisce una rappresentazione testuale dell'esempio, concatenando i valori degli attributi separati da uno spazio.
     * @return Una stringa contenente tutti i valori dell'esempio.
     */
	public String toString(){
		String str="";
		for(Object o:example)
			str+=o.toString()+ " ";
		return str;
	}
	
}