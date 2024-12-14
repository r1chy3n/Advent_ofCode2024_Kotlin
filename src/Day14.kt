fun main() {
    val pX = 0
    val pY = 1
    val vX = 2
    val vY = 3
    val tX = 101
    val tY = 103
    val mX = tX / 2
    val mY = tY / 2

    fun part1(input: List<String>): Int {
        var factor = 1

        input.map { line ->
            line.substring( 2 ).split( " v=|,".toRegex()).map {
                it.toInt()
            }.let { eList ->
                Pair(
                    (( eList[ pX ] + eList[ vX ] * 100 ) % tX + tX ) % tX,
                    (( eList[ pY ] + eList[ vY ] * 100 ) % tY + tY ) % tY
                )
            }
        }.filter { p ->
            p.first != mX && p.second != mY
        }.groupingBy { p ->
            if ( p.first < mX && p.second < mY ) 0 else (
                if ( p.first > mX && p.second < mY ) 1 else (
                    if ( p.first < mX && p.second > mY ) 2 else 3
                )
            )
        }.eachCount().forEach {(_, count) ->
            factor *= count
        }

        return factor

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val pvList = input.map { line ->
            line.substring(2).split(" v=|,".toRegex()).map {
                it.toInt()
            }
        }

        var seconds = 0

        var afterList: List<Pair<Int, Int>>

        do {
            ++seconds

            afterList = pvList.map { eList ->
                Pair(
                    (((eList[pX] + eList[vX] * seconds) % tX + tX) % tX).toInt(),
                    (((eList[pY] + eList[vY] * seconds) % tY + tY) % tY).toInt()
                )
            }
        } while ( !afterList.any {
                afterList.contains( Pair( it.first + 1, it.second ))
                        && afterList.contains( Pair( it.first + 2, it.second ))
                        && afterList.contains( Pair( it.first + 3, it.second ))
                        && afterList.contains( Pair( it.first + 4, it.second ))
                        && afterList.contains( Pair( it.first + 5, it.second ))
                        && afterList.contains( Pair( it.first + 6, it.second ))
            } )

        ( 0 ..< tY ).forEach { y ->
            ( 0 ..< tX ).forEach { x ->
                afterList.count {( rX, rY ) ->
                    x == rX && y == rY
                }.let {
                    print( if ( it == 0 ) "." else it.toString())
                }
            }

            println()
        }

        return seconds
//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}
