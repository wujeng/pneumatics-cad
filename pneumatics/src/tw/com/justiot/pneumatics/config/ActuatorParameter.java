package tw.com.justiot.pneumatics.config;

import tw.com.justiot.pneumatics.part.Port;
import tw.com.justiot.pneumatics.part.Position;

public class ActuatorParameter extends ElementParameter
 {public Port fport,bport;
  public Position LSpos1,LSpos2;
  public ActuatorParameter(String mname,String tooltip,int riwidth,int riheight,String rifile,
                             int iwidth,int iheight,String ifile, int imgnumber, Position fpos, Position bpos, Position ls1, Position ls2)
   {super("Actuator",mname,tooltip,riwidth,riheight,rifile,iwidth,iheight,ifile,imgnumber);
    if(fpos!=null) fport=new Port(fpos);
    else fport=null;
    if(bpos!=null) bport=new Port(bpos);
    else bport=null;
    LSpos1=ls1;
    LSpos2=ls2;
   }
 }
