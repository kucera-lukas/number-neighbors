package com.lukaskucera.numberneighbors.entity;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@SuppressWarnings("NullAway.Init")
public class BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private Instant created;

  @UpdateTimestamp
  @Column(nullable = false)
  private Instant modified;

  public Long getId() {
    return id;
  }

  public Instant getCreated() {
    return created;
  }

  public Instant getModified() {
    return modified;
  }
}
