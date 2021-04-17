package ro.polak.urlshortener.support;

import java.io.Serializable;
import java.util.Properties;
import org.hashids.Hashids;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

public class HashidsSequenceGenerator extends SequenceStyleGenerator {

  // TODO Make it configurable
  /**
   * Salt MUST NOT be changed throughout the lifecycle of the application as it will mess up with the
   * previously stored IDs.
   */
  private static final Hashids HASHIDS = new Hashids("jd98ddshfads32");

  @Override
  public Serializable generate(SharedSessionContractImplementor session,
                               Object object) throws HibernateException {

    long id = (Long) super.generate(session, object);

    return HASHIDS.encode(id);
  }

  /**
   * Overwriting LongType.INSTANCE allows using underlying LONG sequence to generate text IDs.
   */
  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
    super.configure(LongType.INSTANCE, params, serviceRegistry);
  }
}
