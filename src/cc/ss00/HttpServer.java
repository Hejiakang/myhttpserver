package cc.ss00;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private static final String GET = "GET";
    public static void main(String[] args) throws IOException, InterruptedException {

    }

    public void createServer() throws IOException {

        var serverSocket = new ServerSocket(8080);
        while(true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String requestLine = bufferedReader.readLine();
            String method = requestLine.substring(0, requestLine.indexOf("/") - 1);


            switch (method){
                case GET :{
                    OutputStream outputStream = socket.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write("HTTP/1.1 200 OK\n");

                    //猜测http响应头本身的编码默认是utf-8，所以无论content-type是何种编码，此处的汉字都不会乱码
                    bufferedWriter.write("Server: 我的http服务器\n");

                    //猜测application/json 默认是utf-8编码。当设为text/plain时请求体中的汉字将乱码
                    bufferedWriter.write("Content-Type: application/json\n\n");
                    bufferedWriter.write("你好世界");

                    //flush之后会将内容返回给浏览器，但连接不会结束。浏览器会显示flush的内容但浏览器的刷新图标处于等待状态。
                    bufferedWriter.flush();

                    //关闭连接之后请求才会结束
                    socket.close();
                }
            }

        }

    }


}
