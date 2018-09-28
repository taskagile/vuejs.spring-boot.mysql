package com.taskagile.domain.application.impl;

import com.taskagile.domain.application.commands.RegisterCommand;
import com.taskagile.domain.common.event.DomainEventPublisher;
import com.taskagile.domain.common.mail.MailManager;
import com.taskagile.domain.common.mail.MessageVariable;
import com.taskagile.domain.model.user.*;
import com.taskagile.domain.model.user.events.UserRegisteredEvent;
import com.taskagile.utils.IpAddress;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTests {

  private RegistrationManagement registrationManagementMock;
  private DomainEventPublisher domainEventPublisherMock;
  private MailManager mailManagerMock;
  private UserRepository userRepositoryMock;
  private UserServiceImpl instance;

  @Before
  public void setUp() {
    registrationManagementMock = mock(RegistrationManagement.class);
    domainEventPublisherMock = mock(DomainEventPublisher.class);
    mailManagerMock = mock(MailManager.class);
    userRepositoryMock = mock(UserRepository.class);
    instance = new UserServiceImpl(registrationManagementMock, domainEventPublisherMock,
      mailManagerMock, userRepositoryMock);
  }

  //-------------------------------------------
  // Method loadUserByUsername()
  //-------------------------------------------

  @Test
  public void loadUserByUsername_emptyUsername_shouldFail() {
    Exception exception = null;
    try {
      instance.loadUserByUsername("");
    } catch (Exception e) {
      exception = e;
    }
    assertNotNull(exception);
    assertTrue(exception instanceof UsernameNotFoundException);
    verify(userRepositoryMock, never()).findByUsername("");
    verify(userRepositoryMock, never()).findByEmailAddress("");
  }

  @Test
  public void loadUserByUsername_notExistUsername_shouldFail() {
    String notExistUsername = "NotExistUsername";
    when(userRepositoryMock.findByUsername(notExistUsername)).thenReturn(null);
    Exception exception = null;
    try {
      instance.loadUserByUsername(notExistUsername);
    } catch (Exception e) {
      exception = e;
    }
    assertNotNull(exception);
    assertTrue(exception instanceof UsernameNotFoundException);
    verify(userRepositoryMock).findByUsername(notExistUsername);
    verify(userRepositoryMock, never()).findByEmailAddress(notExistUsername);
  }

  @Test
  public void loadUserByUsername_existUsername_shouldSucceed() throws IllegalAccessException {
    String existUsername = "ExistUsername";
    User foundUser = User.create(existUsername, "user@taskagile.com", "Test", "User",
      "EncryptedPassword!");
    // Found user from the database should have id. And since no setter of
    // id is available in User, we have to write the value to it using reflection
    //
    // Besides creating an actual instance of User, we can also create a user
    // mock, like the following.
    // User mockUser = Mockito.mock(User.class);
    // when(mockUser.getUsername()).thenReturn(existUsername);
    // when(mockUser.getPassword()).thenReturn("EncryptedPassword!");
    // when(mockUser.getId()).thenReturn(1L);
    FieldUtils.writeField(foundUser, "id", 1L, true);
    when(userRepositoryMock.findByUsername(existUsername)).thenReturn(foundUser);
    Exception exception = null;
    UserDetails userDetails = null;
    try {
      userDetails = instance.loadUserByUsername(existUsername);
    } catch (Exception e) {
      exception = e;
    }
    assertNull(exception);
    verify(userRepositoryMock).findByUsername(existUsername);
    verify(userRepositoryMock, never()).findByEmailAddress(existUsername);
    assertNotNull(userDetails);
    assertEquals(existUsername, userDetails.getUsername());
    assertTrue(userDetails instanceof SimpleUser);
  }

  //-------------------------------------------
  // Method register()
  //-------------------------------------------

  @Test(expected = IllegalArgumentException.class)
  public void register_nullCommand_shouldFail() throws RegistrationException {
    instance.register(null);
  }

  @Test(expected = RegistrationException.class)
  public void register_existingUsername_shouldFail() throws RegistrationException {
    String username = "existing";
    String emailAddress = "sunny@taskagile.com";
    String password = "MyPassword!";
    String firstName = "Sunny";
    String lastName = "Hu";
    doThrow(UsernameExistsException.class).when(registrationManagementMock)
      .register(username, emailAddress, firstName, lastName, password);

    RegisterCommand command = new RegisterCommand(username, emailAddress, firstName, lastName, password);
    instance.register(command);
  }

  @Test(expected = RegistrationException.class)
  public void register_existingEmailAddress_shouldFail() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "existing@taskagile.com";
    String password = "MyPassword!";
    String firstName = "Sunny";
    String lastName = "Hu";
    doThrow(EmailAddressExistsException.class).when(registrationManagementMock)
      .register(username, emailAddress, firstName, lastName, password);

    RegisterCommand command = new RegisterCommand(username, emailAddress, firstName, lastName, password);
    instance.register(command);
  }

  @Test
  public void register_validCommand_shouldSucceed() throws RegistrationException {
    String username = "sunny";
    String emailAddress = "sunny@taskagile.com";
    String password = "MyPassword!";
    String firstName = "Sunny";
    String lastName = "Hu";
    User newUser = mock(User.class);
    when(newUser.getId()).thenReturn(new UserId(1));
    when(newUser.getUsername()).thenReturn(username);
    when(newUser.getEmailAddress()).thenReturn(emailAddress);
    when(newUser.getPassword()).thenReturn(password);
    when(newUser.getFirstName()).thenReturn(firstName);
    when(newUser.getFirstName()).thenReturn(lastName);

    when(registrationManagementMock.register(username, emailAddress, firstName, lastName, password))
      .thenReturn(newUser);

    IpAddress ipAddress = new IpAddress("127.0.0.1");
    RegisterCommand command = mock(RegisterCommand.class);
    when(command.getUsername()).thenReturn(username);
    when(command.getEmailAddress()).thenReturn(emailAddress);
    when(command.getFirstName()).thenReturn(firstName);
    when(command.getLastName()).thenReturn(lastName);
    when(command.getPassword()).thenReturn(password);
    when(command.getIpAddress()).thenReturn(ipAddress);

    instance.register(command);

    verify(mailManagerMock).send(
      emailAddress,
      "Welcome to TaskAgile",
      "welcome.ftl",
      MessageVariable.from("user", newUser)
    );

    ArgumentCaptor<UserRegisteredEvent> argumentCaptor = ArgumentCaptor.forClass(UserRegisteredEvent.class);
    verify(domainEventPublisherMock).publish(argumentCaptor.capture());

    UserRegisteredEvent event = argumentCaptor.getValue();
    assertEquals(newUser.getId(), event.getUserId());
    assertEquals(ipAddress, event.getIpAddress());
  }
}
