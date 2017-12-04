package ziiim.babybash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import ziiim.babybash.model.Quote;
import ziiim.babybash.repository.QuoteRepository;

@Controller
public class DefaultController
{
	@Autowired
	private QuoteRepository quoteRepository;

	@GetMapping(value = {"/", "index", "home"})
	public String index(ModelMap model)
	{
//		Quote newPublished = new Quote("blarghNewPublished");
//		quoteRepository.save(newPublished);
//		newPublished.togglePublished();
//		quoteRepository.save(new Quote("blargh2"));
//		quoteRepository.save(new Quote("blargh3"));
//		quoteRepository.save(new Quote("blargh4"));

		List<Quote> quoteList = quoteRepository.findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
		model.addAttribute("quoteList", quoteList);
		
		return "/index";
	}
}
