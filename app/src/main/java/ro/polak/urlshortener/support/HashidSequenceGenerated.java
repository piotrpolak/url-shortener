package ro.polak.urlshortener.support;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.hibernate.annotations.IdGeneratorType;

@IdGeneratorType(HashidsSequenceGenerator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface HashidSequenceGenerated {
  String name();

  int startWith() default 1;

  int incrementBy() default 50;

  String salt();
}
