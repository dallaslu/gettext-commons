/*
 *  Gettext Commons
 *
 *  Copyright (C) 2005  Steffen Pingel
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.xnap.commons.i18n;

import java.util.MissingResourceException;

import junit.framework.TestCase;

/**
 * @author Steffen Pingel
 */
public class EmptyResourceBundleTest extends TestCase {

	public void test()
	{
		EmptyResourceBundle bundle = new EmptyResourceBundle(null);
		assertFalse(bundle.getKeys().hasMoreElements());
		try {
			bundle.getObject("Foo");
			fail("MissingResourceException expected");
		}
		catch (MissingResourceException mre) {}
	}

}
