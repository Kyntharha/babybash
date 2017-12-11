package ziiim.babybash.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ziiim.babybash.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>
{
	public Page<Quote> findPublishedByPublishedIsTrueAndRejectedIsFalseOrderByCreationDateDesc(Pageable pageable);
	public Page<Quote> findUnpublishedByPublishedIsFalseAndRejectedIsFalseOrderByCreationDateDesc(Pageable pageable);
	
	
	/**
	 * Fetches the latest published Quotes that are not rejected. 
	 * 
	 * @param page Which page to get. First page is 0
	 * @param itemsPerPage The number of Quotes per Page
	 * @return the requested Page
	 */
	public default Page<Quote> getRecentlyPublished(int page, int itemsPerPage)
	{
		Pageable pageable = new PageRequest(page, itemsPerPage);
		
		return findPublishedByPublishedIsTrueAndRejectedIsFalseOrderByCreationDateDesc(pageable);
	}
	
	/**
	 * Fetches the latest published Quotes that are not rejected. 
	 * 
	 * @param pageable A Pageable for the requested Page
	 * @return the requested Page
	 */
	public default Page<Quote> getRecentlyPublished(Pageable pageable)
	{		
		return findPublishedByPublishedIsTrueAndRejectedIsFalseOrderByCreationDateDesc(pageable);
	}
	
	/**
	 * Fetches recently submitted Quotes (published==false and rejected==false).
	 * 
	 * @param page Which page to get. First page is 0
	 * @param itemsPerPage The number of Quotes per Page
	 * @return the requested Page
	 */
	public default Page<Quote> getRecentlySubmitted(int page, int itemsPerPage)
	{
		Pageable pageable = new PageRequest(page, itemsPerPage);
		
		return findUnpublishedByPublishedIsFalseAndRejectedIsFalseOrderByCreationDateDesc(pageable);
	}
	
	/**
	 * Fetches recently submitted Quotes (published==false and rejected==false).
	 * 
	 * @param pageable A Pageable for the requested Page
	 * @return the requested Page
	 */
	public default Page<Quote> getRecentlySubmitted(Pageable pageable)
	{		
		return findUnpublishedByPublishedIsFalseAndRejectedIsFalseOrderByCreationDateDesc(pageable);
	}
}
