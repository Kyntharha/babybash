package ziiim.babybash.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "votes")
public class Vote
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String ip;
	
	@NotNull
	private byte vote;
	public enum Value
	{
		POSITIVE((byte) 1), NEGATIVE((byte) -1);
		private final int value;
		private Value(byte value)
		{
			this.value = value;
		}
		private byte getValue()
		{
			return (byte) this.value;
		}
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quote_id")
	private Quote quote;
	
	protected Vote()
	{
	}

	public Vote(String ip, Value value)
	{
		this.ip = ip;
		this.vote = value.getValue();
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote )) return false;
        return id != null && id.equals(((Vote) o).id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ip, id);
    }

    public void setQuote(Quote quote)
    {
    	this.quote = quote;
    }
    
	public Integer getId()
	{
		return id;
	}
	
	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}
	
	public byte getVote()
	{
		return vote;
	}
	
	public void setVote(byte vote)
	{
		this.vote = vote;
	}
	
	public String toString()
	{
		return ""+vote;
	}
}
