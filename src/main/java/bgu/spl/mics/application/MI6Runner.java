package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.GsonObj;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.Squad;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args){
        try {
            //Gson obj = new Gson();
            String str = new String(Files.readAllBytes(Paths.get("Gson1.json")));
            GsonObj obj = new Gson().fromJson(str, GsonObj.class);


            //Reader reader = new FileReader("/home/ziv/IdeaProjects/assignment2/Gson1.json");
            //GsonObj obg = new Gson().fromJson(reader, GsonObj.class);

            //ArrayList<String> gadgets2 = new ArrayList<String>();
            String[] gadgets = new String[obj.inventory.size()];
            for(int i=0;i<obj.inventory.size();i++){
                gadgets[i] = obj.inventory.get(i);
            }
            Inventory.getInstance().load(gadgets);
//            for(int i=0;i<gadgets.length;i++){
//                System.out.println(gadgets[i]);
//            }
            Agent[] agents = new Agent[obj.squad.length];
            for(int i=0;i<obj.squad.length;i++){
                agents[i]=new Agent(obj.squad[i].name,obj.squad[i].serialNumber);
            }
            Squad.GetInstance().load(agents);
            for(int i=0;i<agents.length;i++){
                System.out.println(agents[i].getName());
                System.out.println(agents[i].getSerialNumber());
            }
            String name = obj.services.intelligence[0].missions[0].missionName;
            System.out.println(name);


//            for (int i=0;i<obj.services.intelligence.length;i++){
//                LinkedList<MissionInfo> list = new LinkedList<MissionInfo>();
//                //Subscriber intel = new Intelligence(Integer.toString(i),new List<MissionInfo>missions);
//
//
//
//            }


        } catch (IOException ignored) {
        }

    }
}
