package ds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ExprEvaluatorTest {

    private final ExprEvaluator ev = new ExprEvaluator();

    // 基本運算與優先序 / 左結合
    @Test
    void basic_precedence_and_associativity() {
        assertEquals(3, ev.eval("1 + 2"));
        assertEquals(50, ev.eval("10 + 20 * 2"));        // 先乘後加
        assertEquals(12, ev.eval("8 / 2 * 3"));          // 左結合：(8/2)*3
        assertEquals(7, ev.eval("1 + 2 + 4"));           // 連加左結合
        assertEquals(-1, ev.eval("1 - 2"));              // 減法
        assertEquals(0, ev.eval("2 - 1 - 1"));           // (2-1)-1
    }

    // 空白與括號
    @Test
    void spaces_and_parentheses() {
        assertEquals(20, ev.eval(" ( 2 + 3 ) * 4 "));
        assertEquals(14, ev.eval("2*(3+4)"));
        assertEquals(1,  ev.eval("  ( (1 + 2) * 3 - 7 ) / 2 "));
        assertEquals(9,  ev.eval("(((1+2))*((3)))"));
    }

    // 一元負號（含數字與括號）
    @Test
    void unary_minus_variants() {
        assertEquals(2,   ev.eval("-3 + 5"));            // 開頭負號 + 數字
        assertEquals(-20, ev.eval("10 * (-2)"));         // 括號內負數
        assertEquals(-8,  ev.eval("-(3 + 5)"));          // -(...) => 0-(...)
        assertEquals(-14, ev.eval("-(2*(3+4))"));        // 巢狀
        assertEquals(7,   ev.eval("5--2"));              // 5 - (-2)
        assertEquals(-6,  ev.eval("-1*-2*-3"));          // 乘法結合
    }

    // 複合表達式
    @Test
    void mixed_long_expression() {
        assertEquals(22, ev.eval("6 + 2 * 10 - (3 + 1)"));      // 6 + 20 - 4 = 22
        assertEquals(22, ev.eval(" ( 6 + 2*10 ) - (3 + 1) "));  // 6+20 -4 = 22（再驗一次空白）
        assertEquals(6,  ev.eval(" (12/(2*2)) + (3) "));        // 12/4 + 3 = 6
    }

    // 除以 0
    @Test
    void division_by_zero_throws() {
        assertThrows(ArithmeticException.class, () -> ev.eval("5 / 0"));
        assertThrows(ArithmeticException.class, () -> ev.eval("(1+2)/(3-3)"));
    }

    // 非法輸入 / 不匹配括號 / 運算子錯誤
    @Test
    void invalid_and_mismatched_parentheses() {
        assertThrows(IllegalArgumentException.class, () -> ev.eval("foo / 30 + 7")); // 非法字元
        assertThrows(IllegalArgumentException.class, () -> ev.eval("10 + * 3"));     // 運算子連續
        assertThrows(IllegalArgumentException.class, () -> ev.eval("2 + (3 * 4"));   // 左括號未閉
        assertThrows(IllegalArgumentException.class, () -> ev.eval(")1+2("));        // 錯序括號
        assertThrows(IllegalArgumentException.class, () -> ev.eval("10 +"));         // 操作數不足
        assertThrows(IllegalArgumentException.class, () -> ev.eval("*(2+3)"));       // 起首運算子
    }

    // 邊界與溢位
    @Test
    void int_range_and_overflow() {
        assertEquals(Integer.MAX_VALUE, ev.eval("2147483647"));
        assertEquals(Integer.MIN_VALUE, ev.eval("-2147483648"));
        assertThrows(ArithmeticException.class, () -> ev.eval("2147483647 + 1"));   // 溢位
        assertThrows(ArithmeticException.class, () -> ev.eval("-2147483649"));      // 小於最小值
    }

    // 純數字與空白
    @Test
    void bare_numbers_and_spaces() {
        assertEquals(0, ev.eval("0"));
        assertEquals(123, ev.eval("   123   "));
        assertEquals(-42, ev.eval("   -42"));
    }
}
