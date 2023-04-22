package github624

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonValue
import java.math.BigInteger

class Money(
  @field:JsonAlias("amountInCents")
  val amount: AmountInCents,
  val currency: Currency,
)

@JvmInline
value class AmountInCents(val value: BigInteger) {
  constructor(int: Int) : this(int.toBigInteger())
}

enum class Currency(@get:JsonValue val code: String) {
  AED(code = "AED"),
  SAR(code = "SAR"),
  Unspecified("");
}

@JvmInline
value class Savings(val value: Money) {
  inline val amount get() = value.amount
  inline val currency get() = value.currency
}
