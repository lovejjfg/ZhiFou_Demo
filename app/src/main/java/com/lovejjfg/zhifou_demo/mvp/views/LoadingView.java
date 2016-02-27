/*
 * Copyright (C) 2014 Jorge Castillo Pérez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lovejjfg.zhifou_demo.mvp.views;

/**
 * View behavior implemented to allow loading animations
 * *
 * Created by jorge on 24/01/15.
 */
public interface LoadingView {
    /**
     * 是否loading。
     */
    void isLoading(boolean isLoading);

    /**
     * 是否显示正在加载更多
     */
    void isLoadingMore(boolean isLoadingMore);
}
