package com.example.LoginPage;

import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LoginPageApplicationTests {
	private CheckTheCases checkTheCases;


	@Test
	void contextLoads() {
	}

//	@Test
//	void testSumOfNumbers() {
//		int actualResult = checkTheCases.sumOfNumber(10, 2, 4);
//		int expectedResult = 16;
//		assertThat(actualResult).isEqualTo(expectedResult);
//	}
	@Test
	public void isSum(){
		assertTrue(true);
	}
	@Test
	public void isFalse()
	{
		assertTrue(false);
	}

	@Test
	public void isTakeSum(){
		checkTheCases=new CheckTheCases();
		checkTheCases.sumOfNumber(4,5,6);
		assertEquals(15,checkTheCases.sumOfNumber(4,5,6));
	}
}
