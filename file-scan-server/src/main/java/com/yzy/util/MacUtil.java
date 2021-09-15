package com.yzy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
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
            for(int i=0,size=macList.size();i<size;i++) {
                sb.append(macList.get(i));
                if (i != size - 1) {
                    sb.append(";");
                }
            }
            return  sb.toString();
        }
        return null;
    }
    /**
     * 通过访问的Ip地址得到mac地址(访问者非本机)
     * @param ip
     * @return mac
     */
    public static  String getMacByIp(String ip){
        String macAddress = "";
        try {
            java.lang.Process process = Runtime.getRuntime().exec("nbtstat -A "+ip);
            InputStreamReader ir = new InputStreamReader(process.getInputStream(),"utf-8");
            LineNumberReader input = new LineNumberReader(ir);
            String str = "";
            while ((str=input.readLine())!=null){
                str = str.toUpperCase();
                if(str.indexOf("MAC ADDRESS")>1){
                    int start = str.indexOf("=");
                    macAddress = str.substring(start+1,str.length()).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  macAddress;
    }

    public static String getLocalMacAddr(String ip) {
        String macAddress = "";
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                //貌似此方法需要JDK1.6。
                byte[] mac = new byte[0];

                mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();

                //下面代码是把mac地址拼装成String
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    if (i != 0) {
                        sb.append("-");
                    }
                    //mac[i] & 0xFF 是为了把byte转化为正整数
                    String s = Integer.toHexString(mac[i] & 0xFF);
                    sb.append(s.length() == 1 ? 0 + s : s);
                }
                //把字符串所有小写字母改为大写成为正规的mac地址并返回
                macAddress = sb.toString().trim().toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return macAddress;
    }
    public static  String getMacByIp2(String ip){
        String macAddress = "";
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "localhost".equals(ip) ){
            return getLocalMacAddr(ip);
        }
        try {
            Process p = Runtime.getRuntime().exec("arp -A " + ip);
            InputStreamReader isr = new InputStreamReader(p.getInputStream(),"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line != null) {
                    int index = line.indexOf(ip);
                    if (index != -1) {
                        macAddress = line.substring(index + ip.length() + 10,index + ip.length() + 27).trim().toUpperCase();
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return macAddress;
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
