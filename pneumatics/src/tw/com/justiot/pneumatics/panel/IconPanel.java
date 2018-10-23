package tw.com.justiot.pneumatics.panel;

import tw.com.justiot.pneumatics.*;
import tw.com.justiot.pneumatics.config.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class IconPanel extends JPanel
 {public static final int Command_createElement=1;
  private PneumaticsCAD pneumaticscad;
  private String modelType;
  private ButtonGroup toolbarGroup = new ButtonGroup();

  public IconPanel(String type, PneumaticsCAD pneumaticscad)
   {super();
    this.pneumaticscad=pneumaticscad;
    modelType=type;
    setLayout(new BorderLayout());
    JToolBar toolBar = new JToolBar();
    addButtons(toolBar);
    add(toolBar,BorderLayout.CENTER);
//    setPreferredSize(new Dimension(PREFERRED_WIDTH,PREFERRED_HEIGHT));
   }

  protected void addButtons(JToolBar toolBar) 
   {Insets inset=new Insets(1,1,1,1);
    JToggleButton button = null;
    Image img=null;
    for(int i=0;i<PneumaticConfig.keylist.size();i++)
     {	
      Object obj=PneumaticConfig.parameter.get(PneumaticConfig.keylist.get(i));
      if(!(obj instanceof ElementParameter)) continue;
      ElementParameter ep=(ElementParameter) obj; 
      if(ep.modelType.equals(modelType)) 
       {	  
        img=ep.realImage;   
        if(img!=null)
         button=new JToggleButton(ep.modelName,(Icon) new ImageIcon(img));
        else
         button=new JToggleButton(ep.modelName);
        button.setMargin(inset);
        button.setText(null);
        button.setToolTipText(ep.toolTip.replace('_',' '));
        button.setActionCommand(ep.modelName);
        button.addActionListener(new ActionListener() {      
            public void actionPerformed(ActionEvent e) {
              JToggleButton b=(JToggleButton) e.getSource();
              try
              {
                pneumaticscad.createElement(modelType,b.getActionCommand());         	  
              }
               catch(Exception ex)
               {
            	   pneumaticscad.MessageBox("Error:",ex.getMessage());	  
               }

            }
        });
        toolBar.add(button);
        toolbarGroup.add(button);
       }
     }
   }
 }