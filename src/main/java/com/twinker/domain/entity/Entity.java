package com.twinker.domain.entity;

/**
 * Base class for all domain entities in the Twinker application.
 * Provides a common interface for entity identification.
 *
 * <p>
 * All domain entities must extend this class and implement
 * the getId method to provide a unique identifier.
 * </p>
 *
 * @author Twinker Development Team
 */
public abstract class Entity {
    /**
     * Gets the unique identifier of the entity.
     *
     * @return a string representing the unique identifier
     */
    public abstract String getId();
}
