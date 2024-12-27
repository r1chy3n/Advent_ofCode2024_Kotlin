fun main() {
    fun part1(input: List<String>): Int {

        // [tc, tb, ta, td]
        val tStartSet = mutableSetOf<String>()

        // [[kh, tc], [qp, kh], [de, cg], [ka, co], [yn, aq], [qp, ub], ...]
        val connectList = input.map { line ->
            line.split( "-" ).also { names ->
                names.forEach { name ->
                    if ( name.startsWith( "t" )) {
                        tStartSet.add( name )
                    } // if
                } // List<String>.forEach
            } // List<String>.also
        } // List<String>.map

        val interConnects = mutableSetOf<List<String>>()

        tStartSet.forEach { computer1 ->

            // [[kh, tc], [wh, tc], [tc, td], [co, tc]]
            val tStartConnects = connectList.filter { connection ->
                connection.contains( computer1 )
            } // List<List<String>>.filter

            ( 0 ..< tStartConnects.lastIndex ).forEach { i ->
                ( i + 1 .. tStartConnects.lastIndex ).forEach { j ->
                    val computer2 = tStartConnects[ i ].first { name ->
                        name != computer1
                    } // List<String>.first

                    val computer3 = tStartConnects[ j ].first { name ->
                        name != computer1
                    } // List<String>.first

                    if (
                        connectList.count { connection ->
                            connection.contains( computer2 )
                                    && connection.contains( computer3 )
                        } > 0
                    ) {

                        interConnects.add(
                            listOf( computer1, computer2, computer3 ).sorted())
                    } // if
                } // IntRange.forEach
            } // IntRange.forEach
        } // MutableSet<String>.forEach

        return interConnects.size

//        return input.size
    } // fun part1( List<String>)

//    fun part2(input: List<String>): Int {

    fun part2(input: List<String>): String {

        // [kh, tc, qp, de, cg, ka, co, yn, aq, ub, tb, vc, wh, ta, td, wq]
        val nameSet = mutableSetOf<String>()

        // [[kh, tc], [qp, kh], [de, cg], [ka, co], [yn, aq], [qp, ub], ...]
        val connectLists = input.map { line ->
            line.split( "-" ).also { names ->
                names.forEach { name ->
                    nameSet.add( name )
                } // List<String>.forEach
            } // List<String>.also
        } // List<String>.map

        val allConnectedSet = mutableSetOf<List<String>>()

        nameSet.forEach { name1 ->
            // name1: kh
            // name2List = [tc, qp, ub, ta]
            val name2List = connectLists.filter { connectList ->
                connectList.contains( name1 )
            }.map { connectList ->
                connectList.first { connectedName ->
                    connectedName != name1
                } // List<String>.first
            } // List<List<String>>.filter

            var connectedNameList: MutableList<String>

            ( 0 ..< name2List.lastIndex ).forEach { i ->
                connectedNameList = mutableListOf( name2List[ i ])

                ( i + 1 .. name2List.lastIndex ).forEach { j ->
                    if (
                        connectedNameList.all { connectedName ->
                            connectLists.any { connectList ->
                                connectList.contains( name2List[ j ])
                                        && connectList.contains( connectedName )
                            } // List<List<String>>.any
                        } // List<String>.all
                    ) {
                        connectedNameList.add( name2List[ j ])
                    } // if
                } // IntRange.forEach

                connectedNameList.add( name1 )

                allConnectedSet.add( connectedNameList.sorted())
            } // IntRange.forEach
        } // MutableSet<String>.forEach

        return allConnectedSet.maxBy {
            it.size
        }.joinToString( "," )

    //        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}
