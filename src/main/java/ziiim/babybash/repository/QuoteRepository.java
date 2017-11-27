package ziiim.babybash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ziiim.babybash.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer>
{
	public List<Quote> findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
}
