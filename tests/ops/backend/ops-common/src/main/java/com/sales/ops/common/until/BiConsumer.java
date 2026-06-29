package com.sales.ops.common.until;

import com.sales.ops.common.opsexception.OpsException;

import java.util.Objects;

@FunctionalInterface
public interface BiConsumer<T, U> {


    void accept(T t, U u) throws OpsException;

    default BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after) throws OpsException {
        Objects.requireNonNull(after);

        return (l, r) -> {
            accept(l, r);
            after.accept(l, r);
        };
    }
}