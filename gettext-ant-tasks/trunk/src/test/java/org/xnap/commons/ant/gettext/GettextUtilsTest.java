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

    public void testGetRelativePathWithLocationDirectParent() {
    	assertEquals("", GettextUtils.getRelativePath(new File("/tmp/test/file"), new Location("/tmp/test/")));
    }
}
