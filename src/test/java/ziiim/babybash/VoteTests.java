package ziiim.babybash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ziiim.babybash.model.Quote;
import ziiim.babybash.model.Vote;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class VoteTests
{
	@Test
	public void constructorPreventsNullAsIp()
	{	
		Quote quote = new Quote("blargh");
		assertThat(new Vote(null, quote, Vote.Value.POSITIVE).getIp()).isEqualTo("0");
	}
			
	@Test
	public void equalsWithSameObject()
	{	
		Quote quote = new Quote("blargh");
		Vote vote =  new Vote("abc", quote, Vote.Value.POSITIVE);
		
		assertTrue(vote.equals(vote));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equalsWithDifferentClass()
	{	
		Quote quote = new Quote("blargh");
		Vote vote =  new Vote("abc", quote, Vote.Value.POSITIVE);
		
		assertFalse(vote.equals(quote));
	}
	
	@Test
	public void equalsWith2VotesFirstWithoutQuote()
	{	
		Quote quote = new Quote("blargh");
		Vote vote1 =  new Vote("abc", quote, Vote.Value.POSITIVE);
		Vote vote2 =  new Vote("abc", quote, Vote.Value.POSITIVE);
		vote1.remove();
		
		assertFalse(vote1.equals(vote2));
	}
	
	@Test
	public void equalsWith2VotesFirstWithoutIp()
	{	
		Quote quote = new Quote("blargh");
		Vote vote1 =  new Vote(null, quote, Vote.Value.POSITIVE);
		Vote vote2 =  new Vote("abc", quote, Vote.Value.NEGATIVE);

		assertFalse(vote1.equals(vote2));
	}
		
	@Test
	public void equalsWith2VotesOneWithDifferentIp()
	{	
		Quote quote = new Quote("blargh");
		Vote vote1 =  new Vote("abc", quote, Vote.Value.POSITIVE);
		Vote vote2 =  new Vote("abcd", quote, Vote.Value.POSITIVE);
		
		assertFalse(vote1.equals(vote2));
	}
	
	@Test
	public void equalsWith2VotesOneWithDifferentQuote()
	{	
		Quote quote1 = new Quote("blargh1");
		Quote quote2 = new Quote("blargh2");
		Vote vote1 =  new Vote("abc", quote1, Vote.Value.POSITIVE);
		Vote vote2 =  new Vote("abc", quote2, Vote.Value.POSITIVE);
		assertFalse(vote1.equals(vote2));
	}
	
	@Test
	public void equalsWith2EqualVotes()
	{	
		Quote quote = new Quote("blargh");
		Vote vote1 =  new Vote("abc", quote, Vote.Value.POSITIVE);
		Vote vote2 =  new Vote("abc", quote, Vote.Value.NEGATIVE);

		assertTrue(vote1.equals(vote2));
	}
	
	@Test
	public void hashCodeIsWorking()
	{	
		Vote vote = new Vote("abc", null, Vote.Value.POSITIVE);
		assertThat(vote.hashCode()).isEqualTo(vote.hashCode());
	}
}
