package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import project.choice.ChoiceController;
import project.login.Root_Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ServerController implements Initializable{
   
   public static final int SERVER_PORT = 5001;
   
   @FXML private TextArea txtDisplay;
   @FXML private Button btnStartStop;
   // 스레드를 관리하기 위해 선언
   ExecutorService threads;
   // 특정 컴퓨터에서 TCP포트를 열어 그곳으로 요청을 받기위해 서버소켓 사용
   ServerSocket server;
   // 받은 클라이언트 정보 저장
   List<Client> connections = new Vector<>();
   int a = 0;
   
   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {
	   
      btnStartStop.setOnAction(event->handleBtnStartStop(event));
   }
   
   public void handleBtnStartStop(ActionEvent e) {
      System.out.println("click");
      System.out.println(btnStartStop.getText());
      if(btnStartStop.getText().equals("start")) {
         startServer();
      }else {
         stopServer();
      }
   }
   class Client{
      // 받을 소켓
      Socket socket;
      // 받을 닉네임
      String nickName;
      // 클라이언트에 소켓을 받기위해 생성자 사용
      Client(Socket socket){
         this.socket=socket;
         // 스레드 생성됨
         receive();
      }
      // 스레드 생성되고 처리할 내용
      // message 전달 받기
      public void receive() {
    	  // 스레드풀에 실행될것 
    	  Runnable runnable =new Runnable() {

			@Override
            public void run() {
               while(true) {
                  try {
                	 
                     // 입력받은 메세지를 담기위한 그릇이 될  byte[] 배열 선언
                     byte[] byteArr = new byte[100];
                     // 소켓의 입력 스트림을 받아 is에 저장
                     InputStream is = socket.getInputStream();
                     // 1바이트씩 읽어서 byte[]배열에 담는다.
                     int readByteCount = is.read(byteArr);
                     // 만약 다 입력받았을떄 -1을 반환하였다면 예외처리 발생
                     if(readByteCount == -1) throw new IOException();
                     // 요청처리된 ip를 저장함
                     String message = "[요청 처리"+socket.getRemoteSocketAddress()+"]";
                     // 요청처리된 메세지를 출력함
                     Platform.runLater(()->displayText(message));
                     // 문자열안에 처리할 내용을 셋팅함
                     String data = new String(byteArr,0, readByteCount,"UTF-8");
                     // 셋팅한 문자열 출력
                     System.out.println(data); // 2|머시기
                     // |는 문자열로 만들기위해 \\로 정규식을 선언해서 만들어준다
                     // 만약 안쓴다면 |를 or로 사용된다.
                     String[] strs = data.split("\\|");
                     String msg = "";  // " " 메세지  / 1 닉네임
                     System.out.println(strs[0]); //1
                     System.out.println(strs[1]); //Sung
                                       
                     if(strs[0].equals("1")) {
                        Client.this.nickName = strs[1];
                        msg = nickName+"가 입장 하였습니다.";
                     }else if(strs[0].equals(" ")){
                         msg = nickName +" : "+strs[1];   
                      }
                 
                     
                     //Platform.runLater(()->displayText(strs[1]+"님이 입장 하였습니다."));
                     // 여기서 클라이언트 구분해줄것!
                    
                     for(Client client : connections) {
							if (client != Client.this && connections.size() < 3) {
								client.send(msg);
							}else if(connections.size() > 2) {
								socket.close();
								
							}
                     }
                     
                     
                  } catch (Exception e) {
                     connections.remove(Client.this);
                     String message = "[클라이언트 통신 안됨 : "+socket.getRemoteSocketAddress()+"]";
                     Platform.runLater(()->displayText(message));
                     try {
                        socket.close();
                     } catch (IOException e1) {}
                     break;
                  }
               }
            }
         };
         // 스레드 풀에 넣어서 실행
         threads.submit(runnable);
      }
      
      // message 전달
      public void send(String data) {
         try {
        	
            byte[] byteArr = data.getBytes("UTF-8");
            OutputStream os = socket.getOutputStream();
            os.write(byteArr);
            os.flush();
         } catch (Exception e) {
            String message = "[클라이언트 연결 안됨 : "+socket.getRemoteSocketAddress()+"]";
            Platform.runLater(()->displayText(message));
            connections.remove(Client.this);
            try {
               socket.close();
            } catch (IOException e1) {}
         }
      } 
   }
   
   
   
   
   public void startServer() {
      threads = Executors.newFixedThreadPool(30);

      try {
         server = new ServerSocket(SERVER_PORT);
         
         Platform.runLater(()->{
            displayText("[ 서버 시작 ]");
            btnStartStop.setText("stop");
         });
         
      } catch (IOException e) {
         stopServer();
         return;
      }
      
      Runnable runnable = new Runnable() {
         public void run() {
            while(true) {
               try {
                  Socket socket = server.accept();
                  String message = "[연결수락 : ]"+ socket.getRemoteSocketAddress();
                  System.out.println(message);
                  
                  Platform.runLater(()->displayText(message));
                  
                  Client client = new Client(socket);
                  
                  connections.add(client);
                  Platform.runLater(()->displayText("[연결 Client 수 : " + connections.size()+"]"));
               } catch (IOException e) {
                  stopServer();
                  break;
               }
            }
         }
      };
      threads.submit(runnable);
   }
   
   public void stopServer() {
      
      try {
         Iterator<Client> iterator = connections.iterator();
         
         while(iterator.hasNext()) {
            Client client = iterator.next();
            if(client.socket != null && !client.socket.isClosed()) {
               client.socket.close();
               iterator.remove();
            }
         }
         
         if(server != null && !server.isClosed()) {
            server.close();
         }
         
         if(threads != null && !threads.isShutdown()) {
            threads.shutdown();
         }
         
         Platform.runLater(()->{
            btnStartStop.setText("start");
            displayText("[서버 종료]");
         });
         
      } catch (IOException e) {}
      
   }
   
   void displayText(String text) {
      txtDisplay.appendText(text+"\n");
   } 
}