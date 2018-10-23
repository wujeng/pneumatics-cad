package tw.com.justiot.pneumatics.config;

import tw.com.justiot.pneumatics.part.Port;
import tw.com.justiot.pneumatics.part.Position;

public class LogicParameter extends ElementParameter
 {public Port aport;
  public Port xport;
  public Port yport;
  public LogicParameter(String mname,String tip,int riwidth,int riheight,String rifile,
                             int iwidth,int iheight,String ifile, Position ap, Position xp, Position yp)
   {super("Logic",mname,tip,riwidth,riheight,rifile,iwidth,iheight,ifile,4);    
    if(ap!=null) 
     {aport=new Port(ap);
      aport.setDir(Port.DIR_UP);
     }
    else aport=null;
    if(xp!=null) 
     {xport=new Port(xp);
      xport.setDir(Port.DIR_LEFT);
     }
    else xport=null;
    if(yp!=null) 
     {yport=new Port(yp);
      yport.setDir(Port.DIR_RIGHT);
     }
    else yport=null;
   }
 }
