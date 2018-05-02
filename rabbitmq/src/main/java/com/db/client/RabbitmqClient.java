package com.db.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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
        channel.exchangeDeclare("exchangeName","direct",true);
        channel.queueDeclare("queueName", false, false, false, null);
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
