package Server;

import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;

public class Server {
    private Hashtable<Integer, String> userPassword;
    private Hashtable<Integer, User> userData;

    /*
    public Server(Hashtable<Integer, String> userPassword, Hashtable<Integer, User> userData) {
        this.userPassword = userPassword;
        this.userData = userData;
    }*/

    public static void main(String[] args){
        try {
            Server mainserver = new Server();
            //read in users
            mainserver.readInUsers("users.txt", mainserver.userData);
            //read in passwords
            mainserver.readInPassword("password.txt", mainserver.userPassword);
            //Export the remote math object to the Java RMI runtime so that it
            //can receive incoming remote calls.
            //Because RemoteMath extends UnicastRemoteObject, this
            //is done automatically when the object is initialized.
            GsonServant gsonServant = new GsonServant();
            ChatServant chatServant = new ChatServant();

            //Publish the remote object's stub in the registry under the name "Compute"
            Registry registry = LocateRegistry.createRegistry(2020);
            registry.bind("Gson", gsonServant);
            registry.bind("Chatbox", chatServant);

            System.out.println("ServerInterface ready");
            Scanner keybord = new Scanner(System.in);
            //keep listening
            boolean run = true;
            while(run){
                String[] whiteboard = gsonServant.receivePaints();
                String wb0 = whiteboard[0];
                String wb1 = whiteboard[1];
                if(wb0.equals("")||wb1.equals("")){
                    System.out.println("empty jsonPack");
                }else{
                    System.out.println("the string array received in server: "+ whiteboard[0]
                            + " ### " + whiteboard[1]);
                }
                /*
                String temp = keybord.nextLine();
                System.out.println("input from keyboard: "+temp);
                if(temp.equals("exit")){
                    run = false;
                }else{
                    String output = gsonServant.sendGson(temp);
                    System.out.println("output: "+output);
                }*/
            }
            //The server will continue running as long as there are remote objects exported into
            //the RMI runtime, to remove remote objects from the
            //RMI runtime so that they can no longer accept RMI calls you can use:
            // UnicastRemoteObject.unexportObject(remoteMath, false);
            //write out users
            mainserver.saveUsers(mainserver.userData, mainserver.userPassword, "users.txt", "password.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readInUsers (String filename, Hashtable userData) throws IOException{
        BufferedReader input = new BufferedReader(new FileReader(filename));
        while(true){
            String user = input.readLine();
            if(user!=null){
                String[] info = user.split(",");
                int userID = Integer.parseInt(info[0]);
                String username = info[1];
                String email = info[2];
                boolean managerTag = Boolean.parseBoolean(info[3]);
                User currentUser = new User(username, userID, email, managerTag);
                userData.put(userID, currentUser);
            } else{ break; }
        }
    }

    private void readInPassword(String filename, Hashtable userPassword) throws IOException{
        Hashtable<Integer, String> userSystem = new Hashtable<Integer, String>();
        BufferedReader pwInput = new BufferedReader(new FileReader(filename));
        while(true){
            String line = pwInput.readLine();
            if(line!=null){
                String[] info = line.split(",");
                int userID = Integer.parseInt(info[0]);
                String password = info[1];
                userPassword.put(userID, password);
            } else{ break; }
        }
    }

    private void saveUsers(Hashtable userData, Hashtable userPassword, String userDataFile, String userPasswordFile) throws FileNotFoundException {
        //save user data
        PrintWriter output = new PrintWriter(new FileOutputStream(userDataFile));
        Set userIDList = userData.keySet();
        for(Object i : userIDList){
            output.println((userData.get(i).toString()));
        }
        output.flush();
        output.close();

        //save password
        PrintWriter outputp = new PrintWriter(new FileOutputStream(userDataFile));
        Set userPasswordList = userPassword.keySet();
        for(Object i : userPasswordList){
            outputp.println((userPassword.get(i).toString()));
        }
        outputp.flush();
        outputp.close();
    }
}
