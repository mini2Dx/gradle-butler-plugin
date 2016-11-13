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
package org.mini2Dx.butler

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.mini2Dx.butler.domain.AnyOs
import org.mini2Dx.butler.domain.Linux
import org.mini2Dx.butler.domain.OSX
import org.mini2Dx.butler.domain.Windows
import org.mini2Dx.butler.task.LoginTask
import org.mini2Dx.butler.task.LogoutTask
import org.mini2Dx.butler.task.PushTask
import org.mini2Dx.butler.task.UpdateButlerTask

import de.undercouch.gradle.tasks.download.DownloadTaskPlugin

/**
 * Applies the plugin extension and tasks
 */
class ButlerPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.plugins.apply DownloadTaskPlugin
		project.extensions.create("butler", ButlerExtension)
		
		project.butler.extensions.create("anyOs", AnyOs)
		project.butler.extensions.create("windows", Windows)
		project.butler.extensions.create("osx", OSX)
		project.butler.extensions.create("linux", Linux)
		
		project.task('butlerUpdate', type: UpdateButlerTask)
		project.task('butlerLogin', type: LoginTask)
		project.task('butlerLogout', type: LogoutTask)
		project.task('butlerPush', type: PushTask)
	}

}
