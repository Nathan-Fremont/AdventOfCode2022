package puzzle14

class FallingSandRocksExecutor {
    fun createGridWithListOfRocksPaths(listOfRocksPaths: List<List<Rock>>) {
        val minXValue = listOfRocksPaths
            .flatten()
            .minBy { rock ->
                rock.x
            }

        val maxXValue = listOfRocksPaths
            .flatten()
            .maxBy { rock ->
                rock.x
            }

        val delta = maxXValue.x - minXValue.x
        println("createGridWithListOfRocksPaths ${"minXValue" to minXValue}, ${"maxXValue" to maxXValue}, ${"delta" to delta}")

        val grid = mutableListOf<MutableList<String>>()
        repeat(GRID_HEIGHT) {
            grid += mutableListOf<String>()
            for (i in 0 until delta) {
                grid.last() += "."
            }
        }
        println("createGridWithListOfRocksPaths ${"grid" to grid}")
    }

    companion object {
        private const val GRID_HEIGHT = 10
    }
}