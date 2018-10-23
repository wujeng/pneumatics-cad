package tw.com.justiot.pneumatics.panel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import tw.com.justiot.pneumatics.part.*;

import java.awt.geom.*;
import java.io.*;

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