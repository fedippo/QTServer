package data;

/**
 * La classe ContinuousAttribute estende Attribute e modella gli attributi numerici continui.
 * Mantiene i valori estremi (minimo e massimo) del dominio dell'attributo, necessari per la normalizzazione dei dati.
 *
 * @see Attribute
 */
public class ContinuousAttribute extends Attribute {
    /**
     * Valore minimo del range dell'attributo
     */
    private double min;

    /**
     * Valore massimo del range dell'attributo
     */
    private double max;

    /**
     * Costruttore della classe ContinuousAttribute.
     * Inizializza i parametri min e max e richiama il costruttore della classe Attribute per i parametri name e index.
     * @param name Il nome dell'attributo che si sta inizializzando.
     * @param index L'indice posizionale dell'attributo.
     * @param max Il valore massimo del range dell'attributo nel dataset.
     * @param min Il valore minimo del range dell'attributo nel dataset.
     */
    public ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.min = min;
        this.max = max;
    }

    /**
     * Calcola il valore normalizzato (scalato) del parametro v nell'intervallo [0, 1].
     * @param v Il valore originale (grezzo) da scalare.
     * @return Il valore normalizzato (scaled) compreso tra 0.0 e 1.0.
     */
    public double getScaledValue(double v) {
        v=(v-min)/(max-min);
        return v;
    }
}
