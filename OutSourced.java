package Model;

/**
  @author Jessica Thomas
  C 482 Software I Inventory project
  */


/**
  Class OutSourced.java creates Outsourced parts and extends abstract Part class
  */


public class OutSourced extends Part {

    /**
      @param companyName is the class field that holds the company name
      */
    private static String companyName;

    /**Constructor creates Part object.
      @param name  the name to set
      @param price the price to set
      @param stock the stock number to set
      @param min   the minimum item number to set
      @param max   the maximum item number to set
      @param companyName is the company name
     */
    public OutSourced(String name, double price, int stock, int min, int max, String companyName) {
        super(name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**Method sets companyName to this companyName.
      @param companyName is the name to set class field companyName to
      */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;

    }

    /**Method gets the companyName and returns it.
      @return companyName
      */
    public String getCompanyName() {
        return companyName;

    }
}
