package mining;

/**
 * Eccezione specifica lanciata dall'algoritmo di clustering quando il raggio specificato è troppo ampio,
 * risultando in un caso in cui tutti gli esempi del dataset vengono raggruppati in un unico cluster.
 * Estende Exception, rendendola una eccezione controllata (checked exception).
 */
public class ClusteringRadiusException extends Exception {

    /**
     * Costruttore di default. Crea una ClusteringRadiusException senza messaggio di dettaglio.
     */
    public ClusteringRadiusException() {}

    /**
     * Costruttore che crea una ClusteringRadiusException con un messaggio di dettaglio.
     * @param message Il messaggio di dettaglio (String) per l'eccezione.
     */
    public ClusteringRadiusException(String message) {
        super(message);
    }
}
