/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward.exception

/**
 *
 */
class NoBuildException extends Exception {

	public NoBuildException() {
		super("Build does not exist for uploading. Ensure butlerPush.dependsOn is set in your build.gradle")
	}
}
