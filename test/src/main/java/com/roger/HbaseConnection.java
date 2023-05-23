package com.roger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.AsyncConnection;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class HbaseConnection {
    //声明一个静态属性
    public static Connection connection = null;
    static {
        //1.创建连接
        //默认使用同步连接
        try {
            //使用读取本地文件的形式添加参数
            connection = ConnectionFactory.createConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() throws IOException {
        //判断连接是否为空
       if (connection != null) {
           connection.close();
       }

    }

    public static void main(String[] args) throws IOException {
//        // 1.创建连接配置对象
//        Configuration conf = new Configuration();
//        //2.添加配置参数
//        conf.set("hbase.zookeeper.quorum","hadoop102,hadoop103,hadoop104");
//        //3.创建连接
//        //默认使用同步连接
//        Connection connection = ConnectionFactory.createConnection(conf);
//
//        //可使用异步连接
//        CompletableFuture<AsyncConnection> asyncConnection = ConnectionFactory.createAsyncConnection(conf);
//        //4.使用连接
//        System.out.println(connection);
//
//        //5.关闭连接
//        connection.close();

        //直接使用创建好的连接
        //不要在main线程里面单独创建
        System.out.println(connection);


        //在main线程最后关闭连接
        closeConnection();
    }
}
