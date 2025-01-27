package de.homelabs.hlfileserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import de.homelabs.hlfileserver.entity.FSElement;
import de.homelabs.hlfileserver.entity.FileserverProperties;
import de.homelabs.hlfileserver.service.FileserverFactory;
import de.homelabs.hlfileserver.service.FileserverService;

@SpringBootTest
class HlfileserverApplicationTests {

	@Test
	void contextLoads() {
		FileserverProperties props = new FileserverProperties("/home/sm1rk");
		
		FileserverService fsService = FileserverFactory.createInstance(props);
		List<FSElement> elements = fsService.listDirectory("/");
		for (FSElement element : elements) {
			if (element.isDirectory())
				System.out.println("(D) "+element.getName());
			else
				System.out.println("(F) "+element.getName());
		}
	}

}
