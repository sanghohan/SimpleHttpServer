package com.config;


import java.util.ArrayList;

/**
 * Created by 1002074 on 2016. 4. 28..
 */
public class Config {

    private int serverPort;
    private ArrayList<VirtualHostInfo> virtualHostInfo;

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public ArrayList<VirtualHostInfo> getVirtualHostInfo() {
        return virtualHostInfo;
    }

    public void setVirtualHostInfo(ArrayList<VirtualHostInfo> virtualHostInfo) {
        this.virtualHostInfo = virtualHostInfo;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();
        sb.append("Config { serverPort=" + serverPort + ", virtualHostInfo { ");

        for(VirtualHostInfo info : virtualHostInfo) {
            sb.append("hostName=" + info.getHostName());
            sb.append(", docRoot=" + info.getDocRoot() +", ");

        }

        sb.append("}");

        return sb.toString();
    }
}
