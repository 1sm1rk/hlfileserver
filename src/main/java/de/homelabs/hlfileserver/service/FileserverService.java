package de.homelabs.hlfileserver.service;

import java.util.List;

import de.homelabs.hlfileserver.entity.FSElement;
import de.homelabs.hlfileserver.entity.FSResult;

public interface FileserverService {
	FSResult createDirectory(String directory);
	FSResult renameDirectory(String directoryOld, String directoryNew);
	FSResult deleteDirectory(String directory);
	FSResult createFile(String file);
	FSResult renameFile(String fileOld, String fileNew);
	FSResult deleteFile(String file);
			
	List<FSElement> listDirectory(String directory);
	byte[] getFileContent(String file);
}
