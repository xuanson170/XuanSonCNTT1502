package com.mycompany.baitaplon;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private JButton createGroupButton;
    private JButton joinGroupButton;
    private JButton leaveGroupButton;
    private PrintWriter out;
    private String username;

    public Client() {
        initGUI();
        connectToServer();
    }
    private void initGUI() {
    JFrame frame = new JFrame("Máy khách");
    frame.setSize(600, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(240, 240, 240)); // Màu nền xám nhạt

    chatArea = new JTextArea();
    chatArea.setEditable(false);
    chatArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Font chữ và kích thước
    JScrollPane scrollPane = new JScrollPane(chatArea);
    frame.add(scrollPane, BorderLayout.CENTER);

    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setBackground(Color.WHITE);
    
    messageField = new JTextField();
    messageField.setFont(new Font("Arial", Font.PLAIN, 14)); // Font chữ và kích thước
    
    sendButton = new JButton("Gửi");
    styleButton(sendButton);
    messageField.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }
    });

    createGroupButton = new JButton("Tạo nhóm");
    styleButton(createGroupButton);

    joinGroupButton = new JButton("Tham gia nhóm");
    styleButton(joinGroupButton);

    leaveGroupButton = new JButton("Rời nhóm");
    styleButton(leaveGroupButton);

    sendButton.addActionListener(e -> sendMessage());
    createGroupButton.addActionListener(e -> createGroup());
    joinGroupButton.addActionListener(e -> joinGroup());
    leaveGroupButton.addActionListener(e -> leaveGroup());

    bottomPanel.add(messageField, BorderLayout.CENTER);
    bottomPanel.add(sendButton, BorderLayout.EAST);
    bottomPanel.add(createGroupButton, BorderLayout.WEST);
    bottomPanel.add(joinGroupButton, BorderLayout.NORTH);
    bottomPanel.add(leaveGroupButton, BorderLayout.SOUTH);

    frame.add(bottomPanel, BorderLayout.SOUTH);
    frame.setVisible(true);
}

private void styleButton(JButton button) {
    button.setFont(new Font("Arial", Font.BOLD, 12)); // Font chữ và kích thước
    button.setBackground(new Color(70, 130, 180)); // Màu xanh dương
    button.setForeground(Color.WHITE);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Canh lề cho nút
    button.setFocusPainted(false); // Loại bỏ đường viền khi focus
    button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Thay đổi icon chuột khi di chuyển qua nút
}

    


    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 1234);
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new ServerListener(socket)).start();
            handleUsernameInput(); // Thêm phần nhập tên người dùng ở đây
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void handleUsernameInput() {
        boolean isValidUsername = false;
        while (!isValidUsername) {
            String usernameInput = JOptionPane.showInputDialog("Nhập tên của bạn:");
            if (usernameInput != null && !usernameInput.trim().isEmpty()) {
                if (isUsernameValid(usernameInput)) {
                    out.println(usernameInput);
                    username = usernameInput;
                    isValidUsername = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Tên người dùng đã tồn tại hoặc không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Tên người dùng không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isUsernameValid(String username) {
        return !username.equals(this.username); // Kiểm tra tên người dùng có trùng với tên hiện tại không
    }

    private void sendMessage() {
    String message = messageField.getText();
    if (!message.isEmpty()) {
        out.println(message);
        messageField.setText("");
    }
}

    private void createGroup() {
    String groupName = JOptionPane.showInputDialog("Nhập tên nhóm:");
    if (groupName == null || groupName.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Tên nhóm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String password = JOptionPane.showInputDialog("Nhập mật khẩu nhóm:");
    if (password == null || password.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Mật khẩu nhóm không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        return;
    }

    out.println("/taonhom " + groupName + " " + password);
}


    private void joinGroup() {
        String groupName = JOptionPane.showInputDialog("Nhập tên nhóm:");
        String password = JOptionPane.showInputDialog("Nhập mật khẩu nhóm:");
        String UserJoin = JOptionPane.showInputDialog("Nhập người dùng muốn thêm vào:");
        if (!groupName.isEmpty() && !password.isEmpty()) {
            out.println("/themthanhvien " + groupName + " " + UserJoin + " " + password);
        } else {
            JOptionPane.showMessageDialog(null, "Tên nhóm và mật khẩu không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void leaveGroup() {
        String groupName = JOptionPane.showInputDialog("Nhập tên nhóm:");
        out.println("/xoathanhviennhom " + groupName + " " + username);
    }

    private class ServerListener implements Runnable {
        private Socket socket;
        private BufferedReader in;

        public ServerListener(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    chatArea.append(message + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    in.close();
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
