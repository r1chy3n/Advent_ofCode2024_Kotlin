fun main() {
    fun part1(input: List<String>): Int {
        val columnHeight = input.indexOfFirst {
            it.isEmpty()
        } - 2
        val lockList = mutableListOf<List<Int>>()
        val keyList = mutableListOf<List<Int>>()

        input.mapIndexed { index, line ->
            index to line
        }.filter {(_, line ) ->
            line.isEmpty()
        }.map {( index, _) ->
            index
        }.toMutableList().also {
            it.add( 0, -1 )
            it.add( input.size )
        }.zipWithNext().forEach {( startExcl, endExcl ) ->
            input[ startExcl + 1 ].indices.map { xIndex ->
                ( startExcl + 2 .. endExcl - 2 ).count { yIndex ->
                    input[ yIndex ][ xIndex ] == '#'
                }
            }.let {
                if ( input[ startExcl + 1 ].all { it == '#' }) {
                    lockList.add( it )
                } else if ( input[ endExcl - 1 ].all { it == '#' }) {
                    keyList.add( it )
                }
            }
        }

        return lockList.indices.sumOf { lockIndex ->
            keyList.indices.count { keyIndex ->
                lockList[ lockIndex ].indices.all { colIndex ->
                    lockList[ lockIndex ][ colIndex ] +
                            keyList[ keyIndex ][ colIndex ] <= columnHeight
                }
            }
        }

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day25")
    part1(input).println()
    part2(input).println()
}
