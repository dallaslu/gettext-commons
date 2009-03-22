package org.xnap.commons.ant.gettext;

import java.io.File;

import junit.framework.TestCase;

import org.apache.tools.ant.Location;

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
    	assertEquals("/tmp/test/file", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/")));
    }
    
    public void testGetRelativePathWithDifferentPaths() {
    	assertEquals("../tmp/test/file", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/etc/hello")));
    }
    
    public void testGetRelativePathWithCommonAncestor() {
    	assertEquals("../test/files", GettextUtils.getRelativePath(new File("/tmp/test/files"), new Location("/tmp/different/dir")));
    	assertEquals("../../test/files", GettextUtils.getRelativePath(new File("/tmp/test/files"), new Location("/tmp/different/dir/deeper")));
    	assertEquals("../../../tmp/test/files", GettextUtils.getRelativePath(new File("/tmp/test/files"), new Location("/etc/different/dir/deeper")));
    	assertEquals("test/files", GettextUtils.getRelativePath(new File("/tmp/test/files"), new Location("/tmp/different/")));
    	assertEquals("files", GettextUtils.getRelativePath(new File("/tmp/test/files"), new Location("/tmp/test/fi")));
    	assertEquals("ff", GettextUtils.getRelativePath(new File("/tmp/test/files/ff"), new Location("/tmp/test/files/fff")));
    	assertEquals("../ff", GettextUtils.getRelativePath(new File("/tmp/test/files/ff"), new Location("/tmp/test/files/fff/test")));
    	assertEquals("../fft", GettextUtils.getRelativePath(new File("/tmp/test/files/fft"), new Location("/tmp/test/files/fff/test")));
    	assertEquals("../test/files/fft", GettextUtils.getRelativePath(new File("/tmp/test/files/fft"), new Location("/tmpp/test")));
    }
    
    public void testGetCommonPrefix() {
    	assertEquals("", GettextUtils.getCommonPathPrefix("hello", "world"));
    	assertEquals("hello", GettextUtils.getCommonPathPrefix("hello", "hello"));
    	assertEquals("", GettextUtils.getCommonPathPrefix("hello", "hello world"));
    	assertEquals("", GettextUtils.getCommonPathPrefix("", "world"));
    	assertEquals("hello/", GettextUtils.getCommonPathPrefix("hello/test-1", "hello/test-2"));
    	assertEquals("/hello/", GettextUtils.getCommonPathPrefix("/hello/test-1", "/hello/test-2"));
    }
}
