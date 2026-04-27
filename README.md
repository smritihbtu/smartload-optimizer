# SmartLoad Optimization API

## Overview
This service selects the most profitable combination of shipment orders that a truck can carry while respecting:
- Weight constraints
- Volume constraints
- Route compatibility
- Hazmat compatibility

## Tech Stack
- Java 17
- Spring Boot
- Docker
- Maven

---

## How to Run

```bash
git clone https://github.com/smritihbtu/smartload-optimizer.git
cd smartload-optimizer
docker compose up --build
## Service will be available at:
```
http://localhost:8080

## API Endpoint

### Optimize Load
POST /api/v1/load-optimizer/optimize


### Request

```json
{
  "truck": {
    "id": "truck-123",
    "max_weight_lbs": 44000,
    "max_volume_cuft": 3000
  },
  "orders": [
    {
      "id": "ord-001",
      "payout_cents": 250000,
      "weight_lbs": 18000,
      "volume_cuft": 1200,
      "origin": "Los Angeles, CA",
      "destination": "Dallas, TX",
      "pickup_date": "2025-12-05",
      "delivery_date": "2025-12-09",
      "hazmat": false
    }
  ]
}
```


##  Response

```json
{
  "selected_order_ids": ["ord-001"],
  "total_payout_cents": 250000,
  "total_weight_lbs": 18000,
  "total_volume_cuft": 1200,
  "truck_id": "truck-123",
  "utilization_weight_percent": 40.91,
  "utilization_volume_percent": 40.0
}
-------

##  Features

* Bitmask-based optimization (2^n subsets)
* Handles up to 22 orders efficiently
* Ensures:

  * Weight & volume limits
  * Same route
  * Same hazmat type

---

## Health Check

```bash
curl http://localhost:8080/actuator/health
```

---

##  Docker

* Multi-stage build
* Runs on port 8080
* Stateless service

---

##  Author

Smriti Gupta
