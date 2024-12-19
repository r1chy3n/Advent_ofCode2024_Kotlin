fun main() {
    val east = Pair(0, 1)
    val south = Pair(1, 0)
    val west = Pair(0, -1)
    val north = Pair(-1, 0)
    val clockwises = mapOf(
        east to south, south to west, west to north, north to east
    )
    val counterClockwises = mapOf(
        east to north, north to west, west to south, south to east
    )
    val dirMap = mapOf(
        east to listOf(south, east, north),
        south to listOf(west, south, east),
        west to listOf(north, west, south),
        north to listOf(east, north, west)
    )

    fun part1(input: List<String>): Int {
        val tileS = Pair(input.lastIndex - 1, 1)
        val tileE = Pair(1, input[1].lastIndex - 1)
        val scoreMap = mutableMapOf<Pair<Int, Int>, Int>()

        class Reindeer(
            var pos: Pair<Int, Int>,
            val dir: Pair<Int, Int>,
            var score: Int,
            val history: MutableSet<Pair<Int, Int>>
        ) {
            var clockwise: Reindeer? = null
            var counterClockwise: Reindeer? = null
            var canForward = false
            var reachEnd = false

            fun moveForward(): Int {
                var dirCount = 0

                if (input[pos.first + dir.first][pos.second + dir.second]
                    != '#') {
                    pos = Pair(pos.first + dir.first, pos.second + dir.second)
                    score += 1
                    dirCount = dirMap.getValue(dir).count { nextDir ->
                        input[pos.first + nextDir.first][
                            pos.second + nextDir.second] != '#'
                                && !history.contains(
                            Pair(
                                pos.first + nextDir.first,
                                pos.second + nextDir.second
                            )
                        )
                    }

                    if (dirCount > 1) {
                        history.add(pos)
                    }

                    if (pos == tileE) {
                        reachEnd = true
                        canForward = false
                        clockwise = null
                        counterClockwise = null
                    } else {
                        canForward = input[pos.first + dir.first
                        ][pos.second + dir.second] != '#' && !history.contains(
                            Pair(pos.first + dir.first, pos.second + dir.second)
                        )
                        clockwise = rotate(clockwises)
                        counterClockwise = rotate(counterClockwises)
                    }

                    if (dirCount > 1) {
                        if (scoreMap.contains(pos)) {
                            val preScore = scoreMap.getValue(pos)

                            if (preScore > score) {
                                scoreMap[pos] = score
                            } else if (preScore < score - 1000) {
                                dirCount = 0
                                canForward = false
                            }
                        } else {
                            scoreMap[pos] = score
                        }
                    }
                }

                return dirCount
            }

            fun rotate(
                rotateDirs: Map<Pair<Int, Int>, Pair<Int, Int>>
            ): Reindeer? {
                val rotateDir = rotateDirs.getValue(dir)

                return if (input[pos.first + rotateDir.first][
                        pos.second + rotateDir.second] == '#'
                ) null
                else Reindeer(
                    pos, rotateDir,
                    score + 1000, history.toMutableSet()
                )
            }
        }

        val candidates = mutableListOf(
            Reindeer(tileS, east, 0, mutableSetOf()),
            Reindeer(tileS, north, 1000, mutableSetOf())
        )

        var minScore = Int.MAX_VALUE

        var currReindeer: Reindeer

        while (candidates.isNotEmpty()) {
            currReindeer = candidates.removeAt(0)

            do {
                if (currReindeer.moveForward() > 0) {
                    currReindeer.clockwise?.let {
                        candidates.add(it)
                    }
                    currReindeer.counterClockwise?.let {
                        candidates.add(it)
                    }
                } // if
            } while (currReindeer.canForward)

            if (currReindeer.reachEnd) {
                minScore = currReindeer.score.coerceAtMost(minScore)
            }
        } // while

        return minScore
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val tileS = Pair(input.lastIndex - 1, 1)
        val tileE = Pair(1, input[1].lastIndex - 1)
        val scoreMap = mutableMapOf<Pair<Int, Int>, Int>()

        class Reindeer(
            var pos: Pair<Int, Int>, val dir: Pair<Int, Int>, var score: Int,
            val history: MutableSet<Pair<Int, Int>>
        ) {
            var clockwise: Reindeer? = null
            var counterClockwise: Reindeer? = null
            var canForward = false
            var reachEnd = false

            fun moveForward(): Int {
                var dirCount = 0

                if (input[pos.first + dir.first][pos.second + dir.second] != '#'
                ) {
                    pos = Pair(pos.first + dir.first, pos.second + dir.second)
                    score += 1
                    dirCount = dirMap.getValue(dir).count { nextDir ->
                        input[pos.first + nextDir.first][
                            pos.second + nextDir.second] != '#'
                                && !history.contains(
                            Pair( pos.first + nextDir.first,
                                pos.second + nextDir.second )
                        ) // MutableSet<Pair<Int, Int>>.contains
                    } // List<Pair<Int, Int>>.count

                    history.add(pos)

                    if (pos == tileE) {
                        reachEnd = true
                        canForward = false
                        clockwise = null
                        counterClockwise = null
                    } else {
                        canForward = input[pos.first + dir.first
                        ][pos.second + dir.second] != '#' && !history.contains(
                            Pair(pos.first + dir.first, pos.second + dir.second)
                        )
                        clockwise = rotate(clockwises)
                        counterClockwise = rotate(counterClockwises)
                    } // if - else

                    if (dirCount > 1) {
                        if (scoreMap.contains(pos)) {
                            val preScore = scoreMap.getValue(pos)

                            if (preScore > score) {
                                scoreMap[pos] = score
                            } else if (preScore < score - 1000) {
                                dirCount = 0
                                canForward = false
                            } // if - else
                        } else {
                            scoreMap[pos] = score
                        } // if - else
                    } // if
                } // if

                return dirCount
            } // fun moveForward()

            fun rotate(
                rotateDirs: Map<Pair<Int, Int>, Pair<Int, Int>>
            ): Reindeer? {
                val rotateDir = rotateDirs.getValue(dir)

                return if (input[pos.first + rotateDir.first][
                        pos.second + rotateDir.second] == '#' ) null
                else Reindeer( pos, rotateDir,
                    score + 1000, history.toMutableSet())
            } // fun rotate( Map<Pair<Int, Int>, Pair<Int, Int>>)
        } // class Reindeer( Pair<Int, Int>, Pair<Int, Int>, Int, ...)

        val candidates = mutableListOf(
            Reindeer(tileS, east, 0, mutableSetOf()),
            Reindeer(tileS, north, 1000, mutableSetOf())
        ) // mutableListOf

        val endList = mutableListOf<Reindeer>()

        var minScore = Int.MAX_VALUE

        var currReindeer: Reindeer

        while (candidates.isNotEmpty()) {
            currReindeer = candidates.removeAt(0)

            do {
                if (currReindeer.moveForward() > 0) {
                    currReindeer.clockwise?.let {
                        candidates.add(it)
                    }
                    currReindeer.counterClockwise?.let {
                        candidates.add(it)
                    }
                } // if
            } while (currReindeer.canForward)

            if (currReindeer.reachEnd) {
                minScore = currReindeer.score.coerceAtMost(minScore)

                endList.add( currReindeer )
            } // if
        } // while

        return endList.filter { reindeer ->
            reindeer.score == minScore
        }.flatMap { reindeer ->
            reindeer.history
        }.toSet().count() + 1

//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}
