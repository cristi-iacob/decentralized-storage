package files.split;

import java.util.ArrayList;
import java.util.List;

public class BitwiseSplitManager implements SplitManager {
    private int parts;

    public BitwiseSplitManager(int parts) {
        this.parts = parts;
    }

    @Override
    public int getParts() {
        return parts;
    }

    public List < Byte >[] splitFileBytes(byte[] fileBytes) {
        List < Byte > splittedFile[] = new ArrayList[parts];
        for (int i = 0; i < parts; ++ i) {
            splittedFile[i] = new ArrayList<>();
        }

        for (int i = 0; i < fileBytes.length; ++ i) {
            splittedFile[i % parts].add(fileBytes[i]);
        }

        return splittedFile;
    }

    @Override
    public Byte[] revertSplit(List<Byte>[] splitted) {
        ArrayList < Byte > reverted = new ArrayList<Byte>();
        Boolean finished = false;

        while (!finished) {
            for (int i = 0; i < splitted.length; ++i) {
                finished = true;
                if (splitted[i].size() > 0) {
                    reverted.add(splitted[i].get(0));
                    splitted[i].remove(0);
                    finished = false;
                }
            }
        }

        Byte[] ret = new Byte[reverted.size()];
        return reverted.toArray(ret);
    }
}
