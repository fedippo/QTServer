package mining;

import data.Data;
import data.Tuple;
import java.io.*;

/**
 * La classe QTMiner implementa l'algoritmo di clustering QT (Quality Threshold).
 * Si occupa di trovare un set di cluster nel dataset che rispettino un raggio massimo di distanza (radius).
 * Implementa Serializable per consentire il salvataggio e il caricamento di ClusterSet su file.
 */
public class QTMiner implements Serializable{

    /**
     * L'insieme finale dei cluster trovati dall'algoritmo.
     */
    private ClusterSet C;

    /**
     * Il raggio massimo di distanza utilizzato per definire l'appartenenza a un cluster.
     * Dichiarato transient: questo campo non viene salvato durante la serializzazione.
     */
    private transient double radius;

    /**
     * Costruttore per l'esecuzione del clustering.
     * Inizializza il raggio e crea un nuovo set di cluster vuoto.
     * @param radius Il raggio massimo (double) entro cui le tuple possono raggrupparsi.
     */
    public QTMiner(double radius){
        this.radius=radius;
        C=new ClusterSet();
    }

    /**
     * Costruttore per il caricamento di un risultato di clustering salvato in precedenza.
     * Deserializza l'oggetto ClusterSet (C) dal file specificato.
     * @param fileName Il nome del file da cui caricare il ClusterSet.
     * @throws FileNotFoundException Se il file non viene trovato.
     * @throws IOException In caso di errori I/O durante la lettura.
     * @throws ClassNotFoundException Se la classe serializzata (ClusterSet) non è trovata.
     */
    public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream inFile = new FileInputStream(fileName);
        ObjectInputStream inStream = new ObjectInputStream(inFile);
        this.C = (ClusterSet) inStream.readObject();
        inStream.close();
    }

    /**
     * Salva il ClusterSet (C) corrente in un file binario tramite serializzazione.
     * @param fileName Il nome del file in cui salvare il ClusterSet.
     * @throws FileNotFoundException Se il percorso del file non è valido.
     * @throws IOException In caso di errori I/O durante la scrittura.
     */
    public void salva(String fileName) throws FileNotFoundException, IOException{
        FileOutputStream outFile = new FileOutputStream(fileName);
        ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        outStream.writeObject(this.C);
        outStream.close();
    }

    /**
     * Restituisce il ClusterSet contenente i risultati del clustering.
     * @return L'oggetto ClusterSet.
     */
    public ClusterSet getC(){
        return C;
    }

    /**
     * Esegue l'algoritmo di clustering QT sul dataset fornito.
     * L'algoritmo procede iterativamente trovando e aggiungendo il cluster candidato più grande
     * fino a quando tutte le tuple non sono state clusterizzate.
     * @param data Il dataset su cui eseguire il mining.
     * @return Il numero totale di cluster trovati.
     * @throws ClusteringRadiusException Se tutte le tuple finiscono in un unico cluster.
     * @throws EmptyDatasetException Se il dataset (Data) non contiene esempi.
     */
    public int compute(Data data)throws ClusteringRadiusException, EmptyDatasetException{
        int numclusters=0;

        if(data.getNumberOfExamples()==0){throw new EmptyDatasetException("The dataset is empty!");}

        boolean isClustered[]=new boolean[data.getNumberOfExamples()];
        for(int i=0;i<isClustered.length;i++)
            isClustered[i]=false;

        int countClustered=0;
        while(countClustered!=data.getNumberOfExamples()){
            //Ricerca cluster più popoloso
            Cluster c=buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;

            //Rimuovo tuple clusterizzate da dataset

            for(int id : c){
                isClustered[id]=true;
            }
            countClustered+=c.getSize();
        }
        if(numclusters==1){throw new ClusteringRadiusException("14 tuples in one cluster!");}
        return numclusters;
    }

    /**
     * Trova il cluster candidato più grande (cioè con più membri) tra tutte le tuple non ancora clusterizzate.
     * Ogni tupla non clusterizzata è considerata un potenziale centroide.
     * @param data Il dataset su cui lavorare.
     * @param isClustered Array booleano che indica quali tuple sono già state assegnate a un cluster.
     * @return Il cluster candidato (oggetto Cluster) più popoloso trovato nell'iterazione corrente.
     */
    public Cluster buildCandidateCluster(Data data, boolean isClustered[]){
        Cluster bestCluster=null;
        int maxSize = 0;

        for(int i=0;i<data.getNumberOfExamples();i++){
            if(!isClustered[i]){
                Tuple centroid = data.getItemSet(i);
                Cluster candidate = new Cluster(centroid);
                candidate.addData(i);

                for(int j=0;j<data.getNumberOfExamples();j++){
                    if(!isClustered[j]){
                        Tuple currentTuple = data.getItemSet(j);
                        double distance = centroid.getDistance(currentTuple);
                        if(distance<=radius) candidate.addData(j);
                    }
                }
                if(candidate.getSize()>maxSize){
                    maxSize=candidate.getSize();
                    bestCluster=candidate;
                }
            }
        }
        return bestCluster;
    }
}
