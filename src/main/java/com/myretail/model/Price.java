package com.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Price {

    Double value;
    String currencyCode;

    public Price() {
    }

    public Price(Double value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "CurrentPriceVO [value=" + value + ", currencyCode=" + currencyCode + "]";
    }

}
