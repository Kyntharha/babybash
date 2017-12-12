package ziiim.babybash.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ziiim.babybash.form.AddQuoteForm;
import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@Controller
public class AjaxController
{
	@Autowired
	private QuoteRepository quoteRepository;

	@PostMapping("/add")
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
	
	@PostMapping("/queue")
	public String publishQuote(@RequestParam("id") long id)
	{
		
		//quoteRepository.save(quote);
		System.out.println("\n\n\n\n\n ------ \n\n"+id);
		return "redirect:/queue";
	}
}
