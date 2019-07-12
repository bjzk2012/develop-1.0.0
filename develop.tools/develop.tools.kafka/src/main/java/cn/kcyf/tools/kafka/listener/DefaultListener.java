package cn.kcyf.tools.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * kafka监听
 *
 */
@Component
public class DefaultListener {
    Logger logger=Logger.getLogger(DefaultListener.class);
    /**
     * 实时获取kafka数据(生产一条，监听生产topic自动消费一条)
     * @param record
     * @throws IOException
     */
    @KafkaListener(topics = {"${kafka.consumer.topic}"})
    public void listen(ConsumerRecord<?, ?> record) throws IOException {
        String value = (String) record.value();
        System.out.println(value);
    }

}
