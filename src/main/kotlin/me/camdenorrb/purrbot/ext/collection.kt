package me.camdenorrb.purrbot.ext


/**
 * Attempts to find the value that isn't null
 *
 * @param E The element type for the collection
 * @param R The return type for the value found
 * @param block Lambda for retrieving the value
 *
 * @return The value found
 */
fun <E, R> Collection<E>.findValue(block: (E) -> R?): R? {

    forEach {
        return block(it) ?: return@forEach
    }

    return null
}

/**
 * Attempts to find a pair based on a value that isn't null, similar to [findValue] but returns the key aswell
 *
 * @param E The element type for the collection
 * @param R The return type for the value found
 * @param block Lambda for retrieving the value
 *
 * @return A pair of the key and value found
 */
fun <E, R> Collection<E>.findPair(block: (E) -> R?): Pair<E, R>? {

    forEach {
        return it to (block(it) ?: return@forEach)
    }

    return null
}