package ziiim.babybash.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "quotes")
public class Quote
{
	@Id
	@SequenceGenerator(name="quote_id_seq", sequenceName="quote_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="quote_id_seq")
	private Long id;

	private String quote;
	
	@OneToMany
	(
			mappedBy="quote",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER
	)
	private List<Vote> voteList = new ArrayList<Vote>();
	
	private Boolean published = false;
	
	private Boolean rejected = false;

	private LocalDateTime creationDate = LocalDateTime.now();

	protected Quote()
	{
	};

	public Quote(String quote)
	{
		this.quote = quote;
	}
	
	public Integer getVoteCount()
	{
		int count = 0;
		
		for(Vote vote: voteList)
		{
			count += vote.getVote();
		}
		
		return count;
	}
	
	public void addVote(Vote newVote)
	{
		boolean isDuplicate = false;
		for(Vote oldVote: voteList)
		{
			if(oldVote.equals(newVote))
			{
				oldVote.setVote(newVote.getVote());
				isDuplicate = true;
			}
		}
		if (!isDuplicate) voteList.add(newVote);
	}
	
	public void removeVote(Vote vote)
	{
		voteList.remove(vote);
		vote.remove();
	}
	
	public boolean togglePublished()
	{
		if (published) published = false;
		else published = true;
				
		return published;
	}
	
	public boolean toggleRejected()
	{
		if (rejected) rejected = false;
		else rejected = true;
				
		return rejected;
	}
			
	public boolean getPublished()
	{
		return published;
	}
	
	public boolean getRejected()
	{
		return rejected;
	}
		
	public Long getId()
	{
		return id;
	}
	
	public String getQuote()
	{
		return quote;
	}
	
	public List<Vote> getVoteList()
	{
		return this.voteList;
	}

	public LocalDateTime getCreationDate()
	{
		return creationDate;
	}

	public void setQuote(String quote)
	{
		this.quote = quote;
	}

	public void setCreationDate(LocalDateTime creationDate)
	{
		this.creationDate = creationDate;
	}
}
