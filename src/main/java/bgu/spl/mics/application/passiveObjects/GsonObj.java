package bgu.spl.mics.application.passiveObjects;

import java.util.List;

public class GsonObj {
   public List<String> inventory;
   public GsonSquad[] squad;
   public GsonService services;

    public class GsonGadget{
       public String name;
    }

    public class GsonSquad{
        public String name;
        public String serialNumber;
    }

    public class GsonService{
        public int M;
        public int Moneypenny;
        public int time;
        public GsonMissions[] intelligence;


    }

    public class GsonMissions{
        public GsonMission[] missions;
        public class GsonMission{
            public String[] serialAgentsNumbers;
            public int duration;
            public String gadget;
            public String name;
            public int timeExpired;
            public int timeIssued;
        }

    }

}
