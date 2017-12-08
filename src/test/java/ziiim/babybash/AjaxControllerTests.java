package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import ziiim.babybash.controller.AjaxController;
import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(AjaxController.class)
public class AjaxControllerTests
{

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private QuoteRepository quoteRepository;
	
	@Test
	public void addMapping() throws Exception
	{
		final ArgumentCaptor<Quote> captor = ArgumentCaptor.forClass(Quote.class);
				
		mockMvc.perform(post("/add")
				.param("quote", "A test quote")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(view().name("/addQuoteForm"))
				.andExpect(model().attributeHasNoErrors("addQuoteForm"));
		
		verify(quoteRepository).save(captor.capture());
		Quote quote = captor.getValue();
		
		assertThat(quote.getQuote()).isEqualTo("A test quote");
	}
	
	@Test
	public void addMapping_WithInvalidQuoteLength_TooShort() throws Exception
	{				
		mockMvc.perform(post("/add")
				.param("quote", "2short")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(view().name("/addQuoteForm"))
				.andExpect(model().attributeHasFieldErrors("addQuoteForm", "quote"));
	}
	
	@Test
	public void addMapping_WithInvalidQuoteLength_TooLong() throws Exception
	{		
		String tooLong = StringUtils.rightPad("This String is too lo", 1000, "o") + "ng!";
				
		mockMvc.perform(post("/add")
				.param("quote", tooLong)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(view().name("/addQuoteForm"))
				.andExpect(model().attributeHasFieldErrors("addQuoteForm", "quote"));
	}
}
