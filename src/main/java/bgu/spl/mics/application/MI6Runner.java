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
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args){
        try {
            String str = new String(Files.readAllBytes(Paths.get("src/main/java/bgu/spl/mics/application/test1.json")));
            GsonObj obj = new Gson().fromJson(str, GsonObj.class);


            ArrayList<Subscriber> ourBuddies = new ArrayList<>();//List of all the Subscribers
            String[] gadgets = new String[obj.inventory.size()];
            for(int i=0;i<obj.inventory.size();i++){//Reading the Gadgets from the JSON
                gadgets[i] = obj.inventory.get(i);
            }
            Inventory.getInstance().load(gadgets);//Loading the Inventory

            Agent[] agents = new Agent[obj.squad.length];
            for(int i=0;i<obj.squad.length;i++){
                agents[i]=new Agent(obj.squad[i].name,obj.squad[i].serialNumber);//Reading the Agents from the JSON
            }
            Squad.GetInstance().load(agents);//Loading the Squad

            for(int i=0;i<obj.services.M;i++){//Create the M instances and push them to the List of Instances
                int name = i+1;
                M m = new M(Integer.toString(name));
                ourBuddies.add(m);
            }

            for(int i=0;i<obj.services.Moneypenny;i++){//Create the MoneyPenny instances and push them to the List of Instances
                int name = i+1;
                Moneypenny mp = new Moneypenny(Integer.toString(name));
                ourBuddies.add(mp);
            }


            for (int i=0;i<obj.services.intelligence.length;i++){   //Loop all the Intelligence
                ArrayList<MissionInfo> list = new ArrayList<>();

                Intelligence intel = new Intelligence(Integer.toString(i+1));//
                for(int j=0;j<obj.services.intelligence[i].missions.length;j++){    //Loop all over the missions of each Intel
                    ArrayList<String> agentslist = new ArrayList<>();
                    Collections.addAll(agentslist, obj.services.intelligence[i].missions[j].serialAgentsNumbers);
                    list.add(new MissionInfo(obj.services.intelligence[i].missions[j].name,
                            agentslist,obj.services.intelligence[i].missions[j].gadget,
                            obj.services.intelligence[i].missions[j].timeIssued,obj.services.intelligence[i].missions[j].timeExpired,
                            obj.services.intelligence[i].missions[j].duration));//     Add the mission to the Intelligence missionList.

                }
                intel.setMissions(list);// Set the list of missions we created to the intelligence.
                ourBuddies.add(intel);
            }
            Q q = new Q("1");
            ourBuddies.add(q);
            TimeService ts = new TimeService(obj.services.time);
            //==================Array Of Threads==================

            ArrayList<Thread> threadList = new ArrayList<>();

            CountDownLatch latch=new CountDownLatch(ourBuddies.size());//Latch to start the TimeService at the right time.
            //=================Init all==========================
            for(Subscriber sub:ourBuddies){
                Thread t=new Thread(sub);
                threadList.add(t);//Adding the thread to threadList
                t.start();
                latch.countDown();
            }

            latch.await();

            Thread tsTread= new Thread(ts);
            tsTread.start();

            for(Thread t : threadList){
                t.join();
            }

        } catch (IOException ignored) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Diary.getInstance().printToFile("diary");
        Inventory.getInstance().printToFile("inventory");

    }
}