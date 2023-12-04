import AoCUtils.test

fun main() {
    fun part1(input: String, debug: Boolean = false): Long {
        val cards = input.lines().map { cardLine ->
            val split1 = cardLine.split(":")
            val split2 = split1[1].split("|")
            val winningNums = split2[0].split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }
            val yourNums = split2[1].split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }

            val winningNums2 = yourNums
                .filter { winningNums.contains(it) }

            var points = 0L
            winningNums2.forEach { _ ->
                points = if (points == 0L) 1
                else points * 2
            }
            points
        }
        return cards.sum()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        data class Card(val num: Int, var winnings: Int, var cards: Int = 1)

        val cards = input.lines().map { cardLine ->
            val split1 = cardLine.split(":")
            val split2 = split1[1].split("|")
            val card = split1[0].replace("Card ", "").trim().toInt()
            val winningNums = split2[0].split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }
            val yourNums = split2[1].split(" ").filter { it.isNotEmpty() }.map { it.trim().toLong() }

            val winningNums2 = yourNums
                .filter { winningNums.contains(it) }

            Pair(card, Card(card, winningNums2.size))
        }.toMap()

        cards.forEach { card ->
            val startCard = card.value.num
            val winnings = card.value.winnings
            val scratches = card.value.cards

            if (winnings != 0)
                repeat(scratches) {
                    for (x in startCard + 1 until startCard + 1 + winnings) {
                        cards.getOrDefault(x, Card(0, 0)).cards++
                    }
                }
        }

        return cards.map { it.value.cards }.sum().toLong()
    }

    val testInput = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n" +
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n" +
            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n" +
            "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n" +
            "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n" +
            "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"

    val input = AoCUtils.readText("year2023/day04.txt")

    part1(testInput) test Pair(13L, "test 1 part 1")
    part1(input) test Pair(23847L, "part 1")

    part2(testInput, debug = true) test Pair(30L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
