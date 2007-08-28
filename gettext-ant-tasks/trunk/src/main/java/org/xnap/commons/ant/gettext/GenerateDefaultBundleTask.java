package org.xnap.commons.ant.gettext;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.Commandline;

public class GenerateDefaultBundleTask extends AbstractGettextGenerateTask {

	protected String msgenCmd = "msgen";
    public void setMsgenCmd(String msgenCmd) {
		this.msgenCmd = msgenCmd;
	}
    
    protected String potFile = null;
    public void setPotFile(String potFile) {
		this.potFile = potFile;
	}
	
    protected void checkPreconditions() {
    	super.checkPreconditions();
    	if (potFile == null) {
    		throw new BuildException("potFile must be specified: <... potFile=\"po/keys.pot\"");
    	}
    }
    
    public void execute() {
        
    	checkPreconditions();
    	
    	File defaultPo = createDefaultBundle();
    	CommandlineFactory cf = getProperCommandlineFactory();
        Commandline cl = cf.createCommandline(defaultPo, null);
    	
    	log("Executing: " + cl.toString(), Project.MSG_DEBUG);
        runCommandLineAndWait(cl);
        
        defaultPo.delete();
    }


    private File createDefaultBundle() {
    	try {
    		File defaultPo = File.createTempFile("default", ".po");
    		defaultPo.delete();
    		
    		Commandline cl = new Commandline();
    		cl.setExecutable(msgenCmd);
    		cl.createArgument().setValue(potFile);
    		cl.createArgument().setValue("-o");
    		cl.createArgument().setFile(defaultPo);
    		
    		log("Generating default po file: " + cl.toString(), Project.MSG_INFO);
    		runCommandLineAndWait(cl);
    		
    		return defaultPo;
    	} catch (IOException e) {
    		throw new BuildException(e);
    	}
    }
}
