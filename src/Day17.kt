import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): String {
        var registerA = input.first().split( ": " )[ 1 ].toInt()
        var registerB = input[ 1 ].split( ": " )[ 1 ].toInt()
        var registerC = input[ 2 ].split( ": " )[ 1 ].toInt()
        val program = input[ 4 ].split( ": " )[ 1 ].split( ","
        ).let { opxList ->
            ( 0 .. opxList.lastIndex step 2 ).map { i ->
                opxList[ i ].toInt() to opxList[ i + 1 ].toInt()
            } // IntRange.map
        } // List<String>.let

        fun combo( operand: Int ): Int {
            return if ( operand < 4 ) operand
            else ( if ( operand == 4 ) registerA
            else ( if ( operand == 5 ) registerB
            else ( if ( operand == 6 ) registerC else -1 )))
        } // fun combo( Int )

        var output = mutableListOf<Int>()
        var pointer = 0

        do {
            if ( program[ pointer ].first == 3 && registerA != 0 ) {
                pointer = program[ pointer ].second
            } else {
                if ( program[ pointer ].first == 0 ) {
                    registerA /= 2F.pow( combo( program[ pointer ].second )
                    ).toInt()
                } else if ( program[ pointer ].first == 1 ) {
                    registerB = registerB xor program[ pointer ].second
                } else if ( program[ pointer ].first == 2 ) {
                    registerB = combo( program[ pointer ].second ) % 8
                } else if ( program[ pointer ].first == 4 ) {
                    registerB = registerB xor registerC
                } else if ( program[ pointer ].first == 5 ) {
                    output.add( combo( program[ pointer ].second ) % 8 )
                } else if ( program[ pointer ].first == 6 ) {
                    registerB = registerA / 2F.pow(
                        combo( program[ pointer ].second )).toInt()
                } else if ( program[ pointer ].first == 7 ) {
                    registerC = registerA / 2F.pow(
                        combo( program[ pointer ].second )).toInt()
                } // if - else_if x 6

                pointer++
            } // if - else
        } while ( pointer < program.size )

        return output.joinToString( "," )
//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Long {
        val outputList = input[ 4 ].split( ": " )[ 1 ].split( "," ).map {
            it.toLong()
        } // List<String>.map

        class Snapshot( var beginValue: Long, var outputIndex: Int ) {
            var registerA = 0L
            var registerB = 0L
            var registerC = 0L

            fun possibleInput(): List<Snapshot> {
                val possibleList = mutableListOf<Snapshot>()

                do {
                    possibleList.clear()

                    ( 0 .. 7 ).forEach { modulo ->
                        registerA = beginValue + modulo
                        registerB = registerA % 8
                        registerB = registerB xor 1
                        registerC = registerA /
                                2F.pow( registerB.toInt()).toLong()
                        registerB = registerB xor 5
                        registerB = registerB xor registerC

                        if ( registerB % 8 == outputList[ outputIndex ]) {
                            possibleList.add(
                                Snapshot( registerA * 8, outputIndex - 1 ))
                        } // if
                    } // IntRange.forEach

                    if ( possibleList.size == 1 ) {
                        beginValue = possibleList.first().beginValue

                        outputIndex--
                    } // if
                } while ( possibleList.size == 1 && outputIndex >= 0 )

                return possibleList
            } // fun possibleInput()
        } // class Snapshot( Long, Int )

        val runList = mutableListOf( Snapshot( 0, 15 ))
        val programCopied = mutableListOf<Long>()

        var snapshot: Snapshot
        var possibles: List<Snapshot>

        while ( runList.isNotEmpty()) {
            snapshot = runList.removeAt( 0 )
            possibles = snapshot.possibleInput()

            if ( possibles.isNotEmpty()) {
                possibles.forEach { s ->
                    if ( s.outputIndex == -1 ) {
                        programCopied.add( s.beginValue / 8 )
                    } else {
                        runList.add( s )
                    } // if - else
                } // List<Snapshot>.forEach
            } // if
        } // while

        return programCopied.min()
//        return input.size.toLong()
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
