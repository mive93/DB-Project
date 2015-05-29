import javax.swing.*;
import java.awt.*;

public class ProgettoMartoglia
{
  public static void main (String args [])
  {    
    
    JPanel postgres = new JPanel();
    String user = (String)JOptionPane.showInputDialog(
                    postgres,
                    "Inserisci l'user di postgres",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
    String pwd = (String)JOptionPane.showInputDialog(
                    postgres,
                    "Inserisci la password di postgres",
                    "Customized Dialog",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    null);
    
    
    DBConnection database = new DBConnection(user,pwd);
    QueryExecuter qe = new QueryExecuter(database);
    
    JFrame f = new JFrame ("Database"); 
    Pannello p = new Pannello(qe,database);
    f.add(p);
    f.pack();
    f.setResizable(false);
    f.setLocation(100,100);
    f.setVisible (true);
    f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }
}