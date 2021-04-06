package com.rajpaswan.curly.rest;

import com.rajpaswan.curly.domain.ShortenUrlRequest;
import com.rajpaswan.curly.service.LinkShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.logging.Logger;

@RestController
public class CurlyRestController {

	private final static Logger LOGGER = Logger.getLogger(CurlyRestController.class.getName());
	private final LinkShortenerService linkShortenerService;

	@Autowired
	public CurlyRestController(LinkShortenerService linkShortenerService) {
		this.linkShortenerService = linkShortenerService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/")
	public ResponseEntity<String> makeShortUrl(@RequestBody ShortenUrlRequest request,
											   HttpServletRequest servletRequest) {
		LOGGER.info("request = " + request);
		String shortUrl = linkShortenerService.makeShortUrl(request.getTargetUrl());
		String fullUrl = servletRequest.getRequestURL() + shortUrl;
		return ResponseEntity.status(HttpStatus.CREATED).body(fullUrl);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{short_url}")
	public ResponseEntity<String> fetchTargetUrl(@PathVariable("short_url") String shortUrl) {
		LOGGER.info("short_url = " + shortUrl);

		String targetUrl = linkShortenerService.fetchTargetUrl(shortUrl);
		if (targetUrl == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found!");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(targetUrl));
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
}
