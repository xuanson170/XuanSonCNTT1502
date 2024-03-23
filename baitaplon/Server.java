/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package com.mycompany.baitaplon;
 import java.io.*;
 import java.net.*;
 import java.util.*;
 import java.util.List;
 import java.util.ArrayList;
 import java.util.Map;
 import java.util.HashMap;
 import javax.swing.*;
 import java.awt.*;
 
 public class Server extends JFrame {
     private JTextArea vungChat;
     private Map<String, PrintWriter> clients;
     private Map<String, List<String>> groupMembers;
     private Map<String, String> groupPasswords;
 
     public Server() {
         initGUI();
         startServer();
     }
 
     private void initGUI() {
         setTitle("Máy chủ");
         setSize(450, 300);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLayout(new BorderLayout());
 
         vungChat = new JTextArea();
         vungChat.setEditable(false);
         JScrollPane thanhCuon = new JScrollPane(vungChat);
         add(thanhCuon, BorderLayout.CENTER);
 
         clients = new HashMap<>();
         groupMembers = new HashMap<>();
         groupPasswords = new HashMap<>();
         
 
         setVisible(true);
         
 
         JPanel topPanel = new JPanel(new FlowLayout());
 
         JButton createGroupButton = new JButton("Tạo nhóm");
         createGroupButton.addActionListener(e -> {
             String groupName = JOptionPane.showInputDialog("Nhập tên nhóm:");
             String password = JOptionPane.showInputDialog("Nhập mật khẩu nhóm:");
             createGroup(groupName, password, null);
         });
 
         JButton addMemberButton = new JButton("Thêm thành viên");
         addMemberButton.addActionListener(e -> {
             String groupName = JOptionPane.showInputDialog("Nhập tên nhóm:");
             String memberName = JOptionPane.showInputDialog("Nhập tên thành viên:");
             String password = JOptionPane.showInputDialog("Nhập mật khẩu nhóm:");
             addMemberToGroup(groupName, memberName, password);
         });
 
         JButton removeMemberButton = new JButton("Xóa thành viên");
         removeMemberButton.addActionListener(e -> {
    String memberName = JOptionPane.showInputDialog("Nhập tên thành viên:");
    removeClient(memberName);  // Gọi phương thức để xử lý việc này
         });
 
         topPanel.add(createGroupButton);
         topPanel.add(addMemberButton);
         topPanel.add(removeMemberButton);
         add(topPanel, BorderLayout.NORTH);
 
 
     }
 
     private void startServer() {
         try (ServerSocket serverSocket = new ServerSocket(1234)) {
             vungChat.append("Máy chủ đang chạy...\n");
             while (true) {
                 Socket clientSocket = serverSocket.accept();
                 new ClientHandler(clientSocket).start();
             }
         } catch (IOException ex) {
             ex.printStackTrace();
         }
     }
     private synchronized boolean isUsernameTaken(String username) {
        return clients.containsKey(username);
    }
     private synchronized boolean isGroupNameTaken(String groupName) {
         return groupMembers.containsKey(groupName);
    }
     private synchronized void sendMessageToAll(String message) {
         for (PrintWriter client : clients.values()) {
             client.println(message);
         }
     }
 
     private synchronized void sendUserList(PrintWriter writer) {
         writer.println("Danh sách người dùng");
         for (String username : clients.keySet()) {
             writer.println(username);
         }
         writer.println("kết thúc");
     }
 
     private synchronized void createGroup(String groupName, String password, PrintWriter creator) {
         groupMembers.put(groupName, new ArrayList<>());
         groupPasswords.put(groupName, password);
         creator.println("Nhóm " + groupName + " được tạo thành công");
     }
 
     private synchronized void addMemberToGroup(String groupName, String memberName, String password) {
         if (groupPasswords.containsKey(groupName) && groupPasswords.get(groupName).equals(password)) {
             List<String> members = groupMembers.get(groupName);
             if (members != null) {
                 members.add(memberName);
                 groupMembers.put(groupName, members);
                 clients.get(memberName).println("thêm  " + memberName  + " vào nhóm " + groupName + " thành công " );
             }
         }
     }
 
     private synchronized void removeMemberFromGroup(String groupName, String memberName) {
         List<String> members = groupMembers.get(groupName);
         if (members != null) {
             members.remove(memberName);
             groupMembers.put(groupName, members);
             clients.get(memberName).println("Bạn đã rời khỏi nhóm  " + groupName + " thành công");
         }
     }
 
     private synchronized void removeClient(String username) {
         clients.remove(username);
         for (List<String> members : groupMembers.values()) {
             members.remove(username);
         }
         sendMessageToAll(username + " đã rời khỏi chat.");
     }
 
     private class ClientHandler extends Thread {
         private Socket socket;
         private BufferedReader in;
         private PrintWriter out;
         private String username; // Moved the variable here
 
         public ClientHandler(Socket socket) {
             this.socket = socket;
         }
 
         @Override
         public void run() {
             try {
                 setupStreams();
                 handleUsernameInput();
                 informAllAboutJoining();
                 sendUserList(out);
                 handleClientMessages();
             } catch (IOException e) {
                 e.printStackTrace();
             } finally {
                 closeConnection();
             }
         }
 
         private void setupStreams() throws IOException {
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream(), true);
         }
 
         private void handleUsernameInput() throws IOException {
                    //  out.println("NHAPTEN");
                     username = in.readLine();
         while (isUsernameTaken(username)) {
             out.println("Tên người dùng đã tồn tại, vui lòng chọn tên khác.");
             username = in.readLine();
         }
         clients.put(username, out);
                  }
 
         private void informAllAboutJoining() {
    String message = username + " đã tham gia chat.";
    vungChat.append(message + "\n");  // Hiển thị thông báo trên JTextArea
    sendMessageToAll(message);
}
 
         private void handleClientMessages() throws IOException {
             String inputLine;
             while ((inputLine = in.readLine()) != null) {
                 if (inputLine.startsWith("/w")) {
                     handlePrivateMessage(inputLine);
                 } else if (inputLine.startsWith("/taonhom")) {
                     handleGroupCreation(inputLine);
                 } else if (inputLine.startsWith("/themthanhvien")) {
                     handleAddMemberToGroup(inputLine);
                 } else if (inputLine.startsWith("/xoathanhviennhom")) {
                     handleRemoveMemberFromGroup(inputLine);
                 } else if (inputLine.startsWith("/nhom")) {
                     handleGroupMessage(inputLine);
                 } else {
                     sendMessageToAll(username + ": " + inputLine);
                 }
             }
         }
 
                private void handlePrivateMessage(String inputLine) {
     String[] parts = inputLine.split(" ", 3);
     if (parts.length == 3) {
         String recipient = parts[1];
         String message = parts[2];
         
         PrintWriter recipientWriter = clients.get(recipient);
         
         if (recipientWriter != null) {
             recipientWriter.println(username + " (riêng): " + message);  // Gửi tin nhắn cho người nhận
         } else {
             out.println("Người dùng '" + recipient + "' không tồn tại.");
             return;
         }
 
         out.println(username + " (riêng): " + message);  // Gửi tin nhắn cho người gửi
     } else {
         out.println("Định dạng tin nhắn riêng không hợp lệ.");
     }
 }
 
 
 
 
        private void handleGroupCreation(String inputLine) {
    String[] parts = inputLine.split(" ", 3);
    
    if (parts.length == 3) {
        String groupName = parts[1];
        String password = parts[2];

        // Kiểm tra tên nhóm đã tồn tại hay chưa
        if (isGroupNameTaken(groupName)) {
            out.println("Tên nhóm đã tồn tại, vui lòng chọn tên khác.");
        } else {
            createGroup(groupName, password, out);
        }
    } else {
        out.println("Định dạng tạo nhóm không hợp lệ.");
    }
}

 
         private void handleAddMemberToGroup(String inputLine) {
             String[] parts = inputLine.split(" ", 4);
             if (parts.length == 4) {
                 String groupName = parts[1];
                 String memberName = parts[2];
                 String password = parts[3];
                 addMemberToGroup(groupName, memberName, password);
             } else {
                 out.println("Định dạng thêm thành viên không hợp lệ.");
             }
         }
 
         private void handleRemoveMemberFromGroup(String inputLine) {
    String[] parts = inputLine.split(" ", 3);
    if (parts.length == 3) {
        String groupName = parts[1];
        String memberName = parts[2];

        if (groupName.trim().isEmpty() || memberName.trim().isEmpty()) {
            out.println("Tên nhóm hoặc tên thành viên không được để trống!");
            return;
        }

        removeMemberFromGroup(groupName, memberName);
    } else {
        out.println("Định dạng xóa thành viên nhóm không hợp lệ.");
    }
}
         private synchronized void removeClient(String username) {
    if (clients.containsKey(username)) {
        clients.remove(username);
        for (List<String> members : groupMembers.values()) {
            members.remove(username);
        }
        sendMessageToAll(username + " đã rời khỏi chat.");
        vungChat.append("Người dùng '" + username + "' đã rời khỏi chat.\n"); // Thêm thông báo vào vungChat
    } else {
        vungChat.append("Người dùng '" + username + "' không tồn tại.\n");
    }
}

         private void handleGroupMessage(String inputLine) {
    String[] parts = inputLine.split(" ", 3); // Thay đổi số lượng phần cần split
    if (parts.length == 3) {
        String groupName = parts[1];
        String message = parts[2];
        
        List<String> members = groupMembers.get(groupName);
        if (members != null && members.contains(username)) {
            for (String member : members) {
                if (clients.containsKey(member)) {
                    clients.get(member).println(username + " (nhóm " + groupName + "): " + message);
                }
            }
        } else {
            out.println("Bạn không phải là thành viên của nhóm " + groupName);
        }
    } else {
        out.println("Định dạng tin nhắn nhóm không hợp lệ.");
    }
}

 
         private void closeConnection() {
             if (username != null) {
                 removeClient(username);
             }
             try {
                 socket.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
 
     public static void main(String[] args) {
         new Server();
     }
 }
 