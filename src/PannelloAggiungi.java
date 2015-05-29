import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class PannelloAggiungi extends JPanel implements ActionListener
{
  private QueryExecuter qe;
  private DBConnection database;
  private JComboBox comboBox;
  private String [] add = {"Nuovo accesso","Nuova prenotazione"};
  private JTable tabella1;
  private JTable tabella2;
  private JScrollPane pane = null;
  
 public PannelloAggiungi(QueryExecuter qe, DBConnection database)
  {
    super();
    
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));   
    
    this.qe = qe;
    this.database = database;
    
    tabella1 = new JTable();
    
    comboBox = new JComboBox(add);
    
    
    
    comboBox.addActionListener(this);
    
    add(comboBox);
    add(new JScrollPane(tabella1));
    
    

   
  }
 
 public void actionPerformed(ActionEvent e) {
   JComboBox  cb = (JComboBox)e.getSource();
   DefaultTableModel modello1 = null;
   DefaultTableModel modello2 = null;
   
   if(cb.getSelectedIndex() == 0)
   {
    String d = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Data (GG/MM/AAAA)",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String o = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Ora (HH:MM)",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String t = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci CodTessera",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String l= (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Locale",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    
    
    String sql = "INSERT INTO Accesso VALUES('"+d+"','"+o+"',"+t+","+l+")";
    qe.eseguiUpdate(sql);
    
    modello1 = qe.eseguiQueryStm("SELECT Data AS Data_ACCESSO,Ora,CodTessera,IdLoc FROM ACCESSO");
    
    modello2 = qe.eseguiQueryStm("SELECT Data AS Data_RIFIUTO,Ora,CodTessera,IdLoc FROM RIFIUTO");
    
    
    
    tabella2 = new JTable();
    tabella2.setModel(modello2);
    
    pane = new JScrollPane (tabella2);
    add(pane);
 
   }
   else if(cb.getSelectedIndex() == 1)
   {
    String d = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci data (GG/MM/AAAA)",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null); 
    String o1 = (String) JOptionPane.showInputDialog(
                    this,
                    "Inserisci ora (HH:MM)",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
    StringBuffer o = null;
    try 
    {
      o = new StringBuffer (o1);
      
      if(o.charAt(3) !='0') 
        o.setCharAt(3, '0');
      if(o.charAt(4) !='0')
        o.setCharAt(4, '0');
    }
    catch(NullPointerException npe) {}
    String pdl = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Id posto di lavoro",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
    String cf = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci codice fiscale",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
    String l = (String)JOptionPane.showInputDialog(
                    this,
                    "Inserisci Id locale",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
    String sql = "INSERT INTO Prenotazione VALUES('"+d+"','"+o+"',"+pdl+",'"+cf+"',"+l+")";
    qe.eseguiUpdate(sql);
    
    modello1 = qe.eseguiQueryStm("SELECT * FROM PRENOTAZIONE");
    
    
    if(pane != null)
      this.remove(pane);
   }
   
   tabella1.setModel(modello1);
   this.validate();
   this.revalidate();
 }
 
 
}