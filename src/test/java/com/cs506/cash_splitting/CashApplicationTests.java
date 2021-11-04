package com.cs506.cash_splitting;

import org.junit.Before;
import org.junit.After;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
class CashApplicationTests {

	@Before
	public void init(){
		System.out.println("--------Test Starts---------");
	}

	@After
	public void after(){
		System.out.println("--------Test Ends-----------");
	}
//	@Test
//	void contextLoads() {
//	}

}
