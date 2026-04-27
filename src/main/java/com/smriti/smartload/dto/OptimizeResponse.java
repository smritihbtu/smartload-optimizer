package com.smriti.smartload.dto;
import com.smriti.smartload.model.Order;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OptimizeResponse {
    private String truck_id;
    private List<String> selected_order_ids;
    private long total_payout_cents;
    private int total_weight_lbs;
    private List<Order> selected_orders;
    private int total_volume_cuft;
    private double utilization_weight_percent;
    private double utilization_volume_percent;
}
