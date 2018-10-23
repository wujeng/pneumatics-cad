package tw.com.justiot.pneumatics;

public class Creater 
 {
  public static Object instanceElement(String modelType,String modelName,PneumaticsCAD pneumaticscad) throws Exception
  {tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement ele=null;
   if(modelType.equals("Actuator"))
    {
	 tw.com.justiot.pneumatics.pneumaticelement.Actuator act=new tw.com.justiot.pneumatics.pneumaticelement.Actuator(modelName, pneumaticscad);
	 ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) act;
    }
   else if(modelType.equals("Valve"))
    {
     tw.com.justiot.pneumatics.pneumaticelement.Valve va=new tw.com.justiot.pneumatics.pneumaticelement.Valve(modelName, pneumaticscad);
     ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) va;
    }
   else if(modelType.equals("Connector"))
    {
	 tw.com.justiot.pneumatics.pneumaticelement.Connector va=new tw.com.justiot.pneumatics.pneumaticelement.Connector(modelName, pneumaticscad);
	 ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) va;
    }
   else if(modelType.equals("Logic"))
    {
	 tw.com.justiot.pneumatics.pneumaticelement.Logic va=new tw.com.justiot.pneumatics.pneumaticelement.Logic(modelName, pneumaticscad);
	 ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) va;
    }
   else if(modelType.equals("Delay"))
    {
     tw.com.justiot.pneumatics.pneumaticelement.Delay va=new tw.com.justiot.pneumatics.pneumaticelement.Delay(modelName, pneumaticscad);
     ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) va;
	}
   else if(modelType.equals("FlowValve"))
    {
     tw.com.justiot.pneumatics.pneumaticelement.FlowValve va=new tw.com.justiot.pneumatics.pneumaticelement.FlowValve(modelName, pneumaticscad);
     ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) va;
	}
   else if(modelType.equals("PressureValve"))
    {
     tw.com.justiot.pneumatics.pneumaticelement.PressureValve va=new tw.com.justiot.pneumatics.pneumaticelement.PressureValve(modelName, pneumaticscad);
     ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) va;
	}
   else if(modelType.equals("Gauge"))
    {
     tw.com.justiot.pneumatics.pneumaticelement.Gauge va=new tw.com.justiot.pneumatics.pneumaticelement.Gauge(modelName, pneumaticscad);
     ele=(tw.com.justiot.pneumatics.pneumaticelement.PneumaticElement) va;
	}
   return ele;
  } 
  
} 
