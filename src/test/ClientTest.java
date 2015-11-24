package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;

import client.*;
import server.*;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ClientTest {
	private Client c;
	private InetAddress s;
	private ScoringUtil score;
	
	@BeforeClass
	public void startServer() throws Exception {
		// start server
		Server.main(null);
	}
	
	@Test
	public void clientMainTest() throws Exception {
		String[] args = {"Client","-s","test"};
		ClientMain.main(args);
	}
	
	
	@Test
	public void connectTest() throws IOException {
		c.connect(s, 9000);
		assertEquals(100,score.getScore());
	}

}