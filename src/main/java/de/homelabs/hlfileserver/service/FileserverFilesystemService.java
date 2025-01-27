package de.homelabs.hlfileserver.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.homelabs.hlfileserver.entity.FSElement;
import de.homelabs.hlfileserver.entity.FSResult;
import de.homelabs.hlfileserver.entity.FileserverProperties;

public class FileserverFilesystemService implements FileserverService {
	private final Logger log = LoggerFactory.getLogger(FileserverFilesystemService.class);
	
	private FileserverProperties props;
	private final FileSystem fs = FileSystems.getDefault();
	
	ReadWriteLock lock = new ReentrantReadWriteLock();
	Lock writeLock = lock.writeLock();
	Lock readLock = lock.readLock();
	
	protected FileserverFilesystemService(FileserverProperties props) {
		this.props = props;
	}
	
	@Override
	public FSResult createDirectory(String directory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FSResult renameDirectory(String directoryOld, String directoryNew) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FSResult deleteDirectory(String directory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FSResult createFile(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FSResult renameFile(String fileOld, String fileNew) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FSResult deleteFile(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getFileContent(String file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FSElement> listDirectory(String directory) {
		// TODO Auto-generated method stub
		List<FSElement> elements = new ArrayList<FSElement>();
		//elements.add(new FSElement("test", 0, 0, false, false, 0, 0));
		
		try {
			readLock.lock();
			Path path = fs.getPath(props.basePath()+directory);
			log.info("Path:"+path.toString());
			log.info("Parent:"+path.getParent());
			log.info("Root:"+path.getRoot());
			
			
			Files.list(path).forEach(element -> {
				log.info(""+element);
				
				BasicFileAttributeView bv = Files.getFileAttributeView(element,BasicFileAttributeView.class);
				BasicFileAttributes attr = null;
				try {
					attr = bv.readAttributes();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
								
				if (Files.isDirectory(element)) {
					elements.add(
							new FSElement(element.getFileName().toString(), attr.creationTime().toMillis(), attr.lastModifiedTime().toMillis(), false, true, 0, 0));
							
				} else {
					elements.add(
							new FSElement(element.getFileName().toString(), attr.creationTime().toMillis(), attr.lastModifiedTime().toMillis(), false, false, 0, 0));
				}			
				
			});
			
		} catch (IOException err) {
			log.error(err.getLocalizedMessage());
		}		
		finally {
			readLock.unlock();
		}
		
		//sort liste
		//wie die reihenfolge??
		Comparator<FSElement> comp = Comparator.comparing(FSElement::isDirectory)
			      .thenComparing(FSElement::getName);
				
		Collections.sort(elements, comp);
		return elements;
	}

}
