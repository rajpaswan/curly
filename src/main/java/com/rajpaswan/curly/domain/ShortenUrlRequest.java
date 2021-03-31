package com.rajpaswan.curly.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShortenUrlRequest {
	private String targetUrl;
}
