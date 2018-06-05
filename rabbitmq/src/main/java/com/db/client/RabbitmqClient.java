package com.db.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RabbitmqClient {
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public void getConnection() throws Exception {


        //创建一个连接工厂 connection factory
        ConnectionFactory factory = new ConnectionFactory();

        //设置rabbitmq-server服务IP地址
        factory.setHost("39.108.235.200");
        factory.setUsername("dongbo");
        factory.setPassword("dongbo");
        factory.setPort(5672);
        //设置vhost
        factory.setVirtualHost("/");

        //得到 连接
        connection = factory.newConnection();
        if(Objects.nonNull(connection)){
            System.out.println("获取mq连接成功");
        }
        //创建 channel实例
        channel = connection.createChannel();
        byte[] messageBodyBytes = "Hello, world!".getBytes();
        //设置队列所有消息的过期时间
        Map<String, Object> args = new HashMap<String,Object>();
        args.put("x-expires",10000);
        channel.queueDeclare("queueName", false, false, false, args);
        channel.queueBind("queueName", "exchangeName", "queueName");
        channel.basicPublish("exchangeName","queueName",null,messageBodyBytes);

        //将队列和exchange绑定：队列名称，exchangeName名称，routing-key
//        channel.queueBind("queueName", "exchangeName", "queueName");

        //发送一条直能存活10s的消息到队列（单独给一条消息设置存活时间）
//        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().expiration("10000").build();
//        channel.basicPublish("exchangeName","queueName",properties,messageBodyBytes);



//        channel.exchangeDeclare("exchangeName","direct",true);
//        channel.queueDeclare("queueName", false, false, false, null);
    }

    public static void main(String[] args) {
        RabbitmqClient rabbitmqClient = new RabbitmqClient();
        try {
            rabbitmqClient.getConnection();
            rabbitmqClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     * @throws Exception
     */
    public void close() throws Exception{
        this.channel.close();
        this.connection.close();
    }
}
