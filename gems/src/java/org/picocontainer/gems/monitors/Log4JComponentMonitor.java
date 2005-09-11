/*****************************************************************************
 * Copyright (C) PicoContainer Organization. All rights reserved.            *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by Paul Hammant                                             *
 *****************************************************************************/

package org.picocontainer.gems.monitors;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Member;

import org.picocontainer.monitors.AbstractComponentMonitor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;


/**
 * A {@link org.picocontainer.ComponentMonitor} which writes to a Log4J {@link org.apache.log4j.Logger} instance.
 *
 * @author Paul Hammant
 * @version $Revision: $
 */
public class Log4JComponentMonitor extends AbstractComponentMonitor implements Serializable {

    private Logger logger;

    /**
     * Creates a Log4JComponentMonitor using the Log4JComponentMonitor class instance.
     */
    public Log4JComponentMonitor() {
        this(Log4JComponentMonitor.class);
    }

    /**
     * Creates a Log4JComponentMonitor with a given Logger instance class. The class name is used to retrieve the
     * Logger instance.
     *
     * @param loggerClass the class of the Logger
     */
    public Log4JComponentMonitor(Class loggerClass) {
        this(loggerClass.getName());
    }

    /**
     * Creates a Log4JComponentMonitor with a given Logger instance name. It uses the
     * {@link org.apache.log4j.LogManager LogManager} to create the Logger instance.
     *
     * @param loggerName the name of the Log
     */
    public Log4JComponentMonitor(String loggerName) {
        this(LogManager.getLogger(loggerName));
    }

    /**
     * Creates a Log4JComponentMonitor with a given Logger instance
     *
     * @param logger the Logger to write to
     */
    public Log4JComponentMonitor(Logger logger) {
        this.logger = logger;
    }

    public void instantiating(Constructor constructor) {
        Logger logger = getLogger(constructor);
        if (logger.isDebugEnabled()) {
            logger.debug(format(INSTANTIATING, new Object[]{constructor}));
        }
    }

    public void instantiated(Constructor constructor, long duration) {
        Logger logger = getLogger(constructor);
        if (logger.isDebugEnabled()) {
            logger.debug(format(INSTANTIATED, new Object[]{constructor, new Long(duration)}));
        }
    }

    public void instantiationFailed(Constructor constructor, Exception e) {
        Logger logger = getLogger(constructor);
        if (logger.isEnabledFor(Priority.WARN)) {
            logger.warn(format(INSTANTIATION_FAILED, new Object[]{constructor, e.getMessage()}),e);
        }
    }

    public void invoking(Method method, Object instance) {
        Logger logger = getLogger(method);
        if (logger.isDebugEnabled()) {
            logger.debug(format(INVOKING, new Object[]{method, instance}));
        }
    }

    public void invoked(Method method, Object instance, long duration) {
        Logger logger = getLogger(method);
        if (logger.isDebugEnabled()) {
            logger.debug(format(INVOKED, new Object[]{method, instance, new Long(duration)}));
        }
    }

    public void invocationFailed(Method method, Object instance, Exception e) {
        Logger logger = getLogger(method);
        if (logger.isEnabledFor(Priority.WARN)) {
            logger.warn(format(INVOCATION_FAILED, new Object[]{method, instance, e.getMessage()}),e);
        }
    }

    protected Logger getLogger(Member member) {
        return logger;
    }

}
