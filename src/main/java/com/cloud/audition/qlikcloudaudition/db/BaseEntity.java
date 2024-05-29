package com.cloud.audition.qlikcloudaudition.db;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

  @Id
  @UuidGenerator
  @GeneratedValue(generator = "id_generator")
  @Column(name = "id", columnDefinition = "BIGINT")
  private UUID id;
}
