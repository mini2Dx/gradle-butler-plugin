/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.mini2Dx.steward.domain.Linux
import org.mini2Dx.steward.domain.OSX
import org.mini2Dx.steward.domain.Windows
import org.mini2Dx.steward.task.LoginTask
import org.mini2Dx.steward.task.LogoutTask
import org.mini2Dx.steward.task.PushTask
import org.mini2Dx.steward.task.UpdateButlerTask

import de.undercouch.gradle.tasks.download.DownloadTaskPlugin

/**
 *
 */
class StewardPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.plugins.apply DownloadTaskPlugin
		project.extensions.create("steward", StewardExtension)
		
		project.steward.extensions.create("windows", Windows)
		project.steward.extensions.create("osx", OSX)
		project.steward.extensions.create("linux", Linux)
		
		project.task('butlerUpdate', type: UpdateButlerTask)
		project.task('butlerLogin', type: LoginTask)
		project.task('butlerLogout', type: LogoutTask)
		project.task('butlerPush', type: PushTask)
	}

}
