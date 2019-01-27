package tw.com.justiot.pneumatics.panel;

import java.util.EventObject;

import tw.com.justiot.pneumatics.part.Port;

public class ConnectEvent extends EventObject 
 {public Port port;
  public int x,y;
  public ConnectEvent(Object source,Port port,int x,int y)
   {super(source);
    this.port=port;
    this.x=x;
    this.y=y;
   }
 }