package Model;

/**
  Class Part.java is supplied class
 */

    public abstract class Part {

        private int id;
        private static int newId = 0;
        private String name;
        private double price;
        private int stock;
        private int min;
        private int max;


        /**Constructor
          @param name is the name to set
          @param price is the price to set
          @param stock is the stock to set
          @param min is the minimum on hand to set
          @param max is the maximum to have on hand
          */
        public Part(String name, double price, int stock, int min, int max) {

            newId++;
            id = newId;
            this.name = name;
            this.price = price;
            this.stock = stock;
            this.min = min;
            this.max = max;
        }

        /**Method gets the id.
          @return the id
         */
        public int getId() {

            return id;
        }

        /**Method sets the id.
          @param id is the id to set
         * */
        public void setId(int id){

            this.id = id;
        }

        /**Method gets the name.
          @return the name
         */
        public String getName() {

            return name;
        }

        /**Method sets the name.
          @param name the name to set
         */
        public void setName(String name) {

            this.name = name;
        }

        /**Method gets the price.
          @return the price
         */
        public double getPrice() {

            return price;
        }

        /**Method sets the price.
          @param price the price to set
         */
        public void setPrice(double price) {

            this.price = price;
        }

        /**Method gets the stock.
          @return the stock
         */
        public int getStock() {

            return stock;
        }

        /**Method sets the stock.
          @param stock the stock to set
         */
        public void setStock(int stock) {

            this.stock = stock;
        }

        /**Method gets the minimum.
          @return the min
         */
        public int getMin() {

            return min;
        }

        /**Method sets the minimum.
          @param min the min to set
         */
        public void setMin(int min) {

            this.min = min;
        }

        /**Method gets the maximum.
          @return the max
         */
        public int getMax() {

            return max;
        }

        /**Method sets the maximum.
          @param max the max to set
         */
        public void setMax(int max) {

            this.max = max;
        }

    }

