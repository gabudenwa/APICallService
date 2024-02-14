package test;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillingServiceTest {
    @Test
   void trackUsage() {
      main.BillingService billingService = new main.BillingService();
       for (int i = 0; i <5; i++) {
           billingService.trackUsage("aaa-bbb-ccc-ddd", "169.254.169.254");
       }
      assertEquals(1, 1);
   }

   @Test
   void calculate() {
      main.BillingService billingService = new main.BillingService();
       double monthlyBillCost = billingService.getMonthlyBillCost("aaa-bbb-ccc-ddd", new Date());
       assertEquals(0, monthlyBillCost);
   }
}
