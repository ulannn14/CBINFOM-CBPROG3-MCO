// DO NOT FORGET TO CHECK IF IMPORTED ALL NEEDED LIBRARIES FOR EACH FILE

import Controller.*;

public class ISSPa {
    public static void main(String[] args) {
        Controller controller = new Controller();

        controller.initializeInstancesController();       
        controller.Homepage();
    }
}
