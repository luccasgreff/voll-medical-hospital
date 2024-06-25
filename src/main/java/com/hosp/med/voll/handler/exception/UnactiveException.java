package com.hosp.med.voll.handler.exception;

import lombok.Getter;

@Getter
public class UnactiveException extends Exception {

    private String message = "The record found is unactive, unable to process request.";

}
