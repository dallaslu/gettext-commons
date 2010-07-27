package org.xnap.commons.maven.gettext;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;

/**
 * Invokes xgettext to extract keys from source code.
 *
 * @goal gettext
 * 
 * @phase generate-resources
 */
public class GettextMojo
    extends AbstractGettextMojo {
	
    /**
     * @description Source Encoding.
     * @parameter expression="${encoding}" default-value="utf-8" 
     */
	protected String encoding;
	
    /**
     * @description Gettext keywords (see -k in help for details).
     * @parameter expression="${keywords}" default-value="-ktrc:1c,2 -ktrnc:1c,2,3 -ktr -kmarktr -ktrn:1,2 -k"
     * @required
     */
    protected String keywords;
    
    /**
     * @description xgettext command.
     * @parameter expression="${xgettextCmd}" default-value="xgettext"
     * @required 
     */
    protected String xgettextCmd;

	public void execute()
        throws MojoExecutionException
    {
		getLog().info("Invoking xgettext for Java files in '" 
				+ sourceDirectory.getAbsolutePath() + "'.");
		
		Commandline cl = new Commandline();
		cl.setExecutable(xgettextCmd);
    	cl.createArgument().setValue("--from-code=" + encoding);
    	cl.createArgument().setValue("--output=" + new File(poDirectory, keysFile).getAbsolutePath());
    	cl.createArgument().setValue("--language=Java");
    	cl.createArgument().setLine(keywords);
    	
    	DirectoryScanner ds = new DirectoryScanner();
    	ds.setBasedir(sourceDirectory);
    	ds.setIncludes(new String[] {"**/*.java"});
    	ds.scan();
        String[] files = ds.getIncludedFiles();
        
    	File file = createListFile(files);
    	if (file != null) {
    	    cl.createArgument().setValue("--files-from=" + file.getAbsolutePath());
    	} else {
    	    for (int i = 0; i < files.length; i++) {
    	        cl.createArgument().setValue(getAbsolutePath(files[i]));
    	    }
    	}
    	
    	getLog().debug("Executing: " + cl.toString());
    	StreamConsumer out = new LoggerStreamConsumer(getLog(), LoggerStreamConsumer.INFO);
    	StreamConsumer err = new LoggerStreamConsumer(getLog(), LoggerStreamConsumer.WARN);
    	try {
    	    CommandLineUtils.executeCommandLine(cl, out, err);
    	} catch (CommandLineException e) {
    	    getLog().error("Could not execute " + xgettextCmd + ".", e);
    	}
    }

    private File createListFile(String[] files) {
        try {
            File listFile = File.createTempFile("maven", null);
            listFile.deleteOnExit();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(listFile));
            try {
                for (int i = 0; i < files.length; i++) {
                    writer.write(getAbsolutePath(files[i]));
                    writer.newLine();
                }                
            } finally {
                writer.close();
            }
            
            return listFile;
        } catch (IOException e) {
            getLog().error("Could not create list file.", e);
            return null;
        }
    }
    
    private String getAbsolutePath(String path) {
        return sourceDirectory.getAbsolutePath() + File.separator + path;
    }
    
}
