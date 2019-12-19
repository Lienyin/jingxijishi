package com.jxxc.jingxijishi.http;

import java.io.Serializable;

/**
 * Created by feisher on 2018/1/26.
 */

public class HttpResult<T> implements Serializable {
    public T data;
    public int code;
    public String message;

}
