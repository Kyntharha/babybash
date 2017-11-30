package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ziiim.babybash.model.Quote;
import ziiim.babybash.model.Vote;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class QuoteTests
{
	@Test
	public void addVote()
	{
		Quote quote = new Quote("blargh0");
		Vote vote = new Vote("5465454fdfh", quote, Vote.Value.POSITIVE);
		quote.addVote(vote);
		
		assertThat(quote.getVoteList().size()).isEqualTo(1);
	}
	
	@Test
	public void togglesPublished()
	{
		// tooglePublished switches 'published' and returns the new status;
		Quote quote = new Quote("blargh0");
		assertTrue(quote.togglePublished());
		assertFalse(quote.togglePublished());
	}
	
	@Test
	public void addVoteVoteShouldBeUniquePerIpAndNewVoteReplacesOld()
	{
		Quote quote = new Quote("blargh0");
		Vote vote1 = new Vote("5465454fdfh", quote, Vote.Value.POSITIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("5465454fdfh", quote, Vote.Value.NEGATIVE);
		quote.addVote(vote2);

		assertThat(quote.getVoteList().size()).isEqualTo(1);
		assertThat(quote.getVoteList().get(0).getVote()).isEqualTo((byte)-1);
	}
		
	@Test
	public void removeVote()
	{
		Quote quote = new Quote("blargh0");
		Vote vote = new Vote("5465454fdfh", quote, Vote.Value.POSITIVE);
		quote.addVote(vote);
		quote.removeVote(vote);
		
		assertThat(quote.getVoteList().size()).isEqualTo(0);
	}
	
	@Test
	public void getVoteCountPositive()
	{
		Quote quote = new Quote("blargh0");
		Vote vote1 = new Vote("1", quote, Vote.Value.POSITIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("2", quote, Vote.Value.POSITIVE);
		quote.addVote(vote2);
		
		Vote vote3 = new Vote("3", quote, Vote.Value.POSITIVE);
		quote.addVote(vote3);
		
		assertThat(quote.getVoteCount()).isEqualTo(3);
	}
	
	@Test
	public void getVoteCountNegative()
	{	
		Quote quote = new Quote("blargh0");
		Vote vote1 = new Vote("1", quote, Vote.Value.NEGATIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("2", quote, Vote.Value.NEGATIVE);
		quote.addVote(vote2);
		
		Vote vote3 = new Vote("3", quote, Vote.Value.NEGATIVE);
		quote.addVote(vote3);
		
		assertThat(quote.getVoteCount()).isEqualTo(-3);
	}
	
	@Test
	public void getVoteCountMixed()
	{
		Quote quote = new Quote("blargh0");
		Vote vote1 = new Vote("1", quote, Vote.Value.POSITIVE);
		quote.addVote(vote1);
		
		Vote vote2 = new Vote("2", quote, Vote.Value.POSITIVE);
		quote.addVote(vote2);
		
		Vote vote3 = new Vote("3", quote, Vote.Value.POSITIVE);
		quote.addVote(vote3);
		
		Vote vote4 = new Vote("4", quote, Vote.Value.NEGATIVE);
		quote.addVote(vote4);
		
		Vote vote5 = new Vote("5", quote, Vote.Value.NEGATIVE);
		quote.addVote(vote5);
		
		assertThat(quote.getVoteCount()).isEqualTo(1);
	}
}
