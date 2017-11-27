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
	
	private Integer votes = 0;
	
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
		
	public boolean getPublished()
	{
		return published;
	}
	
	public boolean togglePublished()
	{
		if (published) published = false;
		else published = true;
		
		return published;
	}
}
