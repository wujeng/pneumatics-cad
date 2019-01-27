package tw.com.justiot.pneumatics.config;

import tw.com.justiot.pneumatics.part.Port;
import tw.com.justiot.pneumatics.part.Position;

public class DelayParameter extends ElementParameter
 {public Port[] ports;
  public Position textPos;
  public boolean isUpEnd;
  public boolean isNO;
  public DelayParameter(String mname,String tip,int riwidth,int riheight,String rifile,
       int iwidth,int iheight,String ifile, Position[] pos, int[] dir,Position tpos,boolean upEnd,boolean no)
   {super("Delay",mname,tip,riwidth,riheight,rifile,iwidth,iheight,ifile,2);
    int portnumber=3;
    ports=new Port[portnumber];
    for(int i=0;i<portnumber;i++)
     {ports[i]=new Port(pos[i]);
      ports[i].setDir(dir[i]);
     }
    textPos=tpos;
    isUpEnd=upEnd;
    isNO=no;
   }
 }
