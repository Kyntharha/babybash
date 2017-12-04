package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ziiim.babybash.controller.DefaultController;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SmokeTest
{
	@Autowired
	private DefaultController controller;

	@Test
	public void contexLoads() throws Exception
	{
		assertThat(controller).isNotNull();
	}
}
