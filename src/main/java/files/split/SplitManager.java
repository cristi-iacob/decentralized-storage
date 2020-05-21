package files.split;

import java.util.List;

public interface SplitManager {
    int getParts();
    List< Byte >[] splitFileBytes(byte[] fileBytes);
    Byte[] revertSplit(List < Byte >[] splitted);
}
