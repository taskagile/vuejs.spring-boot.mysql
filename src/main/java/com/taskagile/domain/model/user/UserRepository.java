package com.taskagile.domain.model.user;

/**
 * User repository interface
 */
public interface UserRepository {

  /**
   * Find user by a username
   *
   * @param username the provided username used for finding user
   * @return an instance of <code>User</code> if found, null otherwise
   */
  User findByUsername(String username);

  /**
   * Find user by an email address
   *
   * @param emailAddress the provided email address used for finding user
   * @return an instance of <code>User</code> if found, null otherwise
   */
  User findByEmailAddress(String emailAddress);

  /**
   * Find user by id
   *
   * @param userId the id of the user
   * @return a user instance or null if not found
   */
  User findById(UserId userId);

  /**
   * Save a new user or an existing user
   *
   * @param user the user instance to be saved
   */
  void save(User user);
}
