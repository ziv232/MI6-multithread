package bgu.spl.mics.application.passiveObjects;

import java.util.List;

public class GsonObj {
    //public GsonGadget[] inventory;
   public List<String> inventory;
   public GsonSquad[] squad;

    public class GsonGadget{
       public String name;
    }

    public class GsonSquad{
        public String name;
        public String serialNumber;
    }

}
