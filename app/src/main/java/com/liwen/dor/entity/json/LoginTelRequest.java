package com.liwen.dor.entity.json;

import com.liwen.dor.entity.base.UserAndEquipment;
import com.liwen.dor.entity.base.UserBase;

import java.util.List;

/**
 * 用户手机号登陆
 * Created by chenly on 2017/11/4.
 */

public class LoginTelRequest extends BaseRequest{
    private UserBase UserBase;
    private List<UserAndEquipment> UserAndEquipment;

    public com.liwen.dor.entity.base.UserBase getUserBase() {
        return UserBase;
    }

    public void setUserBase(com.liwen.dor.entity.base.UserBase userBase) {
        UserBase = userBase;
    }

    public List<com.liwen.dor.entity.base.UserAndEquipment> getUserAndEquipment() {
        return UserAndEquipment;
    }

    public void setUserAndEquipment(List<com.liwen.dor.entity.base.UserAndEquipment> userAndEquipment) {
        UserAndEquipment = userAndEquipment;
    }
}
