package com.liwen.dor.entity.base;

/**
 * 用户与设备对照表
 * Created by chenly on 2017/11/4.
 */

public class UserAndEquipment {
    private String userAndEquipmentId;
    private Equipment Equipment;
    private CityBase CityBase;
    private UserMemorandumSet UserMemorandumSet;

    public String getUserAndEquipmentId() {
        return userAndEquipmentId;
    }

    public void setUserAndEquipmentId(String userAndEquipmentId) {
        this.userAndEquipmentId = userAndEquipmentId;
    }

    public com.liwen.dor.entity.base.Equipment getEquipment() {
        return Equipment;
    }

    public void setEquipment(com.liwen.dor.entity.base.Equipment equipment) {
        Equipment = equipment;
    }

    public com.liwen.dor.entity.base.CityBase getCityBase() {
        return CityBase;
    }

    public void setCityBase(com.liwen.dor.entity.base.CityBase cityBase) {
        CityBase = cityBase;
    }

    public UserMemorandumSet getUserMemorandumSet() {
        return UserMemorandumSet;
    }

    public void setUserMemorandumSet(UserMemorandumSet userMemorandumSet) {
        UserMemorandumSet = userMemorandumSet;
    }
}
