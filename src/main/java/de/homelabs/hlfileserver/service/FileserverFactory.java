package de.homelabs.hlfileserver.service;

import de.homelabs.hlfileserver.entity.FileserverProperties;

public class FileserverFactory {
	public static FileserverService createInstance(FileserverProperties props) {
		return new FileserverFilesystemService(props);
	}
}
