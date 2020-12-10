package com.socialize.socialize.enums;

public enum APIResponseStatus {
  SUCCESS(1),
  ERROR(0);

  private int value;

  APIResponseStatus(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
