package puzzle10

import FileUtils

internal class InstructionsExecutor {

    private var currentCycle = 0
    private var currentValue = 0
    private var valuesForCycle = mutableListOf<Int>()
    private var crtScreen = mutableListOf<MutableList<String>>()

    fun executeInstructions(instructionsToExecute: List<Instruction>): InstructionsExecutorResult {
        println(
            "===================================================================\n" +
                    "executeInstructions START ${"instructionsToExecute" to instructionsToExecute}"
        )
        currentCycle = 1
        currentValue = 1
        valuesForCycle.clear()
        crtScreen.clear()
        repeat(6) {
            crtScreen += mutableListOf<String>()
        }
        var valueToAdd = 0

        instructionsToExecute
            .forEach { instruction ->
                repeat(instruction.cycleCost) { cycleInCost ->
                    println("executeInstructions START ${"instruction" to instruction}, ${"currentCycle" to currentCycle}, ${"currentValue" to currentValue}")
                    valuesForCycle += currentValue
                    currentValue += valueToAdd
                    drawCRT()
                    valueToAdd = 0
//                    println("executeInstructions END ${"instruction" to instruction}, ${"currentCycle" to currentCycle}, ${"currentValue" to currentValue}")
                    currentCycle += 1
                }
                if (instruction is Instruction.Addx) {
                    valueToAdd = instruction.value
                }
            }

        println(
            "executeInstructions END, ${"currentCycle" to currentCycle}, ${"currentValue" to currentValue}\n" +
                    "==================================================================="
        )

        return InstructionsExecutorResult(
            listOfSignalStrength = valuesForCycle.mapIndexed { cycle, valueAtCycle ->
                SignalStrength(
                    cycle = cycle,
                    valueAtCycle = valueAtCycle,
                )
            },
            crtScreen = crtScreen,
        )
    }

    private fun drawCRT() {
        val row = (currentCycle - 1) / 40
        val column = (currentCycle - 1) % 40
        val isSpriteVisible = column == currentValue
                || column == currentValue + 1
                || column == currentValue - 1
        crtScreen[row] += if (isSpriteVisible) {
            "#"
        } else {
            "."
        }
    }
}