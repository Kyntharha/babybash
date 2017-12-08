package ziiim.babybash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ziiim.babybash.form.AddQuoteForm;
import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@Controller
public class DefaultController
{
	@Autowired
	private QuoteRepository quoteRepository;

	@GetMapping(value = {"/", "index", "home"})
	public String showIndex(ModelMap model)
	{
		List<Quote> quoteList = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		model.addAttribute("quoteList", quoteList);
		
		return "/index";
	}
	
	@GetMapping(value = "/add")
	public String showAddQuoteForm(AddQuoteForm addQuoteForm)
	{
		return "/addQuoteForm";
	}
}
