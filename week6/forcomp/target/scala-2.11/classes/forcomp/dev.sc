type Occurrences = List[(Char, Int)]
type Word = String
type Sentence = List[Word]

val abba = List(('a', 2), ('b', 2))

def loop2(cs: List[Char]): List[List[Char]] = {
  println(cs)
  cs match {
  case Nil => List(Nil)
  case h :: t =>
    for {
      split <- cs.indices.toList
      w <- cs.take(split)
      rest <- loop2(cs.drop(split))
    } yield w :: rest
  }
}

val cs = loop2("Linuxrulez".toList)

def combinations(occurrences: Occurrences): List[Occurrences] = {
  occurrences match {
    case Nil => List(List())
    case ((c, n) :: t) =>
      val rec = combinations(t)
      (for ( in <- (1 to n).toList; el <- rec) yield (c, in) +: el) ::: rec
  }
}

val abbacomb = combinations(abba)

(for ((c, n) <- abba) yield
  for (in <- 1 to n) yield List(c -> in)) flatten

for ((c, n) <- abba; in <- 1 to n)
  yield List(c -> in)

for {
  (c, n) <- abba
  in <- 1 to n
} yield List(c -> in) :: List(c -> in)