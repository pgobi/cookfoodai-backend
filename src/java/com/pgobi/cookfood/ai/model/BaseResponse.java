package com.pgobi.cookfood.ai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private String status = "success";
    private T data = null;
    private String error = null;

    public BaseResponse(boolean status) {
        setStatus(status);
    }

    public void setStatus(boolean status) {
        this.status = status ? "success" : "error";
    }

}
