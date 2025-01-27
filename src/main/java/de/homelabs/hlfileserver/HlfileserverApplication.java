package de.homelabs.hlfileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HlfileserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(HlfileserverApplication.class, args);
		
		
		/*FileSystem fileSystem = FileSystems.getDefault();
		for (FileStore store: fileSystem.getFileStores()) {
		    System.out.println(store.name() + " - " + store.type());
		}
		
		for (Path directory : fileSystem.getRootDirectories()) {
		    boolean readable = Files.isReadable(directory);
		    System.out.println("directory = " + directory + " - " + readable);
		}*/
		
		
	}
}
