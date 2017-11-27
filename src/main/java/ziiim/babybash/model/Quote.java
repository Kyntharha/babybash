package ziiim.babybash.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name = "quotes")
public class Quote
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String quote;
	
	@OneToMany
	(
			mappedBy="quote",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Vote> voteList = new ArrayList<Vote>();
	
	private Boolean published = false;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate = new Date();

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
	
	public void addVote(Vote vote)
	{
		voteList.add(vote);
		vote.setQuote(this);
	}
	
	public void removeVote(Vote vote)
	{
		voteList.remove(vote);
		vote.setQuote(null);
	}
	
	public boolean togglePublished()
	{
		if (published) published = false;
		else published = true;
		
		return published;
	}
	
	public String toString()
	{
		return quote;
	}
		
	public boolean getPublished()
	{
		return published;
	}
		
	public Integer getId()
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

	public Date getCreationDate()
	{
		return creationDate;
	}

	public void setQuote(String quote)
	{
		this.quote = quote;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}
}
