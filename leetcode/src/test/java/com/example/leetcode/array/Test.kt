package com.example.leetcode.array

import org.junit.Test
import java.util.*


/**
 * 给你一个 升序排列 的数组 nums ，
 * 请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
 * 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
 * 输入：nums = [1,1,2]
输出：2, nums = [1,2,_]
解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 */
@Test
fun removeDuplicates(numss: IntArray): Int {
    val nums = mutableListOf(1, 1, 2)
//    nums.plus(listOf(0,0,1,1,1,2,2,3,3,4))
    var left = 0
    for (item in 1 until nums.size) {
        System.out.println("---$item")
        if (nums[left] != nums[item]) {
            nums[++left] = nums[item]
        }
    }
    // TODO ++位置导致的差异
    return ++left
}

/**
 *
 *  两数之和
 * 输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 */
@Test
fun twoSum(nums: IntArray, target: Int): IntArray {
    val hashtable: MutableMap<Int, Int> = HashMap()
    for (i in nums.indices) {
        if (hashtable.containsKey(target - nums[i])) {
            return intArrayOf(hashtable[target - nums[i]]!!, i)
        }
        hashtable[nums[i]] = i
    }
    return IntArray(0)
}

fun maxSubArray(nums: IntArray): Int {
    var maxValue = nums[0]
    for (index in 0..nums.size) {


        var temp = nums[index]
        if (temp <= 0) {
            continue
        }
        for (pos in index..nums.size) {
            temp += nums[pos]
            maxValue = Math.max(temp, maxValue)
        }
    }
    return maxValue
}

fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): Unit {

    for (item in 0 until n) {
        nums1[item + m] = nums2[item]
    }
    Arrays.sort(nums1)

    for (item in nums1) {
        System.out.println("----${item}")
    }
}