package tw.com.justiot.pneumatics;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

import tw.com.justiot.pneumatics.config.PneumaticConfig;
import tw.com.justiot.pneumatics.panel.PneumaticListener;
import tw.com.justiot.pneumatics.panel.PneumaticPanel;

public class testPiston extends JPanel implements WindowListener{

	public static void main(String[] args) {
		config = new Config(null);
		testPiston testpiston = new testPiston(args);
	}
	
    private Piston piston;
	public testPiston(String[] margs) 
    { super();
      args=new Args(margs);
	  new PneumaticConfig("en"); 
	  piston=new Piston(Piston.Type_D42);
		JFrame frame = new JFrame();
	    frame.getContentPane().add(piston, BorderLayout.CENTER);
      frame.addWindowListener(this); 
      frame.pack();
      frame.setVisible(true);  
   }
   
	
	
	public void windowClosing(WindowEvent e) 
    {
     System.exit(0);
    }
   public void windowOpened(WindowEvent e) {}
   public void windowClosed(WindowEvent e) {}
   public void windowIconified(WindowEvent e) {}
   public void windowDeiconified(WindowEvent e) {}
   public void windowActivated(WindowEvent e) {}
   public void windowDeactivated(WindowEvent e) {}
   public Args args;
	public static Config config;
	
}
