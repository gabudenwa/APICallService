package main;

import java.util.Date;

public class Usage {
  private String customerId;
  private String ip;
  private Date time;
  
  public Usage(String customerId, String ip, Date time) {
    this.customerId = customerId;
    this.ip = ip;
    this.time = time;
  }

  public Date getTime() {
    return time;
  }
}