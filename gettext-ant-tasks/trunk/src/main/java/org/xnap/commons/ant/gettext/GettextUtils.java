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
		String filePath = file.getAbsolutePath();
		File locationParent = new File(location.getFileName()).getParentFile();
		if (locationParent == null) {
			return filePath; 
		}

		String locationParentPath = locationParent.getAbsolutePath();
		if (filePath.startsWith(locationParentPath)) {
			if (filePath.length() == locationParentPath.length()) {
				return "";
			} else {
				return filePath.substring(getPathWithSeparator(locationParentPath).length());
			}
		}
		
		int commonPrefixLength = getCommonPrefix(filePath, locationParentPath).length();
		if (commonPrefixLength > 0) {
			String locationSubPath = locationParentPath.substring(commonPrefixLength);
			// + 1 for the one folder you have to back out of, since the last / is part of the common path
			int folders = countOccurrences(locationSubPath, File.separatorChar) + 1;
			String fileSubPath = filePath.substring(commonPrefixLength);
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < folders; i++) {
				builder.append("..").append(File.separatorChar);
			}
			builder.append(fileSubPath);
			return builder.toString();
		}
		
		return filePath;
	}

	public static String createAbsolutePath(String parentPath, String path) {
	    return parentPath + File.separator + path;
	}

	private static String getPathWithSeparator(String path) {
		return path.endsWith(File.separator) ? path : path + File.separator;
	}
	
	static String getCommonPrefix(String path1, String path2) {
		int length = Math.min(path1.length(), path2.length());
		for (int i = 0; i < length; i++) {
			if (path1.charAt(i) != path2.charAt(i)) {
				return path1.substring(0, i);
			}
		}
		return path1.substring(0, length);
	}
	
	static int countOccurrences(String text, char character) {
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == character) {
				++count;
			}
		}
		return count;
	}
}
