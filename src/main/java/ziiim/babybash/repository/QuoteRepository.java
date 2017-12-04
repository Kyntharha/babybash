package ziiim.babybash.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ziiim.babybash.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>
{
	public List<Quote> findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
	public Page<Quote> findAllByPublishedIsTrueOrderByCreationDateDesc(Pageable pageable);
}
