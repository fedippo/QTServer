package mining;

/**
 * Eccezione specifica lanciata quando si tenta di eseguire un'operazione di mining
 * su un dataset che non contiene alcuna transazione.
 * Estende Exception, rendendola una eccezione controllata (checked exception).
 */
public class EmptyDatasetException extends Exception {

    /**
     * Costruttore di default. Crea una EmptyDatasetException senza messaggio di dettaglio.
     */
    public EmptyDatasetException() {}

    /**
     * Costruttore che crea una EmptyDatasetException con un messaggio di dettaglio.
     * @param message Il messaggio di dettaglio (String) per l'eccezione.
     */
    public EmptyDatasetException(String message) {
        super(message);
    }
}
