/**
 * Copyright 2016 Thomas Cashman
 */
package org.mini2Dx.steward

/**
 *
 */
class StewardExtension {
	String user;
	String game;
	
	boolean updateButler = true;
	boolean alphaChannel = false;
	boolean betaChannel = false;
	String userVersion;
	String butlerInstallDirectory;
	
	String windows32Download = "https://dl.itch.ovh/butler/windows-386/head/butler.exe"
	String windows64Download = "https://dl.itch.ovh/butler/windows-amd64/head/butler.exe"
	String osxDownload = "https://dl.itch.ovh/butler/darwin-amd64/head/butler"
	String linux32Download = "https://dl.itch.ovh/butler/linux-386/head/butler"
	String linux64Download = "https://dl.itch.ovh/butler/linux-amd64/head/butler"
}
