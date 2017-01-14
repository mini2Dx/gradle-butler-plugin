/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Thomas Cashman
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.mini2Dx.butler.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.mini2Dx.butler.ButlerUtils
import org.mini2Dx.butler.exception.NoBuildException

/**
 * Calls 'butler push'. Will push the game binary corresponding to the OS the task runs on.
 */
class PushTask extends DefaultTask  {
	PushTask() {
		super()
		dependsOn("butlerUpdate")
	}

	@TaskAction
	def pushToItch() {
		boolean anyOsBuildPushed = pushAnyOsBuild();
		boolean platformSpecificBuildPushed = pushPlatformSpecificBuild();
		
		if(!anyOsBuildPushed && !platformSpecificBuildPushed) {
			throw new Exception("No build was configured to be pushed")
		}
	}
	
	def pushAnyOsBuild() {
		if(project.getExtensions().findByName('butler').anyOs.binDirectory == null) {
			return false;
		}
		def osBinDir = project.getExtensions().findByName('butler').anyOs.binDirectory
		String channel = project.getExtensions().findByName('butler').anyOs.channel
		pushBuild(osBinDir, channel)
		return true;
	}
	
	def pushPlatformSpecificBuild() {
		def osBinDir = null;
		String channel;

		if (Os.isFamily(Os.FAMILY_WINDOWS)) {
			if(project.getExtensions().findByName('butler').windows == null) {
				return false;
			}
			osBinDir = project.getExtensions().findByName('butler').windows.binDirectory
			channel = project.getExtensions().findByName('butler').windows.channel
		} else if (Os.isFamily(Os.FAMILY_MAC)) {
			if(project.getExtensions().findByName('butler').osx == null) {
				return false;
			}
			osBinDir = project.getExtensions().findByName('butler').osx.binDirectory
			channel = project.getExtensions().findByName('butler').osx.channel
		} else {
			if(project.getExtensions().findByName('butler').linux == null) {
				return false;
			}
			osBinDir = project.getExtensions().findByName('butler').linux.binDirectory
			channel = project.getExtensions().findByName('butler').linux.channel
		}
		pushBuild(osBinDir, channel)
		return true;
	}
	
	def pushBuild(String osBinDir, String channel) {
		if(osBinDir == null) {
			throw new Exception("No butler binary directory set for " + channel)
		}
		if(project.getExtensions().findByName('butler').alphaChannel) {
			channel += "-alpha"
		} else if(project.getExtensions().findByName('butler').betaChannel) {
			channel += "-beta"
		}

		File binDirectory = new File(osBinDir)
		if(!binDirectory.exists()) {
			throw new NoBuildException()
		}
		String user = project.getExtensions().findByName('butler').user
		if(user == null) {
			throw new Exception("'user' not set in butler configuration")
		}
		String game = project.getExtensions().findByName('butler').game
		if(game == null) {
			throw new Exception("'game' not set in butler configuration")
		}
		String deployDetails = user + "/" + game + ":" + channel;

		if(project.getExtensions().findByName('butler').userVersion != null) {
			println "Deploying to itch.io [" + deployDetails + "] with version " + project.getExtensions().findByName('butler').userVersion
			ButlerUtils.execButler(project, "push", binDirectory.getAbsolutePath(), deployDetails, "--userversion", project.getExtensions().findByName('butler').userVersion);
		} else {
			println "Deploying to itch.io [" + deployDetails + "]"
			ButlerUtils.execButler(project, "push", binDirectory.getAbsolutePath(), deployDetails);
		}
	}
}
