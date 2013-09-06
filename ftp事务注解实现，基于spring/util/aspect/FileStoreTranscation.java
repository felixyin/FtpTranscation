package com.app.platform.util.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: yinbin
 * Date: 12-11-26
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileStoreTranscation {

}
