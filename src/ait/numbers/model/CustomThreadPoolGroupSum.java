package ait.numbers.model;

import ait.numbers.task.OneGroupSum;
import ait.utils.pool.ThreadPool;

public class CustomThreadPoolGroupSum extends GroupSum {
    public CustomThreadPoolGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int sum = 0;
        int poolSize = Runtime.getRuntime().availableProcessors();
        ThreadPool threads = new ThreadPool(poolSize);
        OneGroupSum[] groupSums = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < numberGroups.length; i++) {
            groupSums[i] = new OneGroupSum(numberGroups[i]);
            threads.execute(groupSums[i]);
        }
        try {
            threads.joinToPool(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (OneGroupSum groupSum : groupSums) {
            sum += groupSum.getSum();
        }
        threads.shutDown();
        return sum;
    }
}
