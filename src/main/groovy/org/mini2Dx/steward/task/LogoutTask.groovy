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
class LogoutTask extends DefaultTask  {
	LogoutTask() {
		super()
		dependsOn("butlerUpdate")
	}
	
	@TaskAction
	def logout() {
		StewardUtils.execButler(project, "logout");
	}
}
