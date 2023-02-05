package Model;

/**
  @author Jessica Thomas
  C 482 Software I Inventory project
  */


/**
  Class InHouse.java creates parts that are made in-house and extends abstract Part class
  */


public class InHouse extends Part {

    /**
      @param machineId is the unique machine number for each InHouse part
      */
    private int machineId;

    /**Constructor creates Part object
      @param name  the name to set
      @param price the price to set
      @param stock the stock number to set
      @param min   the minimum item number to set
      @param max   the maximum item number to set
      @param machineId is the machine id number
     */
    public InHouse( String name, double price, int stock, int min, int max, int machineId) {
        super(name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**Method sets machine id number to machineId.
     @param machineId is the unique machineId number that the class machineId field is set to
     */
    public void setMachineId(int machineId) {

       this.machineId = machineId;
    }

    /**Method gets the machine id.
     @return machineId
     */
    public int getMachineId() {

        return machineId;
    }
}
