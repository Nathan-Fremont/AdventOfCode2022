package puzzle11

class MonkeysExecutor() {
    fun executeMonkeysForNumberOfTurns(givenMonkeys: List<Monkey>, numberOfTurns: Int = 20): MonkeysExecutorResult {
        println("executeMonkeysForNumberOfTurns")
        val resultMonkeys = mutableListOf<Monkey>()
            .apply {
                addAll(givenMonkeys)
            }
            .toList()
        val numberOfInspectionsByMonkeys = mutableListOf<Int>()
            .apply {
                repeat(givenMonkeys.size) {
                    add(0)
                }
            }

        for (iteration in 0 until numberOfTurns) {
            resultMonkeys.forEachIndexed { monkeyIndex, monkey ->
                monkey.items.forEachIndexed { itemIndex, item ->
                    // Monkey inspects item
                    monkey.items[itemIndex] = monkey.operationOnItem.operation(item)
                    numberOfInspectionsByMonkeys[monkeyIndex] += 1
                    // After each monkey inspects an item but before it tests your worry level,
                    // your relief that the monkey's inspection didn't damage the item causes your
                    // worry level to be divided by three and rounded down to the nearest integer.
                    monkey.items[itemIndex] /= 3

                    val newItem = monkey.items[itemIndex]
                    // Monkey tests and give item
                    if (monkey.testOnItem.test(newItem)) {
                        resultMonkeys[monkey.testOnItem.monkeyTargetWhenTrue].items += newItem
                    } else {
                        resultMonkeys[monkey.testOnItem.monkeyTargetWhenFalse].items += newItem
                    }
                }
                monkey.items.clear()
            }
        }

        val result = MonkeysExecutorResult(
            monkeysBeforeExecution = givenMonkeys,
            monkeysAfterExecution = resultMonkeys,
            numberOfInspectionsByMonkeys = numberOfInspectionsByMonkeys
        )
        println("executeMonkeysForNumberOfTurns ${"result" to result}")

        return result
    }
}