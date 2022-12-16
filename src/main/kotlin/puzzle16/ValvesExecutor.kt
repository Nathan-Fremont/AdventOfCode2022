package puzzle16

class ValvesExecutor {
    private lateinit var valvesParserResult: ValvesParserResult
    private var turnCount = 0
    private var totalPressure = 0
    private var valvesInspected = mutableSetOf<ValveWithCost>()

    fun findBestPathForValves(valvesParserResult: ValvesParserResult, numberOfTurns: Int = 30): ValvesExecutorResult {
        this.valvesParserResult = valvesParserResult
        println("findBestPathForValves")

        var currentValve = valvesParserResult.valves.first { valve ->
            valve.valveLabel == START_VALVE_LABEL
        }
        turnCount = 1
        while (turnCount < numberOfTurns) {
            // Travel to best valve
            println("== Minute $turnCount ==")
            addPressure()

//            findValve(startingValve = currentValve)
            val bestPath = getBestPath(
                fromValve = currentValve,
            )

            if (bestPath.size == 1) {
                // Should open
                println("Opening ${currentValve.valveLabel}")
                currentValve.isOpened = true
            } else {
                currentValve = bestPath[1].valve
                println("Traveling to ${currentValve.valveLabel}")
            }

//            if (currentValve == getBestInspectedValve(currentValve).valve) {
//                // Open it if possible
//                if (!currentValve.isOpened) {
//                    // Opening
//                    println("Opening ${currentValve.valveLabel}")
//                    currentValve.isOpened = true
//                }
//            } else {
//                // Travel
//                currentValve = getBestInspectedValve(currentValve).valve
//                println("Traveling to ${currentValve.valveLabel}")
//            }
            turnCount += 1
        }

        return ValvesExecutorResult(
            valves = valvesParserResult.valves,
            totalPressure = totalPressure,
        )
    }

    private fun addPressure() {
        val valvesOpened = valvesParserResult.valves
            .filter { valve ->
                valve.isOpened
            }
            .joinToString { it.valveLabel }
            .takeIf { it.isNotBlank() }
            ?: "No valves"
        val addedPressure = valvesParserResult.valves
            .filter { valve ->
                valve.isOpened
            }
            .sumOf { valve ->
                valve.flowRate.toInt()
            }

        totalPressure += addedPressure
        println("$valvesOpened are open, releasing $addedPressure pressure.")
        println("Total pressure => $totalPressure")
    }

    private fun findValve(startingValve: Valve): Valve {
//        println("findValve")
        valvesInspected.clear()
        calculateCostsForValve(
            currentValve = startingValve,
            cost = 0.0
        )

        val bestValve = getBestInspectedValve(fromValve = startingValve)
        val path = getPathBetweenValves(fromValve = startingValve, toValve = bestValve.valve)

//        println("findValve ${"bestValve" to bestValve}")
        return bestValve.valve
    }

    private fun getBestPath(fromValve: Valve): List<ValveFlowAndCost> {
        val path = mutableListOf<ValveFlowAndCost>()
        propagateToGetBestPath(
            fromValve = fromValve,
            path = path,
            cost = 0.0,
        )
        val distinctedPath = path
            .distinct()
            .sortedBy { valve ->
                valve.cost
            }
        val bestValve = distinctedPath
            .maxBy { valve ->
                valve.flowRate
            }
        val index = distinctedPath.indexOf(bestValve)
        val bestPath = distinctedPath.subList(
            fromIndex = 0,
            toIndex = index + 1,
        )
        val pathBetweenTwoValves = getPathBetweenValves(
            fromValve = fromValve,
            toValve = bestValve.valve,
        )
        return pathBetweenTwoValves
    }

