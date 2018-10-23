package tw.com.justiot.pneumatics.panel;

import tw.com.justiot.pneumatics.*;
import tw.com.justiot.pneumatics.config.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
//import java.lang.reflect.*;

public class CircuitIconPanel extends JPanel
 {private PneumaticsCAD pneumaticscad;
  private String modelType;
  private ButtonGroup toolbarGroup = new ButtonGroup();
  public CircuitIconPanel(String type, PneumaticsCAD pneumaticscad)
   {super();
    this.pneumaticscad=pneumaticscad;
    modelType=type;
    setLayout(new BorderLayout());
    JToolBar toolBar = new JToolBar();
    addButtons(toolBar);
    add(toolBar,BorderLayout.CENTER);
   }
  protected void addButtons(JToolBar toolBar) 
   {Insets inset=new Insets(1,1,1,1);
    JToggleButton button = null;
    Image img=null;
    Enumeration e = PneumaticConfig.circuit.elements();
    while(e.hasMoreElements())
     {CircuitParameter cp=(CircuitParameter) e.nextElement();
      if(cp.modelType.equals(modelType)) 
       {
        img=cp.image;
        if(img!=null)
         button=new JToggleButton(cp.modelName,(Icon) new ImageIcon(img));
        else
         button=new JToggleButton(cp.modelName);
        button.setMargin(inset);
        button.setText(null);
        button.setToolTipText(cp.toolTip.replace('_',' '));
        button.setActionCommand(cp.modelName);
        button.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {
	          JToggleButton b=(JToggleButton) e.getSource();
              String modelName=b.getActionCommand();             
              try
              {
               pneumaticscad.loadCircuit(modelName);            
              }
               catch(Exception ex)
               {
            	   pneumaticscad.MessageBox("Error:",ex.getMessage());	  
               }
              
//	          WebLadderCAD.self.panelAction("CircuitIcon",modelName,"",-1);
   
            }
        });
        toolBar.add(button);
        toolbarGroup.add(button);
       }
     }
   }
 }