package ro.polak.urlshortener.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ro.polak.urlshortener.support.HashidSequenceGenerated;

@Entity
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UrlShortcut {

  @Id
  @HashidSequenceGenerated(
      name = "url_shortcut_sequence",
      startWith = 1,
      incrementBy = 1,
      salt = "dfga083hf-SOME-RANDOM-VALUE")
  private String textId;

  @NotNull @URL private String destinationUrl;

  @CreatedDate private OffsetDateTime createdAt;

  /** Identifies user */
  private long createdBy;
}
