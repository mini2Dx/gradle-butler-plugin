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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.mini2Dx.butler.ButlerUtils
import org.mini2Dx.butler.exception.NoBuildException

/**
 * Calls 'butler push'. Will push the specified game binary to the specified channel.
 */
class PushTask extends DefaultTask  {

	@Input
	public String binDirectory

	@Input
	public String channel

	PushTask() {
		super()
		dependsOn("butlerUpdate")
	}

	@TaskAction
	def pushToItch() {
        if(channel == null) {
            throw new Exception("'channel' not set in butler push task")
        }
        if(binDirectory == null) {
            throw new Exception("'binDirectory' not set in butler push task")
        }

        File binDirectory = new File(binDirectory)
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

		String finalChannel = channel
		if(project.getExtensions().findByName('butler').allChannelsPostfix) {
			finalChannel += project.getExtensions().findByName('butler').allChannelsPostfix
		}
		String deployDetails = user + "/" + game + ":" + finalChannel

		if(project.getExtensions().findByName('butler').userVersion != null) {
			println "Deploying to itch.io [" + deployDetails + "] with version " + project.getExtensions().findByName('butler').userVersion
			ButlerUtils.execButler(project, "push", binDirectory.getAbsolutePath(), deployDetails, "--userversion", project.getExtensions().findByName('butler').userVersion);
		} else {
			println "Deploying to itch.io [" + deployDetails + "]"
			ButlerUtils.execButler(project, "push", binDirectory.getAbsolutePath(), deployDetails);
		}
	}
}
