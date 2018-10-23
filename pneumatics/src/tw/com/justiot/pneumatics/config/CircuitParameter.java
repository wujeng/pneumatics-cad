package tw.com.justiot.pneumatics.config;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import tw.com.justiot.pneumatics.util;

public class CircuitParameter
 {public String modelType;
  public String modelName;
  public String toolTip;
  public String fileString;
  public Image image;
  public Dimension imageDim;
  public CircuitParameter(String mtype,String mname,String tip,String fstr,int iwidth,int iheight,String ifile)
   {modelType=mtype;
    modelName=mname;
    toolTip=tip;
    fileString=fstr;
    imageDim=new Dimension(iwidth,iheight);
    image=util.loadImage(mtype,mname,"image",File.separator+"resources"+File.separator+ifile);
   }
 }
