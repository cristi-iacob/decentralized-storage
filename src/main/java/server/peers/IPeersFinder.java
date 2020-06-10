package server.peers;

import java.util.List;

public interface IPeersFinder {
    List< Integer > findPeers(int clientId);
}
