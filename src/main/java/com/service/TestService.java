package com.service;

import com.exception.ServiceException;

public interface TestService {

    void testEverything(String jwt) throws ServiceException;

}