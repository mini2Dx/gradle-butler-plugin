/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward.task

import org.gradle.api.DefaultTask

/**
 *
 */
class LoginTask extends DefaultTask {
	LoginTask() {
		super()
		dependsOn("butlerUpdate")
	}
}
