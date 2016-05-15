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
package org.mini2Dx.steward

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.process.internal.ExecAction
import org.mini2Dx.steward.exception.NoButlerDirectoryException

/**
 * Static utility methods for tasks
 */
class StewardUtils {
	/**
	 * Returns the Butler install directory
	 * @param project The {@link Project} being built
	 * @return A {@link File} for the install directory
	 */
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
	
	/**
	 * Returns the Butler binary executable
	 * @param project The {@link Project} being built
	 * @return A {@link File} for the executable
	 */
	public static File getButlerBinary(Project project) {
		if (Os.isFamily(Os.FAMILY_WINDOWS)) {
			return new File(getInstallDirectory(project), "butler.exe");
		} else if (Os.isFamily(Os.FAMILY_MAC)) {
			return new File(getInstallDirectory(project), "butler");
		} else {
			return new File(getInstallDirectory(project), "butler");
		}
	}
	
	/**
	 * Returns if this OS is 64 bit
	 * @return False is 32 bit
	 */
	public static boolean is64Bit() {
		if(System.getProperty("sun.arch.data.model") == null) {
			return System.getProperty("os.arch").contains("64");
		}
		if(System.getProperty("sun.arch.data.model").equalsIgnoreCase("unknown")) {
			return System.getProperty("os.arch").contains("64");
		}
		return System.getProperty("sun.arch.data.model").equals("64");
	}
	
	/**
	 * Executes Butler with a set of arguments
	 * @param project The {@link Project} being built
	 * @param args The arguments to pass to {@link Butler}
	 */
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
