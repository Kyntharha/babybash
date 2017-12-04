package ziiim.babybash;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ziiim.babybash.controller.DefaultController;
import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(DefaultController.class)
public class DefaultControllerTests
{

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuoteRepository quoteRepository;

	@Test
	public void indexMappings() throws Exception
	{
		List<Quote> quoteList = new ArrayList<Quote>();
		quoteList.add(new Quote("Blargh1"));
		quoteList.add(new Quote("Blargh2"));
		quoteList.add(new Quote("Blargh3"));

		when(quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc()).thenReturn(quoteList);

		mockMvc.perform(get("/")).andExpect(status().isOk())
				.andExpect(model().attribute("quoteList", is(quoteList)))
				.andExpect(view().name("/index"));

		mockMvc.perform(get("/index")).andExpect(status().isOk())
				.andExpect(model().attribute("quoteList", is(quoteList)))
				.andExpect(view().name("/index"));
		
		mockMvc.perform(get("/home")).andExpect(status().isOk())
				.andExpect(model().attribute("quoteList", is(quoteList)))
				.andExpect(view().name("/index"));
	}
}
