package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import database.TableSchema.Column;

/**
 * La classe TableData gestisce l'interrogazione dei dati (transazioni e valori) contenuti in una specifica tabella del database.
 * Utilizza la connessione fornita da DbAccess per eseguire le query SQL.
 */
public class TableData {

    /**
     * Riferimento all'oggetto per la gestione della connessione al database.
     */
	DbAccess db;

    /**
     * Costruttore della classe TableData.
     * Inizializza l'oggetto con il riferimento all'oggetto DbAccess che gestisce la connessione attiva al database.
     * @param db L'oggetto DbAccess contenente la connessione al database.
     */
	public TableData(DbAccess db) {
		this.db=db;
	}

    /**
     * Esegue una query sul database per estrarre tutte le transazioni (righe) distinte presenti nella tabella specificata.
     * Le transazioni vengono lette e convertite in una lista di oggetti Example.
     * @param table Il nome della tabella del database da interrogare.
     * @return Una lista di oggetti Example (le tuple distinte).
     * @throws SQLException Se si verifica un errore durante l'esecuzione della query SQL.
     * @throws EmptySetException Se la query non restituisce alcuna riga.
     */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		
		
		String query="select distinct ";
		
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty=true;
		while (rs.next()) {
			empty=false;
			Example currentTuple=new Example();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i+1));
				else
					currentTuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if(empty) throw new EmptySetException("Empty set");
		
		
		return transSet;

	}

    /**
     * Esegue una query per estrarre tutti i valori distinti (dominio) per la colonna specificata.
     * I valori vengono restituiti in un TreeSet, garantendo l'unicità e l'ordinamento.
     * @param table Il nome della tabella.
     * @param column La colonna di cui estrarre i valori distinti.
     * @return Un Set di oggetti (String o Double) contenente i valori distinti ordinati.
     * @throws SQLException Se si verifica un errore durante l'esecuzione della query SQL.
     */
	public  Set<Object>getDistinctColumnValues(String table,Column column) throws SQLException{
		Set<Object> valueSet = new TreeSet<Object>();
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		
		
		String query="select distinct ";
		
		query+= column.getColumnName();
		
		query += (" FROM "+table);
		
		query += (" ORDER BY " +column.getColumnName());
		
		
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
				if(column.isNumber())
					valueSet.add(rs.getDouble(1));
				else
					valueSet.add(rs.getString(1));
			
		}
		rs.close();
		statement.close();
		
		return valueSet;

	}

    /**
     * Esegue una query per calcolare un valore aggregato (MIN o MAX) sulla colonna specificata.
     * @param table Il nome della tabella.
     * @param column La colonna su cui applicare l'aggregazione.
     * @param aggregate Il tipo di aggregazione da calcolare (MIN o MAX) definito in QUERY_TYPE.
     * @return L'oggetto (String o Float) risultante dall'aggregazione.
     * @throws SQLException Se si verifica un errore durante l'esecuzione della query SQL.
     * @throws NoValueException Se la query di aggregazione non restituisce alcun valore (risultato NULL).
     */
	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		TableSchema tSchema=new TableSchema(db,table);
		Object value=null;
		String aggregateOp="";
		
		String query="select ";
		if(aggregate==QUERY_TYPE.MAX)
			aggregateOp+="max";
		else
			aggregateOp+="min";
		query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;
		
		
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
				if(column.isNumber())
					value=rs.getFloat(1);
				else
					value=rs.getString(1);
			
		}
		rs.close();
		statement.close();
		if(value==null)
			throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());
			
		return value;

	}

}
