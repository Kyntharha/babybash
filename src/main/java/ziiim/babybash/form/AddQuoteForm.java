package ziiim.babybash.form;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddQuoteForm
{
	@NotNull
	@Size(min=10, max=1000)
	private String quote;
	
	private Date creationDate = new Date();
								
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
}
