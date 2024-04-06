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
): Address

data class Supermarket(
    override val name: String,
    override val number: String,
    override val city: City,
    val brandName: String
): Address
