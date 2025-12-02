package data;

import database.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * La classe Data modella un dataset caricato da una tabella del database.
 * Gestisce una matrice di valori grezzi e una lista di attributi che descrivono lo schema dei dati
 * (distinguendo tra attributi continui e discreti).
 */
public class Data {

    /**
     * Matrice bidimensionale che contiene i valori del dataset.
     * Il primo indice rappresenta la riga (esempio), il secondo la colonna (attributo).
     */
    private Object data[][];

    /**
     * Numero totale di esempi (transazioni) contenuti nel dataset.
     */
    private int numberOfExamples;

    /**
     * Lista degli attributi che definiscono lo schema del dataset.
     */
    private List<Attribute> attributeSet = new LinkedList<>();

    /**
     * Costruttore della classe Data.
     * Carica i dati di addestramento da una tabella specificata nel database.
     * Inizializza la connessione al database, recupera lo schema della tabella,
     * popola la matrice dei dati con le transazioni distinte creando
     * oggetti ContinuousAttribute o DiscreteAttribute a seconda dei metadati.
     * @param TableName Il nome della tabella del database da cui caricare i dati.
     * @throws DatabaseConnectionException Se fallisce la connessione al database.
     * @throws SQLException Se si verifica un errore durante l'esecuzione delle query SQL.
     * @throws EmptySetException Se la tabella specificata è vuota (nessuna tupla trovata).
     * @throws NoValueException Se non è possibile calcolare i valori aggregati (min/max) per gli attributi continui.
     */
    public Data(String TableName) throws DatabaseConnectionException, SQLException, EmptySetException, NoValueException {
        //inizializza la connessione
        DbAccess db = new DbAccess();
        //apre la connessione
        db.initConnection();
        //recupera lo schema della tabella
        TableSchema ts = new TableSchema(db, TableName);
        //prepara il gestore dei dati
        TableData td = new TableData(db);
        //estrae le transazioni (tuple)
        List<Example> transazioni = td.getDistinctTransazioni(TableName);
        //inizializza numberOfExample
        numberOfExamples = transazioni.size();
        //inizializza data
        data = new Object[numberOfExamples][ts.getNumberOfAttributes()];
        //popola la matrice data
        int i = 0;
        for(Example e : transazioni){
            for(int j=0;j<ts.getNumberOfAttributes();j++){
                data[i][j] = e.get(j);
            }
            i++;
        }
        //creazione attributeSet
        for(int k=0; k< ts.getNumberOfAttributes(); k++){
            TableSchema.Column column = ts.getColumn(k);
            String columnName = column.getColumnName();

            //controllo se la colonna è numerica e quindi avremo un Continuous attribute o no e avremo un Discrete attribute
            if(column.isNumber()){
                //otteniamo i valori MIN e MAX della colonna
                double min = ((Number) td.getAggregateColumnValue(TableName, column, QUERY_TYPE.MIN)).doubleValue();
                double max = ((Number) td.getAggregateColumnValue(TableName, column, QUERY_TYPE.MAX)).doubleValue();

                attributeSet.add(new ContinuousAttribute(columnName, k, min, max));
            }else{
                //recupero valori distinti
                Set<Object> distinctObject = td.getDistinctColumnValues(TableName, column);
                Set<String> distinctString = new HashSet<>();
                //converte ogni oggetto del set in stringa
                for(Object o : distinctObject){
                    distinctString.add((String) o);
                }

                attributeSet.add(new DiscreteAttribute(columnName, k, distinctString));
            }
        }
        //chiusura connessione
        db.closeConnection();
    }

    /**
     * Restituisce il numero di esempi (transazioni) contenuti nel dataset.
     * @return Il numero di righe della matrice dati.
     */
    public int getNumberOfExamples(){
        return numberOfExamples;
    }

    /**
     * Restituisce il numero di attributi (colonne) che compongono ogni esempio del dataset.
     * @return La dimensione della lista degli attributi.
     */
    public int getNumberOfAttributes(){
        return attributeSet.size();
    }

    /**
     * Restituisce lo schema degli attributi del dataset sotto forma di array.
     * @return Un array contenente gli oggetti Attribute (DiscreteAttribute o ContinuousAttribute).
     */
    public Attribute[] getAttributeSchema(){
        return  attributeSet.toArray(new Attribute[getNumberOfAttributes()]);
    }

    /**
     * Restituisce il valore memorizzato nella matrice dati in corrispondenza dell'esempio e dell'attributo specificati.
     * @param exampleIndex L'indice della riga (esempio).
     * @param attributeIndex L'indice della colonna (attributo).
     * @return L'oggetto che rappresenta il valore della cella specificata.
     */
    public Object getValue(int exampleIndex, int attributeIndex){
        return data[exampleIndex][attributeIndex];
    }

    /**
     * Crea e restituisce un oggetto Tuple che modella una riga specifica del dataset.
     * La tupla contiene una sequenza di oggetti Item (DiscreteItem o ContinuousItem)
     * creati in base al tipo di attributo e al valore presente nella matrice dati.
     * @param index L'indice della riga da recuperare.
     * @return L'oggetto Tuple che rappresenta la riga specificata.
     */
    public Tuple getItemSet(int index){
        Tuple tuple=new Tuple(attributeSet.size());
        for(int i=0;i<attributeSet.size();i++) {
            Attribute attribute = attributeSet.get(i);

            if (attribute instanceof DiscreteAttribute) {
                tuple.add(new DiscreteItem((DiscreteAttribute) attribute, (String) data[index][i]), i);
            }else if (attribute instanceof ContinuousAttribute) {
                tuple.add(new ContinuousItem((ContinuousAttribute) attribute, (Double) data[index][i]), i);
            }

        }

        return tuple;
    }

    /**
     * Restituisce una rappresentazione testuale dell'intero dataset.
     * La stringa include l'intestazione degli attributi e l'elenco di tutti gli esempi con i relativi valori.
     * @return Una stringa che rappresenta il contenuto del dataset.
     */
    public String toString(){
        String stampa = "Outlook,Temperature,Humidity,Wind,PlayTennis\n";
        for(int i=0;i<getNumberOfExamples();i++){
            stampa+=i+":";
            for(int j=0;j<getNumberOfAttributes();j++){
                stampa+=getValue(i,j)+",";
            }
            stampa+="\n";
        }
        return stampa;
    }
}
