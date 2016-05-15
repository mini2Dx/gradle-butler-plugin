/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward.task

import org.gradle.api.DefaultTask



/**
 *
 */
class LogoutTask extends DefaultTask  {
	LogoutTask() {
		super()
		dependsOn("butlerUpdate")
	}
}
