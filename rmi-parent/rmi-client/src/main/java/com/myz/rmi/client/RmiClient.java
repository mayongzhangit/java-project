package com.myz.rmi.client;

import com.myz.rmi.api.model.UserModel;
import com.myz.rmi.api.service.HelloService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by yzMa on 2020/4/15.
 */
public class RmiClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {


        //方式一 直接查找
//        Registry registry = LocateRegistry.getRegistry(1099);// get
//        HelloService helloService = (HelloService)registry.lookup("helloServerImpl");

        //方式二 Naming查找
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:1099/helloServerImpl");

        UserModel userModel = helloService.getById(1L);
        System.out.println("result="+userModel);
    }
}
