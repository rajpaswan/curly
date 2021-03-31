package com.rajpaswan.curly.repo;

import com.rajpaswan.curly.model.UrlDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlDetailRepository extends JpaRepository<UrlDetail, Long> {

	UrlDetail findByShortUrl(String shortUrl);

	@Modifying(clearAutomatically = true)
	@Query("update UrlDetail u set u.redirectCount = (u.redirectCount+1) WHERE u.id = :id")
	void updateRedirectCount(@Param("id") long id);
}
