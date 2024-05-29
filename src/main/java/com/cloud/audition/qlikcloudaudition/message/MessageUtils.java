package com.cloud.audition.qlikcloudaudition.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {

  public static boolean isPalindrome(final String text) {
    if (StringUtils.isBlank(text)) {
      return false;
    }

    final var clean = text.replaceAll("\\s+", "").toLowerCase();
    final var length = clean.length();
    var forward = 0;
    var backward = length - 1;

    while (backward > forward) {
      char forwardChar = clean.charAt(forward++);
      char backwardChar = clean.charAt(backward--);
      if (forwardChar != backwardChar) return false;
    }

    return true;
  }
}
