package tw.com.justiot.pneumatics.config;

import java.awt.*;
import java.io.File;

import tw.com.justiot.pneumatics.*;

public class ElementParameter
 {public String modelName,modelType,toolTip;
  public Dimension realImageDim;
  public Image realImage;
  public Dimension imageDim;
  public Image[] images;
  public ElementParameter(String mtype,String mname,String tip,int riwidth,int riheight,String rifile,
                             int iwidth,int iheight,String ifile, int imgnumber)
   {modelType=mtype;
    modelName=mname;
    toolTip=tip;
    realImageDim=new Dimension(riwidth,riheight);
    imageDim=new Dimension(iwidth,iheight);
    realImage=util.loadImage(mtype,mname,"realImage",File.separator+"resources"+File.separator+rifile);
    images=util.loadImages(mtype,mname,ifile,imgnumber);
   }
 }
