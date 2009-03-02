package org.xnap.commons.ant.gettext;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.Location;

public class GettextUtils {

    public static String getJavaLocale(String locale) {
        if (locale == null) {
            throw new IllegalArgumentException();
        }
        
        List tokens = new ArrayList(3);
        StringTokenizer t = new StringTokenizer(locale, "_");
        while (t.hasMoreTokens()) {
            tokens.add(t.nextToken());
        }
        
        if (tokens.size() < 1 || tokens.size() > 3) {
            throw new IllegalArgumentException("Invalid locale format: " + locale);
        }

        if (tokens.size() < 3) {
            // check for variant
            String lastToken = (String) tokens.get(tokens.size() - 1);
            int index = lastToken.indexOf("@");
            if (index != -1) {
                tokens.remove(tokens.size() - 1);
                tokens.add(lastToken.substring(0, index));
                if (tokens.size() == 1) {
                    // no country code was provided, but a variant
                    tokens.add("");
                }
                tokens.add(lastToken.substring(index + 1));
            }
        }

        StringBuffer sb = new StringBuffer();
        for (Iterator it = tokens.iterator(); it.hasNext();) {
            String token = (String) it.next();
            sb.append(token);
            if (it.hasNext()) {
                sb.append("_");
            }
        }
        
        return sb.toString();
    }

    /**
     * Returns <code>file</code>'s path relative to <code>location</code>'s parent folder.
     * 
     * If <code>file</code> and <code>location</code> have a common prefix, this method will
     * return a path to <code>file</code> that is relative to <code>location</code>
     * 
     * Examples:
     * 
     * If parent is the parent of location it will return "", so that
     */
	public static String getRelativePath(File file, Location location) {
		File locationParent = new File(location.getFileName()).getParentFile();
		String locationParentPath = locationParent.getAbsolutePath();
		String parentPath = file.getAbsolutePath();
		if (parentPath.startsWith(locationParentPath)) {
			if (parentPath.length() == locationParentPath.length()) {
				return "";
			} else {
				return parentPath.substring(getPathWithSeparator(locationParentPath).length());
			}
		}
		return parentPath;
	}

	public static String createAbsolutePath(String parentPath, String path) {
	    return parentPath + File.separator + path;
	}

	private static String getPathWithSeparator(String path) {
		return path.endsWith(File.separator) ? path : path + File.separator;
	}
}
