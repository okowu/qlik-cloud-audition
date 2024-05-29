package com.cloud.audition.qlikcloudaudition.db.message;

import com.cloud.audition.qlikcloudaudition.db.EntityIdGenerator;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "qlik_cloud_audition", name = "message")
public class Message {

  @Id
  @GenericGenerator(name = "id_generator", type = EntityIdGenerator.class)
  @GeneratedValue(generator = "id_generator")
  @Column(name = "id", columnDefinition = "UUID")
  private UUID id;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "DATE")
  private Instant createdDate;

  @UpdateTimestamp
  @Column(name = "updated_date", columnDefinition = "DATE")
  private Instant updatedDate;

  @Column(name = "value", columnDefinition = "TEXT")
  private String value;

  @Convert(converter = MessageAdditionalInfoConverter.class)
  @Column(name = "additional_information", columnDefinition = "TEXT")
  private MessageAdditionalInfo additionalInfo;

  public void setPalindrome(final boolean isPalindrome) {
    if (additionalInfo == null) {
      this.additionalInfo = new MessageAdditionalInfo();
    }

    additionalInfo.setPalindrome(isPalindrome);
  }
}
