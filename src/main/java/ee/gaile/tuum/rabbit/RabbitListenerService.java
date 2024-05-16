package ee.gaile.tuum.rabbit;

import ee.gaile.tuum.model.response.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitListenerService {

    @RabbitListener(queues = {"${rabbit.queue.transaction}"})
    public void getRabbitLog(TransactionResponse request){
        log.info("transaction completed. account id - {}, transaction id - {}, amount - {}, currency - {}," +
                        " direction - {}, description - {}, balance - {}",
                request.getAccountId(),request.getTransactionId(), request.getAmount(),request.getCurrency(),
                request.getDirection(),request.getDescription(), request.getBalance());
    }

}
