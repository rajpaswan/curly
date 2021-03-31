package com.rajpaswan.curly.service;

import com.rajpaswan.curly.model.UrlDetail;
import com.rajpaswan.curly.repo.UrlDetailRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class UrlShortnerService {

	private final static Logger LOGGER = Logger.getLogger(UrlShortnerService.class.getName());
	private final UrlDetailRepository urlRepo;

	@Autowired
	public UrlShortnerService(UrlDetailRepository urlRepo) {
		this.urlRepo = urlRepo;
	}

	public String makeShortUrl(String targetUrl) {
		String shortUrl = RandomStringUtils.random(6, "0123456789abcdefghijklmnopqrstuvwxyz");

		UrlDetail urlDetail = new UrlDetail();
		urlDetail.setTargetUrl(targetUrl);
		urlDetail.setShortUrl(shortUrl);
		urlRepo.save(urlDetail);
		LOGGER.info("urlDetail = " + urlDetail);

		return shortUrl;
	}

	@Transactional
	public String fetchTargetUrl(String shortUrl) {
		UrlDetail urlDetail = urlRepo.findByShortUrl(shortUrl);
		LOGGER.info("urlDetail = " + urlDetail);
		if (urlDetail == null) {
			return null;
		}
		urlRepo.updateRedirectCount(urlDetail.getId());
		return urlDetail.getTargetUrl();
	}
}
