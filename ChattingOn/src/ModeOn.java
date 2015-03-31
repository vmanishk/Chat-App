/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ModeOn extends javax.swing.JFrame {

    /**
     * Creates new form ModeOn
     */
    volatile int isconnected;
    Socket socketClient;
    BufferedReader in; //= new DataInputStream(socket.getInputStream());
    PrintWriter out;
    BufferedReader reader;// = new BufferedReader(streamreader);
    PrintWriter writer;

    public ModeOn(BufferedReader in, PrintWriter out) {
        initComponents();
        this.in = in;
        this.out = out;
        start();
    }

    public void start() {
        try {
            // take ip address of the auth socket;
            //Socket socket = new Socket("localhost", 6098);
            isconnected = 1;
            //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //out = new PrintWriter(socket.getOutputStream(), true);
            Server s = new Server();
            s.start();
            //send port;
            //System.out.println("port : "+s.port);
            //   s.thread.start();
        } catch (Exception ex) {
            Logger.getLogger(ModeOn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class Server extends Thread {

        ServerSocket server;
        int port;

        Server() {
            port = 0;

            for (int i = 6100; i <= 7777; i++) {
                try {
                    this.server = new ServerSocket(i);
                    this.port = i;
                    break;
                } catch (Exception e) {
                }
            }
            System.out.println("port assigned to : " + port);
        }

        public void run() {
            try {
                //String input = "";
                //boolean done = false;
                System.out.println("sending port "+port);
                out.println(this.port + "");
                out.flush();
                // System.out.println("done sending port");
                /*while (!done) {
                    try {
                        input = in.readLine();
                        //  System.out.println("input" + input);
                        chatTextArea.append("INCOMING" + ": " + input + "\n");
                        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                        input = in.readLine();
                        chatTextArea.append("INCOMING" + ": " + input + "\n");
                        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                        input = in.readLine();
                        chatTextArea.append("INCOMING" + ": " + input + "\n");
                        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                        input = in.readLine();
                        chatTextArea.append("INCOMING" + ": " + input + "\n");
                        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                        if (input.equals("connected")) {
                            done = true;
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ModeOn.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
                while (true) {
                    //System.out.println("isconnected = " + isconnected);
                    Thread.sleep(10);
                    if (isconnected == 1) {
                        try {
                            System.out.println("connected");
                            socketClient = server.accept();
                            txt_Username.setEditable(false);
                            InputStreamReader streamreader = new InputStreamReader(socketClient.getInputStream());
                            reader = new BufferedReader(streamreader);
                            writer = new PrintWriter(socketClient.getOutputStream());
                            isconnected = 2;
                            chatTextArea.append("Connected to : " + socketClient.getInetAddress().getHostAddress() + "\n");
                            chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                            out.println("connect");
                            ListenThread();
                        } catch (IOException ex) {
                            Logger.getLogger(ModeOn.class.getName()).log(Level.SEVERE, null, ex);
                            JOptionPane.showMessageDialog(null, "Server failed to accept");
                        }
                    }/* else if (socketClient != null && isconnected == 2) {
                        Socket tempSoc = null;
                        tempSoc = server.accept();
                        InputStreamReader streamreader = new InputStreamReader(tempSoc.getInputStream());
                        BufferedReader tempReader = new BufferedReader(streamreader);// = new BufferedReader(streamreader);
                        PrintWriter tempWriter = new PrintWriter(tempSoc.getOutputStream());
                        tempWriter.println("I am busy.....tata\n");
                        tempWriter.flush();
                        tempWriter.println("quit");
                        tempWriter.flush();
                        tempReader = null;
                        tempWriter = null;
                        tempSoc.close();
                    }*/
                }
            } catch (Exception ex) {
                Logger.getLogger(ModeOn.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class IncomingReader implements Runnable {

        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while (socketClient != null && isconnected == 2 && (stream = reader.readLine()) != null) {
                    chatTextArea.append("INCOMING" + ": " + stream + "\n");
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                    if (stream.equals("quit")) {
                        System.out.println("Connection quits");
                        chatTextArea.append("Connection has disconnected.\n");
                        chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                        isconnected = 1;
                        txt_Username.setEditable(true);
                        txt_Username.setText("");
                        out.println("disconnect");
                        out.flush();
                        socketClient.close();
                        writer = null;
                        reader = null;
                        break;
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

    public void ListenThread() {
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }

    void friendList() throws IOException{
        //taking friends one by one
        String inp = new String();
        while((inp=in.readLine())!=null || !inp.equals("")){
            addFriendList(inp);
        }
    }
    void addFriendList(String user){
        Friend_list.append(user + "\n");
        Friend_list.setCaretPosition(chatTextArea.getDocument().getLength());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Disconnect_btn = new javax.swing.JButton();
        txt_friend = new javax.swing.JTextField();
        Connect_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        Friend_list = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        TypeArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        Send_btn = new javax.swing.JButton();
        txt_Username = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Add_friendbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Disconnect_btn.setText("Disconnect");
        Disconnect_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Disconnect_btnActionPerformed(evt);
            }
        });

        Connect_btn.setText("Connect");
        Connect_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Connect_btnActionPerformed(evt);
            }
        });

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setLineWrap(true);
        chatTextArea.setRows(5);
        jScrollPane1.setViewportView(chatTextArea);

        Friend_list.setColumns(20);
        Friend_list.setRows(5);
        jScrollPane3.setViewportView(Friend_list);

        TypeArea.setColumns(20);
        TypeArea.setRows(5);
        jScrollPane2.setViewportView(TypeArea);

        jLabel2.setText("Friends");

        Send_btn.setText("Send");
        Send_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Send_btnActionPerformed(evt);
            }
        });

        jLabel1.setText("Username");

        Add_friendbtn.setText("Add Friend");
        Add_friendbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Add_friendbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Send_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Connect_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Disconnect_btn)
                                .addGap(18, 18, 18)
                                .addComponent(txt_friend, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Add_friendbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Connect_btn)
                    .addComponent(Disconnect_btn)
                    .addComponent(Add_friendbtn)
                    .addComponent(txt_friend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Send_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Connect_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Connect_btnActionPerformed
        // TODO add your handling code here:
        String user = txt_Username.getText();
        txt_Username.setEditable(false);
        if (user != null && !user.equals("") && isconnected == 1) {
            try {
                out.println("try to connect");
                chatTextArea.append("try to connect to " + user + "\n");
                chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                out.println(user);
                System.out.println("user : " + user);
                String IP = in.readLine();
                if (IP != null && !IP.equals("")) {
                    int port = Integer.parseInt(in.readLine());
                    //System.out.println("port  " + port);
                    //JOptionPane.showMessageDialog(null, "port " + port);
                    socketClient = new Socket(IP, port);
                    InputStreamReader streamreader = new InputStreamReader(socketClient.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(socketClient.getOutputStream());
                    isconnected = 2;
                    out.println("connect");
                    out.flush();
                    chatTextArea.append("connection established to " + user + "\n");
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                    ListenThread();
                } else {
                    chatTextArea.append("connection failed to " + user + "\n");
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                    txt_Username.setEditable(true);
                }
                //connect with the server of user;  //not online then show
            } catch (IOException ex) {
                //  Logger.getLogger(User_info.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (isconnected == 2) {
            JOptionPane.showMessageDialog(null, "Alredy connected");
        } else {
            JOptionPane.showMessageDialog(null, "Enter the user first with whom to chat or login properly");
            txt_Username.setEditable(true);
        }
    }//GEN-LAST:event_Connect_btnActionPerformed

    private void Disconnect_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Disconnect_btnActionPerformed
        // TODO add your handling code here:
        try {
            chatTextArea.append("Disconnected.\n");
            writer.println("quit");
            writer.flush();
            out.println("disconnect");
            out.flush();
            isconnected = 1;
            reader = null;
            writer = null;
            socketClient.close();
        } catch (Exception ex) {
            chatTextArea.append("Failed to disconnect. \n");
        }
        txt_Username.setEditable(true);
        txt_Username.setText("");
    }//GEN-LAST:event_Disconnect_btnActionPerformed

    private void Send_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Send_btnActionPerformed
        // TODO add your handling code here:
        String nothing = "";
        if ((TypeArea.getText()).equals(nothing)) {
            TypeArea.setText("");
            TypeArea.requestFocus();
        } else {
            if (isconnected == 2) {
                try {
                    writer.println(TypeArea.getText());
                    chatTextArea.append("OUTGOING" + ": " + TypeArea.getText() + "\n");
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                    writer.flush(); // flushes the buffer
                } catch (Exception ex) {
                    chatTextArea.append("Message was not sent. \n");
                }
            } else {
                try {
                    out.println(TypeArea.getText());
                    chatTextArea.append("OUTGOING" + ": " + TypeArea.getText() + "\n");
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                    out.flush();
                } catch (Exception e) {
                }
            }
            TypeArea.setText("");
            TypeArea.requestFocus();
        }
        TypeArea.setText("");
        TypeArea.requestFocus();
    }//GEN-LAST:event_Send_btnActionPerformed

    private void Add_friendbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Add_friendbtnActionPerformed
        // TODO add your handling code here:
        String user = txt_friend.getText();
        if (user != null && !user.equals("") && isconnected == 1){
            try{
                out.println("add friend");
                out.flush();
                out.println(user);
                out.flush();
                addFriendList(user);
            } catch(Exception e){
                JOptionPane.showMessageDialog(null, "Failed to add");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Enter the user first with whom to chat or login properly");
        }
    }//GEN-LAST:event_Add_friendbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ModeOn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ModeOn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ModeOn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ModeOn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        //java.awt.EventQueue
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               // ModeOn m = new ModeOn();
               // m.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_friendbtn;
    private javax.swing.JButton Connect_btn;
    private javax.swing.JButton Disconnect_btn;
    private javax.swing.JTextArea Friend_list;
    private javax.swing.JButton Send_btn;
    private javax.swing.JTextArea TypeArea;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txt_Username;
    private javax.swing.JTextField txt_friend;
    // End of variables declaration//GEN-END:variables
}
