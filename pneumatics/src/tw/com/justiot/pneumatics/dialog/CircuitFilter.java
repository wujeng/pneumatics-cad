package tw.com.justiot.pneumatics.dialog;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import tw.com.justiot.pneumatics.Config;

public class CircuitFilter extends FileFilter {
    
    public boolean accept(File f) {
        if (f.isDirectory()) {return true;}
        String extension = getExtension(f);
	    if (extension != null) {
            if (extension.equals("pc")) {
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }
    
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    public String getDescription() {
        return Config.getString("CircuitFilter.CircuitFile");
    }
}

