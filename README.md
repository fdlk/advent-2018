# advent-2018
[Advent of Code 2018](https://adventofcode.com/2018) solutions in Kotlin

This is me trying to get to know Kotlin, I did previous years in Scala.
I noticed that at the end, when revisiting my earlier solutions, I could clean them up quite a bit so at least I've learned something.

Initial setup copied from [Todd Ginsberg](https://github.com/tginsberg/) who has a great repo with accompanying
blog that you should definitely look at if you have not already.

Files you may find interesting:

## [Day 14](https://github.com/fdlk/advent-2018/blob/master/src/main/kotlin/nl/kelpin/fleur/advent2018/Day14.kt)
Kotlin sequence block is very handy, I retrofitted one into Day 1 as well.

## [Day 15](https://github.com/fdlk/advent-2018/blob/master/src/main/kotlin/nl/kelpin/fleur/advent2018/Day15.kt)
I managed to reduce the `move` method to a single breadth-first search with a little bit of extra administration.

## [Day 20](https://github.com/fdlk/advent-2018/blob/master/src/main/kotlin/nl/kelpin/fleur/advent2018/Day20.kt)
Used the sort-of-normalized form of the input to solve that one with minimal means.
No need to create a map of the dungeon.

## [Day 23](https://github.com/fdlk/advent-2018/blob/master/src/main/kotlin/nl/kelpin/fleur/advent2018/Day23.kt)
I tried so many things here, my initial idea was to solve the problem at a coarser resolution and then repeatedly 
zoom in to find the exact point. Worked alright for my own input but for other inputs this fails.
Then I tried some heuristic optimisation techniques which failed as well.
Finally I saw someone who kept splitting a box into eight parts, tracking the one that overlaps with the most 
spheres. So simple that I should've thought of it, and efficient enough to work for all inputs that I saw.

## [Extensions.kt](https://github.com/fdlk/advent-2018/blob/master/src/main/kotlin/nl/kelpin/fleur/advent2018/Extensions.kt)
I am used to the extremely consistent and well-designed Scala standard library and hopping over to Kotlin took
a little getting used to.
But the ability to create a little advent-of-code library made up for some of the hardship.
The first time you notice you can re-use methods you put on certain classes is good fun.
