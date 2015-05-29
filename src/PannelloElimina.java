import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PannelloElimina extends JPanel implements ActionListener
{
  
  private QueryExecuter qe;
  private DBConnection database;
  private JButton bR;
  private JButton bU;
  private JButton bP;
  private JLabel lR;
  private JLabel lU;
  private JLabel lP1;
  private JLabel lP2;
  private JLabel lP3;
  private JLabel lP4;
  private JTextField tR;
  private JTextField tU;
  private JTextField tP1;
  private JTextField tP2;
  private JTextField tP3;
  private JTextField tP4;
  private SpringLayout layout;
  
   
  public PannelloElimina(QueryExecuter qe, DBConnection database)
  {
    super();
    
    this.qe = qe;
    this.database = database;
    
    layout = new SpringLayout();
    this.setLayout(layout);
    
    Dimension d = new Dimension (200,25);
    
    lR = new JLabel("Inserisci l'id risorsa da eliminare");
    tR = new JTextField(15);
    bR = new JButton("Elimina Risorsa");
    bR.setPreferredSize(d);
    
    lU = new JLabel("Inserisci il codice fiscale dell'utente da eliminare");
    tU = new JTextField(15);
    bU = new JButton("Elimina Utente");
    bU.setPreferredSize(d);
    
    lP1 = new JLabel("Inserisci la data della prenotazione da eliminare");
    lP2 = new JLabel("Inserisci l'ora della prenotazione da eliminare");
    lP3 = new JLabel("Inserisci l'id posto della prenotazione da eliminare");
    lP4 = new JLabel("Inserisci l'id loc della prenotazione da eliminare");
    
    tP1 = new JTextField(15);
    tP2 = new JTextField(15);
    tP3 = new JTextField(15);
    tP4 = new JTextField(15);
    bP = new JButton("Elimina Prenotazione");
    bP.setPreferredSize(d);
    
    
    tR.addActionListener(this);
    tU.addActionListener(this);
    tP1.addActionListener(this);
    tP2.addActionListener(this);
    tP3.addActionListener(this);
    tP4.addActionListener(this);
    bR.addActionListener(this);
    bU.addActionListener(this);
    bP.addActionListener(this);
    
    aggiungiAlPannello();
  
  }
  
  public void actionPerformed(ActionEvent e) {
  
    String sql = null;
    if(e.getSource() == bR)
      sql = "DELETE FROM RISORSA WHERE idRis = "+tR.getText();
    else if (e.getSource() == bP)
      sql = "DELETE FROM PRENOTAZIONE WHERE Data = '"+tP1.getText()+"' AND Ora ='"
      +tP2.getText()+"' AND IdPosto = "+tP3.getText()+
      " AND IdLoc = "+tP4.getText();
    else if (e.getSource() == bU)
      sql = "DELETE FROM UTENTE WHERE CF= '"+tU.getText()+"'";
      
      qe.eseguiUpdate(sql);
      tR.setText(null);
      tU.setText(null);
      tP1.setText(null);
      tP2.setText(null);
      tP3.setText(null);
      tP4.setText(null);
    
  }
  
  private void aggiungiAlPannello()
  {
    add(lR);
    add(tR);
    add(bR);
    
    add(lU);
    add(tU);
    add(bU);
    
    add(lP1);
    add(tP1);
    add(lP2);
    add(tP2);
    add(lP3);
    add(tP3);
    add(lP4);
    add(tP4);
    add(bP);
       
    layout.putConstraint(SpringLayout.WEST, lR, 5,SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, lR, 10,SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.WEST, tR, 300,SpringLayout.WEST, lR);
    layout.putConstraint(SpringLayout.NORTH, tR, 10,SpringLayout.NORTH, this);
    layout.putConstraint(SpringLayout.WEST, bR, 200,SpringLayout.WEST, tR);
    layout.putConstraint(SpringLayout.NORTH, bR, 5,SpringLayout.NORTH, this); 
    
    layout.putConstraint(SpringLayout.WEST, lU, 5,SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, lU, 30,SpringLayout.NORTH, lR);
    layout.putConstraint(SpringLayout.WEST, tU, 300,SpringLayout.WEST, lU);
    layout.putConstraint(SpringLayout.NORTH, tU, 30,SpringLayout.NORTH, tR);
    layout.putConstraint(SpringLayout.WEST, bU, 200,SpringLayout.WEST, tU);
    layout.putConstraint(SpringLayout.NORTH, bU, 30,SpringLayout.NORTH, bR);
    
    layout.putConstraint(SpringLayout.WEST, lP1, 5,SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, lP1, 30,SpringLayout.NORTH, lU);
    layout.putConstraint(SpringLayout.WEST, tP1, 300,SpringLayout.WEST, lP1);
    layout.putConstraint(SpringLayout.NORTH, tP1, 30,SpringLayout.NORTH, tU);
    
    layout.putConstraint(SpringLayout.WEST, lP2, 5,SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, lP2, 30,SpringLayout.NORTH, lP1);
    layout.putConstraint(SpringLayout.WEST, tP2, 300,SpringLayout.WEST, lP2);
    layout.putConstraint(SpringLayout.NORTH, tP2, 30,SpringLayout.NORTH, tP1);
    
    layout.putConstraint(SpringLayout.WEST, lP3, 5,SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, lP3, 30,SpringLayout.NORTH, lP2);
    layout.putConstraint(SpringLayout.WEST, tP3, 300,SpringLayout.WEST, lP3);
    layout.putConstraint(SpringLayout.NORTH, tP3, 30,SpringLayout.NORTH, tP2);
    
    layout.putConstraint(SpringLayout.WEST, lP4, 5,SpringLayout.WEST, this);
    layout.putConstraint(SpringLayout.NORTH, lP4, 30,SpringLayout.NORTH, lP3);
    layout.putConstraint(SpringLayout.WEST, tP4, 300,SpringLayout.WEST, lP4);
    layout.putConstraint(SpringLayout.NORTH, tP4, 30,SpringLayout.NORTH, tP3);
    layout.putConstraint(SpringLayout.WEST, bP, 200,SpringLayout.WEST, tP4);
    layout.putConstraint(SpringLayout.NORTH, bP, 30,SpringLayout.NORTH, tP3);
 
  }
 
}