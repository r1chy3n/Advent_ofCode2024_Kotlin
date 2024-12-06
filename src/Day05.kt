import java.util.Collections

fun main() {
    fun isCorrect(
        pageList: List<String>, orderList: List<Pair<String, String>>
    ): Boolean {
        return ( 0 .. pageList.lastIndex - 1 ).flatMapIndexed { i, pageIndex ->
            ( i + 1 .. pageList.lastIndex ).map { afterIndex ->
                Pair( pageList[ afterIndex ], pageList[ pageIndex ])
            } // Range.map
        }.none { pair ->
            pair in orderList
        } // Range.flatMapIndexed.none
    } // fun isCorrect( List<String>, List<Pair<String, String>>)

    fun middlePage( pageList: List<String>): Int {
        return pageList[ pageList.size / 2 ].toInt()
    } // fun middlePage( List<String>)

    fun part1(input: List<String>): Int {
        val emptyIndex = input.indexOf( "" )

        return ( 0 .. emptyIndex - 1 ).map { i ->
            input[ i ].split( "|" ).let { pages ->
                Pair( pages[ 0 ], pages[ 1 ])
            } // let
        }.let { orderList ->
            ( emptyIndex + 1 .. input.lastIndex ).sumOf { i ->
                input[ i ].split( "," ).let { pageList ->
                    if ( isCorrect( pageList, orderList ))
                        middlePage( pageList ) else 0
                } // List<String>.split.let
            } // Range.sumOf
        } // return - Range.map.let
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val emptyIndex = input.indexOf( "" )
        val orderList = ( 0 .. emptyIndex - 1 ).map { i ->
            input[ i ].split( "|" ).let { pages ->
                Pair( pages[ 0 ], pages[ 1 ])
            } // let
        } // map

        var pageList: MutableList<String>
        var reversedIndex: Int = -2

        return ( emptyIndex + 1 .. input.lastIndex ).sumOf { i ->
            pageList = input[ i ].split( "," ).toMutableList()

            if ( isCorrect( pageList, orderList )) 0 else run {
                ( 0 .. pageList.lastIndex - 1 ).forEach { anchor ->
                    do {
                        reversedIndex = ( anchor + 1 .. pageList.lastIndex ).map { i ->
                            Pair( pageList[ i ], pageList[ anchor ])
                        }.indexOfFirst { pair ->
                            pair in orderList
                        } // reversedIndex = Range.map.indexOfFirst

                        if ( reversedIndex > -1 ) {
                            Collections.swap( pageList, anchor, anchor + reversedIndex + 1 )
                        } // if
                    } while ( reversedIndex != -1 )
                } // Range.forEach

                middlePage( pageList )
            } // if else
        } // return Range.sumOf
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
