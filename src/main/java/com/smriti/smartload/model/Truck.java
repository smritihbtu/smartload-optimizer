package com.smriti.smartload.model;
import lombok.Data;

@Data
public class Truck {
    private String id;
    private int max_weight_lbs;
    private int max_volume_cuft;
}
