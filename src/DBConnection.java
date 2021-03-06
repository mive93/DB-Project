import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;


public class DBConnection {
  
  private Connection conn=null;
  private Statement stm=null;
  private PreparedStatement prstm=null;
 
    public DBConnection(String usr,String pass) { 
      
      try 
      {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ProgettoMartoglia",usr,pass);
        
      }
      catch(ClassNotFoundException e)
      {
        JFrame frame3 = new JFrame("Errore");
        JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
        System.exit(0);
      }
      catch(SQLException e)
      {
       JFrame frame3 = new JFrame("Errore");
       JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
       System.exit(1);
      }
    } 
    public Statement getState()
    {
      try{
      if (stm==null)
        stm = conn.createStatement();
      }
        catch(SQLException e)
      {
         JFrame frame3 = new JFrame("Errore");
        JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
      }
        return stm;
    }
    
    public PreparedStatement getPrState(String sql)
    {
      try{
      if (prstm==null)
        prstm = conn.prepareStatement(sql);
      }
      catch(SQLException e)
      {
         JFrame frame3 = new JFrame("Errore");
        JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
      }
      return prstm;
    }
    
    public void closeStm()
    {
      try{
      if(stm!=null)
        stm.close();
      }
      catch(SQLException e)
      {
         JFrame frame3 = new JFrame("Errore");
        JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
      }
      
    }
    public void closePrStm()
    {
      try{
      if(prstm!=null)
        prstm.close();
      }
      catch(SQLException e)
      {
         JFrame frame3 = new JFrame("Errore");
        JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
      }
      
    }
    public void closeALL()
    {
      try{
       closeStm();
       closePrStm();
      if(conn!=null)
        conn.close();
      }
      catch(SQLException e)
      {
        JFrame frame3 = new JFrame("Errore");
        JOptionPane.showMessageDialog(frame3,e.toString(),"Errore",JOptionPane.ERROR_MESSAGE);
    }
  }
}
