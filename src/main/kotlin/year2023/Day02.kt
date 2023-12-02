import AoCUtils.test

fun main() {
    data class Game(val game: Int, var red: Int = 0, var blue: Int = 0, var green: Int = 0)

    fun part1(input: String, debug: Boolean = false): Long {
        val games = input.lines().map { line ->
            val split1 = line.split(":")
            val gameNum = split1[0].replace("Game ", "")
            val rounds = split1[1].split(";")

            val roundMap = rounds.map { round ->
                round.split(",").map { t ->
                    val r = t.trim().split(" ")
                    r[1] to r[0].toInt()
                }
            }

            val game = Game(gameNum.toInt())
            roundMap.forEach { round ->
                round.forEach { pair ->
                    if (pair.first == "red" && pair.second > game.red) game.red = pair.second
                    if (pair.first == "blue" && pair.second > game.blue) game.blue = pair.second
                    if (pair.first == "green" && pair.second > game.green) game.green = pair.second
                }
            }
            game
        }


        val sum = games.filter { game -> game.red <= 12 && game.blue <= 14 && game.green <= 13 }
            .map { it.game }
            .sum().toLong();

        return sum
    }

    data class Round(var red: Int = 0, var blue: Int = 0, var green: Int = 0)
    data class Game2(val game: Int, val rounds: MutableList<Round>)

    fun part2(input: String, debug: Boolean = false): Long {
        val games = input.lines().map { line ->
            val split1 = line.split(":")
            val gameNum = split1[0].replace("Game ", "")
            val rounds = split1[1].split(";")

            val roundMap = rounds.map { round ->
                round.split(",").map { t ->
                    val r = t.trim().split(" ")
                    r[1] to r[0].toInt()
                }
            }

            val game = Game2(gameNum.toInt(), mutableListOf())

            val list = roundMap.map { round ->
                val r = Round()
                round.map { pair ->
                    if (pair.first == "red") r.red = pair.second
                    if (pair.first == "blue") r.blue = pair.second
                    if (pair.first == "green") r.green = pair.second
                }
                r
            }

            game.rounds.addAll(list)

            game
        }

        val sum = games.map { game ->
            val result = Round()
            game.rounds.forEach { round ->
                if (round.red > result.red) result.red = round.red
                if (round.green > result.green) result.green = round.green
                if (round.blue > result.blue) result.blue = round.blue
            }
            result
        }.map { it.blue.toLong() * it.red.toLong() * it.green.toLong() }
            .sum();

        return sum

    }

    val testInput = "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green\n" +
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue\n" +
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red\n" +
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red\n" +
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"

    val input = AoCUtils.readText("year2023/day02.txt")

    part1(testInput) test Pair(8L, "test 1 part 1")
    part1(input) test Pair(2162L, "part 1")

    part2(testInput) test Pair(2286L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
