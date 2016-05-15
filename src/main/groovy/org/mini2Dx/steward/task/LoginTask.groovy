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
		if (Os.isFamily(Os.FAMILY_WINDOWS)) {
			if(StewardUtils.is64Bit()) {
				downloadAction.src(project.getExtensions().findByName('steward').windows64Download)
			} else {
				downloadAction.src(project.getExtensions().findByName('steward').windows32Download)
			}
		} else if (Os.isFamily(Os.FAMILY_MAC)) {
			downloadAction.src(project.getExtensions().findByName('steward').osxDownload)
		} else if (Os.isFamily(Os.FAMILY_UNIX)) {
			if(StewardUtils.is64Bit()) {
				downloadAction.src(project.getExtensions().findByName('steward').linux64Download)
			} else {
				downloadAction.src(project.getExtensions().findByName('steward').linux32Download)
			}
		}
	}
}
