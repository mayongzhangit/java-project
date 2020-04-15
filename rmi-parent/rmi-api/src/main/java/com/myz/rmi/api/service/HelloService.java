package com.myz.rmi.api.service;

import com.myz.rmi.api.model.UserModel;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 1.接口要继承{@link Remote}
 * 2.方法要抛出{@link RemoteException}
 * Created by yzMa on 2020/4/15.
 */
public interface HelloService extends Remote {

    String sayHi(String name) throws RemoteException;

    void save(UserModel userModel) throws RemoteException;

    UserModel getById(Long id) throws RemoteException;
}
