package server.peers;

import server.model.User;
import server.persistence.UserRepository;
import server.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class TimezonePeersFinder implements IPeersFinder {
    private UserService userService;
    private UserRepository userRepository;
    private int peersNumber;

    public TimezonePeersFinder(int peersNumber) {
        this.userService = new UserService();
        this.userRepository = new UserRepository();
        this.peersNumber = peersNumber;
    }

    @Override
    public List<Integer> findPeers(int clientId) {
        List < Integer > retList = new ArrayList<>();
        ArrayList <User> users = (ArrayList) userService.getUsersSortedByRelativeTimezone(userRepository.get(clientId).getTimezone());
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
