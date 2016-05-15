/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.mini2Dx.steward.StewardUtils

/**
 *
 */
class LoginTask extends DefaultTask {
	LoginTask() {
		super()
		dependsOn("butlerUpdate")
	}
	
	@TaskAction
	def login() {
		StewardUtils.execButler(project, "login");
	}
}
