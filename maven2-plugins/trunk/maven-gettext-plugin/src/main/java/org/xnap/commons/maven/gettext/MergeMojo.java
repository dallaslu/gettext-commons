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

import java.io.StringWriter;
import java.io.Writer;

import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.StreamConsumer;
import org.codehaus.plexus.util.cli.WriterStreamConsumer;

/**
 * Goal which touches a timestamp file.
 *
 * @goal merge
 * 
 * @phase generate-resources
 */
public class MergeMojo
    extends AbstractGettextMojo {
	
    /**
     * @description msgcat command.
     * @parameter expression="${msgmergeCmd}" default-value="msgmerge"
     * @required 
     */
    protected String msgmergeCmd;
    
    public void execute()
        throws MojoExecutionException
    {
		getLog().info("Invoking msgmerge for po files in '" 
				+ poDirectory.getAbsolutePath() + "'.");
		
		DirectoryScanner ds = new DirectoryScanner();
    	ds.setBasedir(sourceDirectory);
    	ds.setIncludes(new String[] {"**/*.po"});
    	ds.scan();
    	String[] files = ds.getIncludedFiles();
    	for (int i = 0; i < files.length; i++) {
    		getLog().info("Processing " + files[i]);
    		Commandline cl = new Commandline();
    		cl.setExecutable(msgmergeCmd);
        	cl.createArgument().setValue("-q");
        	cl.createArgument().setValue("--backup=numbered");
        	cl.createArgument().setValue("-U");
        	cl.createArgument().setValue(keysFile.getAbsolutePath());
        	cl.createArgument().setValue(files[i]);
        	
        	Writer sw = new StringWriter();
    		StreamConsumer out = new WriterStreamConsumer(sw);
    		StreamConsumer err = new WriterStreamConsumer(sw);
        	try {
    			CommandLineUtils.executeCommandLine(cl, out, err);
    			getLog().info(sw.toString());
    		} catch (CommandLineException e) {
    			getLog().error("Could not execute xgettext.", e);
    		}
    	}

    }
}
