package puzzle13

import kotlin.math.max

class DistressSignalExecutor {
    fun executePairsOfSignals(pairsOfSignals: List<Pair<DistressSignal?, DistressSignal?>>): List<DistressSignalResult> {
        println("executePairsOfSignals")
        val result = pairsOfSignals
            .mapIndexed { index, pairOfSignals ->
                val resultForIndex = DistressSignalResult(
                    leftSignal = pairOfSignals.first,
                    rightSignal = pairOfSignals.second,
                    indexInList = index + 1,
                    isInCorrectOrder = isInCorrectOrder(
                        leftSignal = pairOfSignals.first,
                        rightSignal = pairOfSignals.second,
                    )
                )
//                println("executePairsOfSignals ${"resultForIndex" to resultForIndex}")
                resultForIndex
            }

        println("executePairsOfSignals ${"result" to result}")
        return result
    }

    private fun isInCorrectOrder(leftSignal: DistressSignal?, rightSignal: DistressSignal?): Boolean {
        val leftList = when (leftSignal) {
            is DistressSignal.Multiple -> leftSignal.value
            is DistressSignal.Single -> listOf(leftSignal)
            null -> listOf()
        }
        val rightList = when (rightSignal) {
            is DistressSignal.Multiple -> rightSignal.value
            is DistressSignal.Single -> listOf(rightSignal)
            null -> listOf()
        }

        var index = 0


        if (leftList.isEmpty()) {
            return true
        } else if (rightList.isEmpty()) {
            return false
        }

        while (index < max(leftList.size, rightList.size)) {
            val leftValue = leftList.getOrNull(index)
            val rightValue = rightList.getOrNull(index)

            if (leftValue == null) {
                return true
            } else if (rightValue == null) {
                return false
            }

            when {
                leftValue is DistressSignal.Single
                        && rightValue is DistressSignal.Single -> {
                    if (leftValue.value > rightValue.value) {
                        return false
                    } else if (leftValue.value < rightValue.value) {
                        return true
                    }

                    index += 1
                }

                leftValue is DistressSignal.Multiple
                        && rightValue is DistressSignal.Multiple
                        && leftValue.value.isEmpty()
                        && rightValue.value.isEmpty()
                -> {
                    index += 1
                }

                else -> {
                    return isInCorrectOrder(
                        leftSignal = leftValue,
                        rightSignal = rightValue,
                    )
                }
            }
        }

        println("isInCorrectOrder")
        return true
    }
}