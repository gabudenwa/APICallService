package main;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


public class BillingService {

    private Map<String, List<Usage>> customersAndUsage =new HashMap<>();
    private Map<String, Date> seriveStartDate = new HashMap<>();

    public void trackUsage(String customerId, String ip) {

        Date callDate = new Date();

        if (!seriveStartDate.containsKey(customerId))
            seriveStartDate.put(customerId, callDate);

        if (!customersAndUsage.containsKey(customerId)) {
            List<Usage> usageList = new ArrayList<>();

            usageList.add(new Usage(customerId, ip, callDate));

            customersAndUsage.put(customerId, usageList);
        } else {
            customersAndUsage.get(customerId).add(new Usage(customerId, ip, callDate));
        }
    }

    public double getMonthlyBillCost(String customerId, Date date) {

        if (!seriveStartDate.containsKey(customerId)) {
            return 0;
        } else {
            Date startDate = seriveStartDate.get(customerId);
            int dayOfTheMonth = startDate.getDay();

            LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), dayOfTheMonth);

            Date lowerbound = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date upperBound = Date.from(localDate.plusMonths(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            List<Usage> usageList = customersAndUsage.get(customerId);
            int usageCount = 0;
            for (Usage usage : usageList) {
                Date callDate = usage.getTime();
                if ((lowerbound.compareTo(callDate) == 0 || lowerbound.compareTo(callDate) < 0)
                        && (upperBound.compareTo(callDate) == 0 || upperBound.compareTo(callDate) > 0)) {
                    usageCount++;
                }
            }

            return calculateCost(usageCount);
        }
    }

    private double calculateCost(int usageCount) {
        if (usageCount < 1) return 0;

        double totalCost = 0;

        double _1_MILL = 1000000;
        double _10_MILL = 10000000;

        if (usageCount > _10_MILL) {
            totalCost += (usageCount / 1000d) * 3.5;
            usageCount %= 1000;
        }

        if (usageCount > _1_MILL && usageCount <= _10_MILL) {
            totalCost += (usageCount / 1000d) * 4.2;
            usageCount %= 1000;
        }

        if (usageCount > 0 && usageCount <= 1000000) {
            totalCost += (usageCount / 10000d) * 5;
            usageCount %= 1000;
            totalCost += usageCount * 5;
        }

        return totalCost;
    }

}
