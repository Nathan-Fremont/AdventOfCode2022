package puzzle10

import FileUtils

internal class InstructionsExecutor {

    private var currentCycle = 0
    private var currentValue = 0
    private var valuesForCycle = mutableListOf<Int>()

    fun executeInstructions(instructionsToExecute: List<Instruction>): List<SignalStrength> {
        println("===================================================================\n" +
                "executeInstructions START ${"instructionsToExecute" to instructionsToExecute}")
        currentCycle = 1
        currentValue = 1
        valuesForCycle.clear()
        var valueToAdd = 0

        instructionsToExecute
            .forEach { instruction ->
                repeat(instruction.cycleCost) { cycleInCost ->
                    println("executeInstructions START ${"instruction" to instruction}, ${"currentCycle" to currentCycle}, ${"currentValue" to currentValue}")
                    valuesForCycle += currentValue
                    currentValue += valueToAdd
                    valueToAdd = 0
//                    println("executeInstructions END ${"instruction" to instruction}, ${"currentCycle" to currentCycle}, ${"currentValue" to currentValue}")
                    currentCycle += 1
                }
                if (instruction is Instruction.Addx) {
                    valueToAdd = instruction.value
                }
            }

        println("executeInstructions END, ${"currentCycle" to currentCycle}, ${"currentValue" to currentValue}\n" +
                "===================================================================")

        return valuesForCycle.mapIndexed { cycle, valueAtCycle ->
            SignalStrength(
                cycle = cycle,
                valueAtCycle = valueAtCycle,
            )
        }
    }
}