fun main() {
    val wall = 0
    val space = 1
    val box = 2
    val robot = 3
    val boxLeft = 4
    val boxRight = 5

    fun part1(input: List<String>): Int {
        val emptyIndex = input.indexOf( "" )
        var robotX = 0
        var robotY = 0
        val mapStates = input.subList( 0, emptyIndex ).mapIndexed { y, line ->
            line.mapIndexed { x, ch ->
                // 0: wall, 1: space, 2: box, 3: robot
                when ( ch ) {
                    '#' -> wall
                    '.' -> space
                    'O' -> box
                    else -> {
                        robotX = x
                        robotY = y
                        robot
                    } // else
                } // when
            }.toMutableList()
        } // List<String>.mapIndexed

        input.subList(
            emptyIndex + 1, input.size
        ).joinToString().forEachIndexed { i, ch ->
            when ( ch ) {
                '<' -> mapStates[robotY].subList(
                    0, robotX
                ).indexOfLast { state ->
                    // 0: wall, 1: space, 2: box, 3: robot
                    state == space || state == wall
                }.takeIf { lastIndex ->
                    mapStates[robotY][lastIndex] == space
                }?.let { spaceIndex ->
                    (spaceIndex..<robotX - 1).forEach { x ->
                        mapStates[robotY][x] = box
                    } // IntRange.forEach

                    mapStates[robotY][robotX] = space
                    mapStates[robotY][--robotX] = robot
                } // takeIf?.let
                // ------------------------------------------------------------
                '>' -> mapStates[ robotY ].subList(
                    robotX, mapStates[ robotY ].size
                ).indexOfFirst { state ->
                    // 0: wall, 1: space, 2: box, 3: robot
                    state == space || state == wall
                }.takeIf { firstIndex ->
                    mapStates[ robotY ][ robotX + firstIndex ] == space
                }?.let { spaceIndex ->
                    ( 2 .. spaceIndex ).forEach { x ->
                        mapStates[ robotY ][ robotX + x ] = box
                    }

                    mapStates[ robotY ][ robotX ] = space
                    mapStates[ robotY ][ ++robotX ] = robot
                } // takeIf?.let
                // ------------------------------------------------------------
                '^' -> mapStates.map { horizontal ->
                    horizontal[ robotX ]
                }.subList( 0, robotY ).indexOfLast { state ->
                    // 0: wall, 1: space, 2: box, 3: robot
                    state == space || state == wall
                }.takeIf { lastIndex ->
                    mapStates[ lastIndex ][ robotX ] == space
                }?.let { spaceIndex ->
                    ( spaceIndex ..< robotY - 1 ).forEach { y ->
                        mapStates[ y ][ robotX ] = box
                    }

                    mapStates[ robotY ][ robotX ] = space
                    mapStates[ --robotY ][ robotX ] = robot
                } // takeIf?.let
                // ------------------------------------------------------------
                'v' -> mapStates.map { horizontal ->
                    horizontal[ robotX ]
                }.subList( robotY, mapStates.size ).indexOfFirst { state ->
                    // 0: wall, 1: space, 2: box, 3: robot
                    state == space || state == wall
                }.takeIf { firstIndex ->
                    mapStates[ robotY + firstIndex ][ robotX ] == space
                }?.let { spaceIndex ->
                    ( 2 .. spaceIndex ).forEach { y ->
                        mapStates[ robotY + y ][ robotX ] = box
                    }

                    mapStates[ robotY ][ robotX ] = space
                    mapStates[ ++robotY ][ robotX ] = robot
                } // takIf?.let
            } // when
        } // String.forEach

        var sum = 0

        mapStates.forEachIndexed { y, horizontal ->
            horizontal.forEachIndexed { x, state ->
                sum += if ( mapStates[ y ][ x ] == box ) y * 100 + x else 0
            } // MutableList<Int>.forEachIndexed
        } // List<MutableList<Int>>.forEachIndexed

        return sum
//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val emptyIndex = input.indexOf( "" )
        var robotX = 0
        var robotY = 0
        val mapStates = input.subList( 0, emptyIndex ).mapIndexed { y, line ->
            line.flatMapIndexed { x, ch ->
                // 0: wall, 1: space, 2: box, 3: robot
                when ( ch ) {
                    '#' -> listOf( wall, wall )
                    '.' -> listOf( space, space )
                    'O' -> listOf( boxLeft, boxRight )
                    else -> {
                        robotX = x * 2
                        robotY = y
                        listOf( robot, space )
                    } // else
                } // when
            }.toMutableList()
        } // List<String>.mapIndexed

        input.subList(
            emptyIndex + 1, input.size
        ).joinToString().forEachIndexed { i, ch ->
            when ( ch ) {
                '<' -> {
                    mapStates[robotY].subList(
                        0, robotX
                    ).indexOfLast { state ->
                        // 0: wall, 1: space, 2: box, 3: robot
                        state == space || state == wall
                    }.takeIf { lastIndex ->
                        mapStates[robotY][lastIndex] == space
                    }?.let { spaceIndex ->
                        (spaceIndex..<robotX - 1 step 2).forEach { x ->
                            mapStates[robotY][x] = boxLeft
                            mapStates[robotY][x + 1] = boxRight
                        } // IntRange.forEach

                        mapStates[robotY][robotX] = space
                        mapStates[robotY][--robotX] = robot
                    } // takeIf?.let
                }
                // ------------------------------------------------------------
                '>' -> mapStates[ robotY ].subList(
                    robotX, mapStates[ robotY ].size
                ).indexOfFirst { state ->
                    // 0: wall, 1: space, 2: box, 3: robot
                    state == space || state == wall
                }.takeIf { firstIndex ->
                    mapStates[ robotY ][ robotX + firstIndex ] == space
                }?.let { spaceIndex ->
                    ( 2 .. spaceIndex step 2 ).forEach { x ->
                        mapStates[ robotY ][ robotX + x ] = boxLeft
                        mapStates[ robotY ][ robotX + x + 1 ] = boxRight
                    }

                    mapStates[ robotY ][ robotX ] = space
                    mapStates[ robotY ][ ++robotX ] = robot
                } // takeIf?.let
                // ------------------------------------------------------------
                '^' -> {
                    fun canMoveUp( y: Int, leftX: Int ): Boolean {
                        return if ( mapStates[ y - 1 ][ leftX ] == space
                            && mapStates[ y - 1 ][ leftX + 1 ] == space ) true
                        else ( if ( mapStates[ y - 1 ][ leftX ] == boxLeft )
                            canMoveUp( y - 1, leftX )
                        else ( if ( mapStates[ y - 1 ][ leftX ] == space
                            && mapStates[ y - 1 ][ leftX + 1 ] == boxLeft )
                            canMoveUp( y - 1, leftX + 1 )
                        else ( if ( mapStates[ y - 1 ][ leftX ] == boxRight
                            && mapStates[ y - 1 ][ leftX + 1 ] == space )
                            canMoveUp( y - 1, leftX - 1 )
                        else ( if ( mapStates[ y - 1 ][ leftX ] == boxRight
                            && mapStates[ y - 1 ][ leftX + 1 ] == boxLeft )
                            canMoveUp( y - 1, leftX - 1)
                                    && canMoveUp( y - 1, leftX + 1 )
                        else false ))))
                    } // fun canMoveUp( y: Int, leftX: Int )

                    fun moveUp( y: Int, leftX: Int ) {
                        if ( mapStates[ y - 1 ][ leftX ] == boxLeft ) {
                            moveUp( y - 1, leftX )
                        } else {
                            if ( mapStates[ y - 1 ][ leftX + 1 ] == boxLeft ) {
                                moveUp( y - 1, leftX + 1 )
                            }

                            if ( mapStates[ y - 1 ][ leftX ] == boxRight ) {
                                moveUp( y - 1, leftX - 1 )
                            }
                        }

                        mapStates[ y - 1 ][ leftX ] = boxLeft
                        mapStates[ y - 1 ][ leftX + 1 ] = boxRight
                        mapStates[ y ][ leftX ] = space
                        mapStates[ y ][ leftX + 1 ] = space
                    }

                    if ( mapStates[ robotY - 1 ][ robotX ] == space ) {
                        mapStates[ robotY ][ robotX ] = space
                        mapStates[ --robotY ][ robotX ] = robot
                    } else if ( mapStates[ robotY - 1 ][ robotX ] == boxLeft ) {
                        if ( canMoveUp( robotY - 1, robotX )) {
                            moveUp( robotY - 1, robotX )

                            mapStates[ robotY ][ robotX ] = space
                            mapStates[ --robotY ][ robotX ] = robot
                        }
                    } else if ( mapStates[ robotY - 1 ][ robotX ] == boxRight ) {
                        if ( canMoveUp( robotY - 1, robotX - 1 )) {
                            moveUp( robotY - 1, robotX - 1 )

                            mapStates[ robotY ][ robotX ] = space
                            mapStates[ --robotY ][ robotX ] = robot
                        }
                    }
                }
                // ------------------------------------------------------------
                'v' -> {
                    fun canMoveDown( y: Int, leftX: Int ): Boolean {
                        return if ( mapStates[ y + 1 ][ leftX ] == space
                            && mapStates[ y + 1 ][ leftX + 1 ] == space ) true
                        else ( if ( mapStates[ y + 1 ][ leftX ] == boxLeft )
                            canMoveDown( y + 1, leftX )
                        else ( if ( mapStates[ y + 1 ][ leftX ] == space
                            && mapStates[ y + 1 ][ leftX + 1 ] == boxLeft )
                            canMoveDown( y + 1, leftX + 1 )
                        else ( if ( mapStates[ y + 1 ][ leftX ] == boxRight
                            && mapStates[ y + 1 ][ leftX + 1 ] == space )
                            canMoveDown( y + 1, leftX - 1 )
                        else ( if ( mapStates[ y + 1 ][ leftX ] == boxRight
                            && mapStates[ y + 1 ][ leftX + 1 ] == boxLeft )
                            canMoveDown( y + 1, leftX - 1)
                                    && canMoveDown( y + 1, leftX + 1 )
                        else false ))))
                    } // fun canMoveUp( y: Int, leftX: Int )

                    fun moveDown( y: Int, leftX: Int ) {
                        if ( mapStates[ y + 1 ][ leftX ] == boxLeft ) {
                            moveDown(y + 1, leftX)
                        } else {
                            if ( mapStates[ y + 1 ][ leftX + 1 ] == boxLeft ) {
                                moveDown( y + 1, leftX + 1 )
                            }

                            if ( mapStates[ y + 1 ][ leftX ] == boxRight ) {
                                moveDown( y + 1, leftX - 1 )
                            }
                        }

                        mapStates[ y + 1 ][ leftX ] = boxLeft
                        mapStates[ y + 1 ][ leftX + 1 ] = boxRight
                        mapStates[ y ][ leftX ] = space
                        mapStates[ y ][ leftX + 1 ] = space
                    }

                    if ( mapStates[ robotY + 1 ][ robotX ] == space ) {
                        mapStates[ robotY ][ robotX ] = space
                        mapStates[ ++robotY ][ robotX ] = robot
                    } else if ( mapStates[ robotY + 1 ][ robotX ] == boxLeft ) {
                        if ( canMoveDown( robotY + 1, robotX )) {
                            moveDown( robotY + 1, robotX )

                            mapStates[ robotY ][ robotX ] = space
                            mapStates[ ++robotY ][ robotX ] = robot
                        }
                    } else if ( mapStates[ robotY + 1][ robotX ] == boxRight ) {
                        if ( canMoveDown( robotY + 1, robotX - 1 )) {
                            moveDown( robotY + 1, robotX - 1 )

                            mapStates[ robotY ][ robotX ] = space
                            mapStates[ ++robotY ][ robotX ] = robot
                        }
                    }
                }
            } // when
        } // String.forEach

        var sum = 0

        mapStates.forEachIndexed { y, horizontal ->
            horizontal.forEachIndexed { x, state ->
                sum += if ( mapStates[ y ][ x ] == boxLeft ) y * 100 + x else 0
            } // MutableList<Int>.forEachIndexed
        } // List<MutableList<Int>>.forEachIndexed

        return sum
//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}
