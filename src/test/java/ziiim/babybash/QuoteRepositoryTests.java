package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuoteRepositoryTests
{
	@Autowired
	private QuoteRepository quoteRepository;
	
	@Before
	public void initialize()
	{
		List<Quote> quoteList = new ArrayList<Quote>();
		quoteList.add(new Quote("blargh0"));
		quoteList.add(new Quote("blargh1"));
		quoteList.add(new Quote("blargh2"));
		quoteList.add(new Quote("blargh3"));
		quoteList.add(new Quote("blargh4"));
		quoteList.add(new Quote("blargh5"));
		quoteList.add(new Quote("blargh6"));
		quoteList.add(new Quote("blargh7"));
		quoteList.add(new Quote("blargh8"));
		quoteList.add(new Quote("blargh9"));
		quoteList.add(new Quote("blargh10"));
		quoteList.add(new Quote("blargh11"));
		quoteRepository.save(quoteList);
	}
	
	@Test
	public void findsQuotesById()
	{
		Quote found = quoteRepository.findOne(1);
		assertThat(found.getQuote()).isEqualTo("blargh0");
	}
	
	@Test
	public void togglesPublished()
	{
		// tooglePublished switches 'published' and returns the new status;
		Quote found = quoteRepository.findOne(1);
		assertTrue(found.togglePublished());
		assertFalse(found.togglePublished());
	}
	
	//TODO: Vote tests;
	
	@Test
	@DirtiesContext
	public void showLatest10PublishedQuotes()
	{
		List<Quote> quoteList = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		quoteList.size();
		//TODO: complete test
	}
}
