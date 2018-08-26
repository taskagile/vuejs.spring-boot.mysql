package com.taskagile.domain.common.mail;

public interface Message {

  /**
   * Get the recipient of the message
   *
   * @return recipient's email address
   */
  String getTo();

  /**
   * Get the subject of the message
   *
   * @return message's subject
   */
  String getSubject();

  /**
   * Get the body of the message
   *
   * @return the body of the message
   */
  String getBody();

  /**
   * Get the from of this message
   *
   * @return where this message is from
   */
  String getFrom();
}
