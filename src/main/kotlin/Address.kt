import org.example.City

interface Address {
    val name: String
    val number: String
    val city: City
}

data class BaseAddress(
    override val name: String,
    override val number: String,
    override val city: City
) : Address

data class Supermarket(
    override val name: String,
    override val number: String,
    override val city: City,
    val brandName: String
) : Address

fun createAddressIfNotBlank(
    name: String?,
    number: String?,
    cityName: String?,
    supermarket: Boolean,
    supermarketBrandName: String?
): Address? {
    if (listOf(name, number, cityName).any { it.isNullOrBlank() }) {
        return null
    }
    if (supermarket) {
        return Supermarket(name.orEmpty(), number.orEmpty(), City(cityName.orEmpty()), supermarketBrandName.orEmpty())
    }
    return BaseAddress(name.orEmpty(), number.orEmpty(), City(cityName.orEmpty()))
}