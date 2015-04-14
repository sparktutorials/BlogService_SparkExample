package me.tomassetti;

import java.util.UUID;

/**
 * A UUID generator.
 * 
 * @author ftomassetti
 * @since Mar 2015
 */
public interface UuidGenerator {

    /**
     * Each call should return a different UUID.
     */
    UUID generate();
}
