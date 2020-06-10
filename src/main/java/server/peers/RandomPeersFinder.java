package server.peers;

import server.model.User;
import server.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomPeersFinder implements IPeersFinder {
    private UserService userService;
    private int peersNumber;

    public RandomPeersFinder(int peersNumber) {
        userService = new UserService();
        this.peersNumber = peersNumber;
    }

    @Override
    public List<Integer> findPeers(int clientId) {
        List <Integer> retList = new ArrayList<>();
        ArrayList <User> users = (ArrayList) userService.getAllUsers();
        Collections.shuffle(users);
        boolean skip = false;

        for (int i = 0; i < peersNumber; ++ i) {
            if (users.get(i).getId() == clientId) {
                skip = true;
                continue;
            }
            retList.add(users.get(i).getId());
        }

        if (skip) {
            retList.add(users.get(peersNumber).getId());
        }

        return retList;
    }
}
