import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class PannelloVisualizza extends JPanel implements ActionListener
{
  private String [] tabelle = {"Account","Utente","Studente","Laureando","Docente",
    "NonDocente","Tecnico","Locale","NumAccessi","Laboratorio","Assegnazione",
    "Prenotazione","Risorsa","Permesso"};
  private JLabel vis;
  private JComboBox comboBox;
  private QueryExecuter qe;
  private DBConnection database;
  private JTable tabella;
  private DefaultTableModel modello;

  public PannelloVisualizza(QueryExecuter qe, DBConnection database)
  {
    super();
    
    this.qe = qe;
    this.database = database;
    
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    
    comboBox = new JComboBox (tabelle);
    comboBox.addActionListener(this);
    vis = new JLabel("Visualizza");
    
    tabella = new JTable();
    
    
    add(vis);
    add(comboBox);
    this.add(new JScrollPane(tabella));
    
  
  }
  
   public void actionPerformed(ActionEvent e) {
     
        if (((String)comboBox.getSelectedItem()).equals("Account"))
        {
          String sql = "SELECT * FROM ACCOUNT";
          modello = qe.eseguiQueryStm(sql);
          
        }else if (((String)comboBox.getSelectedItem()).equals("Utente"))
        {
          String sql = "SELECT * FROM UTENTE";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Studente"))
        {
          String sql = "SELECT * FROM STUDENTE";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Laureando"))
        {
          String sql = "SELECT * FROM LAUREANDO";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Docente"))
        {
          String sql = "SELECT * FROM DOCENTE";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("NonDocente"))
        {
          String sql = "SELECT * FROM NONDOCENTE";
          modello = qe.eseguiQueryStm(sql);         
        }else if (((String)comboBox.getSelectedItem()).equals("Tecnico"))
        {
          String sql = "SELECT * FROM TECNICO";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Locale"))
        {
          String sql = "SELECT * FROM LOCALE";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Laboratorio"))
        {
          String sql = "SELECT * FROM LABORATORIO";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("NumAccessi"))
        {
          String sql = "SELECT * FROM NUMACCESSI";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Assegnazione"))
        {
          String sql = "SELECT * FROM ASSEGNAZIONE";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Prenotazione"))
        {
          String sql = "SELECT * FROM PRENOTAZIONE";
          modello = qe.eseguiQueryStm(sql);
        }else if (((String)comboBox.getSelectedItem()).equals("Risorsa"))
        {
          String sql = "SELECT * FROM RISORSA";
          modello = qe.eseguiQueryStm(sql);
        }
        else if (((String)comboBox.getSelectedItem()).equals("Permesso"))
        {
          String sql = "SELECT * FROM PERMESSO";
          modello = qe.eseguiQueryStm(sql);
        }
        tabella.setModel(modello);
        this.revalidate();
    }
}
  