package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorGroupSum extends GroupSum {
    public ExecutorGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int sum = 0;
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        OneGroupSum[] groupSums = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < numberGroups.length; i++) {
            groupSums[i] = new OneGroupSum(numberGroups[i]);
            executorService.execute(groupSums[i]);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (OneGroupSum groupSum : groupSums) {
            sum += groupSum.getSum();
        }
        return sum;
    }
}
