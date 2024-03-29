package com.pokeapigo.core.common.util;

import com.pokeapigo.core.exception.InvalidColumnNameException;
import com.pokeapigo.core.exception.OtherDataAccessApiException;
import org.apache.commons.lang3.StringUtils;
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

    /**
     * Applies English locale if null is provided
     *
     * @param locale object to be checked
     * @return provided or English locale
     */
    public static Locale setEngLocaleIfNull(Locale locale) {
        if (locale == null) {
            return Locale.ENGLISH;
        }
        return locale;
    }

    /**
     * Applies max page size if it's exceeded
     *
     * @param pageable    to be checked if exceeded max page size
     * @param maxPageSize max size value
     * @return pageable with new or existing max page size
     */
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

    /**
     * Gets pageable and its properties and applies ascending sort by the given properties if no sorting is already
     * provided
     *
     * @param pageable          to apply sorting
     * @param defaultProperties sorting properties
     * @return pageable with new or existing sorting method
     */
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

    /**
     * Returns correct exception when something goes wrong while sorting by custom columns
     *
     * @param e             exception containing details of sorting issue
     * @param locale        used to return error description in correct supported language
     * @param messageSource
     * @return correct sorting exception
     */
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

    /**
     * Splits email into two parts around '@' character.
     * After that first part is truncated and '***@{secondPart}' is added.
     *
     * @param plainEmail email in plain text to be masked
     * @return masked email in the form of exa***@example.com
     */
    public static String maskEmail(String plainEmail) {
        final String[] emailParts = plainEmail.split("@");
        final String truncatedEmail = StringUtils.truncate(emailParts[0], 3);
        return truncatedEmail.concat("***@").concat(emailParts[1]);
    }

    private PokeApiUtils() {
    }
}
