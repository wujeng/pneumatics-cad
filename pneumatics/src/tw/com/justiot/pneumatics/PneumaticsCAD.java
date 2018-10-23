package tw.com.justiot.pneumatics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import tw.com.justiot.pneumatics.config.CircuitParameter;
import tw.com.justiot.pneumatics.config.PneumaticConfig;
import tw.com.justiot.pneumatics.dialog.CircuitFilter;
import tw.com.justiot.pneumatics.panel.IconPanel;

public class PneumaticsCAD extends JPanel implements ChangeListener,WindowListener
  { 
	private static final long serialVersionUID = 1L;
	public Args args;
	public static Config config;
	
    private ArrayList<Command> commands=new ArrayList<Command>();
    private int commandIndex=-1;
    
    public java.util.Timer timer=new java.util.Timer();
	public Pneumatics pneumatics=null;
    public JFrame frame = null;
    private  JTextField statusField = null;
    public void setStatus(String s) {statusField.setText(s);}
    
   public static final int Data_Element=0;
   public static final int Data_Line=1;
   public static final int Data_Limitswitch=2;
   
    private String exampleDescription=null;    
    private JDialog aboutBox = null;
    
    public void MessageBox(String des,String type)
     {JOptionPane.showMessageDialog(frame,type+":"+des);
     }

     public boolean modified=false;
     public File file;
     private PneumaticsCAD self;
    public static void main(String[] args) 
     {config = new Config();
      new PneumaticsCAD(args);
     }
    
   public void windowClosing(WindowEvent e) 
    {pneumatics.pneumaticPanel.stopTimer();
     System.exit(0);
    }
   public void windowOpened(WindowEvent e) {}
   public void windowClosed(WindowEvent e) {}
   public void windowIconified(WindowEvent e) {}
   public void windowDeiconified(WindowEvent e) {}
   public void windowActivated(WindowEvent e) {}
   public void windowDeactivated(WindowEvent e) {}
     
    public PneumaticsCAD(String[] margs) 
     { super();
       args=new Args(margs);
       
       self=this;
       setLayout(new BorderLayout()); 
       
       new PneumaticConfig();     // read config.txt
       pneumatics=new Pneumatics(this);
	   add(pneumatics,BorderLayout.CENTER);

	   statusField = new JTextField("");
	   statusField.setEditable(false);
	   add(statusField, BorderLayout.SOUTH);	 
	   
	   JPanel top=new JPanel();
       top.setLayout(new BorderLayout());
       top.add(createMenus(), BorderLayout.NORTH);
       top.add(createToolBar(), BorderLayout.CENTER);
       add(top, BorderLayout.NORTH);
        
       if(args.exampleURL!=null) loadExample();
       
       frame = new JFrame();
           Image iconImage=util.loadImage("webladdercad","","iconImage",File.separator+"resources"+File.separator+"images"+File.separator+"webladdercad.gif");
           if(iconImage!=null) frame.setIconImage(iconImage);
     	  frame.setTitle(config.getString("window.title"));
     	  frame.getContentPane().add(this, BorderLayout.CENTER);
           frame.addWindowListener(this);
           frame.setSize(config.getInt("window.width"),config.getInt("window.height"));
           frame.setVisible(true);  
    }
    
//-------------------------------------
//    
  public boolean saveAs()
   {final JFileChooser fc = new JFileChooser(file);
    fc.addChoosableFileFilter(new CircuitFilter());
    int returnVal = fc.showSaveDialog(frame);
    if(returnVal != JFileChooser.APPROVE_OPTION) return false;
    file = fc.getSelectedFile();

    String filePath = file.getPath();
    if(!filePath.toLowerCase().endsWith(".pc")) // for pneumatic circuit
     {
      file = new File(filePath + ".pc");
     }
    if(file.exists())
     {int n = JOptionPane.showConfirmDialog(frame,
        "The file with the selected filename exists, would you like to overwrite it?",
        "Exist?Overwrite?",
        JOptionPane.YES_NO_OPTION);
      if(n==1) return false;
     }
    save();
    return true;
   }

  public boolean exportFile()
   {final JFileChooser fc = new JFileChooser(file);
    fc.addChoosableFileFilter(new CircuitFilter());
    int returnVal = fc.showSaveDialog(frame);
    if(returnVal != JFileChooser.APPROVE_OPTION) return false;
    file = fc.getSelectedFile();
    if(file.exists())
     {int n = JOptionPane.showConfirmDialog(frame,
        "The file with the selected filename exists, would you like to overwrite it?",
        "Exist?Overwrite?",
        JOptionPane.YES_NO_OPTION);
      if(n==1) return false;
     }
    saveGroup();
    return true;
   }

 private boolean checkFileSaved()
  {if(pneumatics.pneumaticPanel.empty()) return true;
   if(oneModified())
     {int n = JOptionPane.showConfirmDialog(frame,
        "The circuit has been modified, would you like to save it?",
        "Save",
        JOptionPane.YES_NO_CANCEL_OPTION);
      if(n==0)
       {if(file!=null) save();
        else return(saveAs());
       }
      else if(n==2) return false;
      return true;
    }
   return true;
 } 

  public boolean openFile()
   {if(!checkFileSaved()) return false;
    final JFileChooser fc = new JFileChooser(file);
    fc.addChoosableFileFilter(new CircuitFilter());
    int returnVal = fc.showOpenDialog(frame);
    if(returnVal != JFileChooser.APPROVE_OPTION) return false;
    file = fc.getSelectedFile();
    if(!file.exists()) return false;
    if(!clear(false)) return false;
    open();
    repaint();
    modified=false;
    return true;
   }

  public boolean importFile()
   {if(!checkFileSaved()) return false;
    final JFileChooser fc = new JFileChooser(file);
    fc.addChoosableFileFilter(new CircuitFilter());
    int returnVal = fc.showOpenDialog(frame);
    if(returnVal != JFileChooser.APPROVE_OPTION) return false;
    file = fc.getSelectedFile();
    if(!file.exists()) return false;
    open();
    repaint();
    modified=true;
    return true;
   }  
 
  public void open(BufferedReader br, String filestr) throws Exception
   {
	  pneumatics.pneumaticPanel.deGroup();
	  pneumatics.pneumaticPanel.tempElementArray.clear();
//	System.err.println("degroup()");
	     String s=null;
  //       CEDevice.ratio=1.0; 
   boolean firstReadMatrix=true;      
   try
    {while((s=br.readLine())!=null)
      {if(s==null || s.length()==0) continue;
 //System.out.println(s);     
       StringTokenizer token=new StringTokenizer(s);
       int dtype=Integer.parseInt(token.nextToken());
        switch(dtype)
         {case Data_Element: 
          case Data_Line: 
          case Data_Limitswitch: 
           pneumatics.pneumaticPanel.open(s);
           break;
         }
      }
     br.close();
    }
   catch(Exception bre)
    {System.err.println(bre.getMessage());
     setStatus(filestr+" reading error!!");
    }
   }
  
  private boolean saveFile(boolean isgroup)
   {
	try 
     {PrintWriter out =new PrintWriter(new BufferedWriter(new FileWriter(file))); 
      if(isgroup) out.println(pneumatics.pneumaticPanel.saveGroup());
      else out.println(pneumatics.pneumaticPanel.save());
 //     out.println(electrics.save()); 
      out.close();
     } 
    catch(EOFException e) 
     {MessageBox("End of stream","Error");
      return false;
     } 
    catch(IOException ioe)
     {MessageBox("IOException","Error");
      return false;
     }
    return true;
    
   }
  public void saveGroup()
   {saveFile(true);
   }
   
  public void save()
   {if(file==null)
     {saveAs();
      return;
     }
    if(!saveFile(false)) return;
    modified=false;
   }

  public void open()
   {if(file==null) 
      {setStatus("File open error! : null");
        return;
      }
    try 
     {BufferedReader ins =new BufferedReader(new FileReader(file));
      open(ins,"File \""+file.toString()+"\"");
     }
    catch(Exception ioe)
     {setStatus("File \""+file.toString()+"\" I/O error!!");}
   }

  public boolean oneModified()
   {
    return modified;
   }

  
  public void forceClear(boolean nullfile)
   {pneumatics.pneumaticPanel.forceClear();
//    if(electrics!=null) electrics.forceClear();
	modified=false;
    
//    commands.clear();
    if(nullfile) file=null;
    repaint();
   } 

  public boolean clear(boolean nullfile)
   {if(oneModified() && !pneumatics.pneumaticPanel.empty())
     {int n = JOptionPane.showConfirmDialog(frame,
        "The circuit has been modified, would you like to save it?",
        "Save",
        JOptionPane.YES_NO_CANCEL_OPTION);
      if(n==0)
       {if(!saveAs()) return false;}
      else if(n==2)
       return false;
     } 
    forceClear(nullfile);
    return true;
   } 

    private void loadExample()
     {if(args.exampleURL!=null)
       {try
         {URL url= new java.net.URL("file:" + args.exampleURL);
    	  BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
          open(br, args.exampleURL.toString());
          pneumatics.pneumaticPanel.deGroup();
         }
        catch(Exception e)
         {statusField.setText(Config.getString("exampleURL.loaderror")+args.exampleURL.toString());} 
        statusField.setText(exampleDescription);       
       }
//debug("loadExample");       
     }
     
   private allAction aAction=new allAction();
   private allMenuListener allMenulistener=new allMenuListener();
   private menuItemMouseAdapter allmenuItemMouseAdapter=new menuItemMouseAdapter();
   public JButton newButton,openButton,saveButton,runButton,pauseButton,stepButton,stopButton,sequenceButton,plcButton,accButton,controllerButton;
   public JButton undoButton,redoButton;
   private JToolBar createToolBar()
    {
//	   System.out.println("createToolBar");
	  JToolBar toolBar = new JToolBar();
      Insets inset=new Insets(1,1,1,1);
      Image image=util.loadImage("/resources/images/new.gif");
      if(image!=null) newButton=new JButton((Icon) new ImageIcon(image));
      else newButton=new JButton();
//         button=new JButton((Icon) new ImageIcon(iconImages[i]));
      newButton.setMargin(inset);
      newButton.setText(null);
      newButton.setToolTipText("Start a new design.");
      newButton.setActionCommand("F_new");
      newButton.addActionListener(aAction);
      toolBar.add(newButton);
      openButton=new JButton((Icon) util.loadImageIcon("/resources/images/open.gif"));
//         button=new JButton((Icon) new ImageIcon(iconImages[i]));
      openButton.setMargin(inset);
      openButton.setText(null);
      openButton.setToolTipText("Open an old design.");
      openButton.setActionCommand("F_open");
      openButton.addActionListener(aAction);
      toolBar.add(openButton);
      saveButton=new JButton((Icon) util.loadImageIcon("/resources/images/save.gif"));
//         button=new JButton((Icon) new ImageIcon(iconImages[i]));
      saveButton.setMargin(inset);
      saveButton.setText(null);
      saveButton.setToolTipText("Save the current design.");
      saveButton.setActionCommand("F_save");
      saveButton.addActionListener(aAction);
      toolBar.add(saveButton);
      
      toolBar.addSeparator();

      undoButton=new JButton((Icon) util.loadImageIcon("/resources/images/undo.gif"));
  //    System.out.println(undoButton);
//         button=new JButton((Icon) new ImageIcon(iconImages[i]));
      undoButton.setMargin(inset);
      undoButton.setText(null);
      undoButton.setToolTipText("Undo");
      undoButton.setActionCommand("undo");
      undoButton.addActionListener(aAction);
      toolBar.add(undoButton); 
      redoButton=new JButton((Icon) util.loadImageIcon("/resources/images/redo.gif"));
//         button=new JButton((Icon) new ImageIcon(iconImages[i]));
      redoButton.setMargin(inset);
      redoButton.setText(null);
      redoButton.setToolTipText("Redo");
      redoButton.setActionCommand("redo");
      redoButton.addActionListener(aAction);
      toolBar.add(redoButton);  
      
      undoButton.setEnabled(false);     
      redoButton.setEnabled(false);
      toolBar.addSeparator();
 
      return toolBar;
     }

    public void stateChanged(ChangeEvent e)
     {if(!(e.getSource() instanceof JTabbedPane)) return;
       JTabbedPane tab=(JTabbedPane) e.getSource();
       int ind=tab.getSelectedIndex();
     }
     
    private JMenuBar menuBar = null;
//    private JMenuItem newMenuItem,openMenuItem,saveMenuItem,saveasMenuItem,importMenuItem,exportMenuItem;
    private JMenuItem moveMenuItem,cutMenuItem,copyMenuItem,pasteMenuItem,deleteMenuItem;

    private JMenuItem epasteMenuItem,eoverwriteMenuItem;
    private JMenu selectedMenu=null;
    private JMenuItem undoMenuItem,redoMenuItem;
    
    public JMenuBar createMenus() 
     {JMenuItem mi;
      
	// ***** create the menubar ****
	  menuBar = new JMenuBar();
	  menuBar.getAccessibleContext().setAccessibleName(Config.getString("menubar.accessible_description"));
	// ***** create File menu 
	  JMenu fileMenu = (JMenu) menuBar.add(new JMenu(Config.getString("FileMenu.file_label")));
      fileMenu.setMnemonic(getMnemonic("FileMenu.file_mnemonic"));
	  fileMenu.getAccessibleContext().setAccessibleDescription(Config.getString("FileMenu.accessible_description"));
      fileMenu.setActionCommand("file");
      fileMenu.addMenuListener(allMenulistener); 
      createMenuItem(fileMenu, "F_new","FileMenu.new_label", "FileMenu.new_mnemonic","FileMenu.new_accessible_description", aAction);
      createMenuItem(fileMenu, "F_open","FileMenu.open_label", "FileMenu.open_mnemonic","FileMenu.open_accessible_description", aAction);
      createMenuItem(fileMenu, "F_save","FileMenu.save_label", "FileMenu.save_mnemonic","FileMenu.save_accessible_description", aAction);
      createMenuItem(fileMenu,"F_saveas", "FileMenu.save_as_label", "FileMenu.save_as_mnemonic","FileMenu.save_as_accessible_description", aAction);             
  
      fileMenu.addSeparator();
      createMenuItem(fileMenu,"F_import", "FileMenu.import_label", "FileMenu.import_mnemonic","FileMenu.import_accessible_description", aAction);	    
      createMenuItem(fileMenu,"F_export","FileMenu.export_label", "FileMenu.export_mnemonic","FileMenu.export_accessible_description", aAction);	 
      fileMenu.addSeparator();
	  createMenuItem(fileMenu,"F_exit","FileMenu.exit_label", "FileMenu.exit_mnemonic","FileMenu.exit_accessible_description", aAction);
	   
	// ***** create Edit menu 
	  JMenu editMenu = (JMenu) menuBar.add(new JMenu(Config.getString("EditMenu.edit_label")));
      editMenu.setMnemonic(getMnemonic("EditMenu.edit_mnemonic"));
	  editMenu.getAccessibleContext().setAccessibleDescription(Config.getString("EditMenu.accessible_description"));
//        editMenu.addMenuListener(new EditMenuListener());           
      editMenu.setActionCommand("edit");
      editMenu.addMenuListener(allMenulistener);  
      undoMenuItem=createMenuItem(editMenu,"E_undo", "EditMenu.undo_label", "EditMenu.undo_mnemonic","EditMenu.undo_accessible_description", aAction);
      redoMenuItem=createMenuItem(editMenu,"E_redo", "EditMenu.redo_label", "EditMenu.redo_mnemonic","EditMenu.redo_accessible_description", aAction);
      undoMenuItem.setEnabled(false);
      redoMenuItem.setEnabled(false);
      editMenu.addSeparator();
      
//      createMenuItem(editMenu,"pneumatics", "EditMenu.pneumatics_label", "EditMenu.pneumatics_mnemonic","EditMenu.pneumatics_accessible_description", null);
      moveMenuItem=createMenuItem(editMenu,"E_groupmove", "EditMenu.group_move_label", "EditMenu.group_move_mnemonic","EditMenu.group_move_accessible_description", aAction);
      cutMenuItem=createMenuItem(editMenu,"E_groupcut", "EditMenu.cut_label", "EditMenu.cut_mnemonic","EditMenu.cut_accessible_description", aAction);
      copyMenuItem=createMenuItem(editMenu,"E_groupcopy", "EditMenu.copy_label", "EditMenu.copy_mnemonic","EditMenu.copy_accessible_description", aAction);
      pasteMenuItem=createMenuItem(editMenu,"E_grouppaste", "EditMenu.paste_label", "EditMenu.paste_mnemonic","EditMenu.paste_accessible_description", aAction);
      deleteMenuItem=createMenuItem(editMenu,"E_groupdel", "EditMenu.del_label", "EditMenu.del_mnemonic","EditMenu.del_accessible_description", aAction);
      createMenuItem(editMenu,"E_select", "EditMenu.select_label", "EditMenu.select_mnemonic","EditMenu.select_accessible_description", aAction);
      
      editMenu.addSeparator();
      createMenuItem(editMenu,"E_saveProperty","EditMenu.save_label","EditMenu.save_mnemonic","EditMenu.save_accessible_description", aAction);
              
      menuBar.add(Box.createHorizontalGlue());
        	               // ***** create Help menu 
	  JMenu helpMenu = (JMenu) menuBar.add(new JMenu(Config.getString("helpMenu.edit_label")));
      helpMenu.setMnemonic(getMnemonic("helpMenu.edit_mnemonic"));
	  helpMenu.getAccessibleContext().setAccessibleDescription(Config.getString("helpMenu.accessible_description"));
//      if(isApplet())
      createMenuItem(helpMenu,"H_online","helpMenu.online_label","helpMenu.online_mnemonic","helpMenu.online_accessible_description", aAction);

      helpMenu.addSeparator();
      createMenuItem(helpMenu,"H_register","helpMenu.register_label","helpMenu.register_mnemonic","helpMenu.register_accessible_description", aAction);
       
      helpMenu.addSeparator();
      createMenuItem(helpMenu,"H_about","helpMenu.about_label", "helpMenu.about_mnemonic","helpMenu.about_accessible_description", aAction);

	  return menuBar;
    }

   public JMenuItem createMenuItem(JMenu menu,String acommand, String label, String mnemonic,String accessibleDescription, Action action) 
    {JMenuItem mi=null;
	 mi = (JMenuItem) menu.add(new JMenuItem(config.getString(label)));
	 mi.setActionCommand(acommand);
     mi.setMnemonic(getMnemonic(mnemonic));
	 mi.getAccessibleContext().setAccessibleDescription(config.getString(accessibleDescription));
	 mi.addActionListener(action);
     mi.addMouseListener(allmenuItemMouseAdapter);
	 if(action==null) mi.setEnabled(false);
	 return mi;
    }   

    public char getMnemonic(String key) {return (Config.getString(key)).charAt(0);}
    
   class allAction extends AbstractAction
    {
     public void actionPerformed(ActionEvent e) 
      {
          String com=null;
          if(e.getSource() instanceof JMenuItem) com=((JMenuItem) e.getSource()).getActionCommand();
          else if(e.getSource() instanceof JButton) com=((JButton) e.getSource()).getActionCommand();
          else return;
          ActionPerformed(com);
      }
   }

  private void ActionPerformed(String com) 
   {   
	if(com.equals("H_about"))
     {if(aboutBox==null) 
       {JPanel panel = new AboutPanel(this);
		panel.setLayout(new BorderLayout());
		aboutBox = new JDialog(frame, Config.getString("AboutBox.title"), false);
		aboutBox.getContentPane().add(panel, BorderLayout.CENTER);
		JPanel buttonpanel = new JPanel();
		buttonpanel.setOpaque(false);
		JButton button = (JButton) buttonpanel.add(new JButton(Config.getString("AboutBox.ok_button_text")));
		panel.add(buttonpanel, BorderLayout.SOUTH);
		button.addActionListener(new OkAction(aboutBox));
	   }
	  aboutBox.pack();
	  aboutBox.setLocation(getLocationOnScreen().x + 10, getLocationOnScreen().y +10);
	  aboutBox.show();
     }
    else if(com.equals("F_new")) 
     {
      clear(true);
     }
    else if(com.equals("F_open")) 
     {
      openFile();
     }
    else if(com.equals("F_save")) 
     {save();}
    else if(com.equals("F_saveas")) 
     {saveAs();}
    else if(com.equals("F_import")) 
     {
      importFile();
     }
    else if(com.equals("F_export")) 
     {exportFile();}
    else if(com.equals("E_groupmove")) 
     {pneumatics.pneumaticPanel.groupMove=true;
      pneumatics.pneumaticPanel.repaint();
     }
    else if(com.equals("E_groupcut"))
     {pneumatics.pneumaticPanel.groupCut();
     }
    else if(com.equals("E_groupcopy"))
     {pneumatics.pneumaticPanel.copyToBoard();}
    else if(com.equals("E_grouppaste"))
     {pneumatics.pneumaticPanel.pasteBoardTo();}
    else if(com.equals("E_groupdel"))
     {pneumatics.pneumaticPanel.groupDelete();
     }
    else if(com.equals("E_select")) pneumatics.pneumaticPanel.selectAll();
    else if(com.equals("F_exit")) 
     {
      int value=JOptionPane.showConfirmDialog(new Frame(),Config.getString("pneumatics.s32"),
    		  Config.getString("pneumatics.s33"),
                JOptionPane.YES_NO_OPTION);
      if (value == JOptionPane.YES_OPTION) 
       {pneumatics.pneumaticPanel.stopTimer();
        System.exit(0);
       }
     }
    else if(com.equals("E_undo"))
     {((Command) commands.get(commandIndex)).undo();
      commandIndex--;
      switchDoMenuButton();
     }
    else if(com.equals("E_redo"))
     {commandIndex++;
      ((Command) commands.get(commandIndex)).redo();
      switchDoMenuButton();
     }
    else if(com.equals("E_saveProperty"))
    {
    }
  }

    class OkAction extends AbstractAction 
     {JDialog aboutBox;
      protected OkAction(JDialog aboutBox) 
       {super("OkAction");
	    this.aboutBox = aboutBox;
       }
      public void actionPerformed(ActionEvent e) {aboutBox.setVisible(false);}
     }

    class AboutPanel extends JPanel 
     {ImageIcon aboutimage = null;
	  PneumaticsCAD pneumatics = null;
	  public AboutPanel(PneumaticsCAD pneu) 
	   {this.pneumatics = pneu;
	    aboutimage = util.loadImageIcon("/images/About.jpg");
	    setOpaque(false);
	   }
	  public void paint(Graphics g) 
	   {aboutimage.paintIcon(this, g, 0, 0);
	    super.paint(g);
	   }
	  public Dimension getPreferredSize() 
	   {return new Dimension(aboutimage.getIconWidth(),aboutimage.getIconHeight());
	   }
     }

    class allMenuListener implements MenuListener
     {
      public void menuSelected(MenuEvent e) 
       {if(!(e.getSource() instanceof JMenu)) return;
 
         JMenu menu=(JMenu) e.getSource();
         selectedMenu=menu;
         int nmenu=-1;
         for(int i=0;i<menuBar.getMenuCount();i++)
          {if(menu==menuBar.getMenu(i)) {nmenu=i;break;}}
         
        String com=menu.getActionCommand();
        if(com.equals("edit"))
         {
          if(pneumatics.pneumaticPanel.popupPoint==null) pneumatics.pneumaticPanel.popupPoint=new Point();
          if(pneumatics.pneumaticPanel.groupArray.size()>0)
           {moveMenuItem.setEnabled(true);
             deleteMenuItem.setEnabled(true);
             cutMenuItem.setEnabled(true);
             copyMenuItem.setEnabled(true);
           }
          else
           {moveMenuItem.setEnabled(false);
             deleteMenuItem.setEnabled(false);
             cutMenuItem.setEnabled(false);
             copyMenuItem.setEnabled(false);
           }
          if(pneumatics.pneumaticPanel.boardArray.size()>0)
           pasteMenuItem.setEnabled(true);
          else
           pasteMenuItem.setEnabled(false);

         }         
      }
      public void menuCanceled(MenuEvent e) {}
      public void menuDeselected(MenuEvent e) {}      
     }      
    
 private void switchDoMenuButton()
  {
   if(commandIndex>=0 && commandIndex<commands.size())
    {undoMenuItem.setEnabled(true);
     undoButton.setEnabled(true);
    }
   else 
    {undoMenuItem.setEnabled(false);
     undoButton.setEnabled(false);
    }
   if(commandIndex+1>=0 && commandIndex+1<commands.size())
    {redoMenuItem.setEnabled(true);
     redoButton.setEnabled(true);
    }
   else 
    {redoMenuItem.setEnabled(false);
     redoButton.setEnabled(false);
    }
  }    

  class menuItemMouseAdapter extends MouseAdapter 
   {
     public void mouseEntered(MouseEvent e) 
       {JMenuItem mitem=(JMenuItem) e.getSource();
         if(selectedMenu==null) return;
         int n=-1;
         for(int i=0;i<selectedMenu.getItemCount();i++)
          if(selectedMenu.getItem(i)==mitem) {n=i;break;}
        if(n<0) return;
       }
   }

  private class createElementCommand extends Command
    {boolean oldmodified;
	public createElementCommand(Object ele,boolean old)
     {super("IconPanel",ele,IconPanel.Command_createElement);
      oldmodified=old;
     }
    public void undo()
     {pneumatics.pneumaticPanel.remove((tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) source);
	  pneumatics.pneumaticPanel.repaint();
      modified=oldmodified;
      
     }
    public void redo()
     {pneumatics.pneumaticPanel.add((tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) source);
	  pneumatics.pneumaticPanel.repaint();
      modified=true;
                 
     }
   }  

  public void createElement(String modelType,String command) throws Exception
  {
   Object obj=Creater.instanceElement(modelType,command, self);
   if(obj!=null)
    {
     tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement element=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) obj;
	  boolean oldmodified=modified;
	  pneumatics.pneumaticPanel.add(element);
	  pneumatics.pneumaticPanel.repaint();
     modified=true;                  
     addCommand(new createElementCommand(element,oldmodified));     
    }
  }

  public void loadCircuit(String modelName)
   {
    String fstr=((CircuitParameter) PneumaticConfig.circuit.get(modelName)).fileString;
//    System.out.println(fstr);
    BufferedReader br;
    URL url;
    try
     {//url = getClass().getResource("../../../resources/"+fstr); 
      br =new BufferedReader(new FileReader(Paths.get("").toAbsolutePath().toString()+File.separator+"resources"+File.separator+fstr));   
      open(br,fstr);
     }
    catch(Exception ce)
     {System.err.println(ce.getMessage());}
   }

  public void addCommand(Command com)
   {commandIndex++;
	if(commandIndex>commands.size()-1) commands.add(com);
	else commands.set(commandIndex,com);
	switchDoMenuButton();
	
	Object s=com.getSource();
	int id=com.getId();
	String cn=com.getClassName();
   }

  public void setModified(boolean m) 
   {modified=m;   
   }
  public boolean getModified() {return modified;}  

}