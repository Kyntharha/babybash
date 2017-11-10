package ziiim.babybash.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	private Integer votes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	protected Quote()
	{
	};

	public Quote(String quote)
	{
		this.quote = quote;
		this.creationDate = new Date();
		this.votes = 0;
	}

	public Integer getId()
	{
		return id;
	}
	
	public String getQuote()
	{
		return quote;
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
	
	public Integer getVotes()
	{
		return votes;
	}
	
	public String toString()
	{
		return quote;
	}
	
	public void increaseVotes()
	{
		//TODO: prevent race condition
		votes += 1;
	}
	
	public void decreaseVotes()
	{
		//TODO: prevent race condition
		votes -= 1;
	}
}
