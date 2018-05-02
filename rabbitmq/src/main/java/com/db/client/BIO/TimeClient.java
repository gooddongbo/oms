package com.db.client.BIO;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class TimeClient {
    public static void main(String[] args) {
        for(int i=0;i<5;i++){
            Socket socket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            try {
                socket = new Socket("127.0.0.1", 9999);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(),true);
                out.println("hello server");
//            System.out.println("server send :" + in.readLine());

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(out != null){
                    out.close();
                    out = null;
                }
                if(in != null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    in = null;
                }
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    socket = null;
                }
            }
        }


    }
}
