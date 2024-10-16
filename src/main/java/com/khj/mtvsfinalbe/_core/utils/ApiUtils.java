package com.khj.mtvsfinalbe._core.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response);
    }
    public static <T> ApiResult<T> error(T response) {
        return new ApiResult<>(false, response);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class ApiResult<T> {
        private final boolean success;
        private final T response;
    }

}
