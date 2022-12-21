//package com.joshua.qrmenu.models.repositories.util;
//
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SharedSessionContractImplementor;
//import org.hibernate.id.enhanced.SequenceStyleGenerator;
//
//import java.io.Serializable;
//import java.security.SecureRandom;
//import java.util.Random;
//
///**
// * This class generates ID's for the database
// */
//public class IdGenerator extends SequenceStyleGenerator {
//
//    private static final Random RANDOM = new SecureRandom();
//    private static final long JAVASCRIPT_MAX_SAFE = (1L << 53) -1;
//
//    /**
//     * Generates random Long ID's withing the range safe for the browser.
//     * The generated ID is nevel null.
//     *
//     * @return : a Random ID.
//     */
//    @Override
//    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
//        return (Math.abs(RANDOM.nextLong() % (JAVASCRIPT_MAX_SAFE - 1))) + 1;
//    }
//}
