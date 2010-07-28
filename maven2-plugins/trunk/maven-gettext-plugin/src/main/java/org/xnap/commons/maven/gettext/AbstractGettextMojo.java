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

import org.apache.maven.plugin.AbstractMojo;

/**
 *
 */
public abstract class AbstractGettextMojo
    extends AbstractMojo {
	
	
	/**
     * The output directory for generated class or properties files.
     * @parameter expression="${outputDirectory}" default-value="${project.build.outputDirectory}"
     * @required
     */
    protected File outputDirectory;

    /**
     * Source directory. This directory is searched recursively for .java files.
     * @parameter expression="${sourceDirectory}" default-value="${project.build.sourceDirectory}"
     * @required
     */
    protected File sourceDirectory;
    
    /**
     * The output directory for the keys.pot directory for merging .po files. 
     * @parameter expression="${poDirectory}" default-value="${project.build.sourceDirectory}/main/po"
     * @required
     */
    protected File poDirectory;
    
    /**
     * Filename of the .pot file.
     * @parameter expression="${keysFile}" default-value="keys.pot"
     * @required
     */
    protected String keysFile;
    
}
