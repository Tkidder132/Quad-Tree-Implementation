/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import java.math.BigDecimal;

/**
 *
 * @author tkidder
 */
public class BigDecimalUtility
{
    public BigDecimalUtility() {}
    
    public static boolean BigDecimalGreaterThanOrEqualTo(BigDecimal one, BigDecimal two)
    {
        return (BigDecimalGreaterThan(one, two) || BigDecimalEqualTo(one, two));
    }
    
    public static boolean BigDecimalLessThanOrEqualTo(BigDecimal one, BigDecimal two)
    {
        return (BigDecimalLessThan(one, two) || BigDecimalEqualTo(one, two));
    }
    
    public static boolean BigDecimalGreaterThan(BigDecimal one, BigDecimal two)
    {
        return one.compareTo(two) == 1;
    }
    
    public static boolean BigDecimalLessThan(BigDecimal one, BigDecimal two)
    {
        return one.compareTo(two) == -1;
    }
    
    public static boolean BigDecimalEqualTo(BigDecimal one, BigDecimal two)
    {
        return one.compareTo(two) == 0;
    }
}
