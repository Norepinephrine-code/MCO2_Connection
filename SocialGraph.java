import java.util.*;

public class SocialGraph {

    private int numUsers;
    private int numConnections;
    private HashMap<Integer, ArrayList<Integer>> userList;

    public SocialGraph(int numUsers, int numConnections, HashMap<Integer, ArrayList<Integer>> userList) {
        this.numUsers = numUsers;
        this.numConnections = numConnections;
        this.userList = userList;
    }

    public int getNumUsers() { return numUsers;}
    public int getNumConnections() {return numConnections;}
    public HashMap<Integer, ArrayList<Integer>> getUserList() {return userList;}

    public ArrayList<Integer> getFriendsOf(int userId) {
        return userList.getOrDefault(userId, new ArrayList<>());
    }

    public boolean userExists(int userId) {
        return userList.containsKey(userId);
    }
}
