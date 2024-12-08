fun main() {
    fun part1(input: List<String>): Int {
        val antFreq = mutableSetOf<Char>()
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        val antPos = input.flatMapIndexed { yIndex, line ->
            "[^.]+".toRegex().findAll( line ).flatMap { result ->
                result.range.map { i ->
                    antFreq.add( line[ i ])

                    Triple( line[ i ], yIndex, i )
                } // Range.map
            } // Sequence<MatchResult>.flatMap
        } // antPos = List<String>.flatMapIndexed

        var freqList: List<Triple<Char, Int, Int>>
        var antiY: Int
        var antiX: Int

        antFreq.forEach { antenna ->
            freqList = antPos.filter { triple ->
                triple.first == antenna
            } // List<Triple<Char, Int, Int>>.filter

            freqList.forEach { freqPos1 ->
                freqList.forEach { freqPos2 ->
                    if ( freqPos2 != freqPos1 ) {
                        antiY = 2 * freqPos2.second - freqPos1.second
                        antiX = 2 * freqPos2.third - freqPos1.third

                        if ( antiY in input.indices
                            && antiX in input.first().indices ) {
                            antiNodes.add( Pair( antiY, antiX ))
                        } // if
                    } // if
                }  // List<Triple<Char, Int, Int>>.forEach
            } // List<Triple<Char, Int, Int>>.forEach
        } // MutableSet<Char>.forEach

        return antiNodes.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val antFreq = mutableSetOf<Char>()
        val antiNodes = mutableSetOf<Pair<Int, Int>>()
        val antPos = input.flatMapIndexed { yIndex, line ->
            "[^.]+".toRegex().findAll( line ).flatMap { result ->
                result.range.map { i ->
                    antFreq.add( line[ i ])

                    Triple( line[ i ], yIndex, i )
                } // Range.map
            } // Sequence<MatchResult>.flatMap
        } // antPos = List<String>.flatMapIndexed

        var freqList: List<Triple<Char, Int, Int>>
        var antiY: Int
        var antiX: Int
        var stepY: Int
        var stepX: Int
        var isInside: Boolean

        antFreq.forEach { antenna ->
            freqList = antPos.filter { triple ->
                triple.first == antenna
            } // List<Triple<Char, Int, Int>>.filter

            freqList.forEach { freqPos1 ->
                antiNodes.add( Pair( freqPos1.second, freqPos1.third ))

                freqList.forEach { freqPos2 ->
                    if ( freqPos2 != freqPos1 ) {
                        antiY = freqPos2.second
                        antiX = freqPos2.third
                        stepY = freqPos2.second - freqPos1.second
                        stepX = freqPos2.third - freqPos1.third

                        do {
                            antiY += stepY
                            antiX += stepX
                            isInside = antiY in input.indices
                                    && antiX in input.first().indices

                            if ( isInside ) {
                                antiNodes.add( Pair( antiY, antiX ))
                            } // if
                        } while ( isInside )
                    } // if
                }  // List<Triple<Char, Int, Int>>.forEach
            } // List<Triple<Char, Int, Int>>.forEach
        } // MutableSet<Char>.forEach

        return antiNodes.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
