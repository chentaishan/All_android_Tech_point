package com.example.leetcode.array

import org.junit.Test
import java.util.*


/**
 * ---删除排序数组中的重复项
 * 给你一个 升序排列 的数组 nums ，
 * 请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
 * 返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。
 * 输入：nums = [1,1,2]
输出：2, nums = [1,2,_]
解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
 */
fun removeDuplicates(nums: IntArray): Int {
    var left = 0
    for (item in 1 until nums.size) {
        println("---$item")
        if (nums[left] != nums[item]) {
            nums[++left] = nums[item]
        }
    }
    return ++left
}

/**
 * 买卖股票的最佳时机 II
给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
返回 你能获得的 最大 利润 。
输入：prices = [7,1,5,3,6,4]
输出：7
解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
     随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
总利润为 4 + 3 = 7 。

 */
fun maxProfit(prices: IntArray?): Int {

    return 0
}

/**
 * 旋转数组
给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数
输入: nums = [1,2,3,4,5,6,7], k = 3
输出: [5,6,7,1,2,3,4]
解释:
向右轮转 1 步: [7,1,2,3,4,5,6]
向右轮转 2 步: [6,7,1,2,3,4,5]
向右轮转 3 步: [5,6,7,1,2,3,4]
 */
fun rotate(nums: IntArray, k: Int): Unit {

    val copyOfRange = nums.copyOfRange(nums.size - k, nums.size+1)
    println("copy=$copyOfRange")




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