// DO NOT FORGET TO CHECK IF IMPORTED ALL NEEDED LIBRARIES FOR EACH FILE

import Controller.*;
import Model.*;
import ServiceClassPackage.Constants;

public class ISSPa {
    public static void main(String[] args) {
        Controller controller = new Controller();

        Instance[] instances = new Instance[Constants.MAX_INSTANCE]; // Array of size 3
        controller.initializeInstancesController(instances);
        controller.Homepage(instances);
    }
}
