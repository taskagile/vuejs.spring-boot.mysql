package com.taskagile.web.socket;

import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ChannelHandlerInvokerTests {

  @ChannelHandler("/test/*")
  private static class TestChannelHandler {

    @Action("execute")
    public void execute(@ChannelValue String channel, RealTimeSession session, @Payload TestMessage message) {
    }

    @Action("subscribe")
    public void subscribe(RealTimeSession session) {
    }

    @Action("empty")
    public void empty() {
    }
  }

  private static class TestMessage {
    private String message;

    public static TestMessage create(String message) {
      TestMessage testMessage = new TestMessage();
      testMessage.message = message;
      return testMessage;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof TestMessage)) return false;
      TestMessage that = (TestMessage) o;
      return Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
      return Objects.hash(message);
    }
  }

  //--------------------------------------
  // Constructor
  //--------------------------------------

  @Test(expected = IllegalArgumentException.class)
  public void constructor_nullHandler_shouldFail() {
    new ChannelHandlerInvoker(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructor_noHandlerAnnotation_shouldFail() {
    new ChannelHandlerInvoker(new Object());
  }

  @Test
  public void constructor_validHandler_shouldSucceed() {
    new ChannelHandlerInvoker(new TestChannelHandler());
  }

  //--------------------------------------
  // Method supports()
  //--------------------------------------

  @Test
  public void supports_notFoundAction_shouldReturnFalse() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    assertFalse(invoker.supports("not exist action"));
  }

  @Test
  public void supports_existAction_shouldReturnTrue() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    assertTrue(invoker.supports("execute"));
  }

  //--------------------------------------
  // Method handle()
  //--------------------------------------

  @Test(expected = IllegalArgumentException.class)
  public void handle_wrongChannelValueInIncomingMessage_shouldFail() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/abc", "execute", ""), session);
  }

  @Test(expected = IllegalArgumentException.class)
  public void handle_wrongActionValueInIncomingMessage_shouldFail() {
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(new TestChannelHandler());
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test", "find", ""), session);
  }

  @Test
  public void handle_validIncomingMessageAndEmptyParameterInActionMethod_shouldSucceed() {
    TestChannelHandler mockHandler = mock(TestChannelHandler.class);
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(mockHandler);
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test/abc", "empty", null), session);

    verify(mockHandler).empty();
  }

  @Test
  public void handle_validIncomingMessageAndOnlySessionParameterRequired_shouldSucceed() {
    TestChannelHandler mockHandler = mock(TestChannelHandler.class);
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(mockHandler);
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test/abc", "subscribe", null), session);

    verify(mockHandler).subscribe(session);
  }

  @Test
  public void handle_validIncomingMessageAndSessionPayloadAllRequired_shouldSucceed() {
    TestChannelHandler mockHandler = mock(TestChannelHandler.class);
    ChannelHandlerInvoker invoker = new ChannelHandlerInvoker(mockHandler);
    RealTimeSession session = mock(RealTimeSession.class);
    invoker.handle(IncomingMessage.create("/test/abc", "execute", "{\"message\": \"ABC\"}"), session);

    verify(mockHandler).execute("/test/abc", session, TestMessage.create("ABC"));
  }
}
