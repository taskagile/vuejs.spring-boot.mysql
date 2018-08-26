package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.UserService;
import com.taskagile.domain.application.commands.RegistrationCommand;
import com.taskagile.domain.model.user.RegistrationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Override
  public void register(RegistrationCommand command) throws RegistrationException {
    // TODO implement this
  }

}
