import javax.swing.*;
import java.awt.*;

public class Pannello extends JPanel
{
  private PannelloVisualizza pv;
  private PannelloElimina pe;
  private PannelloCerca pc;
  private PannelloModifica pm; 
  private PannelloAggiungi pa; 
  private JTabbedPane tabbedPane;
  private Dimension d;
  private QueryExecuter qe;
  private DBConnection database;

  public Pannello(QueryExecuter qe, DBConnection database)
  {
    super();
    
    this.qe = qe;
    this.database = database;
    
    d = new Dimension(800, 250);
    
    pc = new PannelloCerca(qe,database);
    pc.setPreferredSize(d);
    pe = new PannelloElimina(qe,database);
    pe.setPreferredSize(d);
    pm = new PannelloModifica(qe,database);
    pm.setPreferredSize(d);
    pv = new PannelloVisualizza(qe,database); 
    pv.setPreferredSize(d);
    pa = new PannelloAggiungi (qe,database);
    pa.setPreferredSize(d);
    
    tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Visualizza", null, pv,
                  "Mostra le tabelle del database");
    tabbedPane.addTab("Modifica", null, pm,
                  "Permette di modificare alcuni dati");
    tabbedPane.addTab("Cerca", null, pc,
                  "Permette determinate ricerche sui dti");
    tabbedPane.addTab("Elimina", null, pe,
                  "E' possibile eliminare qualche dato");
    tabbedPane.addTab("Aggiungi", null, pa,
                  "E' possibile inserire nuovi accessi o prenotazioni");
    
    add(tabbedPane);
    
    
  }

}
  