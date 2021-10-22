package com.company

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

fun main() {
    val scan: Scanner = Scanner(System.`in`)
    val n: Int = scan.nextInt()
    var objectCounter: Int = n + 1
    val length: Int = 2.0.pow(n.toDouble()).toInt()
    val objects = arrayOfNulls<CharArray>(length)
    var values = arrayOfNulls<Int>(length)
    var disObjects = ArrayList<Int>()
    for (i in 0 until length) {
        objects[i] = scan.next().toCharArray()
        values[i] = scan.nextInt()
    }
    if (values.contains(1)) {
        var size = n
        var lines = 0
        for (i in 0 until length) {
            if (values[i] == 1) {
                size += objects[i]!!.count { it == '0' }
                size += (n - 1)
                lines++
            }
        }
        println(lines + size - 1)
        for (i in 0 until length) {
            val ist = objects[i]
            val value = values[i]
            var lastElemIsFalse = false
            if (value == 1) {
                for (j in 0..ist!!.lastIndex) {
                    if (ist[j] == '0') {
                        println("1 ${j + 1}")
                        objectCounter++
                        if (j > 0) {
                            if ((lastElemIsFalse && j == 1) || j > 1) {
                                println("2 ${objectCounter - 2} ${objectCounter - 1}")
                            } else {
                                println("2 ${j} ${objectCounter - 1}")
                            }
                            objectCounter++
                        }
                        lastElemIsFalse = true

                    } else {
                        if (j > 0) {
                            if ((lastElemIsFalse && j == 1) || j > 1) {
                                println("2 ${objectCounter - 1} ${j + 1}")
                                lastElemIsFalse = false
                            } else {
                                println("2 ${j} ${j + 1}")
                            }
                            objectCounter++
                        }
                    }


                }
                disObjects.add(objectCounter-1)
            }
        }
        for (i in 1..disObjects.lastIndex) {
            if(i==1){
                println("3 ${disObjects[i-1]} ${disObjects[i]}")
            }
            else{
                println("3 ${objectCounter-1} ${disObjects[i]}")
            }
            objectCounter++
        }
    }
    else {
        println(n + 2)
        println("1 ${1}")
        println("2 ${1} ${n+1}")
    }
}