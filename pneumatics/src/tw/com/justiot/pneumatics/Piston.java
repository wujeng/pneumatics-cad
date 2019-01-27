package tw.com.justiot.pneumatics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import tw.com.justiot.pneumatics.config.PneumaticConfig;
import tw.com.justiot.pneumatics.panel.PneumaticListener;
import tw.com.justiot.pneumatics.panel.PneumaticPanel;

public class Piston extends JPanel implements PneumaticListener {
	public static final int Type_D42=0;
	public static final int Type_43=1;
	public static final int Type_S42=2;
	private int Type = 0;
	public static final int Data_Element=0;
	public static final int Data_Line=1;
	public static final int Data_Limitswitch=2;
	public static void main(String[] args) {
	  
	}
	public java.util.Timer timer=new java.util.Timer();
	public java.util.Timer getTimer() {return timer;}
    public void setTimer(java.util.Timer timer) {this.timer=timer;}
    private PneumaticPanel pneumaticPanel;
	public Piston(int type) {
	  super();
	  this.Type = type;
	  pneumaticPanel=new PneumaticPanel(this);
	  Border blackline, raisedetched, loweredetched,
      raisedbevel, loweredbevel, empty;
	  blackline = BorderFactory.createLineBorder(Color.black);
	  raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
	  loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
	  raisedbevel = BorderFactory.createRaisedBevelBorder();
	  loweredbevel = BorderFactory.createLoweredBevelBorder();
	  empty = BorderFactory.createEmptyBorder();
	System.out.println("Type:"+Type);  
	  String dir=Paths.get("").toAbsolutePath().toString()+File.separator+"resources"+File.separator+"piston"+File.separator;
	  String fstr="piston1.pc";
	  if(Type==Type_43) fstr="piston2.pc";
	  else if(Type==Type_S42) fstr="piston3.pc";
	  try {
		open(new File(dir+fstr));
//	    Object obj=Creater.instanceElement("Actuator","Piston", this);
//	    tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement element=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) obj;
//	    pneumaticPanel.add(element);
//	    pneumaticPanel.repaint();
	  } catch(Exception e) {
		  System.out.println(dir+fstr+":"+e.getMessage());
	  }
	  setLayout(new BorderLayout());
	  add(pneumaticPanel, BorderLayout.CENTER);
	  setPreferredSize(new Dimension(120, 120));
	  setBorder(raisedetched);
	}
	
	public void open(File file) throws Exception
	   {
		  pneumaticPanel.deGroup();
		  pneumaticPanel.tempElementArray.clear();
//		System.err.println("degroup()");
		     String s=null;
	  //       CEDevice.ratio=1.0; 
	   boolean firstReadMatrix=true;      
	   try
	    {BufferedReader ins =new BufferedReader(new FileReader(file));
		   
		 while((s=ins.readLine())!=null)
	      {if(s==null || s.length()==0) continue;
	 //System.out.println(s);     
	       StringTokenizer token=new StringTokenizer(s);
	       int dtype=Integer.parseInt(token.nextToken());
	        switch(dtype)
	         {case Data_Element: 
	          case Data_Line: 
	          case Data_Limitswitch: 
	           pneumaticPanel.open(s);
	           break;
	         }
	      }
	     ins.close();
	     pneumaticPanel.deGroup();
	    }
	   catch(Exception bre)
	    {System.err.println(bre.getMessage());
	     setStatus("File "+ file.getCanonicalPath()+" reading error!!");
	    }
	   }
	
	public PneumaticPanel getPneumaticPanel() {
		return pneumaticPanel;
	}
	@Override
	public void setModified(boolean b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean getModified() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addCommand(Command com) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void MessageBox(String des, String type) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatus(String str) {
		// TODO Auto-generated method stub
		
	}
}
