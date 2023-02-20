package com.example.leetcode

import com.example.leetcode.array.merge
import com.example.leetcode.array.removeDuplicates
import com.example.leetcode.array.rotate
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
//        val nums1 = intArrayOf(1, 2, 3, 0, 0, 0)
//
//        val removeDuplicates = removeDuplicates(nums1)
//        println("removeDuplicates$removeDuplicates")

        val nums1 = intArrayOf(1, 2, 3, 4, 5, 6,7)
        rotate(nums1,3)

    }
}