package ro.polak.urlshortener.support;

import java.io.Serializable;
import java.util.Properties;
import org.hashids.Hashids;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

/**
 * The generator combines the of database sequences (fast, reliable, single hit to the database, scalable)
 * with the text-ids that are hard to reverse engineer (and predict the next value).
 * <p>
 * The generator allows to avoid integer/long ids in your database schema at all.
 */
public class HashidsSequenceGenerator extends SequenceStyleGenerator {

  private static final String SALT_PROPERTY_NAME = "salt";
  private static final String DEFAULT_SALT = "DO-CHANGE-ME";

  /**
   * Salt MUST NOT be changed throughout the lifecycle of the application as it will mess up with the
   * previously stored IDs.
   */
  private static Hashids hashids;

  @Override
  public Serializable generate(SharedSessionContractImplementor session,
                               Object object) throws HibernateException {
    long id = (Long) super.generate(session, object);
    return hashids.encode(id);
  }

  /**
   * Overwriting LongType.INSTANCE allows using underlying LONG sequence to generate text IDs.
   */
  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
    super.configure(LongType.INSTANCE, params, serviceRegistry);

    String salt = ConfigurationHelper.getString(SALT_PROPERTY_NAME,
        params, DEFAULT_SALT);
    hashids = new Hashids(salt);
  }
}
