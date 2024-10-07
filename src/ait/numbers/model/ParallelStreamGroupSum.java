package ait.numbers.model;

import ait.numbers.task.OneGroupSum;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelStreamGroupSum extends GroupSum {
    public ParallelStreamGroupSum(int[][] numberGroups) {
        super(numberGroups);
    }

    @Override
    public int computeSum() {
        AtomicInteger totalSum = new AtomicInteger(0);
        Arrays.stream(numberGroups)
                .parallel()
                .forEach(group -> {
                    OneGroupSum oneGroupSum = new OneGroupSum(group);
                    oneGroupSum.run();
                    totalSum.addAndGet(oneGroupSum.getSum());
                });

        return totalSum.get();
    }
}
