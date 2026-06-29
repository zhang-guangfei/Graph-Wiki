
package com.smc.smccloud.core.model.wrapper;


import com.smc.smccloud.core.model.page.PageUtil;

/**
 *
 * @date Nov 15, 2019 12:52:47 PM
 * @description
 */
public class PageWrapMapper {

    /**
     * Instantiates a new page wrap mapper.
     */
    private PageWrapMapper() {
    }

    private static <E> PageWrapper<E> wrap(int status, String message, E o, PageUtil pageUtil) {
        return new PageWrapper<E>(status, message, o, pageUtil);
    }

    /**
     * Wrap data with default status=200.
     *
     * @param <E>      the type parameter
     * @param o        the o
     * @param pageUtil the page util
     *
     * @return the page wrapper
     */
    public static <E> PageWrapper<E> wrap(E o, PageUtil pageUtil) {
        return wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, o, pageUtil);
    }

    /**
     * Wrap.
     *
     * @param <E>     the type parameter
     * @param status  the status
     * @param message the message
     *
     * @return the page wrapper
     */
    public static <E> PageWrapper<E> wrap(int status, String message) {
        return wrap(status, message, null, null);
    }

    /**
     * Wrap.
     *
     * @param <E>    the type parameter
     * @param status the status
     *
     * @return the page wrapper
     */
    public static <E> PageWrapper<E> wrap(int status) {
        return wrap(status, null, null, null);
    }

    /**
     * Wrap.
     *
     * @param <E> the type parameter
     * @param e   the e
     *
     * @return the page wrapper
     */
    public static <E> PageWrapper<E> wrap(Exception e) {
        return new PageWrapper<E>(Wrapper.ERROR_CODE, e.getMessage(), null, null);
    }

    /**
     * Un wrapper.
     *
     * @param <E>     the type parameter
     * @param wrapper the wrapper
     *
     * @return the e
     */
    public static <E> E unWrap(PageWrapper<E> wrapper) {
        return wrapper.getResult();
    }

    /**
     * Wrap ERROR. status=100
     *
     * @param <E> the type parameter
     *
     * @return the page wrapper
     */
    public static <E> PageWrapper<E> illegalArgument() {
        return wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, null, null);
    }

    /**
     * Wrap ERROR. status=500
     *
     * @param <E> the type parameter
     *
     * @return the page wrapper
     */
    public static <E> PageWrapper<E> error() {
        return wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE, null, null);
    }

    public static <E> PageWrapper<E> error(String msg) {
        return wrap(Wrapper.ERROR_CODE, msg, null, null);
    }

    /**
     * Wrap SUCCESS. status=200
     *
     * @param <E> the type parameter
     *
     * @return the page wrapper
     */
    public static <E> PageWrapper<E> ok() {
        return new PageWrapper<E>();
    }
}
