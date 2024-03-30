
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Group {
    private String name;
    private ArrayList<String> members;

    public Group(String name) {
        this.name = name;
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addMember(String member) {
        members.add(member);
    }

    public ArrayList<String> getMembers() {
        return members;
    }
}

public class GroupManagerFrame extends JFrame {
    private ArrayList<Group> groups;

    private JList<String> groupList;
    private DefaultListModel<String> listModel;
    private JButton addGroupButton;
    private JButton removeGroupButton;
    private JButton saveGroupsButton;
    private JButton exitButton;
    private JButton openChatButton; // Thêm nút mở phòng chat

    public GroupManagerFrame() {
        groups = new ArrayList<>();
        initComponents();
        setupListeners();
        setupLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        groupList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(groupList);

        addGroupButton = new JButton("Thêm nhóm");
        removeGroupButton = new JButton("Xóa nhóm");
        saveGroupsButton = new JButton("Lưu nhóm");
        exitButton = new JButton("Thoát");
        openChatButton = new JButton("Mở Phòng Chat"); // Thêm nút mở phòng chat
         JButton sendButton = new JButton("Gửi");
         sendButton.setPreferredSize(new Dimension(80, 30));

        // Thêm ActionListener cho nút "Gửi"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Thêm xử lý gửi tin nhắn vào đây
            }
        });
    }

    private void setupListeners() {
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupName = JOptionPane.showInputDialog(GroupManagerFrame.this, "Nhập tên nhóm mới:");
                if (groupName != null && !groupName.isEmpty()) {
                    Group newGroup = new Group(groupName);
                    groups.add(newGroup);
                    listModel.addElement(groupName);
                }
            }
        });

        removeGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = groupList.getSelectedIndex();
                if (selectedIndex != -1) {
                    groups.remove(selectedIndex);
                    listModel.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(GroupManagerFrame.this, "Vui lòng chọn một nhóm để xóa.");
                }
            }
        });

        saveGroupsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(GroupManagerFrame.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    saveGroupsToFile(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        openChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = groupList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String groupName = listModel.getElementAt(selectedIndex);
                    openChat(groupName);
                } else {
                    JOptionPane.showMessageDialog(GroupManagerFrame.this, "Vui lòng chọn một nhóm để mở phòng chat.");
                }
            }
        });
    }

    private void setupLayout() {
        JPanel groupPanel = new JPanel(new BorderLayout());
        groupPanel.add(new JLabel("Danh sách nhóm:"), BorderLayout.NORTH);
        groupPanel.add(groupList, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5)); // Thêm một cột nút button
        buttonPanel.add(addGroupButton);
        buttonPanel.add(removeGroupButton);
        buttonPanel.add(saveGroupsButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(openChatButton); // Thêm nút mở phòng chat

        add(groupPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveGroupsToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Group group : groups) {
                writer.write(group.getName());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin nhóm vào file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu thông tin nhóm vào file.");
            e.printStackTrace();
        }
    }

    private void openChat(String groupName) {
        // Tạo cửa sổ phòng chat mới với tên nhóm được chọn
        JFrame chatFrame = new JFrame(groupName + " Chat Room");
        // Thêm các thành phần phòng chat vào đây...
       
        JTextArea chatArea = new JTextArea();
        JTextField messageField = new JTextField();
        JButton addClientButton = new JButton("Thêm Client");
        JButton sendButton = new JButton("Gửi"); // Thêm nút "Gửi"
        
        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientName = JOptionPane.showInputDialog(chatFrame, "Nhập tên client:");
                if (clientName != null && !clientName.isEmpty()) {
                    // Logic để thêm client vào phòng chat
                    chatArea.append(clientName + " đã tham gia phòng chat.\n");
                }
            }
        });
        
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý khi nút "Gửi" được nhấn
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    chatArea.append("You: " + message + "\n");
                    //updateChatArea(chatArea, messages); // Cập nhật khu vực chat với tin nhắn mới
                    // Clear the message field after sending
                    messageField.setText("");
                }
            }
        });
        chatFrame.setLayout(new BorderLayout());
        chatFrame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.add(messageField, BorderLayout.CENTER);
    inputPanel.add(addClientButton, BorderLayout.EAST);
    inputPanel.add(sendButton, BorderLayout.SOUTH); // Thêm nút "Gửi" vào dưới cùng
    
    chatFrame.add(inputPanel, BorderLayout.SOUTH); // Thêm panel này vào cửa sổ chat
        
        chatFrame.setSize(600, 300);
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GroupManagerFrame();
            }
        });
    }
}
