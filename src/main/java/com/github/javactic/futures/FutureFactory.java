package com.github.javactic.futures;

import java.util.function.Function;
import java.util.function.Supplier;

import com.github.javactic.Bad;
import com.github.javactic.Or;

public class FutureFactory<B> {

    private final Function<? super Exception, ? extends B> converter;

    public FutureFactory(Function<? super Exception, ? extends B> exceptionConverter) {
        this.converter = exceptionConverter;
    }

    @SuppressWarnings("unchecked")
    public <G> OrFuture<G,B> future(Supplier<? extends Or<G,? extends B>> supp) {
        return OrFuture.of(() -> {
            try {
                return (Or<G, B>) supp.get();
            } catch (Exception e) {
                return Bad.of(converter.apply(e));
            }            
        });
    }
    
    public static final FutureFactory<String> OF_EXCEPTION_MESSAGE = new FutureFactory<>(ex -> ex.getMessage());
}