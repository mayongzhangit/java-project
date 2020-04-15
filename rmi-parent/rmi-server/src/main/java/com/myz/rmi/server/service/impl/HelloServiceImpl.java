package com.myz.rmi.server.service.impl;

import com.myz.rmi.api.model.UserModel;
import com.myz.rmi.api.service.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 继承UnicastRemoteObject主要就是为了将自己export
 *     protected UnicastRemoteObject(int port) throws RemoteException
 *     {
 *         this.port = port;
 *         exportObject((Remote) this, port);
 *     }
 *
 * Created by yzMa on 2020/4/15.
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    public HelloServiceImpl() throws RemoteException {
        // 父类构造方法将自己export
        super();
    }

    @Override
    public String sayHi(String name) {
        return "hi "+name;
    }

    @Override
    public void save(UserModel userModel) {

        System.out.println("userModel="+userModel);
    }

    @Override
    public UserModel getById(Long id) {
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setName("name"+id);
        return userModel;
    }
}
