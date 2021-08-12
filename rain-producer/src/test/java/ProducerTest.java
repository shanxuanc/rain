import com.bamboo.rain.RainProducerApplication;
import com.bamboo.rain.rocket.Demo01Producer;
import com.bamboo.rain.rocket.OrderService;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RainProducerApplication.class)
public class ProducerTest {

    private static final Logger logger = LoggerFactory.getLogger(ProducerTest.class);
    @Autowired
    private Demo01Producer demo01Producer;
    @Autowired
    private OrderService orderService;

    @Test
    public void onewaySend(){
        demo01Producer.onewaySend(122);
    }

    @Test
    public void asyncSend(){
        demo01Producer.asyncSend(122);
    }

    @Test
    public void syncSendOrder() {
        for (int i = 0; i < 10; i++) {
            demo01Producer.syncSendOrder(i);
        }
    }

    @Test
    public void delete() {
        orderService.delete("no1");
    }

    @Test
    public void syncSend() {
        for (int i = 0; i < 1000; i++) {
            demo01Producer.syncSend(125 + i);
        }
    }

    @Test
    public void sendDelayMessage() {
        demo01Producer.sendDelayMessage("delay-topic", "Hello,JAVA日知录", 5);
    }
}
