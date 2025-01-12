package com.capstone.jobscheduler.functions;

public class SnowflakeIdGenerator {
    private static final long EPOCH = 1672531200000L; // Custom epoch (e.g., 2023-01-01)
    private static final long MACHINE_ID = 1; // Unique machine ID
    private static final long MAX_SEQUENCE = 4095; // Sequence range (0-4095)

    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public synchronized long generateUniqueId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0) {
                // Sequence exhausted, wait for the next millisecond
                while (currentTimestamp <= lastTimestamp) {
                    currentTimestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L; // Reset sequence for new millisecond
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - EPOCH) << 22) | (MACHINE_ID << 12) | sequence;
    }
}
