fun main() {
    val east = Pair( 0, 1 )
    val south = Pair( 1, 0 )
    val west = Pair( 0, -1 )
    val north = Pair( -1, 0 )
    val startOrder = listOf( east, south, west, north )
    val dirMap = mapOf(
        east to listOf( south, east, north ),
        south to listOf( west, south, east ),
        west to listOf( north, west, south ),
        north to listOf( east, north, west )
    )

    fun part1(input: List<String>): Int {
        fun nextStep(
            pos: Pair<Int, Int>, height: Int,
            dir: Pair<Int, Int>, trailCatch: MutableList<Pair<Int, Int>>
        ): Int {
            val nextY = pos.first + dir.first
            val nextX = pos.second + dir.second

            if ( nextY in input.indices && nextX in input.first().indices ) {
                val nextPos = Pair( nextY, nextX )

                if ( trailCatch.contains( nextPos )) {
                    return 0
                } else {
                    val nextHeight = input[ nextY ][ nextX ].digitToInt()

                    if ( nextHeight == height + 1 ) {
                        trailCatch.add( nextPos )

                        return if ( nextHeight == 9 ) 1
                        else dirMap.getValue(dir).sumOf { nextDir ->
                            nextStep(
                                Pair(nextY, nextX),
                                nextHeight, nextDir, trailCatch
                            ) // nextStep
                        } //  return if:else
                    } else {
                        return 0
                    } // if - else
                } // if - else
            } else {
                return 0
            } // if - else
        } // fun nextStep( Pair<Int, Int>, Int, Pair<Int, Int>)

        val trailCatch: MutableList<Pair<Int, Int>>

        return input.flatMapIndexed { yIndex, line ->
            "0".toRegex().findAll(line).map { result ->
                Pair(yIndex, result.range.first)
            } // String.toRegex.findAll.map
        }.sumOf { trailhead ->
            trailCatch = mutableListOf()

            startOrder.sumOf { dir ->
                nextStep( trailhead, 0, dir, trailCatch )
            } // List<Pair<Int, Int>>
        } // List<String>.flatMapIndexed.sumOf
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        fun nextStep( pos: Pair<Int, Int>, height: Int, dir: Pair<Int, Int>
        ): Int {
            val nextY = pos.first + dir.first
            val nextX = pos.second + dir.second

            if ( nextY in input.indices && nextX in input.first().indices ) {
                val nextHeight = input[ nextY ][ nextX ].digitToInt()

                return if ( nextHeight == height + 1 )
                        ( if ( nextHeight == 9 ) 1
                        else dirMap.getValue( dir ).sumOf { nextDir ->
                            nextStep(
                                Pair( nextY, nextX ), nextHeight, nextDir )
                        })
                else 0
            } else {
                return 0
            } // if - else
        } // fun nextStep( Pair<Int, Int>, Int, Pair<Int, Int>)

        return input.flatMapIndexed { yIndex, line ->
            "0".toRegex().findAll(line).map { result ->
                Pair(yIndex, result.range.first)
            } // String.toRegex.findAll.map
        }.sumOf { trailhead ->
            startOrder.sumOf { dir ->
                nextStep( trailhead, 0, dir )
            } // List<Pair<Int, Int>>
        } // List<String>.flatMapIndexed.sumOf
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
