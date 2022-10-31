package com.MoP2022.Team10.mapping.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@Getter
@Setter
public class ResDefault {
    public String status;
    public Object data;
    @JsonIgnore
    public HttpStatus statusCode;
    @JsonIgnore
    public HttpHeaders headers;

    public ResDefault() {
        this.status = "success";
        this.data = new ArrayList<String>();
        this.statusCode = HttpStatus.OK;
        this.headers = new HttpHeaders();
    }

    public ResDefault(Object data) {
        this.data = data;
    }

    public ResDefault(Object data, String status) {
        this.data = data;
        this.status = status;
    }
}
