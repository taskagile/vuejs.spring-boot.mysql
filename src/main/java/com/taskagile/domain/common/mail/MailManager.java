package com.taskagile.domain.common.mail;

public interface MailManager {

  /**
   * Send a message to a recipient
   *
   * @param emailAddress the recipient's email address
   * @param subject the subject key of the email
   * @param template the template file name of the email
   * @param variables message variables in the template file
   */
  void send(String emailAddress, String subject, String template, MessageVariable... variables);
}
