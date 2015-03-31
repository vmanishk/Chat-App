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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class ServerThread extends Thread {

    Socket connectionSocket;
    int port;
    boolean running = true;
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    // Thread thread;

    public ServerThread(Socket connSocket) {
        this.connectionSocket = connSocket;
        conn = databaseConnect.ConnectDb();
        // System.out.println("here i m");
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            out = new PrintWriter(connectionSocket.getOutputStream(), true);
            //System.out.println("here");
            String Password = "";
            //System.out.println("input : " + input);
            //int port = Integer.parseInt(input);
            //System.out.println("port : "+port);
            boolean done = false;
            String Username = "";
            //String input="";
            while (!done) {
                //out.println("Enter 1 to login and 2 to signup");
                int choice = Integer.parseInt(in.readLine());
                //out.println("Username : ");
                Username = in.readLine();
                //out.println("Password : ");
                Password = in.readLine();
                switch (choice) {
                    case 1:
                        done = Login(Username, Password);
                        /*{
                         //fetch port and ip;
                         //update ip and port;
                         // String user = in.readLine();
                         //System.out.println("in login");
                         String sql = "UPDATE User SET IP = ?, PORT = ? WHERE Username = ?";
                         try {
                         pst = conn.prepareStatement(sql);
                         pst.setString(1, "localhost");
                         pst.setInt(2, port);
                         pst.setString(3, Username);
                         pst.executeUpdate();
                         pst.close();
                         System.out.println("done updation");
                         } catch (Exception e) {
                         }
                         //out.writeInt(1);
                         out.println("connected");
                         done = true;
                         } else {
                         out.println("Incorrect username or password");
                         }*/
                        break;
                    case 2:
                        done = Signup(Username, Password);
                        /*{
                         //fetch port and ip;
                         //update ip and port;
                         //String user = in.readLine();
                         System.out.println("in signup");
                         String sql = "UPDATE User SET IP=?,PORT=? where Username=?";
                         try {
                         pst = conn.prepareStatement(sql);
                         pst.setString(1, "localhost");
                         pst.setInt(2, port);
                         pst.setString(3, username);
                         pst.executeUpdate();
                         pst.close();
                         System.out.println("done update");
                         } catch (Exception e) {
                         System.out.println("Error in updating " + username);
                         }
                         //out.writeInt(0);
                         out.println("connected");
                         done = true;
                         }*/
                        break;
                }
                if (done) {
                    out.println("okay");
                    out.flush();
                } else {
                    out.println("");
                    out.flush();
                }
            }
            // return friend list!!
            //get ip and port
            int Port = Integer.parseInt(in.readLine());
            String Ip = connectionSocket.getInetAddress().getHostAddress();
            //update ip and port of username
            update_IpPort(Username,Port,Ip);
            while (true) {
                try {
                    String inp = in.readLine();
                    System.out.println(Username + " : " + inp);
                    // add option to add friend
                    if (inp.equals("try to connect")) {
                        String user = in.readLine();
                       // JOptionPane.showMessageDialog(null, "Requested for "+user);
                        String sql1 = "select IP,PORT,Status from User where Username=?";
                        try {
                            pst = conn.prepareStatement(sql1);
                            pst.setString(1, user);
                            rs = pst.executeQuery();
                            if (rs.next()) {
                                String IP = rs.getString("IP");
                                int port = rs.getInt("PORT");
                                int status = rs.getInt("Status");
                                if (status == 1) {
                                    out.println("");
                                }/*
                                 else{
                                 //update the status ----- !!
                                 update_status(username, 1);
                                 out.println(IP);
                                 out.println(Port + "");
                                 inp = in.readLine();
                                 if(inp.equals("disconnect")){  //update the status to false
                                 update_status(username, 0);
                                 }
                                 }*/ else {
                                    out.println(IP);
                                    out.println(port + "");
                                }
                                pst.close();
                            } else {
                                out.println("");
                            }
                        } catch (Exception e) {
                            System.out.println("excepton here\n\n");
                        }
                    } else if (inp.equals("connect")) {
                        update_status(Username, 1);
                    } else if (inp.equals("disconnect")) {
                        update_status(Username, 0);
                    } else if (inp.equals("add friend")) {
                        String user = in.readLine();
                        add_friend(Username, user);
                    }
                } catch (Exception e) {
                    //  
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            //  
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    void update_IpPort(String Username,int port,String IP) {
        String sql = "UPDATE User SET IP = ?, PORT = ? WHERE Username = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, IP);
            pst.setInt(2, port);
            pst.setString(3, Username);
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
        }
    }

    void add_friend(String user, String friend) {
        String sql = "INSERT INTO Friends (Username,Friend) VALUES (?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, friend);
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            //
        }
    }

    ArrayList<String> get_friends(String user) {
        ArrayList<String> friends = new ArrayList<String>();
        String sql = "select Friend from User where Username=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            rs = pst.executeQuery();
            while (rs.next()) {
                friends.add(rs.getString("Friend"));
            }
            pst.close();
        } catch (Exception e) {
            //return false;
        }
        return friends;
    }

    void update_status(String user, int value) {
        String sql = "UPDATE User SET Status=? where Username=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, value);
            pst.setString(2, user);
            pst.executeUpdate();
            pst.close();
            //return true;
        } catch (Exception e) {
            //return false;
        }
    }

    boolean Login(String username, String password) {
        String sql = "select * from User where Username=? and Password=?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            rs = pst.executeQuery();
            if (rs.next()) {
                pst.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    boolean Signup(String username, String password) {
        String sql = "INSERT INTO User (Username,Password) VALUES (?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            pst.executeUpdate();
            pst.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

public class AuthServer {

    static Connection conn = null;
    // ResultSet rs=null;
    // PreparedStatement pst=null;

    public static void main(String args[]) throws IOException {
//        databaseConnect db = new databaseConnect();
        ServerSocket welcomeSocket = new ServerSocket(6098);
        conn = databaseConnect.ConnectDb();
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            ServerThread newserver = new ServerThread(connectionSocket);
            newserver.start();
        }
    }
}
