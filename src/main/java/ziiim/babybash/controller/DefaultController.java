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
	QuoteRepository quoteRepository;

	@GetMapping("/")
	public String index(ModelMap model)
	{
		//TODO: switch hibernate to "update" and remove the testdata
		quoteRepository.save(new Quote("blargh"));
		quoteRepository.save(new Quote("blargh2"));
		quoteRepository.save(new Quote("blargh3"));
		quoteRepository.save(new Quote("blargh4"));

		//TODO: fetch only a specific amount of quotes;
		List<Quote> quoteList = quoteRepository.findAll();
		model.addAttribute("quoteList", quoteList);

		return "/index";
	}
}
