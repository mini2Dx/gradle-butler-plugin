/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.process.internal.ExecAction
import org.mini2Dx.steward.exception.NoButlerDirectoryException

/**
 *
 */
class StewardUtils {

	public static File getInstallDirectory(Project project) {
		String installPath = project.getExtensions().findByName('steward').butlerInstallDirectory
		
		File installDirectory;
		if(installPath == null) {
			if(project.getGradle().getGradleHomeDir() != null) {
				installDirectory = new File(project.getGradle().getGradleHomeDir(), ".butler");
			} else if(project.getGradle().getGradleUserHomeDir() != null) {
				installDirectory = new File(project.getGradle().getGradleUserHomeDir(), ".butler");
			} else {
				throw new NoButlerDirectoryException()
			}
		} else {
			installDirectory = new File(installPath);
		}
		return installDirectory;
	}
	
	public static File getButlerBinary(Project project) {
		if (Os.isFamily(Os.FAMILY_WINDOWS)) {
			return new File(getInstallDirectory(project), "butler.exe");
		} else if (Os.isFamily(Os.FAMILY_MAC)) {
			return new File(getInstallDirectory(project), "butler");
		} else {
			return new File(getInstallDirectory(project), "butler");
		}
	}
	
	public static boolean is64Bit() {
		if(System.getProperty("sun.arch.data.model") == null) {
			return System.getProperty("os.arch").contains("64");
		}
		if(System.getProperty("sun.arch.data.model").equalsIgnoreCase("unknown")) {
			return System.getProperty("os.arch").contains("64");
		}
		return System.getProperty("sun.arch.data.model").equals("64");
	}
	
	public static void execButler(Project project, String... args) {
		String [] processArgs = new String [args.length + 1]
		processArgs[0] = getButlerBinary(project).getAbsolutePath()
		for(int i = 0; i < args.length; i++) {
			processArgs[i + 1] = args[i];
		}
		
		project.exec {
			commandLine processArgs
		}
	}
}
