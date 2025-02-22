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

import de.homelabs.hlfileserver.entity.FSDirectoryResult;
import de.homelabs.hlfileserver.entity.FSElement;
import de.homelabs.hlfileserver.entity.FSErrorCode;
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
		try {
			writeLock.lock();
			if (!Files.exists(Path.of(props.basePath() + directory))) {
				Files.createDirectory(Path.of(props.basePath() + directory));
				return FSResult.ok();
			} else {
				return FSResult.error(props.basePath() + directory + " already exists", FSErrorCode.PATHEXISTS);
			}
		} catch (IOException e) {
			return FSResult.error(e.getMessage(), FSErrorCode.GENERAL);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public FSResult renameDirectory(String directoryOld, String directoryNew) {
		try {
			writeLock.lock();
			if (Files.exists(Path.of(props.basePath() + directoryOld))) {
				Files.move(Path.of(props.basePath() + directoryOld), Path.of(props.basePath() + directoryNew));
				return FSResult.ok();
			} else {
				return FSResult.error(props.basePath() + directoryOld + " does not exists", FSErrorCode.PATHEXISTS);
			}
		} catch (IOException e) {
			return FSResult.error(e.getMessage(), FSErrorCode.GENERAL);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public FSResult deleteDirectory(String directory) {
		try {
			writeLock.lock();
			if (Files.exists(Path.of(props.basePath() + directory))) {
				Files.delete(Path.of(props.basePath() + directory));
				return FSResult.ok();
			} else {
				return FSResult.error(props.basePath() + directory + " does not exists", FSErrorCode.PATHEXISTS);
			}
		} catch (IOException e) {
			return FSResult.error(e.getMessage(), FSErrorCode.GENERAL);
		} finally {
			writeLock.lock();
		}
	}

	@Override
	public FSResult createFile(String file) {
		try {
			writeLock.lock();
			if (!Files.exists(Path.of(props.basePath() + file))) {
				Files.createFile(Path.of(props.basePath() + file));
				return FSResult.ok();
			} else {
				return FSResult.error(props.basePath() + file + " already exists", FSErrorCode.PATHEXISTS);
			}
		} catch (IOException e) {
			return FSResult.error(e.getMessage(), FSErrorCode.GENERAL);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public FSResult renameFile(String fileOld, String fileNew) {
		try {
			writeLock.lock();
			if (Files.exists(Path.of(props.basePath() + fileOld))) {
				Files.move(Path.of(props.basePath() + fileOld), Path.of(props.basePath() + fileNew));
				return FSResult.ok();
			} else {
				return FSResult.error(props.basePath() + fileOld + " does not exists", FSErrorCode.PATHEXISTS);
			}
		} catch (IOException e) {
			return FSResult.error(e.getMessage(), FSErrorCode.GENERAL);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public FSResult deleteFile(String file) {
		try {
			writeLock.lock();
			if (Files.exists(Path.of(props.basePath() + file))) {
				Files.delete(Path.of(props.basePath() + file));
				return FSResult.ok();
			} else {
				return FSResult.error(props.basePath() + file + " does not exists", FSErrorCode.PATHEXISTS);
			}
		} catch (IOException e) {
			return FSResult.error(e.getMessage(), FSErrorCode.GENERAL);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public byte[] getFileContent(String file) {
		// TODO here goes the magic!
		return null;
	}

	@Override
	public FSDirectoryResult listDirectory(String directory) {
		List<FSElement> elements = new ArrayList<FSElement>();

		try {
			readLock.lock();
			Path path = fs.getPath(props.basePath() + directory);
			log.debug("Path:" + path.toString());
			log.debug("Parent:" + path.getParent());
			log.debug("Root:" + path.getRoot());

			Files.list(path).forEach(element -> {
				log.debug("" + element);

				BasicFileAttributeView bv = Files.getFileAttributeView(element, BasicFileAttributeView.class);
				BasicFileAttributes attr = null;
				try {
					attr = bv.readAttributes();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}

				if (Files.isDirectory(element)) {
					elements.add(new FSElement(element.getFileName().toString(), attr.creationTime().toMillis(),
							attr.lastModifiedTime().toMillis(), false, true, 0, 0));

				} else {
					elements.add(new FSElement(element.getFileName().toString(), attr.creationTime().toMillis(),
							attr.lastModifiedTime().toMillis(), false, false, 0, 0));
				}

			});

		} catch (IOException err) {
			log.error(err.toString());

			return FSDirectoryResult.error(err.toString(), FSErrorCode.INVALIDPATH);
		} finally {
			readLock.unlock();
		}

		// sort liste
		Comparator<FSElement> comp = Comparator.comparing(FSElement::isDirectory).reversed()
				.thenComparing(FSElement::getName);

		Collections.sort(elements, comp);
		return FSDirectoryResult.ok(elements);
	}

}
