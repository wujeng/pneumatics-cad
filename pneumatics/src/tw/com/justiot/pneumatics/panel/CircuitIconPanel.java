package tw.com.justiot.pneumatics.panel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import tw.com.justiot.pneumatics.PneumaticsCAD;
import tw.com.justiot.pneumatics.config.CircuitParameter;
import tw.com.justiot.pneumatics.config.PneumaticConfig;

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