package tw.com.justiot.pneumatics.panel;

import javax.swing.JFrame;

import tw.com.justiot.pneumatics.Command;

public interface PneumaticListener {
//  public java.util.Timer getTimer();
//  public void setTimer(java.util.Timer timer);
  public PneumaticPanel getPneumaticPanel();
  public void setModified(boolean b);
  public boolean getModified();
  public void addCommand(Command com);
  public JFrame getFrame();
  public void MessageBox(String des,String type);
  public void setStatus(String str);
  public void repaint();
}
