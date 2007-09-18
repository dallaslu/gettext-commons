package org.xnap.commons.ant.gettext;

import java.io.File;

import junit.framework.TestCase;

public class GettextDistTaskTest extends TestCase {

	public void testFileMatchesPercentage() throws Exception {
		// 99.1 percent file
		File file = new File(getClass().getResource("de.po").toURI());
		assertTrue(file.isFile());
		
		GettextDistTask task = new GettextDistTask();
		task.setPercentage(99);
		
		assertTrue(task.fileMatchesPercentage(file));
		
		task.setMoreOrLess("<");
		assertFalse(task.fileMatchesPercentage(file));
		

		// 0 percent file 
		file = new File(getClass().getResource("ja.po").toURI());
		assertTrue(file.isFile());
		
		task = new GettextDistTask();
		task.setPercentage(92);
		
		assertFalse(task.fileMatchesPercentage(file));
		
		task.setMoreOrLess("<");
		assertTrue(task.fileMatchesPercentage(file));
		
		// file with fuzzy translations
		file = new File(getClass().getResource("fr.po").toURI());
		assertTrue(file.isFile());
		
		task = new GettextDistTask();
		task.setPercentage(92);
		
		assertFalse(task.fileMatchesPercentage(file));
		
		task.setMoreOrLess("<");
		assertTrue(task.fileMatchesPercentage(file));
	}
	
	public void testFileWithZeroTranslations() throws Exception {
		
		File file = new File(getClass().getResource("en.po").toURI());
		assertTrue(file.isFile());
		
		GettextDistTask task = new GettextDistTask();
		task.setPercentage(1);
		
		assertFalse(task.fileMatchesPercentage(file));
		
		task.setMoreOrLess("less");
		assertTrue(task.fileMatchesPercentage(file));
		
		task.setPercentage(0);
		assertFalse(task.fileMatchesPercentage(file));
		
		task.setMoreOrLess("greaterOrEqual");
		assertTrue(task.fileMatchesPercentage(file));
	}

}
