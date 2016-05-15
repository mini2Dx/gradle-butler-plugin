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
package org.mini2Dx.steward.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.mini2Dx.steward.StewardUtils
import org.mini2Dx.steward.exception.NoButlerDirectoryException

import de.undercouch.gradle.tasks.download.DownloadAction

/**
 * Updates Butler to the latest stable version or installs it if it does not exist
 */
class UpdateButlerTask extends DefaultTask {

	@TaskAction
	def updateButler() {
		File installDirectory = StewardUtils.getInstallDirectory(project);
		
		if(installDirectory.exists()) {
			File binaryFile = StewardUtils.getButlerBinary(project);
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
		downloadAction.dest(installDirectory)
		downloadAction.execute()
		
		StewardUtils.execButler(project, "-V");
	}
	
	def update(File binaryFile) {
		if(project.getExtensions().findByName('steward').updateButler) {
			StewardUtils.execButler(project, "upgrade");
		}
		StewardUtils.execButler(project, "-V");
	}
}
