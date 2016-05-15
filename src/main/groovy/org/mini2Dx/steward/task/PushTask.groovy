/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.mini2Dx.steward.StewardUtils
import org.mini2Dx.steward.exception.NoBuildException

/**
 *
 */
class PushTask extends DefaultTask  {
	PushTask() {
		super()
		dependsOn("butlerUpdate")
	}

	@TaskAction
	def pushToItch() {
		def osBinDir = null;
		String channel;

		if (Os.isFamily(Os.FAMILY_WINDOWS)) {
			osBinDir = project.getExtensions().findByName('steward').windows.binDirectory
			channel = "windows"
		} else if (Os.isFamily(Os.FAMILY_MAC)) {
			osBinDir = project.getExtensions().findByName('steward').osx.binDirectory
			channel = "osx"
		} else {
			osBinDir = project.getExtensions().findByName('steward').linux.binDirectory
			channel = "linux"
		}
		if(osBinDir == null) {
			throw new Exception("No steward binary directory set for " + channel)
		}
		
		if(project.getExtensions().findByName('steward').alphaChannel) {
			channel += "-alpha"
		} else if(project.getExtensions().findByName('steward').betaChannel) {
			channel += "-beta"
		}

		File binDirectory = new File(osBinDir)
		if(!binDirectory.exists()) {
			throw new NoBuildException()
		}
		String user = project.getExtensions().findByName('steward').user
		if(user == null) {
			throw new Exception("user not set in steward configuration")
		}
		String game = project.getExtensions().findByName('steward').game
		if(game == null) {
			throw new Exception("game not set in steward configuration")
		}
		String deployDetails = user + "/" + game + ":" + channel;

		if(project.getExtensions().findByName('steward').userVersion != null) {
			println "Deploying to itch.io [" + deployDetails + "] with version " + project.getExtensions().findByName('steward').userVersion
			StewardUtils.execButler(project, binDirectory.getAbsolutePath(), deployDetails, "--userversion", project.getExtensions().findByName('steward').userVersion);
		} else {
			println "Deploying to itch.io [" + deployDetails + "]"
			StewardUtils.execButler(project, binDirectory.getAbsolutePath(), deployDetails);
		}
	}
}
