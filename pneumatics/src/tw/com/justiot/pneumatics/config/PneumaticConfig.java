package tw.com.justiot.pneumatics.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;

import tw.com.justiot.pneumatics.part.Port;
import tw.com.justiot.pneumatics.part.Position;

public class PneumaticConfig
 {
  private static final String file=File.separator+"resources"+File.separator+"config.txt";
  public static ArrayList keylist=new ArrayList();
  public static Hashtable parameter=new Hashtable();
  public static Hashtable circuit=new Hashtable();
  private String mtype;
  private static void addkey(String key) {
	  keylist.add(key);
	}
  public PneumaticConfig()
   {	   
	String line=null;
    InputStream is;
    InputStreamReader isr;
    URL url;

    try
     {
//      url = getClass().getResource(Paths.get("").toAbsolutePath().toString()+file); 
//      is = url.openStream();
//      isr = new InputStreamReader(is);
//      BufferedReader breader=new BufferedReader(isr);
//    	System.out.println(Paths.get("").toAbsolutePath().toString()+file);
    	BufferedReader breader=new BufferedReader(new FileReader(Paths.get("").toAbsolutePath().toString()+file));
      while((line=breader.readLine()) != null)
       {if(line.length()<=0) continue;
        if(line.substring(0,1).equals("%")) continue;
        if(line.substring(0,1).equals("["))
        {mtype=line.substring(1,line.indexOf("]"));
         continue;
        }
        
        if(mtype.equals("Actuator")) Actuator(line);
        if(mtype.equals("Valve")) Valve(line);
        if(mtype.equals("Connector")) Connector(line);
        if(mtype.equals("Logic")) Logic(line);
        if(mtype.equals("Delay")) Delay(line);
        if(mtype.equals("FlowValve")) FlowValve(line);
        if(mtype.equals("PressureValve")) PressureValve(line);
        if(mtype.equals("Gauge")) Gauge(line);

   //     if(mtype.equals("EValve")) EValve(line);
        if(mtype.equals("Cascade Method")) Circuit(line,"Cascade Method");
        if(mtype.equals("Demos")) Circuit(line,"Demos");
        if(mtype.equals("EDemos")) Circuit(line,"EDemos");
        
       }
     }
    catch(FileNotFoundException e)
     {java.lang.System.err.println("FileNotFound!"+e.getMessage());}
    catch(Exception ee)
     {java.lang.System.err.println(ee.getMessage());}
   }

   private void Actuator(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();
     int imageNumber=Integer.parseInt(token.nextToken());
     int x=Integer.parseInt(token.nextToken());
     int y=Integer.parseInt(token.nextToken());
     Position fpos=null;
     if(x > 0 || y > 0) fpos=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position bpos=null;
     if(x > 0 || y > 0) bpos=new Position(x,y);

     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position ls1=null;
     if(x > 0 || y > 0) ls1=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position ls2=null;
     if(x > 0 || y > 0) ls2=new Position(x,y);
     addkey(modelName);
     parameter.put(modelName,new ActuatorParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,imageNumber,fpos,bpos,ls1,ls2));
     }
     catch(Exception e)
      {java.lang.System.err.println("Actuator configuration error!"+e.getMessage());}
    }

   private void Valve(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();
     int imageNumber=Integer.parseInt(token.nextToken());

     int squareNumber=Integer.parseInt(token.nextToken());
     int forceNumber=Integer.parseInt(token.nextToken());
     int forceType=0;
     String ftypestr=token.nextToken();
     if(ftypestr.equals("FLUID")) forceType=ValveParameter.FORCE_FLUID;
     if(ftypestr.equals("MAN")) forceType=ValveParameter.FORCE_MAN;
     if(ftypestr.equals("MECHANIC")) forceType=ValveParameter.FORCE_MECHANIC;
     if(ftypestr.equals("ELECTRIC")) forceType=ValveParameter.FORCE_ELECTRIC;

     boolean exclusiveForce=false;
     if(token.nextToken().equals("1")) exclusiveForce=true;

     int x=Integer.parseInt(token.nextToken());
     int y=Integer.parseInt(token.nextToken());
     Position ppos=null;
     if(x > 0 || y > 0) ppos=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position rpos=null;
     if(x > 0 || y > 0) rpos=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position spos=null;
     if(x > 0 || y > 0) spos=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position apos=null;
     if(x > 0 || y > 0) apos=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position bpos=null;
     if(x > 0 || y >0) bpos=new Position(x,y);
     Position[] pos5=new Position[]{ppos,rpos,spos,apos,bpos};

     String[] connections=new String[squareNumber];
     for(int i=0;i<squareNumber;i++)
      connections[i]=token.nextToken();

     Position[] forcePos=new Position[forceNumber];
     for(int i=0;i<forceNumber;i++)
      {forcePos[i]=null;
       x=Integer.parseInt(token.nextToken());
       y=Integer.parseInt(token.nextToken());
//Tools.trace("x"+x+":y"+y);
       if(x > 0 || y > 0) forcePos[i]=new Position(x,y);
      }

     boolean oneWay=false;
     if(token.nextToken().equals("yes")) oneWay=true;
     addkey(modelName);
     parameter.put(modelName,new ValveParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,imageNumber,
         squareNumber,forceNumber,forceType,exclusiveForce, pos5, connections, forcePos, oneWay));
     }
     catch(Exception e)
      {java.lang.System.err.println("Valve configuration error!"+e.getMessage());}
    }

   private void Connector(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();
     int portNumber=Integer.parseInt(token.nextToken());
     Position[] pos=new Position[portNumber];
     int[] dir=new int[portNumber];
     int x=0,y=0;
     String dirstr=null;
     for(int i=0;i<portNumber;i++)
      {x=Integer.parseInt(token.nextToken());
       y=Integer.parseInt(token.nextToken());
       dirstr=token.nextToken().toLowerCase();
       pos[i]=null;   
       if(x > 0 || y > 0) 
        {pos[i]=new Position(x,y);
         if(dirstr.equals("up")) dir[i]=Port.DIR_UP;
         else if(dirstr.equals("down")) dir[i]=Port.DIR_DOWN;
         else if(dirstr.equals("right")) dir[i]=Port.DIR_RIGHT;
         else if(dirstr.equals("left")) dir[i]=Port.DIR_LEFT;
         else dir[i]=Port.DIR_DOWN;
        }
      }
     addkey(modelName);
     parameter.put(modelName,new ConnectorParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,portNumber,pos,dir));
     }
     catch(Exception e)
      {java.lang.System.err.println("Connector configuration error!"+e.getMessage());}
    }

   private void Logic(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();
     int x=Integer.parseInt(token.nextToken());
     int y=Integer.parseInt(token.nextToken());
     Position apos=null;
     if(x > 0 || y > 0) apos=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position xpos=null;
     if(x > 0 || y > 0) xpos=new Position(x,y);
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position ypos=null;
     if(x > 0 || y > 0) ypos=new Position(x,y);
     addkey(modelName);
     parameter.put(modelName,new LogicParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,apos,xpos,ypos));
     }
     catch(Exception e)
      {java.lang.System.err.println("Logic configuration error!"+e.getMessage());}
    }

  private void Delay(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();
     int portNumber=3;
     Position[] pos=new Position[portNumber];
     int[] dir=new int[portNumber];
     int x=0,y=0;
     String dirstr=null;
     for(int i=0;i<portNumber;i++)
      {x=Integer.parseInt(token.nextToken());
       y=Integer.parseInt(token.nextToken());
       dirstr=token.nextToken().toLowerCase();
       pos[i]=null;   
       if(x > 0 || y > 0) 
        {pos[i]=new Position(x,y);
         if(dirstr.equals("up")) dir[i]=Port.DIR_UP;
         else if(dirstr.equals("down")) dir[i]=Port.DIR_DOWN;
         else if(dirstr.equals("right")) dir[i]=Port.DIR_RIGHT;
         else if(dirstr.equals("left")) dir[i]=Port.DIR_LEFT;
         else dir[i]=Port.DIR_DOWN;
        }
      }
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position tpos=null;
     if(x > 0 || y > 0) tpos=new Position(x,y);
     boolean upEnd=true;
     if(token.nextToken().equals("0")) upEnd=false;
     boolean open=true;
     if(token.nextToken().equals("0")) open=false;   
     addkey(modelName);
     parameter.put(modelName,new DelayParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,pos,dir,tpos,upEnd,open));
     }
     catch(Exception e)
      {java.lang.System.err.println("Logic configuration error!"+e.getMessage());}
    }

  private void FlowValve(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();

     Position[] pos=new Position[2];
     int[] dir=new int[2];
     int x=0,y=0;
     String dirstr=null;
     for(int i=0;i<2;i++)
      {x=Integer.parseInt(token.nextToken());
       y=Integer.parseInt(token.nextToken());
       dirstr=token.nextToken().toLowerCase();
//System.err.println(x+":"+y+":"+dirstr);
       pos[i]=null;   
       if(x > 0 || y > 0) 
        {pos[i]=new Position(x,y);
         if(dirstr.equals("up")) dir[i]=Port.DIR_UP;
         else if(dirstr.equals("down")) dir[i]=Port.DIR_DOWN;
         else if(dirstr.equals("right")) dir[i]=Port.DIR_RIGHT;
         else if(dirstr.equals("left")) dir[i]=Port.DIR_LEFT;
         else dir[i]=Port.DIR_DOWN;
        }
      }

     String para=token.nextToken();
     boolean oneWay=false;
     if(para.equals("1")) oneWay=true;
     para=token.nextToken();
     boolean adjust=false;
     if(para.equals("1")) adjust=true;
     addkey(modelName);
     parameter.put(modelName,new FlowValveParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,pos,dir,oneWay,adjust));
     }
     catch(Exception e)
      {java.lang.System.err.println("FlowValve configuration error!"+e.getMessage());}
    }

  private void PressureValve(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();
     int portNumber=Integer.parseInt(token.nextToken());
     Position[] pos=new Position[portNumber];
     int[] dir=new int[portNumber];
     int x=0,y=0;
     String dirstr=null;
     for(int i=0;i<portNumber;i++)
      {x=Integer.parseInt(token.nextToken());
       y=Integer.parseInt(token.nextToken());
       dirstr=token.nextToken().toLowerCase();
       pos[i]=null;   
       if(x > 0 || y > 0) 
        {pos[i]=new Position(x,y);
         if(dirstr.equals("up")) dir[i]=Port.DIR_UP;
         else if(dirstr.equals("down")) dir[i]=Port.DIR_DOWN;
         else if(dirstr.equals("right")) dir[i]=Port.DIR_RIGHT;
         else if(dirstr.equals("left")) dir[i]=Port.DIR_LEFT;
         else dir[i]=Port.DIR_DOWN;
        }
      }
     addkey(modelName);
     parameter.put(modelName,new PressureValveParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,portNumber,pos,dir));
     }
     catch(Exception e)
      {java.lang.System.err.println("Connector configuration error!"+e.getMessage());}
    }

  private void Gauge(String line)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(parameter.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     int imageWidth=Integer.parseInt(token.nextToken());
     int imageHeight=Integer.parseInt(token.nextToken());
     String imageFile=token.nextToken();
     int portNumber=Integer.parseInt(token.nextToken());
     Position[] pos=new Position[portNumber];
     int[] dir=new int[portNumber];
     int x=0,y=0;
     String dirstr=null;
     for(int i=0;i<portNumber;i++)
      {x=Integer.parseInt(token.nextToken());
       y=Integer.parseInt(token.nextToken());
       dirstr=token.nextToken().toLowerCase();
       pos[i]=null;   
       if(x > 0 || y > 0) 
        {pos[i]=new Position(x,y);
         if(dirstr.equals("up")) dir[i]=Port.DIR_UP;
         else if(dirstr.equals("down")) dir[i]=Port.DIR_DOWN;
         else if(dirstr.equals("right")) dir[i]=Port.DIR_RIGHT;
         else if(dirstr.equals("left")) dir[i]=Port.DIR_LEFT;
         else dir[i]=Port.DIR_DOWN;
        }
      }
     x=Integer.parseInt(token.nextToken());
     y=Integer.parseInt(token.nextToken());
     Position tpos= new Position(x,y);
     addkey(modelName);
     parameter.put(modelName,new GaugeParameter(modelName,toolTip,realImageWidth,realImageHeight,realImageFile,
         imageWidth,imageHeight,imageFile,portNumber,pos,dir,tpos));
     }
     catch(Exception e)
      {java.lang.System.err.println("Gauge configuration error!"+e.getMessage());}
    }

  private void Circuit(String line,String modelType)
    {try{
     StringTokenizer token=new StringTokenizer(line);
     String modelName=token.nextToken();
     if(circuit.containsKey(modelName)) return;
     String toolTip=token.nextToken();
     String fstr=token.nextToken();
     int realImageWidth=Integer.parseInt(token.nextToken());
     int realImageHeight=Integer.parseInt(token.nextToken());
     String realImageFile=token.nextToken();
     circuit.put(modelName,new CircuitParameter(modelType,modelName,toolTip,fstr,realImageWidth,realImageHeight,realImageFile));
     }
     catch(Exception e)
      {java.lang.System.err.println("Circuit configuration error!"+e.getMessage());}
    }

 }     
