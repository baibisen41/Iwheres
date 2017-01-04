package com.bbs.iwhere.model;

/**
 * Created by beasley on 2016/11/29.
 */

public class Location {

    private int IlocationStatus;  //是否打开定位开关
    private String Ilongitude;  //经度
    private String Ilatitude;    //纬度
    private String Idetiallocation;  //详细地址
    private String Imaplocation;  //地图信息
    private int Iflag;  //判断是我的定位还是好友定位

    public int getIlocationStatus() {
        return IlocationStatus;
    }

    public void setIlocationStatus(int ilocationStatus) {
        IlocationStatus = ilocationStatus;
    }

    public String getIlongitude() {
        return Ilongitude;
    }

    public void setIlongitude(String ilongitude) {
        Ilongitude = ilongitude;
    }

    public String getIlatitude() {
        return Ilatitude;
    }

    public void setIlatitude(String ilatitude) {
        Ilatitude = ilatitude;
    }

    public String getIdetiallocation() {
        return Idetiallocation;
    }

    public void setIdetiallocation(String idetiallocation) {
        Idetiallocation = idetiallocation;
    }

    public String getImaplocation() {
        return Imaplocation;
    }

    public void setImaplocation(String imaplocation) {
        Imaplocation = imaplocation;
    }

    public int getIflag() {
        return Iflag;
    }

    public void setIflag(int iflag) {
        Iflag = iflag;
    }
}
