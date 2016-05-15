/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward.task

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.mini2Dx.steward.StewardUtils
import org.mini2Dx.steward.exception.NoButlerDirectoryException

import de.undercouch.gradle.tasks.download.DownloadAction

/**
 *
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