    private fun propagateToGetBestPath(
        fromValve: Valve,
        path: MutableList<ValveFlowAndCost>,
        cost: Double,
    ): List<ValveFlowAndCost> {
        // If we are at target
        path += ValveFlowAndCost(
            valve = fromValve,
            flowRate = if (fromValve.isOpened) {
                0.0
            } else if (cost == 0.0) {
                fromValve.flowRate / 1.0
            } else {
                fromValve.flowRate / cost
            },
            cost = cost,
        )

        val newCost = cost + COST_MODIFIER
        // Else, we search into more tunnels
        fromValve.tunnelsToValve.forEach { destValve ->
            val destWithCost = ValveFlowAndCost(
                valve = destValve,
                flowRate = if (destValve.isOpened) {
                    0.0
                } else {
                    destValve.flowRate / newCost
                },
                cost = newCost,
            )
            val alreadyHaveValve = path.indexOfFirst { valveInPath ->
                valveInPath.valve == destValve
            }.takeIf { it != -1 }
            if (alreadyHaveValve != null
                && path[alreadyHaveValve].cost > newCost
            ) {
                path[alreadyHaveValve] = destWithCost
                propagateToGetBestPath(
                    fromValve = destValve,
                    path = path,
                    cost = newCost
                )
            } else if (alreadyHaveValve == null) {
                path += destWithCost
                propagateToGetBestPath(
                    fromValve = destValve,
                    path = path,
                    cost = newCost
                )
            }
        }

        return path
    }

    private fun getPathBetweenValves(fromValve: Valve, toValve: Valve): List<ValveFlowAndCost> {
        val path = mutableListOf<ValveFlowAndCost>()
        getPathToTargetValve(
            fromValve = fromValve,
            targetValve = toValve,
            path = path,
            cost = 1.0
        )
        return path.distinct()
    }

    private fun getPathToTargetValve(
        fromValve: Valve,
        targetValve: Valve,
        path: MutableList<ValveFlowAndCost>,
        cost: Double,
    ): List<ValveFlowAndCost> {
        // If we are at target
        path += ValveFlowAndCost(
            valve = fromValve,
            flowRate = if (fromValve.isOpened) {
                0.0
            } else {
                fromValve.flowRate / cost
            },
            cost = cost,
        )
        if (fromValve == targetValve) {
            return path
        }


        val newCost = cost + COST_MODIFIER
        // If targetValve is next to currentValve
        if (fromValve.tunnelsToValve.any { destValve ->
                destValve == targetValve
            }
        ) {
            path += ValveFlowAndCost(
                valve = targetValve,
                flowRate = if (targetValve.isOpened) {
                    0.0
                } else {
                    targetValve.flowRate / cost
                },
                cost = newCost,
            )
            return path
        }

        // Else, we search into more tunnels
        fromValve.tunnelsToValve.forEach { destValve ->
            val destWithCost = ValveFlowAndCost(
                valve = destValve,
                flowRate = if (destValve.isOpened) {
                    0.0
                } else {
                    destValve.flowRate / cost
                },
                cost = newCost,
            )
            val alreadyHaveValve = path.indexOfFirst { valveInPath ->
                valveInPath.valve == destValve
            }.takeIf { it != -1 }
            if (alreadyHaveValve != null
                && path[alreadyHaveValve].cost > newCost
            ) {
                path[alreadyHaveValve] = destWithCost
                return getPathToTargetValve(
                    fromValve = destValve,
                    targetValve = targetValve,
                    path = path,
                    cost = newCost
                )
            } else if (alreadyHaveValve == null) {
                path += destWithCost
                return getPathToTargetValve(
                    fromValve = destValve,
                    targetValve = targetValve,
                    path = path,
                    cost = newCost
                )
            }
        }

        return path
    }

    private fun getBestInspectedValve(fromValve: Valve): ValveWithCost = valvesInspected
        .maxBy { valveWithCost ->
            valveWithCost.cost
        }

    private fun calculateCostsForValve(currentValve: Valve, cost: Double) {
        valvesInspected += ValveWithCost(
            valve = currentValve,
            cost = if (currentValve.isOpened) {
                0.0
            } else {
                currentValve.flowRate / cost
            },
        )

        currentValve.tunnelsToValve
            .forEach { destinationValve ->
                if (!valvesInspected.any { pair ->
                        pair.valve == destinationValve
                    }
                ) {
                    val newCost = cost + COST_MODIFIER
                    valvesInspected += ValveWithCost(
                        valve = destinationValve,
                        cost = if (destinationValve.isOpened) {
                            0.0
                        } else {
                            destinationValve.flowRate / newCost
                        },
                    )
                    calculateCostsForValve(currentValve = destinationValve, cost = newCost)
                }
            }
    }

    companion object {
        private const val START_VALVE_LABEL = "AA"
        private const val COST_MODIFIER = 1.0
    }
}