/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.lovejjfg.zhifou.presenters;

import android.os.Bundle;

import com.lovejjfg.zhifou.data.model.DailyStories;
import com.lovejjfg.zhifou.ui.recycleview.StoriesRecycleAdapter;
import com.lovejjfg.zhifou.view.LoadingView;

import java.util.List;

public interface ListPresenter  {
    String SAVED_ITEMS = "SAVEDINSTANCEITEMS";
    String CURRENT_DATE = "CURRENT_DATE";

    void onItemClicked(android.view.View itemView, android.view.View image, int id);

    void onRefresh();

    void onLoading();

    void onLoadMore(String mDate);

    void onSaveInstanceState(Bundle outState);

    interface View extends LoadingView {

        void onLoadMore(DailyStories stories);
        void onLoad(DailyStories stories);

        void onLoadError(String errorCode);


        void onReLoadItems(List<StoriesRecycleAdapter.Item> items);

        void onReSetDate(String date);
    }
}
