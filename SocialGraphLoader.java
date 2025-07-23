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

        System.out.print("Input file path:");
        String filePath = scanner.nextLine();
        SocialGraph socialGraph = this.loadSocialGraph(filePath);

        return socialGraph;
    }


    public SocialGraph loadSocialGraph(String filePath) {

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
        if (!userList.containsKey(personA)) {
            userList.put(personA, new ArrayList<>());
        }

        if (!userList.get(personA).contains(personB)) {
            userList.get(personA).add(personB);
        }
    }


}
