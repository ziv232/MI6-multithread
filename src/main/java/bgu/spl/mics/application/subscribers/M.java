package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.*;
import bgu.spl.mics.application.Messeges.*;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Report;

import java.util.ArrayList;
import java.util.List;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private int currTick=0;


	public M(String name) {
		super(name);
		System.out.println("M "+getName()+" created on M class");
		// TODO Implement this
	}

	public int getCurrTick() {
		return currTick;
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		MessageBrokerImpl.getInstance().register(this);
//		MessageBrokerImpl.getInstance().subscribeEvent(MissionReceivedEvent.class,this);

		Callback<MissionReceivedEvent> missionReceivedCallback=c -> {
			//TODO add to the diary
			Diary.getInstance().incrementTotal();
			AgentsAvailableEvent agentsEvent=new AgentsAvailableEvent(c.getAgentsSerialNumbers());

			ArrayList<String> agentsList= (ArrayList<String>) agentsEvent.getAgentsListForMission();
			for(String agent:agentsList){
				System.out.print("M"+ getName()+" call back, agents for mission "+ agent+ "  ");
			}

			Future<Boolean> agentsFut= MessageBrokerImpl.getInstance().sendEvent(agentsEvent);

			//M send agentsEvent

			boolean agentsAvailable=agentsFut.get();

			//TODO CHECK- I GONNA SEND EVENT OF EXECUTE OR NOT

			System.out.println();
			System.out.println("M "+getName()+" got the future answer on M callback "+ agentsAvailable);

			//



			if(agentsAvailable){	//we acquired the agents
//				System.out.println(true+"XXXXXXXXXXXXXXXXXXXXXXXXXXX");
				GadgetAvailableEvent gadgetEvent=new GadgetAvailableEvent(c.getGadget());
				Future<Boolean> gadgetFut=MessageBrokerImpl.getInstance().sendEvent(gadgetEvent);

				//GADGET EVENT

				boolean gadgetAvailable=gadgetFut.get();

				System.out.println("gadegt "+c.getGadget()+ gadgetAvailable +"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ on M callBack");

				if(gadgetAvailable){	//we got the gadget- lets do the mission!
					if(gadgetEvent.getTime()<=c.getTimeExpired()) {		//TODO we check if Q get the event before
						SendAgentsEvent execute = new SendAgentsEvent(c.getDuration(),c.getAgentsSerialNumbers());
						Future<Boolean> missionCompleted=MessageBrokerImpl.getInstance().sendEvent(execute);		//TODO we added new event for moneyPenny
						missionCompleted.get();		//
						System.out.println("M line 77- moneyPenny executed the mission");
						complete(c, true);
						Report addToDiary=new Report();
						//================================
						addToDiary.setMissionName(c.getMissionName());
						addToDiary.setM(Integer.parseInt(this.getName()));
						addToDiary.setMoneypenny(Integer.parseInt(agentsEvent.getMp().getName()));
						addToDiary.setAgentsSerialNumbersNumber(c.getAgentsSerialNumbers());
						addToDiary.setAgentsNames(execute.getAgentsNames());
						addToDiary.setGadgetName(c.getGadget());
						addToDiary.setTimeIssued(c.getTimeIssued());
						addToDiary.setQTime(gadgetEvent.getTime());
						addToDiary.setTimeCreated(getCurrTick());

						Diary.getInstance().addReport(addToDiary);
						//================================	//checking names

						List<String> names=addToDiary.getAgentsNames();
						for(String name:names)
							System.out.print(name+ "  M");

						System.out.println("M line 74: "+ c.getMissionName()+"XXXXXXXXXXXXXXXXXXX completed successfully");
					}

					else{	//TODO abort
						System.out.println("MISSION abort- time expired "+ gadgetEvent.getTime()+"> "+ c.getTimeExpired());
						AbortMissionEvent abort=new AbortMissionEvent(c.getAgentsSerialNumbers());
						MessageBrokerImpl.getInstance().sendEvent(abort);
						complete(c,false);
					}
				}
				else{	//end of GADGETAVLIBLE if
//					AbortMission
					System.out.println("MISSION abort- gadget "+ gadgetEvent.getGadget()+ " is not available");
					AbortMissionEvent abort=new AbortMissionEvent(c.getAgentsSerialNumbers());
					complete(c,false);
					MessageBrokerImpl.getInstance().sendEvent(abort);
				}
			}	//END OF BIG IF ------ we didnt got the agents so we wont try to get the gadget
			else{	//TODO timeout means we need to abort mission
				System.out.println("FFFFFFFFFFFFFFFFFFFFuckkkkkk");
				complete(c,false);
			}

		};	//callback

		subscribeEvent(MissionReceivedEvent.class,missionReceivedCallback);
		//TODO continue

		Callback<TickBroadcast> tickCallback= (TickBroadcast c) -> {
			currTick=c.getTick();
			System.out.println("M"+getName()+" currTick is "+ currTick);
//				System.out.println("M tickTime "+getCurrTick()+ "on M tickCallback ");

			if(c.isTerminated()){
//				Diary.getInstance().printToFile("/users/studs/bsc/2020/zivshir/IdeaProjects/assignment2/src/main/java/bgu/spl/mics/application/DiaryOutPut.json");
				System.out.println("M is going to terminate");
				terminate();}
		};
		subscribeBroadcast(TickBroadcast.class,tickCallback);
		
	}

}
