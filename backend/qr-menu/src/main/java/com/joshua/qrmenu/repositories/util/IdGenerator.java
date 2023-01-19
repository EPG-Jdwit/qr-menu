package com.joshua.qrmenu.repositories.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Random;

/**
 * This class generates ID's for the database
 */
public class IdGenerator extends SequenceStyleGenerator {

    private static final Random RANDOM = new SecureRandom();
    private static final long JAVASCRIPT_MAX_SAFE = (1L << 53) -1;

    /**
     * Generates random Long ID's within a range that's safe for the browser.
     * The generated ID is never null.
     *
     * @return : a random ID.
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return (Math.abs(RANDOM.nextLong() % (JAVASCRIPT_MAX_SAFE - 1))) + 1;
    }
}
