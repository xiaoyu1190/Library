/*
 * Copyright  2017  zengp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wbxm.lib.recyclerwheelpicker;

import android.annotation.SuppressLint;

import com.wbxm.lib.recyclerwheelpicker.bean.Data;
import com.wbxm.lib.recyclerwheelpicker.dialog.WheelPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by zengp on 2017/11/29.
 */

@SuppressLint("ValidFragment")
public class DateDoubleWheelPicker extends DoubleWheelPicker {

    private DateBuilder dateBuilder;
    private Calendar calendar;

    protected DateDoubleWheelPicker(DateBuilder builder) {
        super(builder);
        builder.dataRelated = true;
        dateBuilder = builder;
    }

    public static DateBuilder instance() {
        return new DateBuilder(DateDoubleWheelPicker.class);
    }

    @Override
    protected List<Data> parseData() {
        // create data
        List<Data> datas = new ArrayList<>();
        calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        int[] limit = dateBuilder.limit;
        int maxYear = currentYear, maxMonth = currentMonth;
        if (null != limit) {
            if (limit.length > 0) maxYear = limit[0];
            if (limit.length > 1) maxMonth = limit[1];
            maxMonth = Math.max(1, Math.min(maxMonth, 12));
        }

        if (builder.isAll) {
            Data data = new Data();
            data.id = -1;
            data.data = "不限";
            data.items = new ArrayList<>();
            data.items.add(new Data());
            datas.add(data);
        }
        int startYear = maxYear;
        int endYear = currentYear - 100;
        if (startYear < endYear) startYear = endYear;
        for (int year = startYear; year >= endYear; year--) {
            Data data_year = new Data();
            data_year.data = year + "";
            data_year.id = year;
            // month
            List<Data> months = new ArrayList<>();
            int startMonth = year == maxYear ? maxMonth : 12;
            for (int month = startMonth; month >= 1; month--) {
                Data data_month = new Data();
                data_month.data = (month < 10 ? "0" : "") + month;
                data_month.id = month;
                months.add(data_month);
            }
            data_year.items = months;
            datas.add(data_year);
        }
        return datas;
    }

    public static class DateBuilder extends Builder {

        public int[] limit;

        public DateBuilder(Class clazz) {
            super(clazz);
        }

        public DateBuilder limit(int... limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public WheelPicker build() {
            return new DateDoubleWheelPicker(this);
        }
    }
}
