package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

public class ThreadGroupSum extends GroupSum {
    public ThreadGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        int sum = 0;
        Thread[] threads = new Thread[numberGroups.length];
        OneGroupSum[] groupSums = new OneGroupSum[numberGroups.length];
        for (int i = 0; i < threads.length; i++) {
            groupSums[i] = new OneGroupSum(numberGroups[i]);
            threads[i] = new Thread(groupSums[i]);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
                sum += groupSums[i].getSum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return sum;
    }
}
