package Client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import RMIInterfaces.Server;

public class Client {
    public static void main(String[] args){
        try {
            //Connect to the rmiregistry that is running on localhost
            Registry registry = LocateRegistry.getRegistry(2020);

            //Retrieve the stub/proxy for the remote math object from the registry
            Server gsonServant = (Server) registry.lookup("Gson");
            String receive = "";
            //System.out.println("The received massage: "+receive);
            Scanner keybord = new Scanner(System.in);
            /*String temp = keybord.nextLine();
            System.out.println(temp);
            String output = gsonServant.sendGson(temp);
            System.out.println("output: "+output);*/

            boolean run = true;
            while(run){
                receive = gsonServant.receiveGson();
                System.out.println("The received massage: "+receive);
                String temp = keybord.nextLine();
                System.out.println("input from keyboard: "+temp);
                if(temp.equals("exit")){
                    run = false;
                }else{
                    String output = gsonServant.sendGson(temp);
                    System.out.println("output: "+output);
                }
            }
            /*
            //Call methods on the remote object as if it was a local object
            double addResult = remoteMath.add(5.0, 3.0);
            System.out.println("5.0 + 3.0 = " + addResult);
            double subResult = remoteMath.subtract(5.0, 3.0);
            System.out.println("5.0 - 3.0 = " + subResult);
            */
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
