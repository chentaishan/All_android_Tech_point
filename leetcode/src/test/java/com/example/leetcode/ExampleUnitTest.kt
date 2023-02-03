package com.example.leetcode

import com.example.leetcode.array.merge
import com.example.leetcode.array.removeDuplicates
import com.example.leetcode.array.twoSum
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//        System.out.println("---------")
//        val nums1 = intArrayOf(1, 2, 3, 0, 0, 0)
//
//        val nums2 = intArrayOf(2, 5, 6)
//        merge(nums1, 3, nums2, 3)

       val num =  removeDuplicates(IntArray(9))
        System.out.println("--jj-$num")

    }


}