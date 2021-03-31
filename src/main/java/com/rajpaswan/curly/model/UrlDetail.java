package com.rajpaswan.curly.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url_details")
@EntityListeners(AuditingEntityListener.class)
public class UrlDetail extends AuditModel {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "short_url", unique = true)
	private String shortUrl;

	@Column(name = "target_url")
	private String targetUrl;

	@Column(name = "expired_at")
	private LocalDateTime expiredAt;

	@Column(name = "redirect_count")
	@ColumnDefault("0")
	private Long redirectCount;
}
