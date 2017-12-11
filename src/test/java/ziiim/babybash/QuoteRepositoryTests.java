package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class QuoteRepositoryTests
{
	@Autowired
	private QuoteRepository quoteRepository;
	
	private List<Quote> quoteList;
	
	@Before
	public void initialize()
	{
		quoteRepository.deleteAll();
		quoteList = new ArrayList<Quote>();	
	}
				
	@Test
	public void showRecentlyPublishedQuotes_CheckThatAllArePublished()
	{
		// add 3 published quotes and 2 unpublished
		for(int i=0; i<5; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			if(i < 3) quote.togglePublished();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.getRecentlyPublished(0, 10).getContent();
		
		assertThat(publishedQuotes.size()).isEqualTo(3);
		
		for (Quote quote: publishedQuotes)
		{
			assertTrue(quote.getPublished());
		}
	}
	
	@Test
	public void showRecentlyPublishedQuotes_CheckThatNoneAreRejected()
	{
		// add 4 quotes: 1 published, 1 published and rejected, 1 unpublished, 1 unpublished and rejected
		for(int i=1; i<=4; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			if(i <= 2) quote.togglePublished();
			if(i%2 == 0) quote.toggleRejected();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.getRecentlyPublished(0, 10).getContent();
		
		assertThat(publishedQuotes.size()).isEqualTo(1);
		
		for (Quote quote: publishedQuotes)
		{
			assertTrue(quote.getPublished());
			assertFalse(quote.getRejected());
		}
	}
	
	@Test
	public void showRecentlyPublishedQuotes_CheckOrder()
	{
		// add 3 published quotes and change creationDate
		for(int i=0; i<3; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			quote.togglePublished();
			quote.setCreationDate(LocalDateTime.of(100*i, 1, 1, 1, 1));
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.getRecentlyPublished(0, 10).getContent();
				
		// j is needed because the order should be reversed
		int j = 2;
		for(int i=0; i<3; i++)
		{
			Quote quote = publishedQuotes.get(i);
			assertThat(quote.getCreationDate().getYear()).isEqualTo(100*j);
			j--;
		}
	}
		
	@Test
	public void showRecentlyPublishedQuotes_25PerPage_OrderedByCreationDateDesc()
	{
		// 55 published quotes
		for (int i=0; i<55; i++)
		{
			Quote quote = new Quote("blub"+i);
			quote.togglePublished();

			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
		
		Page<Quote> firstPage = quoteRepository.getRecentlyPublished(0, 25);
		
		assertThat(firstPage.getNumberOfElements()).isEqualTo(25);
		assertThat(firstPage.getTotalElements()).isEqualTo(55);
		assertThat(firstPage.getTotalPages()).isEqualTo(3);
		
		Page<Quote> secondPage = quoteRepository.getRecentlyPublished(firstPage.nextPageable());
		assertThat(secondPage.getNumberOfElements()).isEqualTo(25);
		assertThat(firstPage.getTotalElements()).isEqualTo(55);
		assertThat(firstPage.getTotalPages()).isEqualTo(3);
	}
	
	@Test
	public void showRecentlySubmitteddQuotes_25PerPage_OrderedByCreationDateDesc()
	{
		// 55 unpublished quotes, 10 of them rejected
		for (int i=0; i<55; i++)
		{
			Quote quote = new Quote("blub"+i);
			if (i < 10) quote.toggleRejected();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
		
		Page<Quote> firstPage = quoteRepository.getRecentlySubmitted(0, 25);
		
		assertThat(firstPage.getNumberOfElements()).isEqualTo(25);
		assertThat(firstPage.getTotalElements()).isEqualTo(45);
		assertThat(firstPage.getTotalPages()).isEqualTo(2);
		
		Page<Quote> secondPage = quoteRepository.getRecentlySubmitted(firstPage.nextPageable());
		assertThat(secondPage.getNumberOfElements()).isEqualTo(20);
		assertThat(firstPage.getTotalElements()).isEqualTo(45);
		assertThat(firstPage.getTotalPages()).isEqualTo(2);	
	}
}
