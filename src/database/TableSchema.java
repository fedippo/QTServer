package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * La classe TableSchema modella lo schema (metadati) di una tabella del database.
 * Estrae i nomi e i tipi delle colonne del database e li mappa su tipi Java semplificati ("string" o "number").
 */
public class TableSchema {

    /**
     * Riferimento all'oggetto per la gestione della connessione al database.
     */
    DbAccess db;

    /**
     * Classe innestata che modella una singola colonna dello schema.
     * Mantiene il nome originale della colonna e il tipo di dato semplificato.
     */
    public class Column {

        /**
         * Nome della colonna nel database.
         */
        private String name;

        /**
         * Tipo di dato semplificato ("string" o "number").
         */
        private String type;

        /**
         * Costruttore della colonna.
         *
         * @param name Nome della colonna.
         * @param type Tipo di dato semplificato.
         */
        Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Restituisce il nome della colonna.
         *
         * @return Il nome della colonna.
         */
        public String getColumnName() {
            return name;
        }

        /**
         * Verifica se il tipo di dato della colonna è numerico.
         *
         * @return true se il tipo è "number", false altrimenti.
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * Restituisce una rappresentazione testuale della colonna.
         *
         * @return La stringa formattata come "nome:tipo".
         */
        public String toString() {
            return name + ":" + type;
        }
    }

    /**
     * Lista ordinata degli oggetti Column che compongono lo schema della tabella.
     */
    List<Column> tableSchema = new ArrayList<Column>();

    /**
     * Costruttore della classe TableSchema.
     * Si connette al database per recuperare i metadati della tabella specificata e popola l'array tableSchema.
     *
     * @param db        L'oggetto DbAccess contenente la connessione al database.
     * @param tableName Il nome della tabella di cui si vuole recuperare lo schema.
     * @throws SQLException Se si verifica un errore durante l'accesso ai metadati del database.
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException {
        this.db = db;
        HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
        //http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");


        Connection con = db.getConnection();
        DatabaseMetaData meta = con.getMetaData();

        //controlla se esiste una tabella nel db del server con il nome
        ResultSet resTables = meta.getTables(null, null, tableName, null);
        if (!resTables.next()) {
            throw new SQLException("La tabella " + tableName + " non esiste nel database.");
        }

        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {

            if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
        }
        res.close();
    }

    /**
     * Restituisce il numero totale di attributi (colonne) nello schema della tabella.
     * @return Il numero di colonne.
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Restituisce l'oggetto Column nella posizione specificata.
     * @param index L'indice della colonna da recuperare.
     * @return L'oggetto Column.
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }

}