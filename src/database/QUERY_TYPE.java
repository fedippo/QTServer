package database;

/**
 * L'enumerazione QUERY_TYPE definisce i tipi di operazioni di aggregazione (funzioni aggregate)
 * che possono essere eseguite sulle colonne del database dalla classe TableData.
 */
public enum QUERY_TYPE {

    /** * Indica la funzione di aggregazione per trovare il valore minimo (Minimum).
     * Corrisponde all'operazione SQL MIN().
     */
    MIN,
    /** * Indica la funzione di aggregazione per trovare il valore massimo (Maximum).
     * Corrisponde all'operazione SQL MAX().
     */
    MAX
}
