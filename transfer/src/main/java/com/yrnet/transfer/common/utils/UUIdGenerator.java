package com.yrnet.transfer.common.utils;

/**
 * @author dengbp
 * @ClassName TaskIdGenerator
 * @Description TODO
 * @date 2019/3/1 5:45 PM
 */
public class UUIdGenerator {

    /**
     * 起始的时间戳
     */
    private final static long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 5;
    private final static long DATACENTER_BIT = 5;

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;
    private long machineId;
    private long sequence = 0L;
    private long lastSTmp = -1L;
    private static UUIdGenerator uuIdGenerator = null;
    private static final long DATA_CENTER_ID = 1;
    private static final long MACHINE_ID = 1;

    private UUIdGenerator(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    /**
     * Description 产生下一个ID
     * @Author dengbp
     * @Date 5:48 PM 2019/3/1
     * @return long
     **/
    public synchronized long nextId() {
        long currStmp = getNewsTmp();
        if (currStmp < lastSTmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastSTmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastSTmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT
                | datacenterId << DATACENTER_LEFT
                | machineId << MACHINE_LEFT
                | sequence;
    }

    private long getNextMill() {
        long mill = getNewsTmp();
        while (mill <= lastSTmp) {
            mill = getNewsTmp();
        }
        return mill;
    }

    private long getNewsTmp() {
        return System.currentTimeMillis();
    }

    public static Long getId(){
        synchronized (UUIdGenerator.class){
            if (uuIdGenerator==null){
                uuIdGenerator = new UUIdGenerator(DATA_CENTER_ID, MACHINE_ID);
            }
        }
        return uuIdGenerator.nextId();
    }
}
