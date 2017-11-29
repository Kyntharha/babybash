package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class QuoteRepositoryTests
{
	@Autowired
	private QuoteRepository quoteRepository;
	
	private List<Quote> quoteList;
	
	@Before
	public void initialize()
	{
		quoteList = new ArrayList<Quote>();
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
		Quote quote = quoteRepository.findOne(1);
		assertTrue(quote.togglePublished());
		assertFalse(quote.togglePublished());
	}
		
	@Test
	@DirtiesContext
	public void showLatest10PublishedQuotesCheckThatAllArePublished()
	{
		// switch 3 quotes to published
		for(int i=0; i<3; i++)
		{
			Quote quote = quoteList.get(i);
			quote.togglePublished();
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		for (Quote quote: publishedQuotes)
		{
			assertTrue(quote.getPublished());
		}
	}
	
	@Test
	@DirtiesContext
	public void showLatest10PublishedQuotesCheckOrder()
	{
		// switch 3 quotes to published and change creationDate
		for(int i=0; i<3; i++)
		{
			Quote quote = quoteList.get(i);
			quote.togglePublished();
			quote.setCreationDate(new Date(1000*i));
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		// j is needed because the order should be reversed
		int j = 2;
		for(int i=0; i<3; i++)
		{
			Quote quote = publishedQuotes.get(i);
			assertThat(quote.getCreationDate().getTime()).isEqualTo(1000*j);
			j--;
		}
	}
	
	@Test
	@DirtiesContext
	public void showLatest10PublishedQuotesWithMoreThan10PublishedQuotes()
	{
		// 12 quotes in list, all published
		for(Quote quote: quoteList)
		{
			quote.togglePublished();
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		assertThat(publishedQuotes.size()).isEqualTo(10);
	}
	
	@Test
	@DirtiesContext
	public void showLatest10PublishedQuotesWithExactly10PublishedQuotes()
	{
		// switch 10 quotes to published
		for(int i=0; i<10; i++)
		{
			Quote quote = quoteList.get(i);
			quote.togglePublished();
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();

		assertThat(publishedQuotes.size()).isEqualTo(10);
	}
	
	@Test
	@DirtiesContext
	public void showLatest10PublishedQuotesWithLessThan10PublishedQuotes()
	{
		// switch 9 quotes to published
		for(int i=0; i<9; i++)
		{
			Quote quote = quoteList.get(i);
			quote.togglePublished();
		}
		quoteRepository.save(quoteList);
		
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		assertThat(publishedQuotes.size()).isEqualTo(9);
	}
}
