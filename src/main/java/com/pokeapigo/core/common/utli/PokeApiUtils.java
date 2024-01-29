package com.pokeapigo.core.common.utli;

import com.pokeapigo.core.exception.InvalidColumnNameException;
import com.pokeapigo.core.exception.OtherDataAccessApiException;
import org.hibernate.query.SemanticException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Locale;

public class PokeApiUtils {

    private static final Logger logger = LoggerFactory.getLogger(PokeApiUtils.class);

    public static Locale setEngLocaleIfNull(Locale locale) {
        if (locale == null) {
            return Locale.ENGLISH;
        }
        return locale;
    }

    public static Pageable ensureMaxPageSize(Pageable pageable, int maxPageSize) {
        if (pageable.getPageSize() <= maxPageSize) {
            return pageable;
        }

        logger.warn("Tried to get page of {} Pokemons", pageable.getPageSize());
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                maxPageSize,
                pageable.getSortOr(Sort.unsorted())
        );
        logger.info("Ensured max page size");

        return pageable;
    }

    public static Pageable applyDefaultSortingIfNone(Pageable pageable, String... defaultProperties) {
        Sort sort = pageable.getSortOr(
                Sort.by(Sort.Direction.ASC, defaultProperties)
        );

        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );
    }

    public static RuntimeException getCorrectSortingException(InvalidDataAccessApiUsageException e, Locale locale,
                                                              MessageSource messageSource) {
        if (e.getRootCause() instanceof SemanticException) {
            final String message = messageSource.getMessage(
                    "global.sort.invalidColumnName", null, locale
            );
            return new InvalidColumnNameException(message);
        }

        final String message = messageSource.getMessage(
                "global.sort.invalidData", new Object[]{e.getMessage()}, locale
        );
        return new OtherDataAccessApiException(message, e);
    }

    private PokeApiUtils() {
    }
}
