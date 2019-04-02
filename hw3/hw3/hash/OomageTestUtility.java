package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] bucketList = new int[M];
        int N = oomages.size();
        for (Oomage O:oomages) {
            int bucketNum = (O.hashCode() & 0x7FFFFFFF) % M;
            bucketList[bucketNum] += 1;
        }
        for (int i = 0; i < M; i += 1) {
            if (bucketList[i] < N / 50 || bucketList[i] > N / 2.5) {
                return false;
            }
        }
        return true;
    }
}
