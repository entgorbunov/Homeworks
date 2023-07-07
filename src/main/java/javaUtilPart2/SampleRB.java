package javaUtilPart2;

import java.util.*;

public class SampleRB extends ListResourceBundle {
    protected Object[][] getContents() {
        Object[][] resources = new Object[3][2];
        resources[0][0] = "title";
        resources[0][1] = "My Program";
        resources[1][0] = "StopText";
        resources[1][1] = "Stop";
        resources[2][0] = "StartText";
        resources[2][1] = "Start";
        return resources;
    }

    public static void main(String[] args) {
        // Load the default bundle.
        ResourceBundle rd = ResourceBundle.getBundle("SampleRB");
        System.out.println("English version: ");
        System.out.println("String for Title key : " +
                rd.getString("title"));
        System.out.println("String for StopText key: " +
                rd.getString("StopText"));
        System.out.println("String for StartText key: " +
                rd.getString("StartText"));
    }
}