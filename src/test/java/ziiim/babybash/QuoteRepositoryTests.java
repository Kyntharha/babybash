package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	public void showLatest10PublishedQuotes_CheckThatAllArePublished()
	{
		// add 3 published quotes and 2 unpublished
		for(int i=0; i<5; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			if(i < 3) quote.togglePublished();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		for (Quote quote: publishedQuotes)
		{
			assertTrue(quote.getPublished());
		}
	}
	
	/*@Test
	public void showLatest10PublishedQuotes_CheckThatNoneAreDeleted()
	{
		// add 3 published quotes and 2 unpublished
		for(int i=0; i<5; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			if(i < 3) quote.togglePublished();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		for (Quote quote: publishedQuotes)
		{
			assertTrue(quote.getPublished());
		}
	}*/
	
	@Test
	public void showLatest10PublishedQuotes_CheckOrder()
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
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
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
	public void showLatest10PublishedQuotes_WithMoreThan10PublishedQuotes()
	{
		// add 12 published quotes
		for(int i=0; i<12; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			quote.togglePublished();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		assertThat(publishedQuotes.size()).isEqualTo(10);
	}
	
	@Test
	public void showLatest10PublishedQuotes_WithExactly10PublishedQuotes()
	{
		// add 10 published quotes and 2 unpublished
		for(int i=0; i<12; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			if(i < 10) quote.togglePublished();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
				
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();

		assertThat(publishedQuotes.size()).isEqualTo(10);
	}
	
	@Test
	public void showLatest10PublishedQuotes_WithLessThan10PublishedQuotes()
	{
		// add 9 published quotes and 2 unpublished
		for(int i=0; i<11; i++)
		{
			Quote quote = new Quote("Blargh"+i);
			if(i < 9) quote.togglePublished();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
		
		List<Quote> publishedQuotes = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		
		assertThat(publishedQuotes.size()).isEqualTo(9);
	}
	
	@Test
	public void showAllPublishedQuotes_25PerPage_OrderedByCreationDateDesc()
	{
		// 55 published quotes
		for (int i=0; i<55; i++)
		{
			Quote quote = new Quote("blub"+i);
			quote.togglePublished();
			quoteList.add(quote);
		}
		quoteRepository.save(quoteList);
		
		Pageable pageable = new PageRequest(0, 25);
		Page<Quote> page = quoteRepository.findAllByPublishedIsTrueOrderByCreationDateDesc(pageable);
		
		assertThat(page.getTotalElements()).isEqualTo(55);
		assertThat(page.getTotalPages()).isEqualTo(3);
	}
}
