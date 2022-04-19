package com.endava.internship.mocking.service.service;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import com.endava.internship.mocking.service.BasicValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class BasicValidationServiceTest {

    private BasicValidationService basicValidationService;

    @BeforeEach
    void setUp() {
        basicValidationService = new BasicValidationService();
    }

    @Test
    void validateAmountThrowsException_ifNullable() {
        assertThatIllegalArgumentException().isThrownBy(() -> basicValidationService.validateAmount(null));
    }

    @Test
    void validateAmountThrowsException_ifAmountNegative() {

        final double amount = -4.5;
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            basicValidationService.validateAmount(amount);
        });
    }


    @Test
    void validatePaymentIdThrowsException_ifNullable() {
        assertThatIllegalArgumentException().isThrownBy(() -> basicValidationService.validatePaymentId(null));
    }

    @Test
    void validateUserIdThrowsException_ifNullable() {
        assertThatIllegalArgumentException().isThrownBy(() -> basicValidationService.validateUserId(null));
    }

//    @Test
//    void validateUserThrowsException_ifWrongStatus() {
//        User user = new User(1, "Vasile", Status.INACTIVE);
//        System.out.println(user.getStatus());
//        assertThatIllegalArgumentException().isThrownBy(() -> user.getStatus());
//    }

    @Test
    void validateMessageThrowsException_ifNullable() {
        assertThatIllegalArgumentException().isThrownBy(() -> basicValidationService.validateMessage(null));
    }


}
