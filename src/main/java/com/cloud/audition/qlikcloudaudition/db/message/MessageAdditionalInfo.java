package com.cloud.audition.qlikcloudaudition.db.message;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageAdditionalInfo {
  private boolean isPalindrome;

  public boolean isPalindrome() {
    return isPalindrome;
  }
}
