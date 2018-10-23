package tw.com.justiot.pneumatics;

import java.util.Hashtable;
import java.awt.Image;
public class ImageMap {
  public static Hashtable<String, Image> graphics=new Hashtable<String, Image>();
  public static boolean hasImage(String modelType,String modelName,String var)
	{
	 String key=modelType+"*"+modelName+"*"+var;
	 Image img=graphics.get(key);
	 if(img==null) return false;
	 else return true;
	}
  public static Image getImage(String modelType,String modelName,String var)
	{
	 String key=modelType+"*"+modelName+"*"+var;
	 Image img=graphics.get(key);
	 return img;
	}
}
