package com.nvd.electroshop.dto.response;

import com.nvd.electroshop.entity.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T>  {

    private int status;
    private T result;

    public ApiResponse(List<Wishlist> userWishlists) {
    }
}
