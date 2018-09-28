package com.taskagile.domain.model.activity;

import java.util.HashMap;
import java.util.Map;

public enum ActivityType {
  UNKNOWN(""),
  ADD_BOARD("add-board"),
  RENAME_BOARD("rename-board"),
  ARCHIVE_BOARD("archive-board"),
  ADD_BOARD_MEMBER("add-board-member"),
  REMOVE_BOARD_MEMBER("remove-board-member"),
  ADD_CARD_LIST("add-card-list"),
  RENAME_CARD_LIST("rename-card-list"),
  ARCHIVE_CARD_LIST("archive-card-list"),
  ADD_CARD("add-card"),
  CHANGE_CARD_TITLE("change-card-title"),
  CHANGE_CARD_DESCRIPTION("change-card-description"),
  ASSIGN_MEMBER_TO_CARD("assign-member-to-card"),
  REMOVE_MEMBER_FROM_CARD("remove-member-from-card"),
  ADD_ATTACHMENT("add-attachment"),
  REMOVE_ATTACHMENT("remove-attachment"),
  ARCHIVE_CARD("archive-card"),
  DELETE_CARD("delete-card"),
  ADD_COMMENT("add-comment"),
  EDIT_COMMENT("edit-comment"),
  DELETE_COMMENT("delete-comment");

  private String type;

  ActivityType(String type) {
    this.type = type;
  }

  public static ActivityType parse(String type) {
    ActivityType found = TYPES.get(type);
    if (found == null) {
      return UNKNOWN;
    }
    return found;
  }

  public static ActivityType parse(int type) {
    ActivityType found = TYPES.get(type);
    if (found == null) {
      return UNKNOWN;
    }
    return found;
  }

  public boolean isValid() {
    return !UNKNOWN.equals(this);
  }

  public String getType() {
    return type;
  }

  private static final Map<Object, ActivityType> TYPES = new HashMap<>();

  static {
    TYPES.put(ADD_BOARD.type, ADD_BOARD);
    TYPES.put(RENAME_BOARD.type, RENAME_BOARD);
    TYPES.put(ARCHIVE_BOARD.type, ARCHIVE_BOARD);
    TYPES.put(ADD_BOARD_MEMBER.type, ADD_BOARD_MEMBER);
    TYPES.put(REMOVE_BOARD_MEMBER.type, REMOVE_BOARD_MEMBER);
    TYPES.put(ADD_CARD_LIST.type, ADD_CARD_LIST);
    TYPES.put(RENAME_CARD_LIST.type, RENAME_CARD_LIST);
    TYPES.put(ARCHIVE_CARD_LIST.type, ARCHIVE_CARD_LIST);
    TYPES.put(ADD_CARD.type, ADD_CARD);
    TYPES.put(CHANGE_CARD_TITLE.type, CHANGE_CARD_TITLE);
    TYPES.put(CHANGE_CARD_DESCRIPTION.type, CHANGE_CARD_DESCRIPTION);
    TYPES.put(ASSIGN_MEMBER_TO_CARD.type, ASSIGN_MEMBER_TO_CARD);
    TYPES.put(REMOVE_MEMBER_FROM_CARD.type, REMOVE_MEMBER_FROM_CARD);
    TYPES.put(ADD_ATTACHMENT.type, ADD_ATTACHMENT);
    TYPES.put(REMOVE_ATTACHMENT.type, REMOVE_ATTACHMENT);
    TYPES.put(ARCHIVE_CARD.type, ARCHIVE_CARD);
    TYPES.put(DELETE_CARD.type, DELETE_CARD);
    TYPES.put(ADD_COMMENT.type, ADD_COMMENT);
    TYPES.put(EDIT_COMMENT.type, EDIT_COMMENT);
    TYPES.put(DELETE_COMMENT.type, DELETE_COMMENT);
  }
}
