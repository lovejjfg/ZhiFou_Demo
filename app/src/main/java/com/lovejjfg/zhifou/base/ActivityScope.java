package com.lovejjfg.zhifou.base;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Joe on 2016/12/22.
 * Email lovejjfg@gmail.com
 */

@Scope
@Retention(RUNTIME)
public @interface ActivityScope {
}
