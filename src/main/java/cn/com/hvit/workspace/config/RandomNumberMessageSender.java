package cn.com.hvit.workspace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时推送随机数据
 */
@Component
public class RandomNumberMessageSender {

  private SimpMessagingTemplate messaging;

  @Autowired
  public RandomNumberMessageSender(SimpMessagingTemplate messaging) {
    this.messaging = messaging;
  }
  
//  @Scheduled(fixedRate=100000)
  public void sendRandomNumber() {
    System.out.println("每100秒向前台推送随机数字");
    Shout random = new Shout();
    random.setMessage("Random # : " + (Math.random() * 100));
    messaging.convertAndSend("/topic/marco", random);
  }
  
}
