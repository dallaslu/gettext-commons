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

import java.io.File;
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
 * @goal gettext
 * 
 * @phase generate-resources
 */
public class GettextMojo
    extends AbstractGettextMojo {
	
    /**
     * @description Source Encoding.
     * @parameter expression="${encoding} default-value="utf-8" 
     */
	protected String encoding;
	
    /**
     * @description Gettext keywords (see -k in help for details).
     * @parameter expression="${keywords}" default-value="-ktrc -ktr -kmarktr -ktrn:1,2"
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
    	cl.createArgument().setValue("--output=" + keysFile.getAbsolutePath());
    	cl.createArgument().setValue("--language=Java");
    	cl.createArgument().setValue("--directory=" + sourceDirectory);
    	cl.createArgument().setLine(keywords);
    	
    	DirectoryScanner ds = new DirectoryScanner();
    	ds.setBasedir(sourceDirectory);
    	ds.setIncludes(new String[] {"**/*.java"});
    	ds.scan();
    	String[] files = ds.getIncludedFiles();
    	for (int i = 0; i < files.length; i++) {
    		cl.createArgument().setValue(sourceDirectory.getAbsolutePath() 
    				+ File.separator +  files[i]);
    	}
    	
    	Writer stringWriter = new StringWriter();
		StreamConsumer out = new WriterStreamConsumer(stringWriter);
		StreamConsumer err = new WriterStreamConsumer(stringWriter);
    	try {
			CommandLineUtils.executeCommandLine(cl, out, err);
			getLog().info(stringWriter.toString());
		} catch (CommandLineException e) {
			getLog().error("Could not execute xgettext.", e);
		}
    }
}
