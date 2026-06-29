package com.smc.smccloud.core.model.wrapper;

import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 */
public class WrapMapper {

    /**
     * Instantiates a new wrap mapper.
     */
    private WrapMapper() {
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param status  the status
     * @param message the message
     * @param o       the o
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(int status, String message, E o) {
        return new Wrapper<>(status, message, o);
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param status  the status
     * @param message the message
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(int status, String message) {
        return wrap(status, message, null);
    }

    /**
     * Wrap.
     *
     * @param <E>    the element type
     * @param status the status
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(int status) {
        return wrap(status, null);
    }

    /**
     * Wrap.
     *
     * @param <E> the element type
     * @param e   the e
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> wrap(Exception e) {
        return new Wrapper<>(Wrapper.ERROR_CODE, e.getMessage());
    }

    /**
     * Un wrapper.
     *
     * @param <E>     the element type
     * @param wrapper the wrapper
     *
     * @return the e
     */
    public static <E> E unWrap(Wrapper<E> wrapper) {
        return wrapper.getResult();
    }

    /**
     * Wrap ERROR. status=100
     *
     * @param <E> the element type
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> illegalArgument() {
        return wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
    }

    /**
     * Wrap ERROR. status=500
     *
     * @param <E> the element type
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> error() {
        return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> error(String message) {
        return wrap(Wrapper.ERROR_CODE, StringUtils.isBlank(message) ? Wrapper.ERROR_MESSAGE : message);
    }

    /**
     * Wrap SUCCESS. status=200
     *
     * @param <E> the element type
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> ok() {
        return new Wrapper<>();
    }

    /**
     * Ok wrapper.
     *
     * @param <E> the type parameter
     * @param o   the o
     *
     * @return the wrapper
     */
    public static <E> Wrapper<E> ok(E o) {
        return new Wrapper<>(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, o);
    }

    public static <E> Wrapper<E> ok(E o, String message) {
        return new Wrapper<>(Wrapper.SUCCESS_CODE, message, o);
    }

    public static <E> Wrapper<E> okMsg(String message) {
        return new Wrapper<>(Wrapper.SUCCESS_CODE, message);
    }
}
