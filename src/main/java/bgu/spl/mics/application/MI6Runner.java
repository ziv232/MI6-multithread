package bgu.spl.mics.application;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.Intelligence;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
import com.google.gson.Gson;

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
        try {
            //Gson obj = new Gson();
            System.out.println("First Line of main");
            String str = new String(Files.readAllBytes(Paths.get("src/main/java/bgu/spl/mics/application/input1")));
            System.out.println("2nd Line of main");

            GsonObj obj = new Gson().fromJson(str, GsonObj.class);


            //Reader reader = new FileReader("/home/ziv/IdeaProjects/assignment2/Gson1.json");
            //GsonObj obg = new Gson().fromJson(reader, GsonObj.class);

            //ArrayList<String> gadgets2 = new ArrayList<String>();
            ArrayList<Subscriber> ourBuddies = new ArrayList<Subscriber>();
            String[] gadgets = new String[obj.inventory.size()];
            for(int i=0;i<obj.inventory.size();i++){
                gadgets[i] = obj.inventory.get(i);
            }
            Inventory.getInstance().load(gadgets);//TODO Load the Inventory
//            for(int i=0;i<gadgets.length;i++){
//                System.out.println(gadgets[i]);
//            }
            //============Check InventoryPrint
//            Inventory.getInstance().printToFile("/users/studs/bsc/2020/zivshir/IdeaProjects/assignment2/src/main/java/bgu/spl/mics/application/InventoryOutPut.json");


            Agent[] agents = new Agent[obj.squad.length];
            for(int i=0;i<obj.squad.length;i++){
                agents[i]=new Agent(obj.squad[i].name,obj.squad[i].serialNumber);
            }
            Squad.GetInstance().load(agents);//TODO Load the Squad
            for(int i=0;i<agents.length;i++){
               // System.out.println(agents[i].getName());
                //System.out.println(agents[i].getSerialNumber());
            }
            //String name = obj.services.intelligence[0].missions[0].missionName;
//            System.out.println(name);
//            System.out.println(obj.services.M);
//            System.out.println(obj.services.Moneypenny);
//            System.out.println(obj.services.time);

            for(int i=0;i<obj.services.M;i++){//Create the M instances and push them to the List of Instances
                int name = i+1;
                M m = new M(Integer.toString(name));
                ourBuddies.add(m);
            }

            for(int i=0;i<obj.services.Moneypenny;i++){
                int name = i+1;
                Moneypenny mp = new Moneypenny(Integer.toString(name));
                ourBuddies.add(mp);
            }


            for (int i=0;i<obj.services.intelligence.length;i++){   //Loop all the Intelligence
                ArrayList<MissionInfo> list = new ArrayList<MissionInfo>();

                Intelligence intel = new Intelligence(Integer.toString(i+1));//TODO Create the Intelligences
                for(int j=0;j<obj.services.intelligence[i].missions.length;j++){    //Loop all over the missions of each Intel
                    ArrayList<String> agentslist = new ArrayList<>();
                    for(int l=0;l<obj.services.intelligence[i].missions[j].serialAgentsNumbers.length;l++){ //Loop all over the Agents of the mission
                        agentslist.add(obj.services.intelligence[i].missions[j].serialAgentsNumbers[l]);
                        //System.out.println(agentslist.get(l));
                    }
//                    System.out.println();
//                    System.out.println("Parse between every mission created on class MI6");
                    list.add(new MissionInfo(obj.services.intelligence[i].missions[j].missionName,
                            agentslist,obj.services.intelligence[i].missions[j].gadget,
                            obj.services.intelligence[i].missions[j].timeIssued,obj.services.intelligence[i].missions[j].timeExpired,
                            obj.services.intelligence[i].missions[j].duration));//     Add the mission to the Intelligence missionList.

                }
                intel.setMissions(list);// Set the list of missions we created to the intelligence.
                ourBuddies.add(intel);
            }
//            System.out.println("fuckkkkkkkkkkkkkkkkkkkkkkkkkk 2.0");
            Q q = new Q("1");
            ourBuddies.add(q);
            TimeService ts = new TimeService(obj.services.time);
            System.out.println("007 J bond");
            //==================Array Of Threads==================

            ArrayList<Thread> threadList = new ArrayList<Thread>();

            //=================Init all=================
            for(Subscriber sub:ourBuddies){
                Thread t=new Thread(sub);
                threadList.add(t);//Adding the thread to threadList
                t.start();
            }

            Thread tsTread= new Thread(ts);
            System.out.println("wow");
            tsTread.start();

            for(Thread t : threadList){
                t.join();
            }

        } catch (IOException ignored) {
            System.out.println("fuckkkkkkkkkkkkkkkkkkkkkk .CATCH()");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Diary.getInstance().printToFile("Diary");
        try {
            Inventory.getInstance().printToFile("inventory");
        } catch (IOException e) {
            System.out.println("FUckkkkkkkkkkkkkkkkkkkkkkkkkkkkkdsdszxxxxxx");
        }

    }
}