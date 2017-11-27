package ziiim.babybash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ziiim.babybash.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer>
{
	public List<Quote> findFirst10ByPublishedIsTrueOrderByCreationDateDesc();
	
	@Modifying
	@Query(value = "UPDATE Quote SET votes = votes + 1 WHERE id = ?1", nativeQuery = true)
	int updateByName(int id);
}
