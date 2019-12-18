package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.GsonObj;
//import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args){
//        try {
//            //Gson obj = new Gson();
//            String str = new String(Files.readAllBytes(Paths.get("Gson1.json")));
//            GsonObj obj = new Gson().fromJson(str, GsonObj.class);
//
//
//            //Reader reader = new FileReader("/home/ziv/IdeaProjects/assignment2/Gson1.json");
//            //GsonObj obg = new Gson().fromJson(reader, GsonObj.class);
//
//            ArrayList<String> gadgets = new ArrayList<String>();
//            for(String gadget : obj.inventory){
//                gadgets.add(gadget);
//            }
//            for(int i=0;i<gadgets.size();i++){
//                System.out.println(gadgets.get(i));
//            }
//            ArrayList<Agent> agents = new ArrayList<Agent>();
//            for(GsonObj.GsonSquad agent : obj.squad){
//                agents.add(new Agent(agent.name,agent.serialNumber));
//            }
//            for(int i=0;i<agents.size();i++){
//                System.out.println(agents.get(i).getName());
//                System.out.println(agents.get(i).getSerialNumber());
//            }
//
//
//        } catch (IOException ignored) {
//        }

    }
}
