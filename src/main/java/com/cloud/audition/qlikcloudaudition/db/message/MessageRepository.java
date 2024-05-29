package com.cloud.audition.qlikcloudaudition.db.message;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, UUID> {}
