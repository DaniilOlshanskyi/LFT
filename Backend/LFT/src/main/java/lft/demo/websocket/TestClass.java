package lft.demo.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lft.demo.UserRepository;

@Service
public class TestClass {
	
	@Autowired
	public  UserRepository userRepository;
	
	public void TestClass() {
		System.out.println("***" + userRepository);
	}

}
