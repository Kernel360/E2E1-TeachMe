package kr.kernel.teachme.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @DisplayName("isNumeric 메서드가 숫자로 이루어진 문자열을 잘 인식하는지")
    @Test
    void testIsNumericWithNumericString() {
        assertTrue(StringUtil.isNumeric("123"));
        assertTrue(StringUtil.isNumeric("123.45"));
    }

    @DisplayName("isNumeric 메서드가 숫자가 아닌 문자열을 잘 인식하는지")
    @Test
    void testIsNumericWithNonNumericString() {
        assertFalse(StringUtil.isNumeric("abc"));
        assertFalse(StringUtil.isNumeric("123abc"));
    }

    @DisplayName("removeNotNumeric 메서드가 알파벳을 잘 제거하는지")
    @Test
    void testRemoveNotNumericWithAlphanumericString() {
        assertEquals("12345", StringUtil.removeNotNumeric("123abc45"));
    }

    @DisplayName("removeNotNumeric 메서드가 특수문자를 잘 제거하는지")
    @Test
    void testRemoveNotNumericWithSpecialCharacters() {
        assertEquals("12345", StringUtil.removeNotNumeric("123!@#$%^&*()_+45"));
    }

    @DisplayName("removeNotNumeric 메서드가 숫자만 있는 문자열을 그대로 반환하는지")
    @Test
    void testRemoveNotNumericWithNumericString() {
        assertEquals("12345", StringUtil.removeNotNumeric("12345"));
    }

}
