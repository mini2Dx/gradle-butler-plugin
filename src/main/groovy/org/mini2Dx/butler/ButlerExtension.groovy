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

/**
 * Butler extension configuration
 */
class ButlerExtension {
	/**
	 * The itch.io user
	 */
	String user;
	/**
	 * The itch.io game
	 */
	String game;
	/**
	 * Set to false to disable Butler updates
	 */
	boolean updateButler = true;
	/**
	 * Set this to append something to every channel name
	 */
	String allChannelsPostfix = ""
	/**
	 * Set if you want to override itch.io's version number
	 */
	String userVersion;
	/**
	 * Set if you want to override the automatic Butler install directory
	 */
	String butlerInstallDirectory;
	
	String windows32Download = "https://dl.itch.ovh/butler/windows-386/head/butler.exe"
	String windows64Download = "https://dl.itch.ovh/butler/windows-amd64/head/butler.exe"
	String osxDownload = "https://dl.itch.ovh/butler/darwin-amd64/head/butler"
	String linux32Download = "https://dl.itch.ovh/butler/linux-386/head/butler"
	String linux64Download = "https://dl.itch.ovh/butler/linux-amd64/head/butler"
}
