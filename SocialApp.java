import java.util.*;

public class SocialApp {

    private final Scanner scanner = new Scanner(System.in);
    private SocialGraph socialGraph;
    private SocialGraphLoader socialGraphLoader;
    private boolean end = false;

    public SocialApp() {}

//=============================================================================================================================//
    /* EXPLANATION:
        * 1. This is the high level starting point of the program
        * 2. This simply loops the entire program until end is true
     */    

    public void start() {
        this.socialGraph = null;                                // Our main social graph ready for values
        this.socialGraphLoader = new SocialGraphLoader();       // Backend handler for handling file path loading

        while (socialGraph == null) {
            socialGraph = socialGraphLoader.inputFilePath();    // Loads the social graph from the file input
            if (socialGraph == null) System.out.println("Failed to load graph. Please try again.\n");
        }

        System.out.println("Graph loaded successfully!");

        while (end==false && socialGraph != null) {             // MAIN MENU
            displayMenu();                                      // Print Choices
            end = menuController();                             // menuController handles user input
        }
    }

//=============================================================================================================================//

    private void displayMenu() {
    /* EXPLANATION:
        * 1. This simply displays the menu
    */  
        System.out.printf("\nMAIN MENU\n");
        System.out.println("[1] Get friend list");
        System.out.println("[2] Get connection");
        System.out.println("[3] Exit");

    }

    private boolean menuController() {
    /* EXPLANATION:
        * 1. This is the high level controller when the user inputs a choice
        * 2. All methods will return false unless case 3 to end the program.
    */  

        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {       // The only way it should return true is to END the program
            case 1: return friendListController();
            case 2: return pathConnectionController();
            case 3: return true;
            default: 
                    System.out.println("Invalid choice!"); 
                    return false;
        }
    }

    public boolean friendListController() {
    /* EXPLANATION:
        * 1. This is the controlelr responsible for calling the getFriendOf() method of the SocialGraph object
        * 2. It verifies first if the user exists.
    */ 

        System.out.print("Enter ID of person: ");
        int userId =  Integer.parseInt(scanner.nextLine());
        if (!socialGraph.userExists(userId)) {
            System.out.println("User does not exist!");
            return false;
        } else {
            ArrayList<Integer> friendList = socialGraph.getFriendsOf(userId);
            System.out.println("Person " + userId + " has " + friendList.size() + " friends!");
            System.out.println("List of friends: " + friendList);
            return false;
        }
    }
    
    public boolean pathConnectionController() {
    /* EXPLANATION:
        * 1. This is the controller responsible for the algorithm in searching the path connection between personA and personB
        * 2. It verifies if the persons do exist.
        * 3. If it does exist it calls the algorithm that will parse the path from personA to personB
    */ 

        System.out.print("Enter ID of first person: ");
        int startId =  Integer.parseInt(scanner.nextLine());

        System.out.print("Enter ID of second person: ");
        int endId =  Integer.parseInt(scanner.nextLine());

        if (!socialGraph.userExists(startId)) {
            System.out.println("First Person does not exist!"); 
            return false;
        }

        if (!socialGraph.userExists(endId)) {
            System.out.println("Second Person does not exist!"); 
            return false;
        }

        List<Integer> path = getPathConnection(startId, endId);

        if (path!=null) {
            System.out.println("There is a connection from " + startId + " to " + endId);
            for (int i = 0; i < path.size()-1; i++) {
                System.out.println(path.get(i) + " is friends with " + path.get(i+1));
            }
        } else {
            System.out.println("Cannot find a connection between " + startId + " to " + endId);
        }

        return false;
    }

        /**
         * BFS Path-Finding Algorithm w/ Path Tracking
         * ------------------------------------------------
         * 
         * 1. Create a Set<Integer> of visited IDs.
         * 
         * 2. Create a List<Integer> path, and initialize it with startId as the first element.
         * 
         * 3. Store this path in a Queue<List<Integer>>.
         *    - The queue will hold multiple List<Integer> instances,
         *      each representing a possible path from the start node to another node.
         * 
         * 4. While the queue is not empty:
         *    a. Use queue.poll() to take out one path (List<Integer>).
         * 
         *    b. Get the last ID element in the List<Integer>
         *       - Compare it with targetId:
         *         - If it matches, return this list.
         *               --> This list represents the path from startId to targetId.
         * 
         *         - If it doesn't match:
         *             i. Get its connections (friends).
         *             ii. For each connection:
         *                 - If it is NOT in the visited set:
         *                     - Add it to visited.
         *                     - Create a copy of the current List<Integer>
         *                     - Add the ID connection element to the end of that copy.
         *                     - Add this new connection to the queue.
         * 
         * 5. Repeat until the queue is empty.
         * 
         * 6. If no path is found after the queue is empty, return null.
         */
    
    public List<Integer> getPathConnection(int startId, int endId) {
        Set<Integer> visited = new HashSet<>();                // Set to keep track of our visited nodes
        Queue<List<Integer>> queue = new LinkedList<>();       // Queue holds a list of paths   

        /*** We need to initialize the first path which is the starting ID ***/
        List<Integer> startPath = new ArrayList<>();                // Initialize the first List<> as the first path
        startPath.add(startId);                                     // Add the startId as the first element in the first path
        queue.add(startPath);                                       // Add that to the queue
        /*********************************************************************/

        while (queue.isEmpty()==false) {                            // We will exhaust all paths in the queue
            List<Integer> currentPath = queue.poll();               // Take the first List<Integer> that represents the path
            int currentId = currentPath.get(currentPath.size()-1);  // Take the last element of that List<Integer>

            if (currentId==endId) return currentPath;               // Check if the last ID element is the targetID

            List<Integer> connections = socialGraph.getFriendsOf(currentId);
            for (int i = 0 ; i < connections.size(); i ++) {

                int friendId = connections.get(i);

                if (!visited.contains(friendId)) {                         // If it doesnt exist in the nodes we already visited
                    visited.add(friendId);                                 // then add it, and create a new List<Integer> path to the queue
                    
                    List<Integer> newPath = new ArrayList<>(currentPath);  // Copy the original path, MAKE SURE TO DEEP COPY and not just reference
                    newPath.add(friendId);                                 // Add the connection
                    queue.add(newPath);                                    // Throw this List<Integer> as a new path in the queue
                }


            }

        }

        return null;  // If nothing at all, we just throw null.

    }
        

}
