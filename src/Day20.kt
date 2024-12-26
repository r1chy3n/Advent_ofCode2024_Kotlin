import kotlin.math.abs

fun main() {
    val wall = -2
    val track = -1
    val start = 0
    val up = -1 to 0
    val down = 1 to 0
    val left = 0 to -1
    val right = 0 to 1
    val dirList = listOf( up, down, left, right )
    var posS = 0 to 0
    var posE = 0 to 0

    fun part1(input: List<String>): Int {
        val racetrack = input.mapIndexed { y, line ->
            line.mapIndexed { x, ch ->
                if ( ch == '#' ) {
                    wall
                } else if ( ch == 'S' ) {
                    posS = y to x
                    start
                } else {
                    if ( ch == 'E' ) {
                        posE = y to x
                    }

                    track
                }
            }.toMutableList()
        } // List<String>.mapIndexed

        var nextStep = posS
        var step = 0

        do {
            nextStep = dirList.first { dir ->
                racetrack[ nextStep.first + dir.first ][
                    nextStep.second + dir.second ] == track
            }.let { dir ->
                nextStep.first + dir.first to nextStep.second + dir.second
            }
            racetrack[ nextStep.first ][ nextStep.second ] = ++step
        } while ( nextStep != posE )

        val picoList = mutableListOf<Int>()

        ( 1 ..< racetrack.lastIndex ).forEach { y ->
            ( 1 ..< racetrack.first().lastIndex ).forEach { x ->
                if ( racetrack[ y ][ x ] == wall ) {
                    if ( racetrack[ y ][ x - 1 ] != wall
                        && racetrack[ y ][ x + 1 ] != wall ) {
                        picoList.add( racetrack[ y ][ x - 1 ].coerceAtLeast(
                            racetrack[ y ][ x + 1 ]) - racetrack[ y ][ x + 1
                            ].coerceAtMost( racetrack[ y ][ x - 1 ]) - 2 )
                    }
                    if ( racetrack[ y - 1 ][ x ] != wall
                        && racetrack[ y + 1 ][ x ] != wall ) {
                        picoList.add( racetrack[ y - 1 ][ x ].coerceAtLeast(
                            racetrack[ y + 1 ][ x ]) - racetrack[ y + 1 ][ x
                        ].coerceAtMost( racetrack[ y - 1 ][ x ]) - 2 )
                    }
                }
            }
        }

        return picoList.groupingBy { it }.eachCount().filter {( pico, _) ->
            pico >= 100
        }.map {(_, cheat) ->
            cheat
        }.sum()

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val leastSaved = 50
        val racetrack = input.mapIndexed { y, line ->
            line.mapIndexed { x, ch ->
                if ( ch == '#' ) {
                    wall
                } else if ( ch == 'S' ) {
                    posS = y to x
                    start
                } else {
                    if ( ch == 'E' ) {
                        posE = y to x
                    }

                    track
                }
            }.toMutableList()
        } // List<String>.mapIndexed

        val stepList = mutableListOf( posS )

        var nextStep = posS
        var step = 0

        do {
            nextStep = dirList.first { dir ->
                racetrack[ nextStep.first + dir.first ][
                    nextStep.second + dir.second ] == track
            }.let { dir ->
                nextStep.first + dir.first to nextStep.second + dir.second
            }
            racetrack[ nextStep.first ][ nextStep.second ] = ++step

            stepList.add( nextStep )
        } while ( nextStep != posE )

        val picoList = mutableListOf<Int>()

        stepList.subList( 0, stepList.size - leastSaved
        ).forEachIndexed { startIndex, ( sY, sX ) ->
            stepList.subList( startIndex + leastSaved, stepList.size
            ).forEach {( eY, eX ) ->
                val stepCount = abs( eY - sY ) + abs( eX - sX )
                val picoSaved = racetrack[ eY ][ eX ] -
                        racetrack[ sY ][ sX ] - stepCount

                if ( stepCount <= 20 && picoSaved >= leastSaved
                ) {
                    picoList.add( picoSaved )
                }
            }
        }

        return picoList.groupingBy { it }.eachCount().filter {( pico, _) ->
            pico >= 100
        }.map {(_, cheat) ->
            cheat
        }.sum()

        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}
