package com.smriti.smartload.model;
import lombok.Data;

@Data
public class Order {
    private String id;
    private long payout_cents;
    private int weight_lbs;
    private int volume_cuft;
    private String origin;
    private String destination;
    private String pickup_date;
    private String delivery_date;
    private boolean hazmat;;;
}
