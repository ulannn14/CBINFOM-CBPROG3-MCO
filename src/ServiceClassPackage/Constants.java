package ServiceClassPackage;

import Model.*;
import java.util.*;

public class Constants {
    public static final int MAX_PLACES = 25;
    public static final int MAX_DAYS = 7;
    public static final int MAX_TIMES = 6;
    public static final int MAX_INSTANCE = MAX_PLACES*MAX_DAYS*MAX_TIMES;

    public static ArrayList<String> fetchPlaceNames() {
        return Place.fetchPlaceNames();
    } 

}