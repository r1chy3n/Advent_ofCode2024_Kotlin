fun main() {
    fun part1(input: List<String>): Int {
        val xmas = arrayOf( 'X', 'M', 'A', 'S' )
        val directions = arrayOf(
            Pair( -1, -1 ), Pair( -1, 0 ), Pair( -1, 1 ),
            Pair(  0, -1 ),                Pair(  0, 1 ),
            Pair(  1, -1 ), Pair(  1, 0 ), Pair(  1, 1 )
        ) // directions

        return input.indices.sumOf { lineNum ->
            input[ lineNum ].indices.sumOf { chIndex ->
                directions.count {( x, y ) ->
                    xmas.indices.all { i ->
                        input.getOrNull( lineNum + x * i )?.let { line ->
                            line.getOrNull( chIndex + y * i )?.let { ch ->
                                xmas[ i ] == ch
                            } // let
                        } == true
                    } // all
                } // count
            } // sumOf
        } // sumOf
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        return ( 1 .. input.lastIndex - 1 ).sumOf { lineNum ->
            ( 1 .. input.first().lastIndex - 1 ).count { chIndex ->
                input[ lineNum ][ chIndex ] == 'A'
                        && ( input[ lineNum - 1 ][ chIndex - 1 ] == 'M'
                        && input[ lineNum + 1 ][ chIndex + 1 ] == 'S'
                        || input[ lineNum - 1 ][ chIndex - 1 ] == 'S'
                        && input[ lineNum + 1 ][ chIndex + 1 ] == 'M' )
                        && ( input[ lineNum - 1 ][ chIndex + 1 ] == 'M'
                        && input[ lineNum + 1 ][ chIndex - 1 ] == 'S'
                        || input[ lineNum - 1 ][ chIndex + 1 ] == 'S'
                        && input[ lineNum + 1 ][ chIndex - 1 ] == 'M' )
            } // count
        } // sumOf
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
