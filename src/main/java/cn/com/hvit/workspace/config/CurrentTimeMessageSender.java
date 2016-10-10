package cn.com.hvit.workspace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class CurrentTimeMessageSender {

  private SimpMessagingTemplate messaging;
  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Autowired
  public CurrentTimeMessageSender(SimpMessagingTemplate messaging) {
    this.messaging = messaging;
  }
  //15秒执行一次
  @Scheduled(fixedRate=15000)
  public void sendRandomNumber() {
    Shout random = new Shout();
    random.setMessage("current-time # : " + format.format(new Date()));
    messaging.convertAndSend("/topic/marco", random);
  }
  
}
