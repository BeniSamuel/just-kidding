package com.justkidding.www.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse <T> {
    public boolean success;
    public String message;
    public T data;
}
