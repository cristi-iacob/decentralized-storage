package files.split;

import java.util.List;

public interface SplitManager {
    List< Byte >[] splitFileBytes(byte[] fileBytes, Integer parts);
    Byte[] revertSplit(List < Byte >[] splitted);
}
