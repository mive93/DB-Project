import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PannelloUpdate extends JPanel implements ActionListener
{
  private QueryExecuter qe;
  private DBConnection database;
  private JFrame f;
  
  private JButton modR;
  private JTextField tr1;
  private JTextField tr2;
  private JLabel lr1;
  private JLabel lr2;
 
  
  private JButton modT;
  private JTextField tt1;
  private JTextField tt2;
  private JTextField tt3;
  private JLabel lt1;
  private JLabel lt2;
  private JLabel lt3;
 

  private JButton modU;
  private JTextField tu1;
  private JTextField tu2;
  private JTextField tu3;
  private JTextField tu4;
  private JTextField tu5;
  private JTextField tu6;
  private JTextField tu7;
  private JTextField tu8;
  private JTextField tu9;  
  private JLabel lu1;
  private JLabel lu2;
  private JLabel lu3;
  private JLabel lu4;
  private JLabel lu5;
  private JLabel lu6;
  private JLabel lu7;
  private JLabel lu8;
  private JLabel lu9;
  
  private JPanel pannello;
    
 public PannelloUpdate(QueryExecuter qe, DBConnection database, String s,JFrame f)
  {
    super();
    
    this.qe = qe;
    this.database = database;
    this.f = f;
    
    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));   
    
    pannello = new JPanel();
    
    if(s.equals("RISORSA"))
    {
      lr1 = new JLabel ("Inserisci il codice risorsa da modificare");
      lr2 = new JLabel ("Inserisci il nuovo posto di lavoro");
      tr1 = new JTextField(15);
      tr2 = new JTextField(15);
      modR = new JButton ("Modifica");
      
      modR.addActionListener(this);
      tr1.addActionListener(this);
      tr2.addActionListener(this);
      
      add(lr1);
      add(tr1);
      add(lr2);
      add(tr2);
      pannello.add(modR);
      add(pannello);
    }
    
    
    if(s.equals("TIPOLOGIA"))
    {
      lt1 = new JLabel ("Inserisci il codice tipologia da modificare");
      lt2 = new JLabel ("Inserisci il nuovo orario di inizio");
      lt3 = new JLabel ("Inserisci il nuovo orario di fine");
      tt1 = new JTextField(15);
      tt2 = new JTextField(15);
      tt3 = new JTextField(15);
      modT = new JButton ("Modifica");
      
      modT.addActionListener(this);
      tt1.addActionListener(this);
      tt2.addActionListener(this);
      tt3.addActionListener(this);
      
      add(lt1);
      add(tt1);
      add(lt2);
      add(tt2);
      add(lt3);
      add(tt3);
      pannello.add(modT);
      add(pannello);
    }
    
    
    if(s.equals("UTENTE"))
    {
      lu1 = new JLabel ("Inserisci il Codice Fiscale dell'utente da modificare");
      lu2 = new JLabel ("Inserisci il nuovo nome");
      lu3 = new JLabel ("Inserisci il nuovo cognome");
      lu4 = new JLabel ("Inserisci il nuova data di nascita");
      lu5 = new JLabel ("Inserisci il nuovo luogo di nascita");
      lu6 = new JLabel ("Inserisci la nuova residenza");
      lu7 = new JLabel ("Inserisci il nuovo CAP");
      lu8 = new JLabel ("Inserisci la nuova Nazionalità");
      lu9 = new JLabel ("Inserisci in nuovo codice tessera");
      
      
      tu1 = new JTextField(15);
      tu2 = new JTextField(15);
      tu3 = new JTextField(15);
      tu4 = new JTextField(15);
      tu5 = new JTextField(15);
      tu6 = new JTextField(15);
      tu7 = new JTextField(15);
      tu8 = new JTextField(15);
      tu9 = new JTextField(15);
      
      modU = new JButton ("Modifica");
      
      modU.addActionListener(this);
      tu1.addActionListener(this);
      tu2.addActionListener(this);
      tu3.addActionListener(this);
      tu4.addActionListener(this);
      tu5.addActionListener(this);
      tu6.addActionListener(this);
      tu7.addActionListener(this);
      tu8.addActionListener(this);
      tu9.addActionListener(this);
      
      add(lu1);
      add(tu1);
      add(lu2);
      add(tu2);
      add(lu3);
      add(tu3);
      add(lu4);
      add(tu4);
      add(lu5);
      add(tu5);
      add(lu6);
      add(tu6);
      add(lu7);
      add(tu7);
      add(lu8);
      add(tu8);
      add(lu9);
      add(tu9);
      pannello.add(modU);
      add(pannello);
    }
   
  }
 

    public void actionPerformed(ActionEvent e) 
    {
      if(e.getSource() == modR)
      {
        String sql = "UPDATE RISORSA SET IdPosto = "+tr2.getText()+" WHERE IdRis = "+tr1.getText();
        qe.eseguiUpdate(sql);        
      }
      
      if(e.getSource() == modT)
      {
        String sql = "UPDATE TIPOLOGIA SET OraInizio = '"+tt2.getText()+"', OraFine = '"+tt3.getText()+"' WHERE IdT = "+tt1.getText();
        qe.eseguiUpdate(sql);   
      }
      
      if(e.getSource() == modU)
      {
        String sql = "UPDATE UTENTE SET Nome='"+tu2.getText()+"',Cognome='"+tu3.getText()+
          "',dataN='"+tu4.getText()+"',luogoN='"+tu5.getText()+"',residenza='"+
          tu6.getText()+"',CAP="+tu7.getText()+",Nazionalita='"+
          tu8.getText()+"',CodTessera="+tu9.getText()+" WHERE CF='"+tu1.getText()+"'";
        qe.eseguiUpdate(sql);
      }
      chiudiFinestra();
    }
    
    
  private void chiudiFinestra ()
  {
     WindowEvent eventoChiudi = new WindowEvent( f, 
                                                WindowEvent.WINDOW_CLOSING );
     Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(eventoChiudi);
  }
 
 
}