import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SocialGraphLoader {

    private File f;
    private Scanner scanner;
    private int numOfUsers;
    private int numOfConnections;
    private HashMap<Integer, ArrayList<Integer>> userList;

    public SocialGraphLoader(){
        this.scanner = new Scanner(System.in);
    }

    public SocialGraph inputFilePath() {
        /* EXPLANATION:
         * 1. Simply load the file with the string input
        */

        System.out.print("Input file path:");
        String filePath = scanner.nextLine();
        SocialGraph socialGraph = this.loadSocialGraph(filePath);

        return socialGraph;
    }


    public SocialGraph loadSocialGraph(String filePath) {
        /* EXPLANATION:
         * 1. The first integer number in the txt file is the number of users in the network
         * 2. The second number indicates the number of connections in that network
         * 3. userList keeps track of the entire friendlist of each person
         * 4. We use those three key elements to instantiate the SocialGraph object 
        */

        try {
            f = new File(filePath);
            scanner = new Scanner(f);
            numOfUsers = scanner.nextInt();         // Scan the first integer
            numOfConnections = scanner.nextInt();   // Scan the second integer

            userList = loadUsers();

            return new SocialGraph(numOfUsers, numOfConnections, userList);

            
        } catch (FileNotFoundException e) {
            System.out.println("Error loading: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    private HashMap<Integer, ArrayList<Integer>> loadUsers() {

        /* EXPLANATION:
         * 1. We load a HashMap with Integer ID as the unique ID identifier and ArrayList<Integer> as the set of unique IDs that is friends with that unique ID.
         * 2. From the txt file, the first number is the unique ID, the second number is the person friends with that unique ID 
         * 3. Since the relationship is bilateral, we call addConnection() twice.
         */

        HashMap<Integer, ArrayList<Integer>> userList = new HashMap<>();

        for (int i = 0; i < numOfConnections; i++) {
            int userId = scanner.nextInt();
            int connection = scanner.nextInt();

            // Bidirectional Connection
            addConnection(userList, userId, connection);    
            addConnection(userList, connection, userId); 
        }

        return userList;
    }



    private void addConnection(HashMap<Integer, ArrayList<Integer>> userList, int personA, int personB) {
        /* EXPLANATION:
         * 1. If personA does not exist in the HashMap, then create it as a unique ID to be added in the HashMap
         * 2. If personA is not friends with personB, then add that friendship list to ArrayList<Integer>
         */
        if (!userList.containsKey(personA)) {
            userList.put(personA, new ArrayList<>());
        }

        if (!userList.get(personA).contains(personB)) {
            userList.get(personA).add(personB);
        }
    }


}
