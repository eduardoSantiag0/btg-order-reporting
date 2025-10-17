package com.eduardoSantiag0.btg_order_reporting.messaging.processors;

import com.eduardoSantiag0.btg_order_reporting.domain.Item;
import com.eduardoSantiag0.btg_order_reporting.domain.OrderMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class FailedOrdersProcessorTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private FailedOrdersProcessor processor;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        processor = new FailedOrdersProcessor(rabbitTemplate);
    }


//    @Test
//    void shouldSendFailedMessageToDLQ() {
//    }

    @Test
    void shouldHandleExceptionThrownByRabbitTemplate() {
        OrderMessage order = new OrderMessage(1L, 123L, new Item[]{});
        Exception exception = new RuntimeException("Original error");

        doThrow(new RuntimeException("RabbitTemplate failed"))
                .when(rabbitTemplate).convertAndSend(anyString(), anyString(), Optional.ofNullable(any()));

        assertDoesNotThrow(() -> processor.handleError(order, exception));

        verify(rabbitTemplate, times(1))
                .convertAndSend(anyString(), anyString(), Optional.ofNullable(any()));
    }
}