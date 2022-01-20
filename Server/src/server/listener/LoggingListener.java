/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.listener;

import commonlib.domain.Employee;

/**
 *
 * @author ANA
 */
public interface LoggingListener {
    void loginHappened(Employee employee);
    void logoutHappened(Employee employee);
}
