package com.smriti.smartload.dto;
import com.smriti.smartload.model.Order;
import com.smriti.smartload.model.Truck;
import lombok.Data;
import java.util.List;


@Data

public class OptimizeRequest {
    private Truck truck;
    private List<Order> orders;
}
