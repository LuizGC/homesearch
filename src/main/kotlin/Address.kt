import org.example.City

interface Address {
    val street: String
    val number: String
    val city: City
}

data class BaseAddress(
    override val street: String,
    override val number: String,
    override val city: City
) : Address

data class Supermarket(
    override val street: String,
    override val number: String,
    override val city: City,
    val name: String
) : Address

fun createAddressIfNotBlank(
    street: String?,
    number: String?,
    cityName: String?,
    supermarket: Boolean,
    supermarketBrandName: String?
): Address? {
    if (listOf(street, number, cityName).any { it.isNullOrBlank() }) {
        return null
    }
    if (supermarket) {
        return Supermarket(street.orEmpty(), number.orEmpty(), City(cityName.orEmpty()), supermarketBrandName.orEmpty())
    }
    return BaseAddress(street.orEmpty(), number.orEmpty(), City(cityName.orEmpty()))
}