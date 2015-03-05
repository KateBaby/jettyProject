package com.hq.one;


import org.hornetq.jms.client.HornetQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by ZhuaiMao.
 * Data : 15/1/7
 * Des : MQ 消息队列工具类，主要提供消息的发送者和接收者
 */
public class AwlHornetQService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AwlHornetQService.class);

    private final String FACTORY = "java.naming.factory.initial";
    private final String PROVIDER_URL = "java.naming.provider.url";
    private final String URL_PKGS = "java.naming.factory.url.pkgs";
    private final String PRINCIPL = "java.naming.security.principal";
    private final String CREDENTIAL = "java.naming.security.credentials";

    private Properties properties;
    private InitialContext initialContext;

    /** HornetQ  */
    private HornetQConnectionFactory connectionFactory;
    private Connection connection;

//    private QueueConnection queueConnection;
//    private TopicConnection topicConnection;


    public AwlHornetQService(String factoryInital, String providerUrl, String urlPkgs,
                        String principal, String credential) {
        this.properties = new Properties();
        properties.put(FACTORY, factoryInital);
        properties.put(PROVIDER_URL, providerUrl);
        properties.put(URL_PKGS, urlPkgs);
        properties.put(PRINCIPL, principal);
        properties.put(CREDENTIAL, credential);

        this.initialContext = provideInitalContext(properties);
        this.connectionFactory = provideHornetQConnectionFactory(initialContext);
        this.connection = provideConnection(connectionFactory, properties);
//        this.queueConnection = provideQueueConnection(connectionFactory, properties);
//        this.topicConnection = provideTopicConnection(connectionFactory, properties);
    }

    public AwlHornetQService(String factoryInital, String providerUrl, String urlPkgs) {
        this(factoryInital, providerUrl, urlPkgs, null, null);
    }




    private InitialContext provideInitalContext(Properties prop) {
        try {
            return new InitialContext(prop);
        } catch (NamingException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return null;
    }

    /** ==================================================  HornetQ =================================================== */

    /**
     * ConnectionFactory
     */
    private HornetQConnectionFactory provideHornetQConnectionFactory(InitialContext initalContext) {
        HornetQConnectionFactory connectionFactory = null;
        try {
            connectionFactory = (HornetQConnectionFactory) initalContext.lookup("/ConnectionFactory");
            String clientID = "awl-hornetQ@";
            try {
                clientID += InetAddress.getLocalHost();
            } catch (Exception e) {
                clientID += UUID.randomUUID().toString();
            }
            connectionFactory.setClientID(clientID);
        } catch (NamingException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return connectionFactory;
    }

    /**
     * Connection
     */
    private Connection provideConnection(HornetQConnectionFactory connectionFactory, Properties prop) {
        Connection connection = null;
        try {
            if (null != properties.getProperty(PRINCIPL)) {
                connection = connectionFactory.createQueueConnection(prop.getProperty(PRINCIPL), prop.getProperty(CREDENTIAL));
            } else {
                connection = connectionFactory.createQueueConnection();
            }
            connection.start();
            return connection;
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return null;
    }

    /**
     * Queue Connection
     */
    @Deprecated
    private QueueConnection provideQueueConnection(HornetQConnectionFactory connectionFactory, Properties prop) {
        QueueConnection connection = null;
        try {
            if (null != properties.getProperty(PRINCIPL)) {
                connection = connectionFactory.createQueueConnection(prop.getProperty(PRINCIPL), prop.getProperty(CREDENTIAL));
            } else {
                connection = connectionFactory.createQueueConnection();
            }
            connection.start();
            return connection;
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return null;
    }

    /**
     * Topic Connection
     */
    @Deprecated
    private TopicConnection provideTopicConnection(HornetQConnectionFactory connectionFactory, Properties prop) {
        TopicConnection connection = null;
        try {
            if (null != properties.getProperty(PRINCIPL)) {
                connection = connectionFactory.createTopicConnection(prop.getProperty(PRINCIPL), prop.getProperty(CREDENTIAL));
            } else {
                connection = connectionFactory.createTopicConnection();
            }
            connection.start();
            return connection;
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return null;
    }

    /**
     * Consumer List
     */
    private List<MessageConsumer> provideConsumer(Connection connection, String queueName, int consumerConunt) {
        List<MessageConsumer> consumerList = new ArrayList<MessageConsumer>();
        for (int count=1; count<=consumerConunt; count++) {
            MessageConsumer consumer = null;
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(queueName);
                consumer = session.createConsumer(destination);
                consumerList.add(consumer);
            } catch (JMSException e) {
                e.printStackTrace();
                LOGGER.error(e.toString(), e);
            }
        }
        return consumerList;
    }

    /**
     * Session
     */
    private Session provideSession(Connection connection) {
        Session session = null;
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return session;
    }

    /**
     * Queue Consumer
     */
    private MessageConsumer provideQueueConsumer(Session session, String queueName) {
        MessageConsumer consumer = null;
        try {
            Destination destination = session.createQueue(queueName);
            consumer = session.createConsumer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return consumer;
    }

    /**
     * Topic Consumer
     */
    private MessageConsumer provideTopicConsumer(Session session, String topicName) {
        MessageConsumer consumer = null;
        try {
            Topic topic = session.createTopic(topicName);
            consumer = session.createConsumer(topic);
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return consumer;
    }


    private List<MessageProducer> provideProducers(Connection connection, String queueName, int producerCount) {
        List<MessageProducer> producerList = new ArrayList<MessageProducer>();
        for (int count=1; count<=producerCount; count++) {
            MessageProducer producer = null;
            try {
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(queueName);
                producer = session.createProducer(destination);
                producerList.add(producer);
            } catch (JMSException e) {
                e.printStackTrace();
                LOGGER.error(e.toString(),e);
            }
        }
        return producerList;
    }

    /**
     * Queue Producer
     */
    private MessageProducer provideProducer(Session session, String queuenName) {
        MessageProducer producer = null;
        try {
            Destination destination = session.createQueue(queuenName);
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return producer;
    }

    /**
     * Topic Producer
     */
    private MessageProducer provideTopicProducer(Session session, String topicName) {
        MessageProducer producer = null;
        try {
            Topic topic = session.createTopic(topicName);
            producer = session.createProducer(topic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (JMSException e) {
            e.printStackTrace();
            LOGGER.error(e.toString(), e);
        }
        return producer;
    }


    /** --------------------------------- send message --------------------------------------------------- */
    /**
     * hornetQ Message Sender : Queue
     */
    public void sendMsgToQueue(String queueName, final String message) throws JMSException {
        Session session = provideSession(connection);
        MessageProducer producer = provideProducer(session, queueName);
        producer.send(session.createTextMessage(message));
        producer.close();
        session.close();
    }

    /**
     * hornetQ Message Sender : Topic
     */
    public void sendMsgToTopic(String topicName, final String message) throws JMSException {
        Session session = provideSession(connection);
        MessageProducer producer = provideTopicProducer(session, topicName);
        producer.send(session.createTextMessage(message));
        producer.close();
        session.close();
    }

    //TODO 多生产者


    /** --------------------------------- consume message --------------------------------------------------- */

    /**
     * HornetQ Message Consume : Queue
     */
    public void consumeMsgFromQueue(String queueName, MessageListener listener) throws JMSException {
//        ConsumeMessageTask consumeMsgTask = new ConsumeMessageTask(queueName, listener);
//        scheduledExecutor.scheduleWithFixedDelay(consumeMsgTask, INIT_DELAY, TASK_DELAY, TimeUnit.MILLISECONDS);
//        while (!scheduledExecutor.isShutdown()) {
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        Session session = provideSession(connection);
        MessageConsumer consumer = provideQueueConsumer(session, queueName);
        consumer.setMessageListener(listener);
    }

    public String consumeMsgFromQueue(String queueName, long delay) throws JMSException {
        Session session = provideSession(connection);
        MessageConsumer consumer = provideQueueConsumer(session, queueName);
        Message  message = consumer.receive(delay);
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            return msg.getText();
        }
        return null;
    }

    /**
     * HornetQ Message Consume : Topic
     */
    public void consumeMsgFromTopic(String topicName, MessageListener listener) throws JMSException {
        Session session = provideSession(connection);
        MessageConsumer consumer = provideTopicConsumer(session, topicName);
        consumer.setMessageListener(listener);
    }

    public String consumeMsgFromTopic(String topicName, long delay) throws JMSException {
        Session session = provideSession(connection);
        MessageConsumer consumer = provideTopicConsumer(session, topicName);
        Message  message = consumer.receive(delay);
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            return msg.getText();
        }
        return null;
    }

    // TODO 多消费者

    /**
     * Close
     */
    public void close() {
//        scheduledExecutor.shutdown();
//        try {
//            connection.close();
//            if (!scheduledExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
//                scheduledExecutor.shutdownNow(); // Cancel currently executing tasks
//                // Wait a while for tasks to respond to being cancelled
//                if (!scheduledExecutor.awaitTermination(60, TimeUnit.SECONDS))
//                    System.err.println("Pool did not terminate");
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            LOGGER.error(e.toString(), e);
//        } catch (JMSException e) {
//            e.printStackTrace();
//            LOGGER.error(e.toString(), e);
//        }
        try {
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }



    /**
     * 定时任务 供消费者使用
     */
//    private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);
//    private final long TASK_DELAY = 1000;
//    private final long INIT_DELAY = 1000;
//
//    private class ConsumeMessageTask implements Runnable {
//        private MessageConsumer consumer;
//        private AwlMQMessageListener listener;
//
//        public ConsumeMessageTask(String queueName, AwlMQMessageListener listener) {
//            Session session = provideSession(connection);
//            this.consumer = provideQueueConsumer(session, queueName);
//            this.listener = listener;
//            try {
//                this.consumer.setMessageListener(this.listener);
//            } catch (JMSException e) {
//                e.printStackTrace();
//                LOGGER.error(e.toString(), e);
//            }
//        }
//
//        public AwlMQMessageListener getListener() {
//            return listener;
//        }
//
//
//        @Override
//        public void run() {
//            LOGGER.info("开启消息队列监听线程：");
//            LOGGER.info("等待 200 ms ...");
//
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                LOGGER.error(e.toString(), e);
//            }
//            LOGGER.info("关闭消息队列监听线程：");
//        }
//    }

}
