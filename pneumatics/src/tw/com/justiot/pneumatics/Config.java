package tw.com.justiot.pneumatics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;

public class Config {
  private static String filename="application.properties";
  private static com.typesafe.config.Config conf;
  public Config() {
	  
	  String s = Paths.get("").toAbsolutePath().toString()+File.separator+"resources"+File.separator+filename;
	  File file=new File(s);
	  conf=ConfigFactory.parseFile(file); 
  }
  
  public static String getString(String key) {
	  return conf.getString(key);
  }
  public static boolean getBoolean(String key) {
	  return conf.getBoolean(key);
  }
  public static int getInt(String key) {
	  return conf.getInt(key);
  }
  public static double getDouble(String key) {
	  return conf.getDouble(key);
  }
  
  public static void saveProperties(String fn) {
      Properties properties = new Properties();
      Set<Entry<String, ConfigValue>> set=conf.entrySet();
      for (Entry<String, ConfigValue> s : set) {
    	  properties.setProperty(s.getKey(), conf.getString(s.getKey()));
    	}
      File f = new File("resources/"+fn);
      try {
      OutputStream out = new FileOutputStream( f );
      properties.store(out, "This is an optional header comment string");
      } catch(Exception e) {
    	  
      }
  }
}
