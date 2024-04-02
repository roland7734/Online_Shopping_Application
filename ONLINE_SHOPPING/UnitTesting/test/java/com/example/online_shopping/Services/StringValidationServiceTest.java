package com.example.online_shopping.Services;

import com.example.online_shopping.Services.StringValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringValidationServiceTest {

    @Test
    void containsOnlyLetters_ValidInput_ShouldReturnTrue() {
        Assertions.assertTrue(StringValidationService.containsOnlyLetters("abcABC"));
    }

    @Test
    void containsOnlyLetters_InvalidInput_ShouldReturnFalse() {
        assertFalse(StringValidationService.containsOnlyLetters("123"));
    }

    @Test
    void hasEmailPattern_ValidEmail_ShouldReturnTrue() {
        assertTrue(StringValidationService.hasEmailPattern("test@example.com"));
    }

    @Test
    void hasEmailPattern_InvalidEmail_ShouldReturnFalse() {
        assertFalse(StringValidationService.hasEmailPattern("invalid_email"));
    }

    @Test
    void containsOnlyNumbers_ValidInput_ShouldReturnTrue() {
        assertTrue(StringValidationService.containsOnlyNumbers("12345"));
    }

    @Test
    void containsOnlyNumbers_InvalidInput_ShouldReturnFalse() {
        assertFalse(StringValidationService.containsOnlyNumbers("abc41"));
    }

    @Test
    void isValidExpiryDate_InvalidInput1_ShouldReturnFalse() {
        assertFalse(StringValidationService.isValidExpiryDate("12/23"));
    }

    @Test
    void isValidExpiryDate_InvalidInput2_ShouldReturnFalse() {
        assertFalse(StringValidationService.isValidExpiryDate("5/23"));
    }

    @Test
    void isValidCVV_ValidInput_ShouldReturnTrue() {
        assertTrue(StringValidationService.isValidCVV("123"));
    }

    @Test
    void isValidCVV_InvalidInput_ShouldReturnFalse() {
        assertFalse(StringValidationService.isValidCVV("1234"));
    }


}
