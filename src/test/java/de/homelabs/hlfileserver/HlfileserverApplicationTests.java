package de.homelabs.hlfileserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import de.homelabs.hlfileserver.entity.FSDirectoryResult;
import de.homelabs.hlfileserver.entity.FSElement;
import de.homelabs.hlfileserver.entity.FileserverProperties;
import de.homelabs.hlfileserver.service.FileserverFactory;
import de.homelabs.hlfileserver.service.FileserverService;

@SpringBootTest
class HlfileserverApplicationTests {
	private final Logger log = LoggerFactory.getLogger(HlfileserverApplicationTests.class);
	
	@Test
	void checkIfPOMExists() {
		FileserverProperties props = new FileserverProperties(".");
		
		FileserverService fsService = FileserverFactory.createInstance(props);
		FSDirectoryResult result = fsService.listDirectory("/");
		
		//check of we have a success, else print error
		assertTrue(result.ok, result.error);
		
		List<FSElement> elements = result.list;
		
		//for debugging
		for (FSElement element : elements) {
			if (element.isDirectory())
				log.info("(D) "+element.getName());
			else
				log.info("(F) "+element.getName());
		}

		//check if pom.xml exists in project root path
		String element = elements.stream()
			.filter(ds -> "pom.xml".equals(ds.getName()))
			.map(FSElement::getName)
			.findFirst()
			.orElse("");
		assertEquals("pom.xml",element);
	}

}
