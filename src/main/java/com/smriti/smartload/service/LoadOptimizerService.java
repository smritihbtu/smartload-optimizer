package com.smriti.smartload.service;
import com.smriti.smartload.dto.OptimizeRequest;
import com.smriti.smartload.dto.OptimizeResponse;
import com.smriti.smartload.model.Order;
import com.smriti.smartload.model.Truck;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LoadOptimizerService {
    public OptimizeResponse optimize(OptimizeRequest request) {
    Truck truck = request.getTruck();
    List<Order> orders = request.getOrders();
        List<Order> selectedOrders = new ArrayList<>();

    int n = orders.size();

    long bestPayout = 0;
    int bestMask = 0;

    // iterate all subsets
        for (int mask = 0; mask < (1 << n); mask++) {

        long payout = 0;
        int weight = 0;
        int volume = 0;

        boolean valid = true;

        String origin = null;
        String destination = null;
        Boolean hazmat = null;

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                Order o = orders.get(i);

                payout += o.getPayout_cents();
                weight += o.getWeight_lbs();
                volume += o.getVolume_cuft();

                // constraint: weight/volume
                if (weight > truck.getMax_weight_lbs() ||
                        volume > truck.getMax_volume_cuft()) {
                    valid = false;
                    break;
                }

                // route check
                if (origin == null) {
                    origin = o.getOrigin();
                    destination = o.getDestination();
                } else {
                    if (!origin.equals(o.getOrigin()) ||
                            !destination.equals(o.getDestination())) {
                        valid = false;
                        break;
                    }
                }

                // hazmat check
                if (hazmat == null) {
                    hazmat = o.isHazmat();
                } else {
                    if (!hazmat.equals(o.isHazmat())) {
                        valid = false;
                        break;
                    }
                }
            }
        }

        if (valid && payout > bestPayout) {
            bestPayout = payout;
            bestMask = mask;
        }
    }

    // build result
    List<String> selected = new ArrayList<>();
    int totalWeight = 0;
    int totalVolume = 0;

        for (int i = 0; i < n; i++) {
        if ((bestMask & (1 << i)) != 0) {
            Order o = orders.get(i);
            selected.add(o.getId());
            selectedOrders.add(o);
            totalWeight += o.getWeight_lbs();
            totalVolume += o.getVolume_cuft();
        }
    }

        return OptimizeResponse.builder()
                .truck_id(truck.getId())
            .selected_order_ids(selected)
                .selected_orders(selectedOrders)
                .total_payout_cents(bestPayout)
                .total_weight_lbs(totalWeight)
                .total_volume_cuft(totalVolume)
                .utilization_weight_percent(
            totalWeight * 100.0 / truck.getMax_weight_lbs())
            .utilization_volume_percent(
            totalVolume * 100.0 / truck.getMax_volume_cuft())
            .build();
}
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
