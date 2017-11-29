package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ziiim.babybash.model.Quote;
import ziiim.babybash.model.Vote;
import ziiim.babybash.repository.QuoteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class QuoteTests
{
	@Autowired
	private QuoteRepository quoteRepository;
			
	private Quote quote;
	
	
	@Before
	public void initialize()
	{
		quote = new Quote("blargh0");
		quoteRepository.save(quote);
	}
	
	@Test
	@DirtiesContext
	public void addVote()
	{
		Vote vote = new Vote("5465454fdfh", Vote.Value.POSITIVE);
		quote.addVote(vote);
		
		assertThat(quote.getVoteList().size()).isEqualTo(1);
	}
	
	@Test
	@DirtiesContext
	public void addVoteAndVoteShouldBeUniquePerIp()
	{
		Vote vote1 = new Vote("5465454fdfh", Vote.Value.POSITIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("5465454fdfh", Vote.Value.POSITIVE);
		quote.addVote(vote2);

		assertThat(quote.getVoteList().size()).isEqualTo(1);
	}
	
	@Test
	@DirtiesContext
	public void removeVote()
	{
		Vote vote = new Vote("5465454fdfh", Vote.Value.POSITIVE);
		quote.addVote(vote);
		quote.removeVote(vote);
		
		assertThat(quote.getVoteList().size()).isEqualTo(0);
	}
	
	@Test
	@DirtiesContext
	public void getVoteCountPositive()
	{
		Vote vote1 = new Vote("1", Vote.Value.POSITIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("2", Vote.Value.POSITIVE);
		quote.addVote(vote2);
		
		Vote vote3 = new Vote("3", Vote.Value.POSITIVE);
		quote.addVote(vote3);
		
		assertThat(quote.getVoteCount()).isEqualTo(3);
	}
	
	@Test
	@DirtiesContext
	public void getVoteCountNegative()
	{	
		Vote vote1 = new Vote("1", Vote.Value.NEGATIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("2", Vote.Value.NEGATIVE);
		quote.addVote(vote2);
		
		Vote vote3 = new Vote("3", Vote.Value.NEGATIVE);
		quote.addVote(vote3);
		
		assertThat(quote.getVoteCount()).isEqualTo(-3);
	}
	
	@Test
	@DirtiesContext
	public void getVoteCountMixed()
	{
		Vote vote1 = new Vote("1", Vote.Value.POSITIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("2", Vote.Value.POSITIVE);
		quote.addVote(vote2);
		
		Vote vote3 = new Vote("3", Vote.Value.POSITIVE);
		quote.addVote(vote3);
		
		Vote vote4 = new Vote("4", Vote.Value.NEGATIVE);
		quote.addVote(vote4);
		
		Vote vote5 = new Vote("5", Vote.Value.NEGATIVE);
		quote.addVote(vote5);
		
		assertThat(quote.getVoteCount()).isEqualTo(1);
	}
}
