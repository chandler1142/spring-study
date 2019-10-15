package com.imooc.disruptor.chapter6.disruptor;

import com.imooc.disruptor.chapter6.common.entity.TranslatorDataWrapper;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

public class RingbufferPoolFactory {

    private static class SingletonHolder {
        static final RingbufferPoolFactory instance = new RingbufferPoolFactory();
    }

    private RingbufferPoolFactory() {

    }

    public static RingbufferPoolFactory getInstance() {
        return SingletonHolder.instance;
    }

    private static Map<String, MessageProducer> producers = new ConcurrentHashMap<>();

    private static Map<String, MessageConsumer> consumers = new ConcurrentHashMap<>();

    private RingBuffer<TranslatorDataWrapper> ringBuffer;

    private WorkerPool<TranslatorDataWrapper> workerPool;

    private SequenceBarrier barrier;

    public void intiAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers) {
        //1. 创建ringbuffer
        this.ringBuffer = RingBuffer.create(
                type,
                new EventFactory<TranslatorDataWrapper>() {

                    @Override
                    public TranslatorDataWrapper newInstance() {
                        return new TranslatorDataWrapper();
                    }

                },
                bufferSize,
                waitStrategy
        );
        //2. 创建barrier
        this.barrier = ringBuffer.newBarrier();

        //3. worerpool
        this.workerPool = new WorkerPool<TranslatorDataWrapper>(
                this.ringBuffer,
                this.barrier,
                new ExceptionHandler<TranslatorDataWrapper>() {
                    @Override
                    public void handleEventException(Throwable ex, long sequence, TranslatorDataWrapper event) {

                    }

                    @Override
                    public void handleOnStartException(Throwable ex) {

                    }

                    @Override
                    public void handleOnShutdownException(Throwable ex) {

                    }
                },
                messageConsumers
        );

        //4. 把所构建的消费者放置入池中
        for (MessageConsumer messageConsumer : messageConsumers) {
            this.consumers.put(messageConsumer.getConsumerId(), messageConsumer);
        }

        //5. 添加我们的sequences
        this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());

        //6. 启动工作池
        this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

    }

    public MessageProducer getMessageProducer(String producerId) {
        MessageProducer producer = this.producers.get(producerId);
        if (null == producer) {
            producer = new MessageProducer(producerId, this.ringBuffer);
            this.producers.put(producerId, producer);
        }
        return producer;
    }


}
