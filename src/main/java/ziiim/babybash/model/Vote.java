package ziiim.babybash.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "votes")
public class Vote
{
	@Id
	@SequenceGenerator(name="vote_id_seq", sequenceName="vote_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="vote_id_seq")
	private Long id;

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

	/**
	 * 
	 * @param ip should not be null
	 * @param quote should not be null or else it won't be persisted
	 * @param value enum Vote.Value
	 */
	public Vote(String ip, Quote quote, Value value)
	{
		setIp(ip);
		this.quote = quote;
		this.vote = value.getValue();
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote )) return false;
        return quote != null && quote.equals(((Vote) o).quote) && ip.equals(((Vote) o).ip);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ip, id);
    }
    
    /**
     * sets the quote to null, which results in hibernate removing the vote from the database
     */
    public void remove()
    {
    	quote = null;
    }
    
	public Long getId()
	{
		return id;
	}
	
	/**
	 * @param ip can not be null
	 */
	private void setIp(String ip)
	{
		if (ip == null) this.ip = "0";
		else this.ip = ip;		
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public byte getVote()
	{
		return vote;
	}
	
	public void setVote(byte vote)
	{
		this.vote = vote;
	}
}
