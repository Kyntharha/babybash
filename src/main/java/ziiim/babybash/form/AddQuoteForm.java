package ziiim.babybash.form;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddQuoteForm
{
	@NotNull
	@Size(min=10, max=1000)
	private String quote;
	
	private LocalDateTime creationDate = LocalDateTime.now();
								
	public String getQuote()
	{
		return quote;
	}
	
	public LocalDateTime getCreationDate()
	{
		return creationDate;
	}

	public void setQuote(String quote)
	{
		this.quote = quote;
	}
}
