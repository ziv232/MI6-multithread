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
	}

	public int getCurrTick() {
		return currTick;
	}

	@Override
	protected void initialize() {

		Callback<MissionReceivedEvent> missionReceivedCallback=c -> {
			Diary.getInstance().incrementTotal();
			AgentsAvailableEvent agentsEvent=new AgentsAvailableEvent(c.getAgentsSerialNumbers(),c.getDuration());

			ArrayList<String> agentsList= (ArrayList<String>) agentsEvent.getAgentsListForMission();
			for(String agent:agentsList){
				System.out.print("M"+ getName()+" call back, agents for mission "+ agent+ "  ");
			}

			Future<Boolean> agentsFut= getSimplePublisher().sendEvent(agentsEvent);

			//M send agentsEvent

			if(agentsFut==null){	//TODO check, and we return czo non got the msg
				complete(agentsEvent,false);
				return;
			}

			boolean agentsAvailable=agentsFut.get();

			//TODO CHECK- I GONNA SEND EVENT OF EXECUTE OR NOT

			System.out.println();
			System.out.println("M "+getName()+" got the future answer on M callback "+ agentsAvailable);

			//


			if(agentsAvailable){	//we acquired the agents
				GadgetAvailableEvent gadgetEvent=new GadgetAvailableEvent(c.getGadget());
				Future<Integer> gadgetFut=getSimplePublisher().sendEvent(gadgetEvent);

				//GADGET EVENT
				if(gadgetFut==null){
					complete(gadgetEvent,-1);
					agentsEvent.setToSend(false);
					return;
				}
				int gadgetAvailableTime=gadgetFut.get();	// TODO CHECKKKKKKK


				if(gadgetAvailableTime!=-1){	//we got the gadget- lets do the mission!
					if(gadgetAvailableTime<=c.getTimeExpired()) {		//TODO we check if Q get the event before
						agentsEvent.setToSend(true);
						System.out.println("M "+getName()+ " ~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						
						complete(c, true);
						Report addToDiary=new Report();
						//================================
						addToDiary.setMissionName(c.getMissionName());
						addToDiary.setM(Integer.parseInt(this.getName()));
						addToDiary.setMoneypenny(Integer.parseInt(agentsEvent.getMp().getName()));	//TODO maybe do pair to get the info
						addToDiary.setAgentsSerialNumbersNumber(c.getAgentsSerialNumbers());
						addToDiary.setAgentsNames(agentsEvent.getNames());
						addToDiary.setGadgetName(c.getGadget());
						addToDiary.setTimeIssued(c.getTimeIssued());
						addToDiary.setQTime(gadgetAvailableTime);
						addToDiary.setTimeCreated(getCurrTick());

						Diary.getInstance().addReport(addToDiary);
						//================================	//checking names

//						List<String> names=addToDiary.getAgentsNames();
//						for(String name:names)
//							System.out.print(name+ " name M");

						System.out.println("M line 74: "+ c.getMissionName()+"XXXXXXXXXXXXXXXXXXX completed successfully");
					}

					else{	//TODO abort
						System.out.println("MISSION abort- time expired "+ gadgetEvent.getTime()+"> "+ c.getTimeExpired());
//						AbortMissionEvent abort=new AbortMissionEvent(c.getAgentsSerialNumbers());
//						getSimplePublisher().sendEvent(abort);
						agentsEvent.setToSend(false);
						complete(c,false);
					}
				}
				else{	//end of GADGETAVLIBLE if
//					AbortMission
//					System.out.println("MISSION abort- gadget "+ gadgetEvent.getGadget()+ " is not available");
//					AbortMissionEvent abort=new AbortMissionEvent(c.getAgentsSerialNumbers());
//					complete(c,false);
//					getSimplePublisher().sendEvent(abort);
					agentsEvent.setToSend(false);
					complete(c,false);

				}
			}	//END OF BIG IF ------ we didnt got the agents so we wont try to get the gadget
			else{	//TODO timeout means we need to abort mission
				System.out.println("agents for mission do not exist");
				complete(c,false);
			}

		};	//callback

		subscribeEvent(MissionReceivedEvent.class,missionReceivedCallback);
		//TODO continue

		Callback<TickBroadcast> tickCallback= (TickBroadcast c) -> {
			currTick=c.getTick();
//			System.out.println("M"+getName()+" currTick is "+ currTick);
//				System.out.println("M tickTime "+getCurrTick()+ "on M tickCallback ");

			if(c.isTerminated()){
				Diary.getInstance().printToFile("/users/studs/bsc/2020/zivshir/IdeaProjects/assignment2/src/main/java/bgu/spl/mics/application/DiaryOutPut.json");
				System.out.println("M is going to terminate");
				terminate();}
		};
		subscribeBroadcast(TickBroadcast.class,tickCallback);
		
	}

}
