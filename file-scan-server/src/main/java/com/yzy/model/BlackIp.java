package com.yzy.model;

import java.util.Date;

/**
 * ip黑名单
 */
public class BlackIp {
    private String ip;
    private String mac;
    private Integer id;
    //访问时间
    private Date visitTime;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "BlackIp{" +
                "ip='" + ip + '\'' +
                ", mac='" + mac + '\'' +
                ", id=" + id +
                ", visitTime=" + visitTime +
                '}';
    }
}
