import kotlin.math.pow

fun main() {
    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    fun blink( stone: Long, times: Int ): Long {
        val key = Pair( stone, times )

        var result = 1L

        if ( times > 0 ) {
            result = if ( cache.containsKey( key )) {
                cache.getValue( key )
            } else {
                if ( stone == 0L ) {
                    blink( 1L, times - 1 )
                } else if ( stone == 1L ) {
                    blink( 2024L, times - 1)
                } else {
                    stone.toString().length.let { digit ->
                        if ( digit % 2 == 0 ) {
                            10F.pow( digit / 2 ).toLong().let { divisor ->
                                listOf(
                                    stone.div( divisor ), stone.mod( divisor )
                                ).sumOf { changed ->
                                    blink( changed, times - 1 )
                                }
                            }
                        } else {
                            blink( stone * 2024, times - 1 )
                        } // if - else
                    } // Long.toString.length.let
                }.also {
                    cache.put( key, it )
                } // if - else if - else.also
            } // result = if - else
        } // if

        return result
    } // fun blink( Long )

    fun stoneCount(
        input: List<String>, blinkTimes: Int
    ) = input.first().split( " " ).map {
        it.toLong()
    }.sumOf { stone ->
        blink(stone, blinkTimes )
    } // fun stoneCount( List<String>, Int ) ...

    fun part1(input: List<String>): Long {
        return stoneCount( input, 25 )

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Long {
        return  stoneCount( input, 75 )

//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
