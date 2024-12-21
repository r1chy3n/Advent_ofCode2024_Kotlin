fun main() {
    val spaceSize = 71
    val byteFallen = 1024
    val unvisited = -1
    val corrupted = -2
    val up = -1 to 0
    val down = 1 to 0
    val left = 0 to -1
    val right = 0 to 1
    val dirList = listOf( up, down, left, right )
    val memorySpace = List( spaceSize ) {
        MutableList( spaceSize ) { unvisited }
    } // List

    fun part1(input: List<String>): Int {
        ( 0 ..< byteFallen ).forEach { i ->
            input[ i ].split( "," ).let { pos ->
                memorySpace[ pos[ 1 ].toInt()][ pos[ 0 ].toInt()] = corrupted
            } // List<String>.let
        } // IntRange.forEach

        fun Pair<Int, Int>.move( dir: Pair<Int, Int>): Pair<Int, Int>? {
            var result: Pair<Int, Int>? = null

            val nextY = first + dir.first
            val nextX = second + dir.second

            if ( nextY in memorySpace.indices
                && nextX in memorySpace.first().indices
                && ( memorySpace[ nextY ][ nextX ] != corrupted )) {
                result = nextY to nextX
            } // if

            return result
        } // fun Pair<Int, Int>.move( Pair<Int, Int>)

        class PosInfo( var y: Int, var x: Int, var step: Int ) {
            init {
                memorySpace[ y ][ x ] = step
            } // init

            fun goNext(): List<PosInfo> {
                val nextSteps = mutableListOf<PosInfo>()
                val nextStep = step + 1

                dirList.forEach { dir ->
                    ( y to x ).move( dir )?.let { nextPos ->
                        if ( memorySpace[ nextPos.first ][ nextPos.second ]
                            == unvisited || memorySpace[ nextPos.first ][
                                nextPos.second ] > nextStep ) {
                            nextSteps.add( PosInfo(
                                nextPos.first, nextPos.second, nextStep ))
                        } // if
                    } // Pair<Int, Int>?.let
                } // List<Pair<Int, Int>>.forEach

                return nextSteps
            } // fun goNext()
        } // class PosInfo( Int, Int, Int )

        val movingList = mutableListOf( PosInfo( 0, 0, 0 ))

        while ( movingList.isNotEmpty()) {
            movingList.addAll( movingList.removeAt( 0 ).goNext())
        } // while

        return memorySpace[ spaceSize - 1 ][ spaceSize - 1]

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): String {
        ( 0 ..< byteFallen ).forEach { i ->
            input[ i ].split( "," ).let { pos ->
                memorySpace[ pos[ 1 ].toInt()][ pos[ 0 ].toInt()] = corrupted
            } // List<String>.let
        } // IntRange.forEach

        fun Pair<Int, Int>.move( dir: Pair<Int, Int>): Pair<Int, Int>? {
            var result: Pair<Int, Int>? = null

            val nextY = first + dir.first
            val nextX = second + dir.second

            if ( nextY in memorySpace.indices
                && nextX in memorySpace.first().indices
                && ( memorySpace[ nextY ][ nextX ] != corrupted )) {
                result = nextY to nextX
            } // if

            return result
        } // fun Pair<Int, Int>.move( Pair<Int, Int>)

        class PosInfo( var y: Int, var x: Int, var step: Int ) {
            init {
                memorySpace[ y ][ x ] = step
            } // init

            fun goNext(): List<PosInfo> {
                val nextSteps = mutableListOf<PosInfo>()
                val nextStep = step + 1

                dirList.forEach { dir ->
                    ( y to x ).move( dir )?.let { nextPos ->
                        if ( memorySpace[ nextPos.first ][ nextPos.second ]
                            == unvisited || memorySpace[ nextPos.first ][
                                nextPos.second ] > nextStep ) {
                            nextSteps.add( PosInfo(
                                nextPos.first, nextPos.second, nextStep ))
                        } // if
                    } // Pair<Int, Int>?.let
                } // List<Pair<Int, Int>>.forEach

                return nextSteps
            } // fun goNext()
        } // class PosInfo( Int, Int, Int )

        var byteIndex = byteFallen

        var movingList: MutableList<PosInfo>
        var bytePos: List<String>

        do {
            ( 0 ..< spaceSize ).forEach { y ->
                ( 0 ..< spaceSize ).forEach { x ->
                    if ( memorySpace[ y ][ x ] > unvisited ) {
                        memorySpace[ y ][ x ] = unvisited
                    } // if
                } // IntRange.forEach
            } // IntRange.forEach

            bytePos = input[ byteIndex++ ].split( "," )
            memorySpace[ bytePos[ 1 ].toInt()][
                bytePos[ 0 ].toInt()] = corrupted

            movingList = mutableListOf( PosInfo( 0, 0, 0 ))

            while ( movingList.isNotEmpty()) {
                movingList.addAll( movingList.removeAt( 0 ).goNext())
            } // while
        } while ( memorySpace[ memorySpace.lastIndex ][
                memorySpace.first().lastIndex ] != unvisited )

        return bytePos.joinToString( "," )
//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}
