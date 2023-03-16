package ro.polak.urlshortener.support;

import org.hashids.Hashids;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.hibernate.type.descriptor.java.LongJavaType;
import org.hibernate.type.descriptor.jdbc.BigIntJdbcType;
import org.hibernate.type.internal.BasicTypeImpl;

import java.io.Serializable;
import java.util.Properties;

/**
 * The generator combines the of database sequences (fast, reliable, single hit to the database, scalable)
 * with the text-ids that are hard to reverse engineer (and predict the next value).
 * <p>
 * The generator allows to avoid integer/long ids in your database schema at all.
 */
public class HashidsSequenceGenerator extends SequenceStyleGenerator {

  private static final String SALT_PROPERTY_NAME = "salt";
  private static final String DEFAULT_SALT = "DO-CHANGE-ME";
  private static final BasicTypeImpl<Long> LONG_TYPE = new BasicTypeImpl<>(LongJavaType.INSTANCE, BigIntJdbcType.INSTANCE);

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
   * Overwriting Long type allows using underlying LONG sequence to generate text IDs.
   */
  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
    super.configure(LONG_TYPE, params, serviceRegistry);

    String salt = ConfigurationHelper.getString(SALT_PROPERTY_NAME,
        params, DEFAULT_SALT);
    hashids = new Hashids(salt);
  }
}
