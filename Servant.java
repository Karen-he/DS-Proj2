package Server;

import RMIInterfaces.Server;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Servant extends UnicastRemoteObject implements Server{
    private Gson gson = new Gson();
    private int i;
    private int j;
    private JsonObject jsonObject = new JsonObject();
    private String jsonpack;
    public Servant() throws RemoteException{
        i = 0;
        j = 0;
        //this.gson = gson;
    }
    @Override
    public String sendGson(String send) throws RemoteException{
        jsonObject.addProperty("keyboard", send);
        jsonpack = gson.toJson(jsonObject);
        System.out.println("the jsonpack in servant"+jsonpack);
        i++;
        System.out.println("the number of command: "+i);
        return jsonpack;
    }
    @Override
    public String receiveGson() throws RemoteException{
        System.out.println(jsonpack);
        JsonElement jsonElement = new JsonParser().parse(jsonpack);
        jsonObject = jsonElement.getAsJsonObject();
        String command = jsonObject.get("keyboard").getAsString();
        //System.out.println("back to string: "+actual);
        j++;
        System.out.println("the number of command: "+j);
        return command;
    }
}
