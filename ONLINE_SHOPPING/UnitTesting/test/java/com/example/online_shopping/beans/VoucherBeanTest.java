package com.example.online_shopping.beans;

import com.example.online_shopping.beans.VoucherBean;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;

public class VoucherBeanTest {

    @Test
    void updateStatusBasedOnDate_CurrentDateBeforeEndDate_ShouldNotChangeStatus() {
        Date futureEndDate = new Date(System.currentTimeMillis() + 86400000); // Add one day
        VoucherBean voucher = new VoucherBean("code", 10, new Date(System.currentTimeMillis()), futureEndDate, true);

        voucher.updateStatusBasedOnDate();

        assertTrue(voucher.isActive());
    }

    @Test
    void updateStatusBasedOnDate_CurrentDateAfterEndDate_ShouldSetStatusToFalse() {
        Date pastEndDate = new Date(System.currentTimeMillis() - 86400000); // Subtract one day
        VoucherBean voucher = new VoucherBean("code", 10, new Date(System.currentTimeMillis()), pastEndDate, true);

        voucher.updateStatusBasedOnDate();

        assertFalse(voucher.isActive());
    }


}
