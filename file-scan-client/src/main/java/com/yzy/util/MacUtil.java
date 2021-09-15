package com.yzy.util;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MacUtil {
    /***因为一台机器不一定只有一个网卡呀，所以返回的是数组是很合理的***/
    public static List<String> getMacList() throws Exception {
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tmpMacList=new ArrayList<>();
        while(en.hasMoreElements()){
            NetworkInterface iface = en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for(InterfaceAddress addr : addrs) {
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if(network==null){continue;}
                byte[] mac = network.getHardwareAddress();
                if(mac==null){continue;}
                sb.delete( 0, sb.length() );
                for (int i = 0; i < mac.length; i++) {sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));}
                tmpMacList.add(sb.toString());
            }        }
        if(tmpMacList.size()<=0){return tmpMacList;}
        /***去重，别忘了同一个网卡的ipv4,ipv6得到的mac都是一样的，肯定有重复，下面这段代码是。。流式处理***/
        List<String> unique = tmpMacList.stream().distinct().collect(Collectors.toList());
        return unique;
    }

    /**
     * 随机获取一个mac地址(字母排序第一的)
     * @return
     */
    public static String getMacAddr(){
        List<String> macList = null;
        try {
            macList = getMacList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(macList!=null && macList.size()>0){
             macList.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
             StringBuilder sb=new StringBuilder();
             for(int i=0,size=macList.size();i<size;i++){
                 sb.append(macList.get(i));
                 if(i!=size-1){
                     sb.append(";");
                 }
             }
            return  sb.toString();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        List<String> macList = getMacList();
        if(macList!=null){
            for(String mac:macList){
                System.out.println(mac);
            }
        }
    }

}
