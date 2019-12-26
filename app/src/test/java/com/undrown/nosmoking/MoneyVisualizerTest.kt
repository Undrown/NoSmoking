package com.undrown.nosmoking

import org.junit.Test
import kotlin.math.abs

class MoneyVisualizerTest {
    @Test
    fun scale(){
        val value = 12227.2344564
        val c = MoneyVisualizer(value)
        c.getSplit()
        println("Normally:")
        println(c.result)
        c.getRandomSplit()
        println("Randomly:")
        println(c.resultRandom)
        println("Accuracy: " + abs(value - c.getResultSum()))
        assert(abs(value - c.getResultSum()) < 0.01)
    }
}