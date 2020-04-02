package project.login;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Root_Controller implements Initializable{
  
   // fx:id로 선언된 레이아웃들을 선언
   @FXML private TextArea txtDisplay;
   @FXML private TextField txtInput,txtNick;
   @FXML private Button  btnSend;
   @FXML private Button btnOn;
   Socket socket;
   
   
   // 설계부분 (초기화부분)
   @Override
   public void initialize(URL location, ResourceBundle resources) {
      
	  startClient();
      btnSend.setOnAction((event)->{
         send(" ",txtInput.getText());
      });
      // CHAT입력에어리어에서 엔터를 눌렀을떄 발생이벤트 셋팅
      txtInput.setOnKeyPressed(key->{
         // 만약 엔터키를 눌렀을때 send버튼을 실행
         if(key.getCode().equals(KeyCode.ENTER)) {
            btnSend.fire();
         }
      });
      
   }
   
   
   // startClient 메소드 정의
   public void startClient() {
      try {
         System.out.println("서버 연결");
        
         // 소켓을 생성하는 동시에 ip값과 포트를 생성함.
         socket = new Socket("192.168.1.6",5001);
         // 연결이 완료된 아이피를 출력
         System.out.println("[연결 완료 : "+socket.getRemoteSocketAddress()+"]");
         txtInput.setEditable(true);
         btnSend.setDisable(false);
         // 닉네임을 텍스트로 받아서 message에 저장
         String message = "상담사";
         // send 메소드 호출 매개변수로 1과 message를 받음
         send("1",message);
         
         // receive()메소드 호출
         receive();
         
         // 연결 실패시 알려줌
      } catch (Exception e) {
         // 입력창에 텍스트 전달받음
         Platform.runLater(()->displayText("[서버 통신 안됨]"));
         stopClient();
         return;
      }
   }
   
   // 연결실패시 호출될 메소드
   public void stopClient() {
      try {
         System.out.println("연결 종료");
         Platform.runLater(()->{
            displayText("[서버와 연결 끊음]");
            
         });
         // 만약 소켓이 없고 소켓이 닫혀있지 않았다면  소켓을 닫는다.
         if(socket != null && !socket.isClosed()) {
            socket.close();
         }
      } catch (Exception e) {}
   }
   
   public void receive() {
      // 새로운 작업스레드를 생성하여 정의함
      new Thread(()->{
         while(true) {
            try {
               // 바이트단위로 100개의 문자를 담을수 있는 byteArr변수 초기화
               byte[] byteArr = new byte[100];
               // 입력스트림 최상위객체를 사용해 is변수에 소켓을 입력받음
               InputStream is = socket.getInputStream();
               // 1바이트씩 읽어서 byteArr안에 담음
               int readByte = is.read(byteArr);
               // 만약 다 읽었다면 IO 예외처리
               if(readByte == -1) throw new IOException();
               
               // 스트링객체를 선언하여 (담을변수,n번쨰인덱스부터,읽어들인 변수, 어떻게읽어올지) data에 담음
               String data = new String(byteArr,0,readByte,"UTF-8");
               // 콘솔에 출력
               Platform.runLater(()->displayText("[받기완료]"+data));
               // 예외가 발생하면 출력
            } catch (IOException e) {
               Platform.runLater(()->displayText("[서버 통신 안됨]"));
               stopClient();
               break;
            }
         }
         // 스레드 실행
      }).start();
   }
   
   // send버튼을 눌렀을시 호출될 메소드 정의
   public void send(String code , String data) {
      try {
         // send버튼 눌렀을시 텍스트로 보낼 메세지 출력
         data = code+"|"+data+" ";
         String msg = data+" ";
         // 입력된 data텍스트내용을 utf-8 형식으로 변경하여 byteArr에 담는다.
         byte[] byteArr = data.getBytes("UTF-8");
         // 출력할 socket 출력스트림을 받아서 os에 담는다. 
         OutputStream os = socket.getOutputStream();
         // byteArr를 출력해준다.
         os.write(byteArr);
         // 만약 공간이 꽉차서 덜나온 문자도 보여주게 한다.
         os.flush();
         if (code==" ") {
			displayText("상담사" + data);
		}else {
         // 버튼에 입력된 내용이 보내졌을때 텍스트에 알림 띄움
         displayText("상담사"+msg);
		}
         // 입력창 비움
         txtInput.clear();
      } catch (Exception e) {
         displayText("[서버통신 안됨]");
         stopClient();
      }
   }
   
   // 메소드가 호출될경우 textarea에 메세지를 남김
   public void displayText(String data) {
      txtDisplay.appendText(data+"\n");
   }
   
}