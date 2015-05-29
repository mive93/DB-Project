import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class PannelloCerca extends JPanel implements ActionListener
{
  private QueryExecuter qe;
  private DBConnection database;
  private JComboBox comboBox;
  private String [] query = {"Di un locale visualizzare gli utenti con permesso e intervallo di permesso", 
    "Mostra studenti che hanno prenotato più di due posti di lavoro nello stesso mese",
    "Visualizzare gli utenti che hannno avuto più di tre rifiuti in un certo giorno per un certo locale",
    "Visualizzare le assegnazioni dei posti di lavoro ai laureandi in un certo mese",
    "Visualizzare risorse assegnate ad un gruppo di account X e non all'Y",
    "Selezionare tutti i locali che non hanno avuto rifiuti",
    "Selezionare tutti i locali con almeno un rifiuto",
    "Selezionare i gruppi di account che hanno una certa risorsa assegnatagli da un certo tecnico",
    "Visualizzare gli orari in cui è permesso l'accesso in un certo laboratorio" };
  
  private JTable tabella;
  
 public PannelloCerca(QueryExecuter qe, DBConnection database)
  {
    super();
    this.qe = qe;
    this.database = database;
    
    tabella = new JTable();
    comboBox = new JComboBox(query);
    
    comboBox.addActionListener(this);
    
    add(comboBox);
    add(new JScrollPane(tabella));

   
  }
 
 public void actionPerformed(ActionEvent e) {
   JComboBox  cb = (JComboBox)e.getSource();
   DefaultTableModel modello = null;
   if(cb.getSelectedIndex() == 0)
   {
    String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci IdLoc",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String sql =  "SELECT u.Nome,u.Cognome,t.OraInizio, t.OraFine "+
      "FROM UTENTE AS U,PERMESSO AS P, TIPOLOGIA AS T "+
      "WHERE P.CF = U.CF AND T.IdT = P.IdT AND P.IdLoc = "+s+" ";

    modello = qe.eseguiQueryStm(sql);
   }
   else if(cb.getSelectedIndex() == 1)
   {
    String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Mese (numerico)",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String sql = "SELECT U.nome, U.cognome "+
      "FROM UTENTE AS U "+
      "WHERE  EXISTS (SELECT COUNT(*) "+
      "FROM PRENOTAZIONE AS P "+
      "WHERE U.CF = P.CF AND Extract(month from P.Data) = "+s+" "+
      "GROUP BY P.CF "+
      "HAVING COUNT(*)>=2) ";

    modello = qe.eseguiQueryStm(sql);
   }
   else if(cb.getSelectedIndex() == 2)
   {
    String d = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci data (GG/MM/AAAA)",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String l = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci IdLoc",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    
    
    String sql =  "SELECT U.nome, R.data, R.idloc "+
      "FROM UTENTE AS U, RIFIUTO AS R "+
      "WHERE U.CodTessera = R.CodTessera AND R.Data = '"+d+"' AND R.IdLoc =  "+l+" "+
      "GROUP BY U.nome, R.data, R.idloc "+
      "HAVING (COUNT(*)>=3)";


    modello = qe.eseguiQueryStm(sql);
   }
   else if(cb.getSelectedIndex() == 3)
   {
    String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Mese (numerico)",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String sql = "SELECT L.cf "+
      "FROM LAUREANDO AS L, ASSEGNAZIONE AS A "+
      "WHERE L.CF = A.CFL AND A.Mese ="+s;

    modello = qe.eseguiQueryStm(sql);
   }
   
   else if(cb.getSelectedIndex() == 4)
   {
    String cdlx = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci IdCDL X",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String annox = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Anno X",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String cdly = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci IdCDL Y",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    
    String annoy = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Anno Y",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String sql = "SELECT IdRis "+
      "FROM DISTRIBUZIONEPR "+
      "WHERE IdCDL = "+cdlx+" AND AnnoIscrizione = "+cdly+" AND IdRis  "+
      "NOT IN ( SELECT IdRis "+
      "FROM DISTRIBUZIONEPR "+
      "WHERE IdCDL="+cdly+" AND AnnoIscrizione="+annoy+")";


    modello = qe.eseguiQueryStm(sql);
   }
   
   else if(cb.getSelectedIndex() == 5)
   {
    String sql = "SELECT IdLoc "+
      "FROM LOCALE "+
      "WHERE IdLoc NOT IN(  SELECT IdLoc "+
      "FROM RIFIUTO)";
    modello = qe.eseguiQueryStm(sql);
   }
   
   else if(cb.getSelectedIndex() == 6)
   {
    String sql =  "SELECT * "+
      "FROM LOCALE as l0 "+
      "WHERE NOT EXISTS(SELECT *  "+
      "FROM LOCALE as l1 "+
      "WHERE l0.idloc=l1.idloc AND NOT EXISTS(SELECT *  "+
      "FROM rifiuto "+
      "WHERE l1.idloc=rifiuto.idloc))";
    modello = qe.eseguiQueryStm(sql);
   }
   else if(cb.getSelectedIndex() == 7)
   {
    String r = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci IdRisorsa",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String t = (String)JOptionPane.showInputDialog(
                                                   this,
                                                   "Inserisci CF tecnico",
                                                   "Customized Dialog",
                                                   JOptionPane.PLAIN_MESSAGE,
                                                   null,
                                                   null,
                                                   null); 

    String sql = "SELECT AnnoIscrizione, IdCDL "+
      "FROM DISTRIBUZIONEPR "+
      "WHERE IdRis = "+r+" AND CF = '"+t+"' ";

    modello = qe.eseguiQueryStm(sql);
   }
   
   else if(cb.getSelectedIndex() == 8)
   {
    String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci IdLocale",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String sql =  "SELECT DISTINCT T.OraInizio, T.OraFine, L.IdLoc "+
      "FROM PERMESSO AS P, LOCALE AS L, TIPOLOGIA AS T "+
      "WHERE L.IdLoc = P.IdLoc AND T.IdT = P.IdT AND L.IdLoc ="+s;

    modello = qe.eseguiQueryStm(sql);
   }
   
   
   tabella.setModel(modello);
   this.revalidate();
 }
 
 
}