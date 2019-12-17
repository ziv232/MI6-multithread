package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.GsonObj;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args){
        try {

            String str = new String(Files.readAllBytes(Paths.get(args[0])));
            GsonObj obj = new Gson().fromJson(str, GsonObj.class);


        } catch (IOException ignored) {
        }

    }
}
