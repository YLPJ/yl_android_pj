package com.liwen.dor.entity.json;

import com.liwen.dor.entity.base.CityBase;

/**
 * Created by ldn on 2017/11/12.
 */

public class SetCityBaseRequest extends BaseRequest {
    private CityBase CityBase;

    public com.liwen.dor.entity.base.CityBase getCityBase() {
        return CityBase;
    }

    public void setCityBase(com.liwen.dor.entity.base.CityBase cityBase) {
        CityBase = cityBase;
    }
}
