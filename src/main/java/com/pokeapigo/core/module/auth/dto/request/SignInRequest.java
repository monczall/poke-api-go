package com.pokeapigo.core.module.auth.dto.request;

import com.pokeapigo.core.common.utli.constants.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignInRequest(
        @NotBlank(message = "{auth.email.notBlank}")
        @Size(min = 10, message = "{auth.email.rangeMin}")
        @Size(max = 320, message = "{auth.email.rangeMax}")
        @Email(
                regexp = Constants.EMAIL_REGEXP,
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "{auth.email.wrongFormat}"
        )
        String email,

        @NotBlank(message = "{auth.password.notBlank}")
        @Size(min = 6, message = "{auth.password.rangeMin}")
        @Size(max = 255, message = "{auth.password.rangeMax}")
        String password
) {
}
