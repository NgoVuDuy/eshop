package com.nvd.electroshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionMessage {

    private Integer status;
    private String message;
}
