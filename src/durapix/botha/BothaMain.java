/*
 * 'Botha' IRC BOT Copyright (C) 2013 April
 * @author: Harsha Siriwardena <www.harshadura.net>
 *
 * Powered by : PircBot <http://www.jibble.org/pircbot.php>
 * Inspired by: TrivialBot <http://code.google.com/p/trivialbot/>
 *
 * GNU Public License <http://www.gnu.org/licenses/>
 *
 */
package durapix.botha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BothaMain {

    public static void main(String[] args) {

        try {
            Map<String, String> config = loadConfig();

            ArrayList<Question> questions = loadQuestions();


            if (!config.keySet().contains("hostname")) {
                System.err.println("No hostname parameter specified in the config file");
                return;
            }
            if (!config.keySet().contains("channelname")) {
                System.err.println("No channelname parameter specified in the config file");
                return;
            }
            if (!config.keySet().contains("botname")) {
                System.err.println("No botname parameter specified in the config file");
                return;
            }
            if (!config.keySet().contains("adminname")) {
                System.err.println("No adminname parameter specified in the config file");
                return;
            }
            if (!config.keySet().contains("questiontimeout")) {
                System.err.println("No questiontimeout parameter specified in the config file");
                return;
            }
            if (!config.keySet().contains("warningstep")) {
                System.err.println("No warningstep parameter specified in the config file");
                return;
            }
            if (!config.keySet().contains("consecutivetimeoutsbeforeexit")) {
                System.err.println("No consecutivetimeoutsbeforeexit parameter specified in the config file");
                return;
            }

            String hostname = config.get("hostname");
            String channelname = config.get("channelname");
            String channelpassword = config.get("channelpassword");
            String botname = config.get("botname");
            String adminname = config.get("adminname");
            Double questiontimeout = Double.parseDouble(config.get("questiontimeout"));
            Double warningstep = Double.parseDouble(config.get("warningstep"));
            Integer allowedConsecutiveTimouts = Integer.parseInt(config.get("consecutivetimeoutsbeforeexit"));

            
            System.out.println("Botha started !");
            
            if(questions.size() == 0)
            {
                System.out.println("Error: Empty question list !");
                return;
            }
            System.out.println("Loaded " + questions.size() + " questions");
            System.out.println("Connecting to: " + hostname);
            System.out.println("channel: "+ channelname);
            System.out.println("Bot name: " + botname);
            System.out.println("Admin name: " + adminname);
            
            Botha botha = new Botha(
                    hostname,
                    channelname,
                    channelpassword,
                    botname,
                    adminname,
                    questions,
                    questiontimeout.intValue(),
                    warningstep.intValue(),
                    allowedConsecutiveTimouts);          

        } catch (Exception ex) {
            Logger.getLogger(BothaMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<Question> loadQuestions() {
        ArrayList<Question> questions = new ArrayList<Question>();
        try {
            Scanner sc = new Scanner(new File("questions.csv"));
            while (sc.hasNext()) {
                String[] line = sc.nextLine().split("\t");
                questions.add(new Question(line[0], line[1]));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BothaMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    public static Map<String, String> loadConfig() {

        Map<String, String> config = new HashMap<String, String>();
        try {
            Scanner sc = new Scanner(new File("config.txt"));
            while (sc.hasNext()) {
                config.put(sc.next(), sc.next());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BothaMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }
}
