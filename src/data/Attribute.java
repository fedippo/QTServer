package data;

/**
 * Classe astratta che modella gli attributi generici nel dataset.
 * Serve da base per le implementazioni concrete di attributi discreti e continui.
 * Mantiene i dati identificativi fondamentali: nome e indice.
 * @see DiscreteAttribute
 * @see ContinuousAttribute
 */
abstract class Attribute{

    /**
     * Nome identificativo dell'attributo
     */
    private String name;

    /**
     * Indice posizionale dell'attributo nella tupla (colonna)
     */
    private int index;

    /**
     * Costruttore della classe astratta Attribute.
     * @param name Il nome da assegnare all'attributo.
     * @param index L'indice posizionale da assegnare all'attributo.
     */
    public Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Restituisce il nome dell'attributo
     * @return nome dell'attributo
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce l'indice posizionale dell'attributo
     * @return indice dell'attributo
     */
    public int getIndex() {
        return index;
    }

    /**
     * Restituisce una rappresentazione testuale dell'attributo,
     * che corrisponde al suo nome.
     * @return La stringa contenente il nome dell'attributo.
     */
    public String toString() {
        return name;
    }

}
