package github624

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.context.annotation.Import
import kotlin.test.assertNotNull

@JsonTest
@Import(JsonConfiguration::class)
class TestGithub624 {

  @Nested
  inner class SavingsTest(@Autowired private val mapper: JacksonTester<Savings>) {

    @Test
    fun testAmount() {
      val result = mapper.parse("""{"amount" : 1500, "currency": "AED"}""")
      assertNotNull(result.`object`) {
        assert(it.amount == AmountInCents(1500))
        assert(it.currency == Currency.AED)
      }
    }

    @Test
    fun testAmountInCents() {
      val result = mapper.parse("""{"amountInCents" : 1500, "currency": "AED"}""")
      assertNotNull(result.`object`) {
        assert(it.amount == AmountInCents(1500))
        assert(it.currency == Currency.AED)
      }
    }

    @Test
    fun testUnspecifiedCurrency() {
      val result = mapper.parse("""{"amountInCents" : "0", "currency": ""}""")
      assertNotNull(result.`object`) {
        assert(it.amount == AmountInCents(0))
        assert(it.currency == Currency.Unspecified)
      }
    }
  }
}
