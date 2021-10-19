package com.germanium.lms.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Arjun Krishna Shenoy
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class SystemProperties {

	@CreatedBy
	@Column(name = "SYS_CREATED_BY", nullable = false, updatable = false)
	protected String createdBy;

	@CreatedDate
	@Column(name = "SYS_CREATION_DATE", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date creationDate;

	@LastModifiedBy
	@Column(name = "SYS_LAST_MODIFIED_BY", nullable = false, updatable = true)
	protected String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "SYS_LAST_MODIFIED_DATE", nullable = false, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedDate;

}
