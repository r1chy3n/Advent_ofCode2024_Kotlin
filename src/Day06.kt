fun main() {
    val north = Pair( -1, 0 )
    val east = Pair( 0, 1 )
    val south = Pair( 1, 0 )
    val west = Pair( 0, -1 )
    val nextDir = mapOf(
        north to east, east to south, south to west, west to north
    )

    fun startPos( input: List<String>): Pair<Int, Int> {
        return input.indexOfFirst { line ->
            line.contains( '^' )
        }.let { i ->
            Pair( i, input[ i ].indexOf( '^' ))
        } // List<String>.indexOfFirst.let
    } // fun startPos( List<String>)

    fun visitedPos(
        input: List<String>, startPos: Pair<Int, Int>
    ): Set<Pair<Int, Int>> {
        val visitedSet = mutableSetOf<Pair<Int, Int>>()
        var currDir = north
        var currPos = startPos

        var nextY: Int
        var nextX: Int

        do {
            nextY = currPos.first + currDir.first
            nextX = currPos.second + currDir.second

            if ( nextY in input.indices && nextX in input.first().indices ) {
                if ( input[ nextY ][ nextX ] == '#' ) {
                    currDir = nextDir.getValue( currDir )
                } else {
                    currPos = Pair( nextY, nextX )

                    visitedSet.add( currPos )
                } // if - else
            } // if
        } while ( nextY in input.indices && nextX in input.first().indices )

        return visitedSet
    } // fun visitedPos( List<String>)

    fun part1(input: List<String>): Int {
        return visitedPos( input, startPos( input )).size + 1
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val startPos = startPos( input )

        var nextY = -1
        var nextX = -1
        var looping = false

        var currDir: Pair<Int, Int>
        var currPos: Pair<Int, Int>
        var newRoute: Pair<Pair<Int, Int>, Pair<Int, Int>>
        var patrolRoute: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>

        return visitedPos( input, startPos ).count { newObst ->
            looping = false
            currDir = north
            currPos = startPos
            patrolRoute = mutableListOf()

            do {
                newRoute = Pair( currPos, currDir )

                if ( patrolRoute.contains( newRoute )) {
                    looping = true
                } else {
                    patrolRoute.add( newRoute )

                    nextY = currPos.first + currDir.first
                    nextX = currPos.second + currDir.second

                    if (
                        nextY in input.indices && nextX in input.first().indices
                    ) {
                        if (
                            input[ nextY ][ nextX ] == '#' ||
                            nextY == newObst.first && nextX == newObst.second
                        ) {
                            currDir = nextDir.getValue( currDir )
                        } else {
                            currPos = Pair( nextY, nextX )
                        } // if - else
                    } // if
                }
            } while (
                nextY in input.indices && nextX in input.first().indices
                && !looping
            )

            looping
        } // return - Set<Pair<Int, Int>>.count
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
