package com.cloud.audition.qlikcloudaudition.db;

import java.util.UUID;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class EntityIdGenerator implements IdentifierGenerator {

  @Override
  public Object generate(final SharedSessionContractImplementor session, final Object object) {
    if (object instanceof BaseEntity baseEntity && baseEntity.getId() != null) {
      return baseEntity.getId();
    }
    return UUID.randomUUID();
  }
}
