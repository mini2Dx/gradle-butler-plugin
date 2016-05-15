/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward.exception

/**
 *
 */
class NoButlerDirectoryException extends Exception {

	public NoButlerDirectoryException() {
		super("Could not determine directory for installing itch.io Butler application. Try setting butlerInstallDirectory");
	}
}
