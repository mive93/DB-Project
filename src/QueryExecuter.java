import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


//allora teoricamente(le eccezzioni sono da sistemare) ora e' tutto funzionate ho aggiunto il fattore tabelle dinamiche in maniera raffinata :D
//ho dovuto modificare pero' le api delle funzioni:
//la funzione eseguiQueryStm e eseguiQueryPrStm ora ritornano un oggetto di tipo jtable che dovra' poi essere visulizzato
//la funzione eseguiQueryStm puo' essere chiamata quando vuoi basta che gli passi la sql poi lei crea pure l'oggetto Statement 
//esegue la query e ritorna una tabella coi valori.
//mentre per la eseguiQueryStm occore fare tutti i passi della crezione dinamica quindi tutti i setInt e setString prima di lanciare
//la funzione eseguiQueryStm (es: db.getPrStm(sql).setInt(1,71498), sql po' essere omesso mettendo null nel tu abbia gia' fatto il db.getPrStm(sql))
//ps: ricordati quando inserisci la tabella nel panello prima metterla dentro un JSCrollPane se no non si vedono le intestazioni
// le eccezzioni lanciano una finestra di dialogo classica.

public class QueryExecuter {
  
  private DBConnection db = null;
  private ResultSet rs = null;
  private ResultSetMetaData md = null ;
  private DefaultTableModel dtf;
  private String [] column;
  
  public QueryExecuter(DBConnection db) { 
    this.db = db;
  }
  
  public void eseguiUpdate(String sql)
  {
    try {
      db.getState().executeUpdate(sql);}
    catch(SQLException e)
    {
      JFrame frame3 = new JFrame("Errore");
      JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  private void visualizza()
  {
    
    try{
      md = rs.getMetaData();
      int numcolonne = md.getColumnCount();
      
      column = new String [numcolonne];
      
      for (int h = 0; h < numcolonne; h++){
       column[h] = new String(md.getColumnName(h+1));
      }
      
      dtf = new DefaultTableModel(column,0);
      
      Object [] app = new Object [numcolonne];
      
      while(rs.next())
      {
        for(int j = 0 ; j < numcolonne ; j++ ){
          //System.out.println(md.getColumnName(j) + ": " + rs.getObject(j));
         app[j] = rs.getObject(j+1); 
          
        }
        dtf.addRow(app);
      }
      rs.close();
    }
    catch(SQLException e)
    {
      JFrame frame3 = new JFrame("Errore");
      JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public DefaultTableModel eseguiQueryStm(String sql)
  {
    try{
      rs = db.getState().executeQuery(sql);}
    catch(SQLException e)
    {
      JFrame frame3 = new JFrame("Errore");
      JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
    }
    visualizza();
    return dtf;
  }
  
  public DefaultTableModel eseguiQueryPrStm(String sql)
  {
    try{
      rs = db.getPrState(null).executeQuery();}
    catch(SQLException e)
    {
      JFrame frame3 = new JFrame("Errore");
      JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
    }
    visualizza();
    return dtf;
  }
}
