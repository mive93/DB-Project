import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class PannelloModifica extends JPanel implements ActionListener
{
  private QueryExecuter qe;
  private DBConnection database;
  private JButton modTipologia;
  private JButton modUtente;
  private JButton modRis;
  
  
  public PannelloModifica(QueryExecuter qe, DBConnection database)
  {
    super();
    
    this.qe = qe;
    this.database = database;
    
    modTipologia = new JButton ("Modifica l'intervallo di accesso di una tipologia");
    modUtente = new JButton ("Modifica i dati anagrafici di un utente");
    modRis = new JButton ("Modifica il posto di lavoro di una risorsa");
    
    modTipologia.addActionListener(this);
    modUtente.addActionListener(this);
    modRis.addActionListener(this);
    
    add(modTipologia);
    add(modUtente);
    add(modRis);
  }
  
  public void actionPerformed(ActionEvent e) {
  
    if(e.getSource() == modTipologia)
    {
      this.apriFinestra(qe,database,"TIPOLOGIA");
    }
    else if(e.getSource() == modUtente)
    {
      this.apriFinestra(qe,database,"UTENTE");
    }
    else if(e.getSource() == modRis)
    {
      this.apriFinestra(qe,database,"RISORSA");
    }
    
  }
  
  private void apriFinestra(QueryExecuter qe, DBConnection database, String s)
  {
      JFrame f = new JFrame("Modifica "+ s);
      PannelloUpdate p = new PannelloUpdate(qe,database,s,f);
      f.add(p);
      f.setVisible(true);
      f.pack();
      f.setLocation(150,150);
      f.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
      
  }
  
}