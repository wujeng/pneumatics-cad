package tw.com.justiot.pneumatics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tw.com.justiot.pneumatics.panel.CircuitIconPanel;
import tw.com.justiot.pneumatics.panel.IconPanel;
import tw.com.justiot.pneumatics.panel.PneumaticPanel;
public class Pneumatics extends JPanel implements ChangeListener
  { private PneumaticsCAD pneumaticscad;
	private JTabbedPane toolbarPane=null;
    public PneumaticPanel pneumaticPanel=null;
    public Pneumatics(PneumaticsCAD pneumaticscad) 
     { super();
       this.pneumaticscad=pneumaticscad;
       setLayout(new BorderLayout());
       pneumaticPanel=new PneumaticPanel(pneumaticscad);
       JScrollPane scroller = new JScrollPane(pneumaticPanel);
       scroller.setPreferredSize(new Dimension(200,200));
       
       add(scroller, BorderLayout.CENTER);
       IconPanel ip1=new IconPanel("Actuator", pneumaticscad);
        IconPanel ip2=new IconPanel("Valve", pneumaticscad);
        IconPanel ip3=new IconPanel("Connector", pneumaticscad); 
        IconPanel ip4=new IconPanel("Logic", pneumaticscad);   
        IconPanel ip5=new IconPanel("Delay", pneumaticscad);   
        IconPanel ip6=new IconPanel("FlowValve", pneumaticscad);  
        IconPanel ip7=new IconPanel("PressureValve", pneumaticscad);    
        IconPanel ip8=new IconPanel("Gauge", pneumaticscad);  
        CircuitIconPanel ip9=new CircuitIconPanel("Cascade Method", pneumaticscad);  
        CircuitIconPanel ip10=new CircuitIconPanel("Demos", pneumaticscad);           
        toolbarPane= new JTabbedPane();
        toolbarPane.addTab(Config.getString("pneumatics.tab.actuator"),ip1);
        toolbarPane.addTab(Config.getString("pneumatics.tab.valve"),ip2);
        toolbarPane.addTab(Config.getString("pneumatics.tab.connector"),ip3);
        toolbarPane.addTab(Config.getString("pneumatics.tab.logic"),ip4);
        toolbarPane.addTab(Config.getString("pneumatics.tab.delay"),ip5);
        toolbarPane.addTab(Config.getString("pneumatics.tab.flow"),ip6);
        toolbarPane.addTab(Config.getString("pneumatics.tab.pressure"),ip7);
        toolbarPane.addTab(Config.getString("pneumatics.tab.gauge"),ip8);
        toolbarPane.addTab(Config.getString("pneumatics.tab.cascade"),ip9);
        toolbarPane.addTab(Config.getString("pneumatics.tab.demos"),ip10);
        toolbarPane.addChangeListener(this);
        add(toolbarPane, BorderLayout.NORTH);
     }
     
   public void stateChanged(ChangeEvent e)
     {if(!(e.getSource() instanceof JTabbedPane)) return;
       JTabbedPane tab=(JTabbedPane) e.getSource();
       int ind=tab.getSelectedIndex();
     }
    public void changeTab(int ind)
     {toolbarPane.setSelectedIndex(ind);
     }
 
}

