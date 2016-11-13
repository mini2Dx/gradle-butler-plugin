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

import de.undercouch.gradle.tasks.download.DownloadAction

/**
 * Updates Butler to the latest stable version or installs it if it does not exist
 */
class UpdateButlerTask extends DefaultTask {

	@TaskAction
	def updateButler() {
		File installDirectory = ButlerUtils.getInstallDirectory(project);
		
		if(installDirectory.exists()) {
			File binaryFile = ButlerUtils.getButlerBinary(project);
			if(binaryFile.exists()) {
				update(binaryFile)
			} else {
				install(installDirectory)
			}
		} else {
			installDirectory.mkdirs()
			install(installDirectory)
		}
	}
	
	def install(File installDirectory) {
		DownloadAction downloadAction = new DownloadAction(project)
		if (Os.isFamily(Os.FAMILY_WINDOWS)) {
			if(ButlerUtils.is64Bit()) {
				downloadAction.src(project.getExtensions().findByName('butler').windows64Download)
			} else {
				downloadAction.src(project.getExtensions().findByName('butler').windows32Download)
			}
		} else if (Os.isFamily(Os.FAMILY_MAC)) {
			downloadAction.src(project.getExtensions().findByName('butler').osxDownload)
		} else if (Os.isFamily(Os.FAMILY_UNIX)) {
			if(ButlerUtils.is64Bit()) {
				downloadAction.src(project.getExtensions().findByName('butler').linux64Download)
			} else {
				downloadAction.src(project.getExtensions().findByName('butler').linux32Download)
			}
		}
		downloadAction.dest(installDirectory)
		downloadAction.execute()
		
		ensureButlerIsExecutable(installDirectory)
		
		ButlerUtils.execButler(project, "-V");
	}
	
	def update(File binaryFile) {
		ensureButlerIsExecutable(installDirectory)
		
		if(project.getExtensions().findByName('butler').updateButler) {
			ButlerUtils.execButler(project, "upgrade");
		}
		ButlerUtils.execButler(project, "-V");
	}
	
	def ensureButlerIsExecutable(File installDirectory) {
		File file = new File(installDirectory, "butler")
		if (!file.isFile()) {
			throw new Exception("Can't access butler file")
		}
		try {
			file.setExecutable(true)
		} catch (Exception e) {
			//File system does not support executable bit
		}
	}
}
