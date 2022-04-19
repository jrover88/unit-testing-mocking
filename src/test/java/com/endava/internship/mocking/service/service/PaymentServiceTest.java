package com.endava.internship.mocking.service.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import com.endava.internship.mocking.service.PaymentService;
import com.endava.internship.mocking.service.ValidationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private ValidationService validationService;
    @InjectMocks
    private PaymentService paymentService;
    @Captor
    private ArgumentCaptor<Payment> paymentArgumentCaptor;
    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(userRepository, paymentRepository, validationService);
    }

    @Test
    void createPayment() {
        User user = new User(1, "John", Status.ACTIVE);
        Payment payment = new Payment(1, 100d, "Payment from user John");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        Payment actualPayment = paymentService.createPayment(1, 100d);
        verify(paymentRepository).save(paymentArgumentCaptor.capture());
        Payment payment1 = paymentArgumentCaptor.getValue();
        verify(validationService).validateUserId(1);
        verify(validationService).validateAmount(100d);
        verify(validationService).validateUser(user);
        assertEquals(actualPayment, payment);
    }

    @Test
    void editMessage() {
        Payment payment = new Payment(1, 100d, "Payment from user John");
        when(paymentRepository.editMessage(payment.getPaymentId(), payment.getMessage())).thenReturn(payment);
        Payment result = paymentService.editPaymentMessage(payment.getPaymentId(), payment.getMessage());
        verify(validationService).validatePaymentId(payment.getPaymentId());
        verify(validationService).validateMessage(payment.getMessage());
        assertEquals(payment.getPaymentId(), result.getPaymentId());
        assertEquals(payment.getMessage(), result.getMessage());
    }

    @Test
    void getAllByAmountExceeding() {
        Payment payment1 = new Payment(1, 100d, "Payment from user 1");
        Payment payment2 = new Payment(2, 200d, "Payment from user 2");
        Payment payment3 = new Payment(3, 300d, "Payment from user 3");
        Payment payment4 = new Payment(4, 400d, "Payment from user 4");
        List<Payment> actual = new LinkedList<>();
        actual.add(payment1);
        actual.add(payment2);
        actual.add(payment3);
        actual.add(payment4);
        List<Payment> expected = new LinkedList<>();
        expected.add(payment2);
        expected.add(payment3);
        expected.add(payment4);
        when(paymentRepository.findAll()).thenReturn(actual);
        List<Payment> allAmount = paymentService.getAllByAmountExceeding(100d);
        verify(paymentRepository).findAll();
        assertEquals(expected, allAmount);
    }
}

