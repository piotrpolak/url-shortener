package ro.polak.urlshortener.domain.model;

import java.net.URI;
import java.time.OffsetDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UrlShortcut {

  @Id
  @GeneratedValue(generator = "hashids-sequence-generator")
  @GenericGenerator(
      name = "hashids-sequence-generator",
      strategy = "ro.polak.urlshortener.support.HashidsSequenceGenerator",
      parameters = {
          @Parameter(name = "sequence_name", value = "url_shortcut_sequence"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1"),
          @Parameter(name = "salt", value = "dfga083hf-SOME-RANDOM-VALUE")
      }
  )
  private String textId;

  private URI destinationUrl;

  @CreatedDate
  private OffsetDateTime createdAt;

  /**
   * Identifies user
   */
  private long createdBy;
}
