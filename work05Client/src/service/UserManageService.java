package service;

import javax.ejb.Remote;

@Remote
public interface UserManageService {
    int login(String account, String password);

}
