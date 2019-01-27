package tw.com.justiot.pneumatics.config;

import tw.com.justiot.pneumatics.part.Port;
import tw.com.justiot.pneumatics.part.Position;

public class ConnectorParameter extends ElementParameter
 {public Port[] ports;
  public ConnectorParameter(String mname,String tip,int riwidth,int riheight,String rifile,
                             int iwidth,int iheight,String ifile, int portnumber, Position[] pos, int[] dir)
   {super("Connector",mname,tip,riwidth,riheight,rifile,iwidth,iheight,ifile,1);
    ports=new Port[portnumber];
    for(int i=0;i<portnumber;i++)
     {ports[i]=new Port(pos[i]);
      ports[i].setDir(dir[i]);
     }
   }
 }
