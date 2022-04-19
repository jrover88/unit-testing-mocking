package com.endava.internship.mocking.service.repository;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.repository.InMemPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import com.endava.internship.mocking.service.BasicValidationService;
import org.junit.jupiter.api.Test;

import java.util.*;

public class InMemPaymentRepositoryTest {

    private InMemPaymentRepository inMemPaymentRepository;

    @BeforeEach
    void setUp() {
        inMemPaymentRepository = new InMemPaymentRepository();
    }

    @Test
    void findByIdThrowsException_ifNullable() {
        assertThatIllegalArgumentException().isThrownBy(() -> inMemPaymentRepository.findById(null));
    }

//    @Test
//    void findByIdReturnsAnOptionalPayment_ifNullable() {
//        Payment payment = new Payment(1, 10.45, "just test string");
//        inMemPaymentRepository.save(payment);
//
//        assertThat(inMemPaymentRepository.findById(Optional.payment.getPaymentId()))
//                .isEqualTo(payment);
//    }

    @Test
    void findAllReturnsPaymentsList() {
        List<Payment> list = new ArrayList<>();
        List<Payment> list2 = new ArrayList<>();
        Payment payment = new Payment(1, 10.45, "first test string");
        Payment payment2 = new Payment(2, 13.45, "second test string");
        Payment payment3 = new Payment(3, 15.45, "third test string");
        Payment payment4 = new Payment(12, 111.45, "fourth test string");
        list.add(payment);
        list.add(payment2);
        list.add(payment3);
        list2.add(payment4);
        inMemPaymentRepository.save(payment);
        inMemPaymentRepository.save(payment2);
        inMemPaymentRepository.save(payment3);

        assertThat(inMemPaymentRepository.findAll())
                .isEqualTo(list).isNotEqualTo(list2);
        assertThat(inMemPaymentRepository.findAll())
                .isEqualTo(list);
    }

    @Test
    void saveThrowsException_ifNullable() {
        assertThatIllegalArgumentException().isThrownBy(() -> inMemPaymentRepository.save(null));
    }

//    @Test
//    void saveThrowsException_ifItHasTheSameElement() {
//        Payment payment = new Payment(1, 10.45, "first test string");
//        inMemPaymentRepository.save(payment);
//
//        assertThatIllegalArgumentException().isThrownBy(() ->
//                inMemPaymentRepository.save(inMemPaymentRepository.findById(payment.getPaymentId())));
//    }
//
    @Test
    void saveReturnAddedPayment() {
        Payment payment = new Payment(1, 10.45, "first test string");

        assertThat(inMemPaymentRepository.save(payment)).isEqualTo(payment);
    }
}
