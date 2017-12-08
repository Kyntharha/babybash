package ziiim.babybash.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

import ziiim.babybash.form.AddQuoteForm;
import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@Controller
public class AjaxController
{
	@Autowired
	private QuoteRepository quoteRepository;

	@PostMapping(value = "/add")
	public String checkNewQuote(@Valid AddQuoteForm addQuoteForm, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors()) {
			return "/addQuoteForm";
		}
				
		Quote quote = new Quote(addQuoteForm.getQuote());
		quote.setCreationDate(addQuoteForm.getCreationDate());
		quoteRepository.save(quote);
		
		return "/addQuoteForm";
	}
}
