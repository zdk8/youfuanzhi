package cn.com.hvit.workspace.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * 接收器，发送器
 */
@Controller
public class MarcoController {

    private static final Logger logger = LoggerFactory
            .getLogger(MarcoController.class);

    @MessageMapping("/marco")
    public Shout handleShout(Shout incoming) {
        logger.info("接收消息: " + incoming.getMessage());
        System.out.println("接收消息: " + incoming.getMessage());

        Shout outgoing = new Shout();

        if (incoming.getMessage().startsWith("All:")) {
            outgoing.setMessage(repsonseAll(incoming.getMessage()));
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            outgoing.setMessage("Polo!");
        }
        return outgoing;
    }

    private String repsonseAll(String allText) {
        if (allText.startsWith("All:")) {
            return "warning:"+allText.substring("All:".length());
        }
        return "";
    }

}
