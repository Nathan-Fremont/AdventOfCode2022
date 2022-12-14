package puzzle13

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

        var leftIndex = 0
        var rightIndex = 0

        while (leftIndex < leftList.size) {
            val leftValue = leftList.getOrNull(leftIndex)
            val rightValue = rightList.getOrNull(rightIndex)

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
                }

                else -> {
                    return isInCorrectOrder(
                        leftSignal = leftValue,
                        rightSignal = rightValue,
                    )
                }
            }

            leftIndex += 1
            rightIndex += 1
        }

        println("isInCorrectOrder")
        return true
    }
}