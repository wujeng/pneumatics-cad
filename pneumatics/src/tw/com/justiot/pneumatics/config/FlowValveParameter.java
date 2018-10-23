package tw.com.justiot.pneumatics.config;

import tw.com.justiot.pneumatics.part.Port;
import tw.com.justiot.pneumatics.part.Position;

public class FlowValveParameter extends ElementParameter
 {public Port port1;
  public Port port2;
  public boolean oneWay;
  public boolean adjustable; 
  public FlowValveParameter(String mname,String tip,int riwidth,int riheight,String rifile,
       int iwidth,int iheight,String ifile, Position[] pos, int[] dir,boolean ow,boolean adj)
   {super("FlowValve",mname,tip,riwidth,riheight,rifile,iwidth,iheight,ifile,1);    
     if(pos[0]!=null)
      {port1=new Port(pos[0]);
        port1.setDir(dir[0]);
      }
     if(pos[1]!=null)
      {
        port2=new Port(pos[1]);
        port2.setDir(dir[1]);
      }
    oneWay=ow;
    adjustable=adj;
   }
 }
