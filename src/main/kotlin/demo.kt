data class Book(val isbn: String, val title: String, val pageCount: Int)

fun main() {
    val books = listOf(
        Book("1617293296", "Kotlin in Action", 360),
        Book("1680506358", "Programming Kotlin", 450),
        Book("1801815720", "Kotlin Design Patterns and Best Practices", 356),
        Book("1492082279", "Java to Kotlin", 300),
    )

    val total = books.sumOf { it.pageCount }
    //Alt+Enter on 'for' -> Change to sumOf {}
    println("Total page count in ${books.size} books is $total")

    // Rename 'book' variable to 'longestBook' with Shift+F6 shortcut
    val book = books.maxBy { it.pageCount }

    // Alt + Enter on 'isbn'-> Rename to _
    val (isbn, title, pages) = book

    // Alt+Enter on the string -> Convert concatenation to template
    println("Longest book: " + title + "(" + pages + "p.)")

    // Alt+Enter on return statement -> convert to expression body
    // Alt+Enter on book parameter -> convert parameter to receiver
    fun format(book: Book): String {
        return "${book.isbn}: ${book.title}, ${book.pageCount}"
    }
    println(format(book))

    // Alt+Enter on books parameter -> convert parameter to receiver
    // Cmd+Option+P on { it.title } (extract functional parameter)
    // Cmd+Option+N on sortBy to inline the function
    fun sortBy(books: List<Book>) = books.sortedBy { it.title }

    // Alt+Enter on { println(it) } -> move lambda out of parentheses
    sortBy(books).forEach({ println(it) })

    // Alt+Enter on 'abook' -> Specify type explicitly
    val abook: Book? = books.firstOrNull { it.title.startsWith("A") }

    // uncomment, F2, Alt+Enter -> Surround with null check
//    println(abook.title)

}