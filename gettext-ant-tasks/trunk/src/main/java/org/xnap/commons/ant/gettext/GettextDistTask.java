package org.xnap.commons.ant.gettext;

import java.io.File;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Commandline;

public class GettextDistTask extends AbstractGettextGenerateTask {
    
    public void execute() {
        
    	checkPreconditions();
    	
        CommandlineFactory cf = getProperCommandlineFactory();
        
        DirectoryScanner ds = new DirectoryScanner();
        ds.setBasedir(poDirectory);
        ds.setIncludes(new String[] {"**/*.po"});
        ds.scan();
        
        String[] files = ds.getIncludedFiles();
        for (int i = 0; i < files.length; i++) {
            log("Processing " + files[i]);
            Commandline cl = cf.createCommandline(new File(poDirectory, files[i]));
            log("Executing: " + cl.toString(), Project.MSG_DEBUG);
            runCommandLineAndWait(cl);
        }
    }
}
