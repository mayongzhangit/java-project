package com.myz.rmi.server;

import com.myz.rmi.server.service.impl.HelloServiceImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by yzMa on 2020/4/15.
 */
public class RmiServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {


        //方式一 直接绑定
//        Registry registry = LocateRegistry.createRegistry(1099);
//        registry.bind("helloServerImpl",new HelloServiceImpl());

        // 方式二 Naming绑定
        //绑定的URL标准格式为：rmi://host:port/name
        LocateRegistry.createRegistry(1099);
        Naming.bind("rmi://127.0.0.1:1099/helloServerImpl", new HelloServiceImpl());

        System.out.println("RMIServer启动成功");

    }
}
