package tw.com.justiot.pneumatics;

import java.util.*;
import java.awt.Dimension;
import java.io.*;
import java.nio.file.Paths;

public class PropertyControl
  {private final String pfile=Paths.get("").toAbsolutePath().toString()+File.separator+"resources"+File.separator+"windows.properties";
   private final String[] winkeys={"win.x","win.y","win.w","win.h","flowrate"};
   private final String langkey="language";
   private final int[] windefault={0,0,660,420,100};
   public Properties properties=null;

   private PneumaticsCAD ladder;
   public PropertyControl(PneumaticsCAD ladder)
    {this.ladder=ladder;
     getProperties();
     showProperties();
    }
   public String getLang() {
	 return properties.getProperty(langkey);
   }
   private void showProperties() {
	  for(int i=0;i<winkeys.length;i++) {
		  System.out.println(winkeys[i]+": "+properties.getProperty(winkeys[i]));
	  }
	  System.out.println(langkey+": "+properties.getProperty(langkey));
   }
   
   public void getProperties() 
   {Properties defaults = new Properties();
    for(int i=0;i<winkeys.length;i++)
     defaults.put(winkeys[i],windefault[i]);
    defaults.put(langkey,"tw");
    
    properties = new Properties(defaults); 
    FileInputStream in = null;
    try
     {in = new FileInputStream(pfile);
      properties.load(in);
     }
    catch (java.io.FileNotFoundException e) 
     {in = null;
      System.err.println("Can't find properties file. " +"Using defaults.");
     }
    catch (java.io.IOException e) 
     {System.err.println("Can't read properties file. " +"Using defaults.");} 
      finally 
       {if(in!=null) {try{in.close();} catch (java.io.IOException e) {} in = null;}
     }
   }
   
   public void applyWinProperties() {
	 int wx=Integer.parseInt(properties.getProperty("win.x"));
	 int wy=Integer.parseInt(properties.getProperty("win.y"));
	 int ww=Integer.parseInt(properties.getProperty("win.w"));
	 int wh=Integer.parseInt(properties.getProperty("win.h"));
	 ladder.frame.setLocation(wx, wy);
	 ladder.frame.setPreferredSize(new Dimension(ww, wh));
   }
   
   public void updateProperties()
    { 
    properties.put("win.x",Integer.toString(ladder.frame.getLocationOnScreen().x));
    properties.put("win.y",Integer.toString(ladder.frame.getLocationOnScreen().y));
    properties.put("win.w",Integer.toString(ladder.frame.getSize().width));
    properties.put("win.h",Integer.toString(ladder.frame.getSize().height));
    
    properties.put(langkey,ladder.lang);
    }

   public void saveProperties() 
    {updateProperties();
  //   showProperties();
     FileOutputStream out = null;
     try 
      {out = new FileOutputStream(pfile);
       properties.store(out,"Windows Properties");
      }
     catch (java.io.IOException e) 
      {System.err.println("Can't save properties. Oh well, it's not a big deal.");
      } 
     finally 
      {if(out!=null) {try{out.close();} catch(java.io.IOException ee){} out = null;}
      }
    }

}

