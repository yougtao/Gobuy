package com.gobuy.order.Utils;

public class OrderIdGeneration {

    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private final static long twepoch = 1288834974657L;
    /**
     * 机器标识位数
     */
    private final static long workerIdBits = 5L;
    /**
     * 数据中心标识位数
     */
    private final static long datacenterIdBits = 5L;

    /**
     * 毫秒内自增位
     */
    private final static long sequenceBits = 10L;
    // 左移数
    private final static long workerIdShift = sequenceBits;
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间左移数
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask = ~(-1L << sequenceBits);

    // 上次生产id时间戳
    private static long lastTimestamp = -1L;
    // 并发序列号
    private long sequence = 0L;
    private long startSequence = 0L;

    private final long workerId;
    /**
     * 数据标识id部分
     */
    private final long datacenterId;

    /**
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     */
    public OrderIdGeneration(long workerId, long datacenterId) {
        if (workerId > 1 << workerIdBits || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 1 << workerIdBits));
        }
        if (datacenterId > 1 << datacenterIdBits || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 1 << datacenterIdBits));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获取下一个ID
     *
     * @return id
     */
    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        return nextId(timestamp);
    }

    public synchronized long nextId(long timestamp) {

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        sequence = (sequence + 1) & sequenceMask;
        if (lastTimestamp == timestamp) {
            if (startSequence == sequence)
                timestamp = tilNextMillis(lastTimestamp);
        } else {
            startSequence = sequence;
        }

        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    // 等待至下一秒
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
