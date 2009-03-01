package org.xnap.commons.ant.gettext;

import java.io.File;

import org.apache.tools.ant.Location;

import junit.framework.TestCase;

public class GettextUtilsTest extends TestCase {

    public void testGetJavaLocale() throws Exception {
        assertEquals("sr", GettextUtils.getJavaLocale("sr"));
        assertEquals("de_DE", GettextUtils.getJavaLocale("de_DE"));
        assertEquals("de_DE_Variant", GettextUtils.getJavaLocale("de_DE_Variant"));
        assertEquals("sr__Latn", GettextUtils.getJavaLocale("sr@Latn"));
        try {
            GettextUtils.getJavaLocale("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }

    public void testGetRelativePathWithLocationsParentSameAsFile() {
    	assertEquals("", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/tmp/test/file/test")));
    }
    
    public void testGetRelativePathWithLocationsParentAncesortOfFiles() {
    	assertEquals("test/file", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/tmp/test/")));
    	assertEquals("file", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/tmp/test/file")));
    	assertEquals("tmp/test/file", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/etc")));
    }
    
    public void testGetRelativePathWithDifferentPaths() {
    	assertEquals("/tmp/test/file", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/etc/hello")));
    }
    
    public void testGetRelativePathWithCommonAncestor() {
    	assertEquals("../test/files", GettextUtils.getRelativePath(new File("/tmp/test/files"), new Location("/tmp/different/dir")));
    }
}
