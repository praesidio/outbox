package io.praesidio.outbox.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Sets {

    @SafeVarargs
    public static <T> Set<T> of(T... args) {
        return new HashSet<>(Arrays.asList(args));
    }
}
